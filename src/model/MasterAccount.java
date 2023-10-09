package model;

import java.io.Serializable;

public class MasterAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private String password;

    public MasterAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username + "," + password;
    }
    public String getInfor(){
        return "Username: "+username+", password: "+password;
    }
}
