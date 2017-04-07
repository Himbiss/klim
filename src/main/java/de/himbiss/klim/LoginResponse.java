package de.himbiss.klim;

import de.himbiss.klim.servlets.beans.User;

/**
 * Created by vincent on 26.12.16.
 */
public class LoginResponse {
    private String sessionId;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String id, User user) {
        this.sessionId = id;
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String id) {
        this.sessionId = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
