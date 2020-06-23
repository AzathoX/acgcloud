package org.acgcloud.web.services;

import org.nrocn.lib.basesocket.IWebSocket;
import org.nrocn.lib.model.socket.SocketDomain;

import javax.websocket.CloseReason;
import javax.websocket.Session;

public interface IWebSocketServices extends IWebSocket {
    void onOpen(Session session, String sessionKey);

    void onClose(String sessionKey, CloseReason closeReason);

    String onMessage(String message);

    void receiveMessage(SocketDomain socketDomain);

    void onError(Throwable t);
}
