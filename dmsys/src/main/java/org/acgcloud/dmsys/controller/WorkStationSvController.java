package org.acgcloud.dmsys.controller;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.NonNull;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.friday.utils.AuthConstant;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.nrocn.lib.utils.BaseFileUtils;
import org.nrocn.user.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.acgcloud.common.dto.WebResponse;
import org.acgcloud.dmsys.dto.FileDomainAndWkstationResponse;
import org.acgcloud.dmsys.dto.FileDomainRequest;
import org.acgcloud.dmsys.dto.FileListPageResponse;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


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


    @Async
    public TreeCloudFlodlerDomain cloudFolderTree(Long id,Integer isAllFile){
        CloudFlodlerDomain pCloudFlodlerDomain = cloudFlodlerDomainService.getById(id);
        if(pCloudFlodlerDomain.getIsDel() > 0){
            return  null;
        }

        QueryWrapper<CloudFlodlerDomain> queryWrapper = new QueryWrapper<CloudFlodlerDomain>()
                .select(CloudFlodlerDomain.COL_ID,
                        CloudFlodlerDomain.COL_NAME, CloudFlodlerDomain.COL_HASH_NAME, CloudFlodlerDomain.COL_LOGIC_PATH,
                        CloudFlodlerDomain.COL_ISFILE, CloudFlodlerDomain.COL_CATALOG_ID, CloudFlodlerDomain.COL_PARENT_ID,
                        CloudFlodlerDomain.COL_PARENT_ID, CloudFlodlerDomain.COL_FILESYS)
                .eq(CloudFlodlerDomain.COL_CATALOG_ID, pCloudFlodlerDomain.getCatalogId())
                .like(CloudFlodlerDomain.COL_LOGIC_PATH, pCloudFlodlerDomain.getLogicPath() + "%")
                .eq(CloudFlodlerDomain.COL_UPLOADING,0)
                .eq(CloudFlodlerDomain.COL_IS_DEL, 0);
        if(isAllFile == 1){
            queryWrapper.eq(CloudFlodlerDomain.COL_ISFILE, false);
        }
        List<CloudFlodlerDomain> list = cloudFlodlerDomainService.list(queryWrapper);
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
        treeCloudFlodlerDomain.setCurrInt(1L);
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
        treeCloudFlodlerDomain.setLabel(flodler.getName());
        for (CloudFlodlerDomain cloudFlodlerDomain : cloudFlodlerDomains) {
            if (fileMap.get(cloudFlodlerDomain.getId()) == null) {
                if (cloudFlodlerDomain.getIsfile()) {
                       treeCloudFlodlerDomain.addCloudFloaderDomains(cloudFlodlerDomain);
                }
                else {
                    TreeCloudFlodlerDomain finalSub = new TreeCloudFlodlerDomain();
                    finalSub.setCurrInt((treeCloudFlodlerDomain.getCurrInt() + 1));
                    finalSub.setCloudFlodlerDomain(cloudFlodlerDomain);
                    finalSub.setLabel(cloudFlodlerDomain.getName());
                    treeCloudFlodlerDomain.addTreeCloudFloaderDomains(finalSub);
                }
                continue;
            }
            TreeCloudFlodlerDomain subTree = new TreeCloudFlodlerDomain();
            treeCloudFlodlerDomain.addTreeCloudFloaderDomains(subTree);
            subTree.setCurrInt((treeCloudFlodlerDomain.getCurrInt() + 1));
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
                .eq(CloudFlodlerDomain.COL_UPLOADING,0)
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

    private List doFindNameInFileById(Integer id,String name,Integer isFile,Integer order){
        //根据id查询出文件夹
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getById(id);
        if(ObjectUtils.isEmpty(cloudFlodlerDomain)){
            throw new IllegalArgumentException("没有该文件");
        }
        String logicPath = cloudFlodlerDomain.getLogicPath() + "/" + cloudFlodlerDomain.getName();
        QueryWrapper<CloudFlodlerDomain> cloudFlodlerWrapper
                = new QueryWrapper<>();
        cloudFlodlerWrapper.select().likeRight(CloudFlodlerDomain.COL_LOGIC_PATH,logicPath);
        if(!ObjectUtils.isEmpty(name)){
            QueryWrapper<CloudFlodlerDomain> parentWrapper = cloudFlodlerWrapper.eq(CloudFlodlerDomain.COL_NAME, name)
                    .eq(CloudFlodlerDomain.COL_IS_DEL, 0);
            List<Long> pIds = cloudFlodlerDomainService.list(parentWrapper)
                    .stream().map(v -> v.getId()).collect(Collectors.toList());
            cloudFlodlerWrapper  = new QueryWrapper<CloudFlodlerDomain>().in(CloudFlodlerDomain.COL_PARENT_ID,pIds);
        }
        if(isFile  > -1){
            cloudFlodlerWrapper.eq(CloudFlodlerDomain.COL_ISFILE,isFile);
        }
        if(order > 0) {
            cloudFlodlerWrapper.orderByDesc(CloudFlodlerDomain.COL_CREATE_TIME);
        }
        return cloudFlodlerDomainService.list(cloudFlodlerWrapper
                .eq(CloudFlodlerDomain.COL_UPLOADING,0)
                .eq(CloudFlodlerDomain.COL_IS_DEL,0));
    }

    //查询响应目录下的所有文件 0 仅文件夹 1 仅文件  -1 所有文件
    @RequestMapping("/find/in/{id}")
    public Object doRespQueryFile(@NonNull @PathVariable Integer id
            , @RequestParam(required = false) String name
            , @RequestParam(defaultValue = "-1") Integer need
            , @RequestParam(defaultValue = "0") Integer order
            , @RequestParam(defaultValue = "0") Integer current
            , @RequestParam(defaultValue = "0") Integer size){
        List list = doFindNameInFileById(id, name, need, order);
        List<CloudFlodlerDomain> page = ListUtil.page( current > 0 ? current - 1:0 , size > 0?size:list.size(), list);
        FileListPageResponse fileListPageResponse = new FileListPageResponse(list.size(), page);
        return fileListPageResponse;
    }




    private List<CloudFlodlerDomain> doQueryFloadleById(String parentId,Integer order,Integer isFile){
        QueryWrapper<CloudFlodlerDomain> cloudFlodlerDomains = new QueryWrapper<CloudFlodlerDomain>().select(CloudFlodlerDomain.COL_ID,
                CloudFlodlerDomain.COL_NAME, CloudFlodlerDomain.COL_HASH_NAME, CloudFlodlerDomain.COL_LOGIC_PATH,
                CloudFlodlerDomain.COL_ISFILE, CloudFlodlerDomain.COL_CATALOG_ID, CloudFlodlerDomain.COL_PARENT_ID,
                CloudFlodlerDomain.COL_PARENT_ID, CloudFlodlerDomain.COL_FILESYS)
                .eq(CloudFlodlerDomain.COL_UPLOADING,0)
                .eq(CloudFlodlerDomain.COL_IS_DEL, 0)
                .eq(CloudFlodlerDomain.COL_PARENT_ID,parentId);
        if(!ObjectUtils.isEmpty(isFile)){
            cloudFlodlerDomains.eq(CloudFlodlerDomain.COL_ISFILE,isFile);
        }
        if(order > 0) {
            cloudFlodlerDomains.orderByDesc(CloudFlodlerDomain.COL_CREATE_TIME);
        }
        return   cloudFlodlerDomainService.list(cloudFlodlerDomains);
    }

    @RequestMapping("/{parentId}/list")
    public IMicroResponsable queryFileByParentId(@PathVariable String parentId
            , @RequestParam(defaultValue = "0") Integer order
            , @RequestParam(required = false) Integer need){
        List<CloudFlodlerDomain> list = doQueryFloadleById(parentId , order , need);
        return WebResponse.getPrototype().successResp("搜索成功",list);
    }





    @RequestMapping("/{parentId}/list/page/{current}/{size}")
    public IMicroResponsable queryFileByParentIdPage(@PathVariable String parentId
                                                    , @RequestParam(required = false) Integer need
                                                    , @PathVariable Integer current
                                                    , @PathVariable Integer size
                                                    , @RequestParam(defaultValue = "0") Integer order){
        List<CloudFlodlerDomain> list = doQueryFloadleById(parentId, order , need);
        List<CloudFlodlerDomain> page = ListUtil.page( current - 1 , size, list);
        FileListPageResponse fileListPageResponse = new FileListPageResponse(list.size(), page);

        return WebResponse.getPrototype().successResp("搜索成功",fileListPageResponse);
    }


    //查询所有文件夹做成文件树
    @RequestMapping("/tree/my/workstation/{floadlerId}")
    public IMicroResponsable myWorkStationByTree(@PathVariable Long floadlerId, @RequestParam(required = false,defaultValue = "0") Integer isAllFile){
        TreeCloudFlodlerDomain treeCloudFlodlerDomain = cloudFolderTree(floadlerId,isAllFile);
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

    private List<CloudFlodlerDomain> doFileCreate(@RequestBody  WorkStationDomainRequest wk ,CloudFlodlerDomain cloudFlodlerDomain){
        //创建列表空间
        List<CloudFlodlerDomain> cloudFlodlerDomains = new ArrayList<>();
        List<String> hashNames =new ArrayList<>();
        //文件名hash值计算
        for (String fileName : wk.getFileNames()) {
            CloudFlodlerDomain newFile = new CloudFlodlerDomain();
            BeanUtils.copyProperties(cloudFlodlerDomain,newFile);
            newFile.setLogicPath(cloudFlodlerDomain.getLogicPath() + "/" + cloudFlodlerDomain.getName());
            newFile.setParentId(cloudFlodlerDomain.getId());
            newFile.setId(null);
            newFile.setCreateTime(new Date());
            newFile.setModifiedTime(new Date());
            String hashFileName = DigestUtil.md5Hex(wk.getFileName() + UUID.fastUUID() + DateUtil.now());
            newFile.setName(fileName);
            newFile.setHashName(hashFileName);
            if(wk.getIsFile()){
                String fileSuffix = BaseFileUtils.getFileSuffix(fileName, false);
                newFile.setSuffix(fileSuffix);
                //fixme 统一格式
                newFile.setIsfile(wk.getIsFile());
                if(!ObjectUtils.isEmpty(wk.getUpload())){
                    newFile.setUploading(wk.getUpload());
                }
            }
            cloudFlodlerDomains.add(newFile);
            hashNames.add(hashFileName);
        }
        cloudFlodlerDomainService.saveBatch(cloudFlodlerDomains);
        return cloudFlodlerDomainService.list(new QueryWrapper<CloudFlodlerDomain>().in(CloudFlodlerDomain.COL_HASH_NAME, hashNames));
    }



    //上传/创建文件夹
    @RequestMapping("/push/my/workstation")
    public Object doRespFileUpload(@RequestBody  WorkStationDomainRequest wk){
//        //获取工作空间路径，设置默认文件夹路径
        WkstationEntity wkstationEntity = jpaRepositoryServices.findWorkStationById(wk.getId());
        if(ObjectUtils.isEmpty(wkstationEntity)){
            return WebResponse.getPrototype().failedResp("工作空间不存在", ResultCode.NOT_FOUND);
        }
        if(wk.getCurrFolderId() == null){
            //默认工作空间路径
            wk.setCurrFolderId(wkstationEntity.getCloudFlodlerEntity().getId());
        }
        //如果文件夹不为空直接拿文件夹
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getById(wk.getCurrFolderId());

        if(ObjectUtils.isEmpty(cloudFlodlerDomain)){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND);
        }
        assert wk.getFileName() != null:"fileName 为空";
        String[] fileNames = new String[]{wk.getFileName()};
        wk.setFileNames(fileNames);
        return doFileCreate(wk, cloudFlodlerDomain);
    }




    //上传多文件夹
    @RequestMapping("/files/push/my/workstation")
    public Object doRespMulfileUpload(@RequestBody  WorkStationDomainRequest wk){
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
        return doFileCreate(wk,cloudFlodlerDomain);
    }




    //上传/创建文件夹
    @RequestMapping("/file/{id}/rename")
    public IMicroResponsable rename(@PathVariable Integer id , @RequestBody FileDomainRequest fileDomainRequest){
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getById(id);
        if(ObjectUtils.isEmpty(cloudFlodlerDomain)||cloudFlodlerDomain.getIsDel() > 0){
            return WebResponse.getPrototype().failedResp("未找到文件", ResultCode.FAILURE);
        }
        cloudFlodlerDomain.setName(fileDomainRequest.getName());
        if(cloudFlodlerDomain.getIsfile()){
            String fileSuffix = BaseFileUtils.getFileSuffix(fileDomainRequest.getName(), false);
            cloudFlodlerDomain.setSuffix(fileSuffix);
        }
        cloudFlodlerDomainService.updateById(cloudFlodlerDomain);
        return  WebResponse.getPrototype().successResp("修改成功",cloudFlodlerDomain);
    }


    @RequestMapping("/files/push/my/workstation/finish")
    public Object doRespFinishUpload(@RequestBody WorkStationDomainRequest wkresq){
        List<Long> ids = wkresq.getIds();
        if(ObjectUtils.isEmpty(ids)){
            return WebResponse.getPrototype().failedResp("文件缺失", ResultCode.NOT_FOUND);
        }
        boolean update = cloudFlodlerDomainService.update(new UpdateWrapper<CloudFlodlerDomain>().set(CloudFlodlerDomain.COL_UPLOADING, 0)
                .in(CloudFlodlerDomain.COL_ID, ids).eq(CloudFlodlerDomain.COL_IS_DEL, 0).eq(CloudFlodlerDomain.COL_UPLOADING, 1));
        return update;
    }



    @RequestMapping("/file/{id}/modified")
    public IMicroResponsable modified(@PathVariable Integer id , @RequestBody FileDomainRequest fileDomainRequest){
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getById(id);
        if(ObjectUtils.isEmpty(cloudFlodlerDomain)||cloudFlodlerDomain.getIsDel() > 0){
            return WebResponse.getPrototype().failedResp("未找到文件", ResultCode.FAILURE);
        }
        if(fileDomainRequest.getName() != null){
            cloudFlodlerDomain.setName(fileDomainRequest.getName());
        }
        if(cloudFlodlerDomain.getIsfile()){
            String fileSuffix = BaseFileUtils.getFileSuffix(fileDomainRequest.getName(), false);
            cloudFlodlerDomain.setSuffix(fileSuffix);
        }
        if(fileDomainRequest.getParentId()!= null){
            CloudFlodlerDomain pFile = cloudFlodlerDomainService.getById(id);
            cloudFlodlerDomain.setParentId(pFile.getParentId());
            cloudFlodlerDomain.setLogicPath(pFile.getLogicPath());
            cloudFlodlerDomain.setCatalogId(pFile.getCatalogId());
        }
        if(fileDomainRequest.getHashName() != null){
            cloudFlodlerDomain.setHashName(fileDomainRequest.getHashName());
        }
        if(fileDomainRequest.getIsDel() != null){
            cloudFlodlerDomain.setIsDel(fileDomainRequest.getId());
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


    @RequestMapping("/datas/rm")
    public Object doRespRmData(@RequestBody WorkStationDomainRequest wkresq){
        List<Long> ids = wkresq.getIds();
        if(ObjectUtils.isEmpty(ids)){
            return WebResponse.getPrototype().failedResp("文件缺失", ResultCode.NOT_FOUND);
        }
        boolean remove = cloudFlodlerDomainService.remove(new QueryWrapper<CloudFlodlerDomain>().in(CloudFlodlerDomain.COL_ID, ids));
        return remove;
    }





}
