package com.forsyslab.talquest10.model;

/**
 * Created by LENOVO on 30/03/2017.
 */

public class Authenticate {

    private String username;
    private String password;
    private boolean rememberMe;

    public Authenticate(String username, String password, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public boolean getRememberMe() { return this.rememberMe; }

    public void setRememberMe(boolean rememberMe) { this.rememberMe = rememberMe; }

    public String getUsername() { return this.username; }

    public void setUsername(String username) { this.username = username; }
}
