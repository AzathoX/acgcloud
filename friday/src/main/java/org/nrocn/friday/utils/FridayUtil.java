package org.nrocn.friday.utils;

import org.nrocn.friday.model.FridaySession;

import javax.servlet.http.HttpServletRequest;

public class FridayUtil {
    public static FridaySession getFridaySessionFromRequest(HttpServletRequest req){
        FridaySession fridaySession = (FridaySession)req.getAttribute(AuthConstant.SESSION_NAME);
        return fridaySession;
    }
}
