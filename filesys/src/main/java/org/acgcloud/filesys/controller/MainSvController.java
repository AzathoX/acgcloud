package org.acgcloud.filesys.controller;

import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.dto.RelativeFileResponse;
import org.acgcloud.filesys.services.IFileOptStragy;
import org.acgcloud.filesys.services.IFileOptionServices;
import org.apache.commons.lang3.StringUtils;
import org.nrocn.lib.baseobj.RelativeFileObj;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.nrocn.lib.utils.BaseFileUtils;
import org.nrocn.lib.utils.BaseIOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.acgcloud.common.dto.WebResponse;


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



    private static final String ACGCLOUD = "/static/acgcloud";

    private static final String FILE_CONTEXT_PATH = MainSvController.class.getClassLoader()
            .getResource("").getPath();


    //解析路径
    private String getVariablePath(HttpServletRequest req){
        final String fullUrl =
                req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String firstUrl = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String path = new AntPathMatcher().extractPathWithinPattern(firstUrl, fullUrl);
        return  path;
    }

    //模块标识
    @RequestMapping("/model/info")
    public IMicroResponsable modelInfo(){
        return WebResponse.getPrototype().successResp("filesys",null);
    }


    //操作文件对象的方式
    @RequestMapping("/cloud/opt/obj/**")
    public IMicroResponsable cloudOpt(FileOptionRequest fileOptionRequest, HttpServletRequest req){
        return cloudOpt(fileOptionRequest.getOpt(),fileOptionRequest.getFileName(),
                fileOptionRequest.getIsFile(),fileOptionRequest.getMultipartFile(),req);
    }




    //操作文件
    @RequestMapping("/cloud/opt/**")
    public IMicroResponsable cloudOpt(@RequestParam String optional,
                                      @RequestParam String fileName,
                                      @RequestParam(defaultValue = "true") Boolean isFile,
                                      @RequestParam(required = false) MultipartFile multipartFile,
                                      HttpServletRequest req){
        final String path = getVariablePath(req);
        File file = new File(FILE_CONTEXT_PATH + ACGCLOUD + "/" + path);
        //不存在文件 404
        if(!file.exists()){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND,path);
        }
        FileOptionRequest fileOptionRequest = new FileOptionRequest();
        fileOptionRequest.setIsFile(isFile);
        fileOptionRequest.setMultipartFile(multipartFile);
        fileOptionRequest.setFileName(fileName);
        fileOptionRequest.setFile(new File(FILE_CONTEXT_PATH + ACGCLOUD + "/"
                + path + "/" + fileOptionRequest.getFileName()));
        IFileOptStragy bean = fileOptionServices.doService(fileOptionRequest, optional);
        bean.setFileOptionRequest(null);
        return WebResponse.getPrototype().successResp(bean != null?bean.getOptCn():"操作失败",bean);
    }


    //获取文件列表
    @GetMapping("/cloud/list/**")
    public IMicroResponsable cloudList(HttpServletRequest req){
        final String path = getVariablePath(req);
        File file = new File(FILE_CONTEXT_PATH + ACGCLOUD + "/" + path);
        //不存在文件 404
        if(!file.exists()){
            return WebResponse.getPrototype().failedResp("文件不存在", ResultCode.NOT_FOUND,path);
        }
        try {
            //文件夹
            List<RelativeFileObj> filelist = BaseFileUtils.ls(FILE_CONTEXT_PATH + ACGCLOUD + "/" + path);
            int liof = path.lastIndexOf("/");
            String parentPath = path.substring(0,liof > 0 ? liof:0);
            RelativeFileResponse relativeFileResponse = new RelativeFileResponse(parentPath,filelist);
            relativeFileResponse.setCurrent(StringUtils.isBlank(path)?ACGCLOUD + "/":path);
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
        File file = new File(FILE_CONTEXT_PATH + ACGCLOUD + "/" + path + "/" + fileName);
        return  WebResponse.getPrototype().successResp("文件" +(file.exists()?"存在":"不存在"),file.exists());
    }



    //文件下载
    @RequestMapping("/cloud/file/download/**")
    public void cloudFileDown(@RequestParam String newFileName,
                              HttpServletRequest req) throws IOException {
        final String path = getVariablePath(req);
        File file = new File(FILE_CONTEXT_PATH + ACGCLOUD + "/" + path);
        BaseIOUtils.httpDownloadFileResponse(file,newFileName,resp);
    }


}
