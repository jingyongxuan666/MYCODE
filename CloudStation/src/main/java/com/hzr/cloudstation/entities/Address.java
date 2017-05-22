package com.hzr.cloudstation.entities;

/**
 * Created by hzr on 2017/4/22.
 */
public class Address {
    private int id;
    private String userId;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public Address() {
        super();
    }

    public Address(int id, String userId, String address) {
        this.id = id;
        this.userId = userId;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
