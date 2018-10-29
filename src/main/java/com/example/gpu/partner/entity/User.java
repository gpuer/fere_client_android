package com.example.gpu.partner.entity;

public class User {
    public String uid;
    public String username;
    public String pw;
    public String cookie;
    public String link_uid;
    public String nickname;
    public String Emergencyphone;
    public String msg;

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", pw='" + pw + '\'' +
                ", cookie='" + cookie + '\'' +
                ", link_uid='" + link_uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", Emergencyphone='" + Emergencyphone + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
