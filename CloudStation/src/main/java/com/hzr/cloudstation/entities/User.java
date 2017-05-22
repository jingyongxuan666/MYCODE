package com.hzr.cloudstation.entities;

import java.io.Serializable;

/**
 * Created by hzr on 2017/3/31.
 */
public class User implements Serializable {
    private String userId;
    private String userName;
    private String password;
    private String realName;
    private String tel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public User(String userId, String userName, String password, String realName, String tel) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.tel = tel;
    }

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
