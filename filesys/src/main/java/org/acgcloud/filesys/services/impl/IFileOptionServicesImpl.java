package org.acgcloud.filesys.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.acgcloud.common.utils.BeanUtils;
import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.services.IFileOptStragy;
import org.acgcloud.filesys.services.IFileOptionServices;

@Service
public class IFileOptionServicesImpl implements IFileOptionServices {

    @Autowired
    private ApplicationContext applicationContext;

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
