package org.acgcloud.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.friday.utils.FridayUtil;
import org.nrocn.lib.basecontroller.BaseWebController;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.acgcloud.common.dto.WebResponse;
import org.acgcloud.web.dto.TicketDomain;
import org.acgcloud.web.services.IWebSocketServices;
import org.acgcloud.web.services.impl.WebSocketImpl;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/web/main/svc")
public class MainController extends BaseWebController {
    @Override
    protected String namedModelName() {
        return "";
    }


    @Autowired
    private IWebSocketServices webSocketServices;


    @RequestMapping("/model/info")
    @Override
    protected IMicroResponsable modelInfo() {
        return WebResponse.getPrototype().successResp(namedModelName(),null);
    }


    @Autowired
    @Qualifier("httpServletRequest")
    private HttpServletRequest httpServletRequest;


    @RequestMapping("/role/ticket")
    public IMicroResponsable ticket(){
        FridaySession session = FridayUtil.getFridaySessionFromRequest(httpServletRequest);
        String ticket = DigestUtil.md5Hex(
                session.getUserId() + session.getEmail() + DateUtil.now() + RandomUtil.randomNumbers(10));
        String uri = httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
        TicketDomain ticketDomain = TicketDomain.newInstance(ticket, uri ,session);
        WebSocketImpl.addTicket(ticket,ticketDomain);
        System.out.println(httpServletRequest.getRequestURI());
        ticketDomain.setFridaySession(null);
        return  WebResponse.getPrototype().successResp("ticket",ticketDomain);
    }
}
