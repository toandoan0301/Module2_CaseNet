package model;

import java.io.Serializable;

public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private String password;
    private int wallet;

    public UserAccount(String username, String password, int wallet) {
        this.username = username;
        this.password = password;
        this.wallet = wallet;
    }
    public UserAccount(String username, String password){
        this.username = username;
        this.password = password;
        this.wallet = 0;
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

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void updateWallet(int wallet) {
        this.wallet += wallet;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + wallet;
    }

    public String showInfor() {
        return "Tài Khoản: " + username + ", Mật Khẩu: " + password + ", Ví: " + wallet + " VND";
    }
}
