package org.nrocn.friday.interceptor;

import org.nrocn.friday.model.MappingProperties;
import org.nrocn.friday.model.ResponseData;

public interface PostForwardResponseInterceptor {
    void intercept(ResponseData data, MappingProperties mapping);
}
