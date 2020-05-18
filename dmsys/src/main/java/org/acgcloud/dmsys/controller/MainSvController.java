package org.acgcloud.dmsys.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.acgcloud.dmsys.dto.FileDomainRequest;
import org.acgcloud.dmsys.entity.LogicCatalogEntity;
import org.acgcloud.dmsys.entity.PrartitionEntity;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;
import org.acgcloud.dmsys.model.LogicCatalogDomain;
import org.acgcloud.dmsys.model.PrartitionDomain;
import org.acgcloud.dmsys.model.WkstationDomain;
import org.acgcloud.dmsys.services.*;
import org.acgcloud.dmsys.services.feign.IFileOptionServices;
import org.acgcloud.dmsys.utils.RequestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.nrocn.lib.baserqnp.AbstractResponse;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.nrocn.lib.utils.BaseFileUtils;
import org.nrocn.user.model.UserDomain;
import org.nrocn.user.services.UserDomainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.acgcloud.common.dto.WebResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/dmsys/main")
@CrossOrigin("*")
public class MainSvController {


    @Autowired
    private IFileOptionServices fileOptionServices;


    @Autowired
    private JpaRepositoryServices jpaRepositoryServices;


    @Autowired
    private PrartitionDomainService prartitionDomainService;


    @Autowired
    private LogicCatalogDomainService logicCatalogDomainService;

    @Autowired
    private CloudFlodlerDomainService cloudFlodlerDomainService;

    @Autowired
    private WkstationDomainService wkstationDomainService;

    @Autowired
    private UserDomainService userDomainService;


    @Autowired
    @Qualifier("httpServletRequest")
    private HttpServletRequest req;


    //模块标识
    @RequestMapping("/model/info")
    public IMicroResponsable modelInfo(){
        return WebResponse.getPrototype().successResp("dmsys",null);
    }




    //申请物理分区 rpc
    @RequestMapping("/prarition/add")
    public IMicroResponsable praritionApplyFor(@RequestBody FileDomainRequest fileDomainRequest){
        PrartitionDomain prartitionDomain = prartitionDomainService.getOne(new QueryWrapper<PrartitionDomain>()
                .eq(PrartitionDomain.COL_VP_NAME, fileDomainRequest.getVpName()));
        fileDomainRequest.setIsFile(false);
        //存在同名
        if(ObjectUtils.allNotNull(prartitionDomain)){
            return WebResponse.getPrototype().failedResp("存在同名分区", ResultCode.FAILURE);
        }
        //计算文件名的hash值
        String fileName = DigestUtil.md5Hex(fileDomainRequest.getVpName() + UUID.fastUUID() + DateUtil.now());
        fileDomainRequest.setVpHashName(fileName);
        //通知远端的filesys接口创建文件夹分区
        AbstractResponse webResponse = fileOptionServices.cloudOpt(fileDomainRequest.getFilesys(),
                "mk", fileDomainRequest.getVpHashName(), false);
        if (!webResponse.isSuccess()) {
            return webResponse;
        }
        prartitionDomain = new PrartitionDomain();
        //分区成功将分区的结果保存到数据库
        BeanUtils.copyProperties(fileDomainRequest,prartitionDomain);
        prartitionDomain.setFilesys(fileDomainRequest.getFilesys());
        int insert = prartitionDomainService.insert(prartitionDomain);
        return WebResponse.getPrototype().successResp(insert > 0 ?"分区申请成功":"分区申请失败",prartitionDomain.getVpName());
    }

    //删除物理分区 rpc
    @RequestMapping("/prarition/del")
    public IMicroResponsable praritionRemove(@RequestBody FileDomainRequest fileDomainRequest){
        return  null;
    }


    //申请逻辑目录
    @RequestMapping("/catalog/add")
    public IMicroResponsable catalogApplyFor(@RequestBody FileDomainRequest fileDomainRequest){
        //查询分区
        PrartitionDomain prartitionDomain = prartitionDomainService.getOne(new QueryWrapper<PrartitionDomain>().eq(PrartitionDomain.COL_VP_HASH_NAME, fileDomainRequest.getVpHashName())
                .eq(PrartitionDomain.COL_FILESYS, fileDomainRequest.getFilesys()));
        if(ObjectUtils.isEmpty(prartitionDomain)){
            return WebResponse.getPrototype().failedResp("该分区不存在请前往创建",ResultCode.FAILURE);
        }
        //分区存在,找是否存在同名目录
        LogicCatalogDomain logicCatalogDomain = logicCatalogDomainService.getOne(new QueryWrapper<LogicCatalogDomain>()
                .eq(LogicCatalogDomain.COL_PRARTITION_ID, prartitionDomain.getId())
                .eq(LogicCatalogDomain.COL_CATALOG_NAME, fileDomainRequest.getCatalogName())
                .eq(true,LogicCatalogDomain.COL_IS_DEL,0));
        if(ObjectUtils.allNotNull(logicCatalogDomain)){
            return WebResponse.getPrototype().failedResp("存在同名目录",ResultCode.FAILURE);
        }
        //在当前分区中创建顶级目录
        logicCatalogDomain = new LogicCatalogDomain();
        String catalogName = DigestUtil.md5Hex(fileDomainRequest.getCatalogName() + UUID.fastUUID() + DateUtil.now());
        BeanUtils.copyProperties(fileDomainRequest,logicCatalogDomain);
        logicCatalogDomain.setPrartitionId(prartitionDomain.getId());
        logicCatalogDomain.setCatalogHashName(catalogName);
        boolean save = logicCatalogDomainService.save(logicCatalogDomain);
        return WebResponse.getPrototype().successResp(save?"目录申请成功":"目录申请失败",logicCatalogDomain.getCatalogName());
    }

    @RequestMapping("/catalog/list")
    public IMicroResponsable catalogList(){
        final List<LogicCatalogEntity> list = jpaRepositoryServices.cataloglist();
        return WebResponse.getPrototype().successResp("查询成功",list);
    }


    //修改目录
    @RequestMapping("/catalog/update")
    public IMicroResponsable catalogUpdate(@RequestBody FileDomainRequest fileDomainRequest){
        return  null;
    }


    //删除目录
    @RequestMapping("/catalog/del")
    public IMicroResponsable catalogDel(@RequestBody FileDomainRequest fileDomainRequest){
        return  null;
    }


    //查询文件
    @RequestMapping("/ls/{filesys}/**")
    public IMicroResponsable fileQuery(@PathVariable String filesys ,HttpServletRequest req){
        String variablePath = RequestUtils.getVariablePath(req);
        //查询当前目录下的所有对象
        List<CloudFlodlerDomain> cloudFlodlerDomains = cloudFlodlerDomainService.list(new QueryWrapper<CloudFlodlerDomain>().eq(CloudFlodlerDomain.COL_LOGIC_PATH, "/" + variablePath)
                .eq(CloudFlodlerDomain.COL_FILESYS,"/" + filesys).eq(CloudFlodlerDomain.COL_IS_DEL, 0));
        return  WebResponse.getPrototype().successResp(variablePath,cloudFlodlerDomains);
    }



    //创建文件
    @RequestMapping("/file/add")
    public IMicroResponsable fileAdd(@RequestBody FileDomainRequest fileDomainRequest){
        //根据cataloghash值查询到分区
        LogicCatalogEntity logicCatalogEntity = jpaRepositoryServices.findByCatalogName(fileDomainRequest.getCatalogName());
        if(ObjectUtils.isEmpty(logicCatalogEntity)){
            return WebResponse.getPrototype().failedResp("该分区或目录不存在请前往创建",ResultCode.FAILURE);
        }
        //文件真实路径 = 物理分区 路径 + 分区hash + 文件hash
        PrartitionEntity prartitionEntity = logicCatalogEntity.getPrartitionEntity();
        String filesys = prartitionEntity.getFilesys() + "/" + prartitionEntity.getVpHashName();
        fileDomainRequest.setFilesys(filesys);
        if(fileDomainRequest.getIsFile()){
            String fileSuffix = BaseFileUtils.getFileSuffix(fileDomainRequest.getName(), false);
            fileDomainRequest.setSuffix(fileSuffix);
        }
        //如果文件名重复
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getOne(new QueryWrapper<CloudFlodlerDomain>()
                .eq(CloudFlodlerDomain.COL_NAME, fileDomainRequest.getName())
                .eq(CloudFlodlerDomain.COL_PARENT_ID,fileDomainRequest.getParentId())
                .eq(CloudFlodlerDomain.COL_IS_DEL,0));
        if(ObjectUtils.allNotNull(cloudFlodlerDomain)){
            return WebResponse.getPrototype().failedResp("文件已存在",ResultCode.FAILURE);
        }
        //计算hash
        String fileName = DigestUtil.md5Hex(fileDomainRequest.getName() + UUID.fastUUID() + DateUtil.now());
        fileDomainRequest.setHashName(fileName);
        //计算当前逻辑路径
        String root =  "/" + logicCatalogEntity.getCatalogHashName();
        //存在
        if(fileDomainRequest.getIsRoot()){
            fileDomainRequest.setLogicPath(root);
            fileDomainRequest.setParentId(0L);
        }
        else{
            cloudFlodlerDomain = cloudFlodlerDomainService.getById(fileDomainRequest.getParentId());
            if(ObjectUtils.isEmpty(cloudFlodlerDomain)){
                return WebResponse.getPrototype().failedResp("父级文件不存在",ResultCode.FAILURE);
            }
            String logicPath = cloudFlodlerDomain.getLogicPath() + "/" + cloudFlodlerDomain.getName();
            fileDomainRequest.setLogicPath(logicPath);
        }
        cloudFlodlerDomain = new CloudFlodlerDomain();
        BeanUtils.copyProperties(fileDomainRequest,cloudFlodlerDomain);
        cloudFlodlerDomain.setIsfile(fileDomainRequest.getIsFile());
        if(cloudFlodlerDomain.getIsfile()){
            AbstractResponse response = fileOptionServices.cloudOpt(filesys, "mk", fileDomainRequest.getHashName(),true);
        }
        cloudFlodlerDomain.setCatalogId(logicCatalogEntity.getId());
        cloudFlodlerDomain.setParentId(fileDomainRequest.getParentId());
        cloudFlodlerDomain.setPrartitionId(prartitionEntity.getId());
        boolean save = cloudFlodlerDomainService.save(cloudFlodlerDomain);
        return WebResponse.getPrototype().successResp(save?"文件创建成功":"文件创建失败",cloudFlodlerDomain.getName());
    }

    //修改文件
    @RequestMapping("/file/update")
    public IMicroResponsable fileUpdate(@RequestBody FileDomainRequest fileDomainRequest){
        return  null;
    }


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

    //上传文件
    @RequestMapping("/file/upload")
    public IMicroResponsable fileUpload(FileDomainRequest fileDomainRequest){
        MultipartFile multipartFile = fileDomainRequest.getMultipartFile();
        if(ObjectUtils.isEmpty(multipartFile)||ObjectUtils.isEmpty(multipartFile.getOriginalFilename())){
            return WebResponse.getPrototype().failedResp("找不到文件,请确认是否上传",ResultCode.FAILURE);
        }
        fileDomainRequest.setName(multipartFile.getOriginalFilename());
        fileDomainRequest.setIsFile(true);

        //根据cataloghash值查询到分区
        LogicCatalogEntity logicCatalogEntity = jpaRepositoryServices.findByCatalogHashName(fileDomainRequest.getCatalogHashName());
        if(ObjectUtils.isEmpty(logicCatalogEntity)){
            return WebResponse.getPrototype().failedResp("该分区或目录不存在请前往创建",ResultCode.FAILURE);
        }
        //文件真实路径 = 物理分区 路径 + 分区hash + 文件hash
        PrartitionEntity prartitionEntity = logicCatalogEntity.getPrartitionEntity();
        String filesys = prartitionEntity.getFilesys() + "/" + prartitionEntity.getVpHashName();
        fileDomainRequest.setFilesys(filesys);
        if(fileDomainRequest.getIsFile()){
            String fileSuffix = BaseFileUtils.getFileSuffix(fileDomainRequest.getName(), false);
            fileDomainRequest.setSuffix(fileSuffix);
        }

        //如果文件名重复
        CloudFlodlerDomain cloudFlodlerDomain = cloudFlodlerDomainService.getOne(new QueryWrapper<CloudFlodlerDomain>()
                .eq(CloudFlodlerDomain.COL_NAME, fileDomainRequest.getName())
                .eq(CloudFlodlerDomain.COL_PARENT_ID,fileDomainRequest.getParentId())
                .eq(CloudFlodlerDomain.COL_IS_DEL,0));
        if(ObjectUtils.allNotNull(cloudFlodlerDomain)){
            return WebResponse.getPrototype().failedResp("文件已存在",ResultCode.FAILURE);
        }
        //计算hash
        String fileName = DigestUtil.md5Hex(fileDomainRequest.getName() + UUID.fastUUID() + DateUtil.now());
        fileDomainRequest.setHashName(fileName);
        //计算当前逻辑路径
        String root =  "/" + logicCatalogEntity.getCatalogHashName();
        //存在
        cloudFlodlerDomain = cloudFlodlerDomainService.getById(fileDomainRequest.getParentId());
        if(ObjectUtils.isEmpty(cloudFlodlerDomain)){
            return WebResponse.getPrototype().failedResp("父级文件不存在",ResultCode.FAILURE);
        }
        String logicPath = cloudFlodlerDomain.getLogicPath() + "/" + cloudFlodlerDomain.getName();
        fileDomainRequest.setLogicPath(logicPath);
        cloudFlodlerDomain = new CloudFlodlerDomain();
        BeanUtils.copyProperties(fileDomainRequest,cloudFlodlerDomain);
        AbstractResponse webResponse = fileOptionServices.cloudOptUpload(filesys, "upload", fileDomainRequest.getName(), true, multipartFile);
        if(webResponse.isSuccess()){
            cloudFlodlerDomain.setIsfile(fileDomainRequest.getIsFile());
            cloudFlodlerDomain.setCatalogId(logicCatalogEntity.getId());
            cloudFlodlerDomain.setPrartitionId(prartitionEntity.getId());
            boolean save = cloudFlodlerDomainService.save(cloudFlodlerDomain);
            return WebResponse.getPrototype().successResp(save?"文件上传成功":"文件上传成功",cloudFlodlerDomain.getName());
        }
        return webResponse;
    }

    //申请工作空间
    @RequestMapping("/mkstation/add")
    public IMicroResponsable mkstationAppFor(@RequestBody  FileDomainRequest fileDomainRequest){
        //判断逻辑文件夹是否存在
        CloudFlodlerDomain flodler = cloudFlodlerDomainService.getById(fileDomainRequest.getId());
        if(ObjectUtils.isEmpty(flodler)){
            return WebResponse.getPrototype().failedResp("申请失败,不存在该目录",ResultCode.NOT_FOUND);
        }
        //判断用户id
        UserDomain user = userDomainService.getById(fileDomainRequest.getUserId());
        if(ObjectUtils.isEmpty(user)){
            return WebResponse.getPrototype().failedResp("用户信息不正确",ResultCode.NOT_FOUND);
        }
        WkstationDomain one = wkstationDomainService.getOne(new QueryWrapper<WkstationDomain>().select(WkstationDomain.COL_ID)
                .eq(WkstationDomain.COL_USER_ID, user.getId()).eq(WkstationDomain.COL_FLODLER_ID, flodler.getId())
                .eq(WkstationDomain.COL_IS_DEL, 0));
        if(ObjectUtils.allNotNull(one)){
            return WebResponse.getPrototype().failedResp("该工作空间已申请过一次",ResultCode.NOT_FOUND);
        }
        //如果存在则写入
        WkstationDomain wkstationDomain = new WkstationDomain();
        wkstationDomain.setFlodlerId(flodler.getId());
        wkstationDomain.setUserId(user.getId());
        wkstationDomain.setLogicPath(flodler.getLogicPath());
        wkstationDomain.setIsAdmin(fileDomainRequest.getIsAdmin());
        boolean save = wkstationDomainService.save(wkstationDomain);
        return WebResponse.getPrototype().successResp(save?"工作空间申请成功":"工作空间申请失败",null);
    }
}
