package de.himbiss.klim.rest;

import de.himbiss.klim.servlets.beans.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by himbiss on 07.04.17.
 */
public final class SessionManager {
    private final static SessionManager INSTANCE = new SessionManager();

    private final Map<UUID, User> sessionMap = new HashMap<>();

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public UUID createSession(User user) {
        if (sessionMap.containsValue(user)) {
            throw new IllegalArgumentException("Session for User already exists!");
        }
        UUID token = UUID.randomUUID();
        sessionMap.put(token, user);
        return token;
    }

    public boolean validateToken(UUID token) {
        return sessionMap.containsKey(token);
    }
}
