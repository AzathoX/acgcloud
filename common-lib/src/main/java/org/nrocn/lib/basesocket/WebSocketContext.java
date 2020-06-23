package org.nrocn.lib.basesocket;

import org.nrocn.lib.model.socket.SocketDomain;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * socket 容器单例
 */
public class WebSocketContext {
    private static volatile WebSocketContext webSocketContext;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static Map<String, IWebSocket> webSockets = new ConcurrentHashMap<>();

    public static synchronized  void sendMsgAll(SocketDomain socketDomain){
        WebSocketContext.webSockets.entrySet().stream()
                .map(k -> k.getValue()).forEach(action ->{
                    action.sendMsg(socketDomain);
        });
    }


    public static synchronized void sendMsgByAtList(SocketDomain socketDomain){
        Arrays.stream(socketDomain.getAtUsers()).forEach(action ->{
            IWebSocket webSocket = webSockets.get(action);
            if(webSocket != null){
                webSocket.sendMsg(socketDomain);
            }
        });
    }

    public  static synchronized void  push(String k,IWebSocket ws){
        if(k == null || ws == null){
            return;
        }
        WebSocketContext.webSockets.put(k,ws);
        addOnlineCount();
    }

    public static  synchronized  IWebSocket pull(String k){
        return WebSocketContext.webSockets.get(k);
    }

    public  static  synchronized  IWebSocket pop(String k){
        IWebSocket remove = WebSocketContext.webSockets.remove(k);
        if(remove != null){
            WebSocketContext.subOnlineCount();
            return  remove;
        }
        return  null;
    }


    public  static int getOnlineCount(){
        return  onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }




    private  WebSocketContext(){
    }
}
