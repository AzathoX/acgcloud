package org.acgcloud.dmsys.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    //解析路径
    public static String getVariablePath(HttpServletRequest req){
        final String fullUrl =
                req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String firstUrl = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String path = new AntPathMatcher().extractPathWithinPattern(firstUrl, fullUrl);
        return  path;
    }
}
