package ua.softgroup.matrix.server.model;

import java.io.Serializable;

public class  TokenModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

    public TokenModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

