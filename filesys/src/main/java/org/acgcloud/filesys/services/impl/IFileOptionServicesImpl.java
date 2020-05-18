package org.acgcloud.filesys.services.impl;

import org.acgcloud.filesys.config.BeanUtils;
import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.services.IFileOptStragy;
import org.acgcloud.filesys.services.IFileOptionServices;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class IFileOptionServicesImpl implements IFileOptionServices {

    private ApplicationContext applicationContext = BeanUtils.getApplicationContext();

    @Override
    public IFileOptStragy doService(FileOptionRequest fileOptionRequest , String opt) {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(IFileOptStragy.class);
        for (String beanName : beanNamesForType) {
            IFileOptStragy bean = (IFileOptStragy) applicationContext.getBean(beanName);
            if(bean.support().equals(opt)){
                bean.setFileOptionRequest(fileOptionRequest);
                bean.setFile(fileOptionRequest.getFile());
                bean.handle();
                return bean;
            }
        }
        return null;
    }
}
