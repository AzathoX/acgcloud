package org.acgcloud.optrxsys.services.impl;

import lombok.SneakyThrows;
import org.acgcloud.optrxsys.services.FileOpenServices;
import org.acgcloud.optrxsys.services.IRxFileOptStragy;
import org.acgcloud.optrxsys.services.opt.AbstractFileOpt;
import org.acgcloud.optrxsys.dto.OptrxRequest;
import org.nrocn.lib.utils.BaseFileUtils;
import org.nrocn.lib.utils.BaseIOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.acgcloud.common.utils.BeanUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@Service
public class FileOpenServicesImpl implements FileOpenServices {

    @Autowired
    private ApplicationContext applicationContext;




    @Override
    @SneakyThrows
    public File getResourceByTcp(URL url, String target){
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target));
        BufferedInputStream webIO = new BufferedInputStream(url.openStream());
        BaseIOUtils.copy(webIO,outputStream);
        return BaseFileUtils.find(target);
    }



    @Override
    public IRxFileOptStragy parse(OptrxRequest optrxRequest) {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(AbstractFileOpt.class);
        for (String beanName : beanNamesForType) {
            AbstractFileOpt bean = (AbstractFileOpt) applicationContext.getBean(beanName);
            bean.setOptrxRequest(optrxRequest);
            if(bean.support().equals(optrxRequest.getOpt())){
                bean.setFile(optrxRequest.getFile());
                bean.setBufferePath( optrxRequest.getBufferPath());
                bean.handle();
                return bean;
            }
        }
        return null;
    }
}
