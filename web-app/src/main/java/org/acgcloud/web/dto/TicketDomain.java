package org.acgcloud.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.lib.baseobj.BaseDomain;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDomain extends BaseDomain {

    private  FridaySession fridaySession;

    private String requestUri;

    public static TicketDomain  newInstance(String ticket,String requestUri ,FridaySession session ){
        TicketDomain ticketDomain = new TicketDomain();
        ticketDomain.setTicky(ticket);
        ticketDomain.setFridaySession(session);
        ticketDomain.setRequestUri(requestUri);
        return  ticketDomain;
    }



}
