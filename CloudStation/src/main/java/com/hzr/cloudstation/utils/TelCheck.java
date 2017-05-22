package com.hzr.cloudstation.utils;

/**
 * Created by 静永萱 on 2017/5/21.
 */
public class TelCheck {
    public static boolean checkTel(String telNum){
        boolean checkTelNum = telNum.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
        return checkTelNum;
    }
}
