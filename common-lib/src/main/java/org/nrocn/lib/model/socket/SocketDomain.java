package org.nrocn.lib.model.socket;

import org.nrocn.lib.baseobj.BaseDomain;
import org.nrocn.lib.baseobj.IPojo;

import java.util.Map;

public class SocketDomain extends BaseDomain implements IPojo {

    private String fromUser;

    private String[] atUsers;

    private Map<String,Object> data;

    private String  message;

    private boolean atAll;

    public String[] getAtUsers() {
        return atUsers;
    }

    public void setAtUsers(String[] atUsers) {
        this.atUsers = atUsers;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAtAll() {
        return atAll;
    }

    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
