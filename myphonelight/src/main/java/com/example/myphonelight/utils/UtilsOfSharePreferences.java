package com.example.myphonelight.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  on 2017/3/14.
 */
public class UtilsOfSharePreferences {
    public static boolean saveTimeInfo(Context context, int hour, int min,int longOfTime) {
        try {
            SharedPreferences sp=context.getSharedPreferences("timeInfo", Context.MODE_PRIVATE);
            //获得一个编辑对象
            SharedPreferences.Editor edit=sp.edit();
            //存数据
            edit.putString("hour", String.valueOf(hour));
            edit.putString("min", String.valueOf(min));
            edit.putString("longOfTime",String.valueOf(longOfTime));
            //提交
            edit.commit();

            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        return false;

    }
    //返回用户信息

    public static Map<String, String> getTimeInfo(Context context){

        SharedPreferences sp=context.getSharedPreferences("timeInfo", Context.MODE_PRIVATE);

        String hour=sp.getString("hour", null);
        String min = sp.getString("min", null);
        String longOfTime = sp.getString("longOfTime",null);

        if(!TextUtils.isEmpty(hour)&& !TextUtils.isEmpty(min) && !TextUtils.isEmpty(longOfTime)){
            Map<String, String> timeInfoMap = new HashMap<String, String>();

            timeInfoMap.put("hour", hour);
            timeInfoMap.put("min", min);
            timeInfoMap.put("longOfTime",longOfTime);
            return timeInfoMap;
        }


        return null;

    }
    public static boolean clearTimeInfo(Context context){
        try {
            SharedPreferences sp=context.getSharedPreferences("timeInfo", Context.MODE_PRIVATE);
            //获得一个编辑对象
            SharedPreferences.Editor edit=sp.edit();
            //清除数据
            edit.clear();
            //提交
            edit.commit();

            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
