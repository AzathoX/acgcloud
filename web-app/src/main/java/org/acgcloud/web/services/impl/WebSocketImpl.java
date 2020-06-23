package org.acgcloud.web.services.impl;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.acgcloud.web.dto.TicketDomain;
import org.acgcloud.web.services.IWebSocketServices;
import org.apache.commons.lang3.ObjectUtils;
import org.nrocn.lib.basesocket.IWebSocket;
import org.nrocn.lib.basesocket.WebSocketContext;
import org.nrocn.lib.model.socket.SocketDomain;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@Service
@ServerEndpoint("/ws/{ticketId}")
public class WebSocketImpl implements IWebSocketServices {

    private static  final Map<String, TicketDomain> ticketMap = new HashMap<>();

    private Session session;

    private TicketDomain ticketDomain;

    public static  void addTicket(String k,TicketDomain v){
        if(!ObjectUtils.allNotNull(k,v)){
            return;
        }
        ticketMap.put(k,v);
    }



    @OnOpen
    @SneakyThrows
    @Override
    public void onOpen(Session session,  @PathParam("ticketId") String sessionKey) {
        if(ObjectUtils.isEmpty(sessionKey)){
            throw  new Exception("登陆票据为空");
        }
        TicketDomain ticketDomain = ticketMap.remove(sessionKey);
        if(ObjectUtils.isEmpty(ticketDomain)){
            throw  new Exception("没有登陆凭证链接错误");
        }
        this.session = session;
        this.ticketDomain = ticketDomain;
        WebSocketContext.push(sessionKey,this);
    }

    @OnClose
    @Override
    public void onClose(@PathParam("ticketId") String sessionKey, CloseReason closeReason) {
        IWebSocket pop = WebSocketContext.pop(sessionKey);
        if(ObjectUtils.allNotNull(pop)){
            return ;
        }
    }

    @OnMessage
    @Override
    public String onMessage(String message) {
        SocketDomain socketDomain = JSON.parseObject(message, SocketDomain.class);
        socketDomain.setTicky(ticketDomain.getTicky());
        if(socketDomain.isAtAll()){
            WebSocketContext.sendMsgAll(socketDomain);
            return "全部发送完成";
        }
        WebSocketContext.sendMsgByAtList(socketDomain);
        return null;
    }


    @Override
    public void receiveMessage(SocketDomain socketDomain) {
        SocketDomain receive = new SocketDomain();
        receive.setAtUsers(new String[]{socketDomain.getTicky()});
        receive.setMessage(socketDomain.getMessage());
        receive.setAtAll(false);
        WebSocketContext.sendMsgByAtList(receive);
    }

    @OnError
    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }



    @SneakyThrows
    @Override
    public void sendMsg(SocketDomain socketDomain) {
        try {
            synchronized (this.session) {
                this.session.getBasicRemote().sendText(socketDomain.getMessage());
            }
        }catch (Exception exception){
            socketDomain.setMessage("发送异常消息为:"+exception.getMessage());
            this.receiveMessage(socketDomain);
        }
    }
}
