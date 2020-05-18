package org.nrocn.friday.interceptor;

import org.nrocn.friday.model.MappingProperties;
import org.nrocn.friday.model.RequestData;

public interface PreForwardRequestInterceptor {
    void intercept(RequestData data, MappingProperties mapping);
}
