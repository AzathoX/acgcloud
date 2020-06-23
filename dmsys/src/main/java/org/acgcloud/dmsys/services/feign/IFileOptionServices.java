package org.acgcloud.dmsys.services.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.acgcloud.common.dto.WebResponse;


@FeignClient(name = "filesysfeign",url = "${acgcloud.dmsys.depend-on-uri}/filesys/main")
public interface IFileOptionServices {
     @GetMapping("/model/info")
     WebResponse modelInfo();




     @GetMapping("/cloud/opt/{filesys}")
     WebResponse cloudOpt(@PathVariable(required = false) String filesys,
                          @RequestParam String optional,
                          @RequestParam String fileName,
                          @RequestParam(defaultValue = "true") Boolean isFile);


     @GetMapping("/cloud/upload/{filesys}")
     WebResponse cloudOptUpload(@PathVariable(required = false) String filesys,
                                @RequestParam String optional,
                                @RequestParam String fileName,
                                @RequestParam(defaultValue = "true") Boolean isFile,
                                @RequestParam MultipartFile multipartFile);


     @GetMapping("/cloud/list/{path}")
     WebResponse cloudList(@PathVariable String path);

}
