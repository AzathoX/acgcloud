package org.acgcloud.dmsys.controller;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.acgcloud.dmsys.dto.FileDomainAndWkstationResponse;
import org.acgcloud.dmsys.dto.FileDomainRequest;
import org.acgcloud.dmsys.dto.WorkStationDomainRequest;
import org.acgcloud.dmsys.entity.CloudFlodlerEntity;
import org.acgcloud.dmsys.entity.WkstationEntity;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;
import org.acgcloud.dmsys.model.TreeCloudFlodlerDomain;
import org.acgcloud.dmsys.model.WkstationDomain;
import org.acgcloud.dmsys.services.CloudFlodlerDomainService;
import org.acgcloud.dmsys.services.JpaRepositoryServices;
import org.acgcloud.dmsys.services.WkstationDomainService;
import org.acgcloud.dmsys.services.feign.IFileOptionServices;
import org.acgcloud.dmsys.utils.RequestUtils;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.friday.utils.AuthConstant;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.nrocn.lib.utils.BaseFileUtils;
import org.nrocn.user.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.acgcloud.common.dto.WebResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/role/dmsys/wk/svc")
public class WorkStationSvController {

    @Autowired
    private JpaRepositoryServices jpaRepositoryServices;

    @Autowired
    private IFileOptionServices fileOptionServices;
    
    @Autowired
    private WkstationDomainService wkstationDomainService;

    @Autowired
    @Qualifier("httpServletRequest")
    private  HttpServletRequest req;

    @Autowired
    private MainSvController mainSvController;


    @Autowired
    private CloudFlodlerDomainService cloudFlodlerDomainService;


    private CloudFlodlerEntity flodlerEntity;


    public TreeCloudFlodlerDomain cloudFolderTree(Long id){
        //查询cloudFlodler 所有文件
//        List<CloudFlodlerDomain> list = cloudFlodlerDomainService.list(new QueryWrapper<CloudFlodlerDomain>().eq(CloudFlodlerDomain.,""));
        List<CloudFlodlerDomain> list = cloudFlodlerDomainService.list();
        if(ObjectUtils.isEmpty(list)){
            return null;
        }
        Map<Long,List<CloudFlodlerDomain>> map = new HashMap<>();
        Map<Long,CloudFlodlerDomain> idMap = new HashMap<>();
        //对父子关系的文件进行归类
        list.forEach(action ->{
            Long parentId = action.getParentId();
            List<CloudFlodlerDomain> cloudFlodlerDomains = map.get(parentId);
            if(ObjectUtils.isEmpty(cloudFlodlerDomains)){
                cloudFlodlerDomains = new ArrayList<>();
                map.put(parentId,cloudFlodlerDomains);

            }
            cloudFlodlerDomains.add(action);
            idMap.put(action.getId(),action);
        });


        List<CloudFlodlerDomain> cloudFlodlerDomains = map.get(id);
        if(cloudFlodlerDomains == null){
            return null;
        }
        TreeCloudFlodlerDomain treeCloudFlodlerDomain = new TreeCloudFlodlerDomain();
        genericFlodlerTree(map,idMap,id,treeCloudFlodlerDomain);
        return  treeCloudFlodlerDomain;
    }

    private static void genericFlodlerTree(Map<Long,List<CloudFlodlerDomain>> fileMap, Map<Long,CloudFlodlerDomain> idMap,
                                   Long id,
                                   TreeCloudFlodlerDomain treeCloudFlodlerDomain){
        treeCloudFlodlerDomain.setFlodlerId(id);
        List<CloudFlodlerDomain> cloudFlodlerDomains = fileMap.get(id);
        CloudFlodlerDomain flodler = idMap.get(id);
        treeCloudFlodlerDomain.setCloudFlodlerDomain(flodler);
        for (CloudFlodlerDomain cloudFlodlerDomain : cloudFlodlerDomains) {
            if (fileMap.get(cloudFlodlerDomain.getId()) == null) {
                if (cloudFlodlerDomain.getIsfile()) {
                       treeCloudFlodlerDomain.addCloudFloaderDomains(cloudFlodlerDomain);
                }
                else {
                    TreeCloudFlodlerDomain finalSub = new TreeCloudFlodlerDomain();
                    finalSub.setCloudFlodlerDomain(cloudFlodlerDomain);
                    treeCloudFlodlerDomain.addTreeCloudFloaderDomains(finalSub);
                }
                continue;
            }
            TreeCloudFlodlerDomain subTree = new TreeCloudFlodlerDomain();
            treeCloudFlodlerDomain.addTreeCloudFloaderDomains(subTree);
            genericFlodlerTree(fileMap,idMap,cloudFlodlerDomain.getId(),subTree);
        }
    }




    public Map<Long, FileDomainAndWkstationResponse>  doMyWorkStationByTreeMap(Long id){
        //1.获取列表
        WkstationDomain wkstationDomain = wkstationDomainService.getById(id);
        List<CloudFlodlerDomain> cloudFlodlerList =
              cloudFlodlerDomainService.list(new QueryWrapper<CloudFlodlerDomain>().select(CloudFlodlerDomain.COL_ID,
                CloudFlodlerDomain.COL_NAME, CloudFlodlerDomain.COL_HASH_NAME, CloudFlodlerDomain.COL_LOGIC_PATH,
                CloudFlodlerDomain.COL_ISFILE, CloudFlodlerDomain.COL_CATALOG_ID, CloudFlodlerDomain.COL_PARENT_ID,
                CloudFlodlerDomain.COL_PARENT_ID, CloudFlodlerDomain.COL_FILESYS)
                .like(CloudFlodlerDomain.COL_LOGIC_PATH,wkstationDomain.getLogicPath() +  "%")
                .eq(CloudFlodlerDomain.COL_IS_DEL, 0));
        //循环列表,进行结构化排列
        Map<Long, FileDomainAndWkstationResponse> map = new HashMap<>();
        cloudFlodlerList.stream()
                .forEach(action -> {
                    Long parentId = action.getParentId();
                    if(ObjectUtils.isEmpty(map.get(parentId))){
                        FileDomainAndWkstationResponse fileDomainAndWkstationResponse = new FileDomainAndWkstationResponse();
                        fileDomainAndWkstationResponse.setParentId(action.getParentId());
                        map.put(parentId,fileDomainAndWkstationResponse);
                    }
                    FileDomainAndWkstationResponse fileDomainAndWkstationResponse = map.get(parentId);
                    fileDomainAndWkstationResponse.addCloudFloderAndIncludeId(action.getId(),action);
                });
         return map;
    }


    //查询文件不进行递归
    @RequestMapping("/tree/map/{wkId}")
    public IMicroResponsable myWorkStationByTreeMap(@PathVariable Long wkId){
        //查询所有文件
        Map<Long, FileDomainAndWkstationResponse> map = doMyWorkStationByTreeMap(wkId);
        return  WebResponse.getPrototype().successResp(Objects.nonNull(map)?
                   "搜索成功":"搜索失败",map);
    }



    private List<CloudFlodlerDomain> doQueryFloadleById(String parentId,Integer order){
        QueryWrapper<CloudFlodlerDomain> cloudFlodlerDomains = new QueryWrapper<CloudFlodlerDomain>().select(CloudFlodlerDomain.COL_ID,
                CloudFlodlerDomain.COL_NAME, CloudFlodlerDomain.COL_HASH_NAME, CloudFlodlerDomain.COL_LOGIC_PATH,
                CloudFlodlerDomain.COL_ISFILE, CloudFlodlerDomain.COL_CATALOG_ID, CloudFlodlerDomain.COL_PARENT_ID,
                CloudFlodlerDomain.COL_PARENT_ID, CloudFlodlerDomain.COL_FILESYS)
                .eq(CloudFlodlerDomain.COL_PARENT_ID, parentId).eq(CloudFlodlerDomain.COL_IS_DEL, 0);
        if(order > 0) {
            cloudFlodlerDomains.orderByDesc(CloudFlodlerDomain.COL_CREATE_TIME);
        }
        return   cloudFlodlerDomainService.list(cloudFlodlerDomains);
    }

    @RequestMapping("/{parentId}/list")
    public IMicroResponsable queryFileByParentId(@PathVariable String parentId,@RequestParam(defaultValue = "0") Integer order){
        List<CloudFlodlerDomain> list = doQueryFloadleById(parentId, order);
        return WebResponse.getPrototype().successResp("搜索成功",list);
    }





    @RequestMapping("/{parentId}/list/page/{current}/{size}")
    public IMicroResponsable queryFileByParentIdPage(@PathVariable String parentId,@PathVariable Integer current ,
                                                     @PathVariable Integer size ,
                                                     @RequestParam(defaultValue = "0") Integer order){
        List<CloudFlodlerDomain> list = doQueryFloadleById(parentId, order);
        List<CloudFlodlerDomain> page = ListUtil.page( current - 1 , size, list);
        return WebResponse.getPrototype().successResp("搜索成功",page);
    }


    //查询所有文件夹做成文件树
    @RequestMapping("/tree/my/workstation/{floadlerId}")
    public IMicroResponsable myWorkStationByTree(@PathVariable Long floadlerId){
        TreeCloudFlodlerDomain treeCloudFlodlerDomain = cloudFolderTree(floadlerId);
        if(treeCloudFlodlerDomain == null){
            return WebResponse.getPrototype().failedResp("文件夹不存在", ResultCode.NOT_FOUND);
        }
        return WebResponse.getPrototype().successResp("查询成功", treeCloudFlodlerDomain);

    }




    //查询用户工作所有空间
    @RequestMapping("/select/my/workstation")
    public IMicroResponsable selectMyWorkStation(){
        FridaySession fridaySession = (FridaySession)req.getAttribute(AuthConstant.SESSION_NAME);
        UserEntity userEntity = jpaRepositoryServices.findByUserName(fridaySession.getUserId());
        List<WkstationEntity> list = jpaRepositoryServices.findWorkStationByUserId(userEntity.getId());
        return WebResponse.getPrototype().successResp("完成搜索",list);
    }

    //进入到工作空间
    @RequestMapping("/cd/my/workstation/{id}/**")
    public IMicroResponsable myworkStation(@PathVariable Long id){
        //获取工作空间路径
        WkstationEntity wkstationEntity = jpaRepositoryServices.findWorkStationById(id);
        CloudFlodlerEntity cloudFlodlerEntity = wkstationEntity.getCloudFlodlerEntity();
        if(ObjectUtils.isEmpty(wkstationEntity)){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND);
        }
        //拿到路径
        String variablePath = RequestUtils.getVariablePath(req);
        String basePath = cloudFlodlerEntity.getLogicPath() + "/" + cloudFlodlerEntity.getName();
        String logicPath =  basePath;
        if(variablePath != null && variablePath.length() > 0 ){
            logicPath =  basePath + "/" + variablePath;
        }
        //查询当前目录下的所有对象
        List<CloudFlodlerDomain> cloudFlodlerDomains = cloudFlodlerDomainService.list(new QueryWrapper<CloudFlodlerDomain>().eq(CloudFlodlerDomain.COL_LOGIC_PATH,  logicPath)
                .eq(CloudFlodlerDomain.COL_FILESYS,cloudFlodlerEntity.getFilesys()).eq(CloudFlodlerDomain.COL_IS_DEL, 0));
        return  WebResponse.getPrototype().successResp(variablePath,cloudFlodlerDomains);
    }



    //上传/创建文件夹
    @RequestMapping("/push/my/workstation")
    public IMicroResponsable fileUpload(@RequestBody WorkStationDomainRequest wk){
//        //获取工作空间路径
        WkstationEntity wkstationEntity = jpaRepositoryServices.findWorkStationById(wk.getId());
        if(ObjectUtils.isEmpty(wkstationEntity)){
            return WebResponse.getPrototype().failedResp("工作空间不存在", ResultCode.NOT_FOUND);
        }
        if(wk.getCurrFolderId() == null){
            //默认工作空间路径
            wk.setCurrFolderId(wkstationEntity.getCloudFlodlerEntity().getId());
        }
        //拿到路径
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getById(wk.getCurrFolderId());

        if(ObjectUtils.isEmpty(cloudFlodlerDomain)){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND);
        }
        CloudFlodlerDomain newFile = new CloudFlodlerDomain();
        //文件名hash值计算
        BeanUtils.copyProperties(cloudFlodlerDomain,newFile);
        newFile.setLogicPath(cloudFlodlerDomain.getLogicPath() + "/" + cloudFlodlerDomain.getName());
        String fileName = wk.getFileName();
        String hashFileName = DigestUtil.md5Hex(wk.getFileName() + UUID.fastUUID() + DateUtil.now());
        newFile.setParentId(cloudFlodlerDomain.getId());
        newFile.setId(null);
        newFile.setName(fileName);
        newFile.setHashName(hashFileName);
        newFile.setCreateTime(new Date());
        newFile.setModifiedTime(new Date());
        if(wk.getIsFile()){
            String fileSuffix = BaseFileUtils.getFileSuffix(fileName, false);
            newFile.setSuffix(fileSuffix);
            newFile.setIsfile(wk.getIsFile());
        }
        boolean save = cloudFlodlerDomainService.save(newFile);
        CloudFlodlerDomain one = cloudFlodlerDomainService.getOne(new QueryWrapper<CloudFlodlerDomain>().eq(CloudFlodlerDomain.COL_HASH_NAME, hashFileName));
        return WebResponse.getPrototype().successResp("添加成功",one);
    }


    //上传/创建文件夹
    @RequestMapping("/file/{id}/rename")
    public IMicroResponsable rename(@PathVariable Integer id , @RequestBody FileDomainRequest fileDomainRequest){
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getById(id);
        if(ObjectUtils.isEmpty(cloudFlodlerDomain)||cloudFlodlerDomain.getIsDel() > 0){
            return WebResponse.getPrototype().failedResp("未找到文件",ResultCode.FAILURE);
        }
        cloudFlodlerDomain.setName(fileDomainRequest.getName());
        if(cloudFlodlerDomain.getIsfile()){
            String fileSuffix = BaseFileUtils.getFileSuffix(fileDomainRequest.getName(), false);
            cloudFlodlerDomain.setSuffix(fileSuffix);
        }
        cloudFlodlerDomainService.updateById(cloudFlodlerDomain);
        return  WebResponse.getPrototype().successResp("修改成功",cloudFlodlerDomain);
    }

    //删除文件夹
    //删除文件
    @RequestMapping("/file/del")
    public IMicroResponsable fileDel(@RequestBody FileDomainRequest fileDomainRequest){
        // todo
        long id = (long)fileDomainRequest.getId();
        CloudFlodlerDomain cloudFlodlerDomain = new CloudFlodlerDomain();
        cloudFlodlerDomain.setIsDel(id);
        cloudFlodlerDomain.setId(id);
        boolean update = cloudFlodlerDomainService.updateById(cloudFlodlerDomain);
        return WebResponse.getPrototype().successResp(update?"删除成功":"删除失败",cloudFlodlerDomain.getName());
    }




}
