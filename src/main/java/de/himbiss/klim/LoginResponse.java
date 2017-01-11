package de.himbiss.klim;

import de.himbiss.klim.servlets.beans.User;

/**
 * Created by vincent on 26.12.16.
 */
public class LoginResponse {
    private String id;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
