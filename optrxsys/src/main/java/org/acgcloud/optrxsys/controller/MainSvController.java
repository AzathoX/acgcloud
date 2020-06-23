package org.acgcloud.optrxsys.controller;

import lombok.SneakyThrows;
import org.acgcloud.optrxsys.AbstractConstant;
import org.acgcloud.optrxsys.services.FileOpenServices;
import org.acgcloud.optrxsys.services.IRxFileOptStragy;
import org.acgcloud.optrxsys.dto.OptrxRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.nrocn.lib.basecontroller.BaseWebController;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.utils.BaseIOUtils;
import org.nrocn.lib.utils.BaseString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.acgcloud.common.dto.WebResponse;
import org.acgcloud.common.utils.RequestUtils;
import org.acgcloud.config.config.AppConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@RestController
@RequestMapping("/optrx/main")
@CrossOrigin(allowCredentials="true",origins="*",maxAge = 3600)
public class MainSvController extends BaseWebController {


    @Autowired
    private AppConfiguration appConfiguration;


    @Autowired
    private FileOpenServices fileOpenServices;



    @Autowired
    private HttpServletRequest httpServletRequest;


    @Autowired
    private HttpServletResponse httpServletResponse;

    private File jointConfigPath(){
        String path = RequestUtils.requestVariablePath(httpServletRequest);
        path  = appConfiguration.getOptrx().getFilePath() + "/" + path;
        return new File(path);
    }

    @Override
    protected String namedModelName() {
        return "文件解析器模块";
    }

    @Override
    @RequestMapping("/model/info")
    protected IMicroResponsable modelInfo() {
        return WebResponse.getPrototype().successResp(namedModelName(),null);
    }


    //本地网络解析,从web端获取资源
    @SneakyThrows
    @GetMapping("/could/file/url/parse/by")
    public void parseByTcp(@RequestParam("url") String webUrl,
                           @RequestParam String fileName,
                           @RequestParam(required = false) String opt ){
        if( ObjectUtils.isEmpty(webUrl) || StringUtils.isBlank(webUrl)){
            BaseIOUtils.httpHtmlWriteResponse(httpServletResponse,"无效地址");
        }
        if(webUrl.indexOf("http://") < 0){
            webUrl = "http://127.0.0.1" + webUrl;
        }

        //获取web端地址
        URL url = new URL(webUrl);
        String baseContent = AbstractConstant.FILE_CONTEXT_PATH + AbstractConstant.TEMP;
        //get resource
        File webResource = fileOpenServices.getResourceByTcp(url, baseContent + "/" + fileName);
        if(StringUtils.isBlank(opt)){
            return;
        }
        OptrxRequest optrxRequest = new OptrxRequest();
        optrxRequest.setFile(webResource);
        optrxRequest.setOpt(opt);
        optrxRequest.setBufferPath(baseContent);
        IRxFileOptStragy parse = fileOpenServices.parse(optrxRequest);
        BufferedInputStream ioStream = new BufferedInputStream(parse.open());
        String html = BaseString.inputStream(ioStream,1024);
        BaseIOUtils.httpHtmlWriteResponse(httpServletResponse,html);
    }

    @SneakyThrows
    @RequestMapping("/cloud/file/parse/**")
    public void parse(@RequestBody OptrxRequest optrxRequest) {
        File file = jointConfigPath();
        optrxRequest.setFile(file);
        fileOpenServices.parse(optrxRequest);
        OptrxRequest optxRequest = new OptrxRequest();
        optxRequest.setPath(file.getPath());
        optxRequest.setBufferPath(optrxRequest.getBufferPath());
        optxRequest.setFile(file);
        optxRequest.setOpt(optrxRequest.getOpt());
        IRxFileOptStragy parse = fileOpenServices.parse(optxRequest);
        InputStream open = parse.open();
        String content = "";
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));
        while(ObjectUtils.isNotEmpty(line = bufferedReader.readLine())) {
            content += line;
        }
        BaseIOUtils.httpHtmlWriteResponse(httpServletResponse,content);
    }


}
