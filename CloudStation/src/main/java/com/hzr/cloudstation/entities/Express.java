package com.hzr.cloudstation.entities;


import java.io.Serializable;

/**
 * Created by hzr on 2017/3/31.
 */
public class Express implements Serializable {
    private String expressId;//单号
    private String company;//快递公司
    private String receiverName;//收件人姓名
    private String startPoint;//起始地址
    private String endPoint;//目标地址
    private String receiverTel;//收件人手机号
    private String senderName;//寄件人姓名
    private String senderTel;//寄件人手机号
    private String managerId;//揽件人编号
    private float fee;//快递费用
    private String addTime;//添加时间
    private int state;//快递状态
    private String currentStation;//当前到达站点
    private float expressWeight;//快递重量
    private int expressType;//快递类型
    private String userId;//用户id
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public float getExpressWeight() {
        return expressWeight;
    }

    public void setExpressWeight(float expressWeight) {
        this.expressWeight = expressWeight;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getExpressType() {
        return expressType;
    }

    public void setExpressType(int expressType) {
        this.expressType = expressType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Express() {
        super();
    }

    public Express(String expressId, String company, String receiverName, String startPoint, String endPoint, String receiverTel, String senderName, String senderTel, String managerId, float fee, String addTime, int state, String currentStation, float expressWeight, int expressType, String userId) {
        this.expressId = expressId;
        this.company = company;
        this.receiverName = receiverName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.receiverTel = receiverTel;
        this.senderName = senderName;
        this.senderTel = senderTel;
        this.managerId = managerId;
        this.fee = fee;
        this.addTime = addTime;
        this.state = state;
        this.currentStation = currentStation;
        this.expressWeight = expressWeight;
        this.expressType = expressType;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Express{" +
                "expressId='" + expressId + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", company='" + company + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderTel='" + senderTel + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", managerId='" + managerId + '\'' +
                ", receiverTel='" + receiverTel + '\'' +
                ", fee=" + fee +
                ", expressWeight=" + expressWeight +
                ", currentStation='" + currentStation + '\'' +
                ", state=" + state +
                ", expressType=" + expressType +
                ", addTime='" + addTime + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
