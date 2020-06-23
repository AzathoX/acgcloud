package org.acgcloud.filesys.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.nrocn.lib.baseobj.RelativeFileObj;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.nrocn.lib.utils.BaseFileUtils;
import org.nrocn.lib.utils.BaseIOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.acgcloud.common.dto.WebResponse;
import org.acgcloud.common.utils.RequestUtils;
import org.acgcloud.filesys.config.FileSys;
import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.dto.RelativeFileResponse;
import org.acgcloud.filesys.services.IFileOptStragy;
import org.acgcloud.filesys.services.IFileOptionServices;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/filesys/main")
@CrossOrigin(allowCredentials="true",origins="*",maxAge = 3600)
public class MainSvController  {


    @Autowired
    @Qualifier("httpServletResponse")
    private HttpServletResponse resp;


    @Autowired
    private IFileOptionServices fileOptionServices;



    private static final String ACGCLOUD = "/static/acgcloud" ;

    private static final String FILE_CONTEXT_PATH = MainSvController.class.getClassLoader()
            .getResource("").getPath();


    @Autowired
    private FileSys fileSys;

    @PostConstruct
    public void init(){
        this.mapFile("",true);
    }


    private  File mapFile(String path,boolean create){
        String basePath = FILE_CONTEXT_PATH + ACGCLOUD;
        if(ObjectUtils.isNotEmpty(fileSys.getPath())){
            basePath = fileSys.getPath();
        }
        basePath += ("/" + path);
        File file = BaseFileUtils.find(basePath);
        if(ObjectUtils.isEmpty(file) && create){
            file = BaseFileUtils.mkdir(basePath);
        }
        return file;
    }

    private String getVariablePath(HttpServletRequest req){
        return RequestUtils.requestVariablePath(req);
    }

    //模块标识
    @RequestMapping("/model/info")
    public IMicroResponsable modelInfo(){
        return WebResponse.getPrototype().successResp("filesys",null);
    }


    //操作文件对象的方式
    @RequestMapping("/cloud/opt/obj/**")
    public IMicroResponsable cloudOpt(FileOptionRequest fileOptionRequest,HttpServletRequest req){
        final String path = getVariablePath(req);
        fileOptionRequest.setPath(path);
        return doOption(fileOptionRequest);
    }



    //操作文件
    @RequestMapping("/cloud/opt/**")
    public IMicroResponsable cloudOpt(@RequestParam String optional,
                                      @RequestParam String fileName,
                                      @RequestParam(defaultValue = "true") Boolean isFile,
                                      @RequestParam(required = false) MultipartFile multipartFile,
                                      HttpServletRequest req){
        final String path = getVariablePath(req);
        FileOptionRequest fileOptionRequest = new FileOptionRequest();
        fileOptionRequest.setPath(path);
        fileOptionRequest.setOpt(optional);
        fileOptionRequest.setIsFile(isFile);
        fileOptionRequest.setMultipartFile(multipartFile);
        fileOptionRequest.setFileName(fileName);
        return doOption(fileOptionRequest);
    }

    @RequestMapping("/cloud/do/option")
    public IMicroResponsable doOption(FileOptionRequest fileOptionRequest){
        if(StringUtils.isAllBlank(fileOptionRequest.getFileName(),fileOptionRequest.getPath(),fileOptionRequest.getOpt())){
            return WebResponse.getPrototype().failedResp("操作错误缺少参数", ResultCode.NOT_FOUND);
        }
        final String path =  fileOptionRequest.getPath();
        File file = mapFile(path,false);
        //不存在文件 404
        if(ObjectUtils.isEmpty(file)){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND,path);
        }
        fileOptionRequest.setFile(new File(file.getAbsolutePath() + "/" + fileOptionRequest.getFileName()));
        IFileOptStragy bean = fileOptionServices.doService(fileOptionRequest, fileOptionRequest.getOpt());
        bean.setFileOptionRequest(null);
        bean.setFile(null);
        return WebResponse.getPrototype().successResp(bean != null?bean.getOptCn():"操作失败",bean);
    }

    //获取文件列表
    @GetMapping("/cloud/list/**")
    public IMicroResponsable cloudList(HttpServletRequest req){
        final String path = getVariablePath(req);
        File file = mapFile(path,false);
        //不存在文件 404
        if(ObjectUtils.isEmpty(file)){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND,path);
        }
        try {
            //文件夹
            List<RelativeFileObj> filelist = BaseFileUtils.ls(file.getAbsolutePath());
            int liof = path.lastIndexOf("/");
            String parentPath = path.substring(0,liof > 0 ? liof:0);
            RelativeFileResponse relativeFileResponse = new RelativeFileResponse(parentPath,filelist);
            relativeFileResponse.setCurrent(StringUtils.isBlank(path)?file.getName() + "/":path);
            return WebResponse.getPrototype().successResp("文件列表",relativeFileResponse);
        } catch (Exception e) {
            return WebResponse.getPrototype().failedResp("文件列表", ResultCode.FAILURE,path);
        }
    }


    //操作文件
    @RequestMapping("/cloud/exists/**")
    public IMicroResponsable cloudFileExists(@RequestParam String fileName,
                                      HttpServletRequest req){
        final String path = getVariablePath(req);
        File file = mapFile(path + "/" +fileName ,false);
        return  WebResponse.getPrototype().successResp("文件" +(ObjectUtils.isEmpty(file)?"存在":"不存在"),file.exists());
    }



    //文件下载
    @RequestMapping("/cloud/file/download/**")
    public void cloudFileDown(@RequestParam String newFileName,
                              HttpServletRequest req) throws IOException {
        final String path = getVariablePath(req);
        File file = mapFile(path,false);
        if(ObjectUtils.isEmpty(file)){
            BaseIOUtils.httpHtmlWriteResponse(resp,"该路径文件不存在");
        }
        BaseIOUtils.httpDownloadFileResponse(file,newFileName,resp);
    }


}
