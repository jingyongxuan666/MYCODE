package com.hzr.cloudstation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hzr.cloudstation.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzr on 2017/4/7.
 */
public class LoginUtils {
    public static boolean SaveUserInfo(Context context, User user){
        try {
            SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userId",user.getUserId());
            editor.putString("userName",user.getUserName());
            editor.putString("password",user.getPassword());
            editor.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static List<User> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        List<User> userList = new ArrayList<User>();
        String name = sp.getString("userName",null);
        String id = sp.getString("userId",null);
        String pwd = sp.getString("password",null);
        User user = new User();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(id)){
            user.setUserName(name);
            user.setUserId(id);
            user.setPassword(pwd);
            userList.add(user);
            return userList;
        }
        return null;
    }
    public static void cleraUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
