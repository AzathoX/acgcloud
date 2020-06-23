package org.nrocn.lib.basesocket;

import org.nrocn.lib.model.socket.SocketDomain;

public interface IWebSocket {

    void sendMsg(SocketDomain socketDomain);
}
