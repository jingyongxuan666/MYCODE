package com.hzr.cloudstation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.User;

/**
 * Created by hzr on 2017/3/31.
 */
public class MySqlite extends SQLiteOpenHelper {
    private static final int version = 5;
    private static final String dbName = "cloudStation.db";
    private String CREATE_TABLE_User =
            "create table user(" +
                    "userid varchar(20) primary key," +
                    "username varchar(50)," +
                    "password varchar(50)," +
                    "realname varchar(10)," +
                    "tel varchar(20))";
    private String CREATE_TABLE_Express =
            "create table express(" +
                    "expressid varchar(20) primary key," +
                    "company varchar(50)," +
                    "startpoint varchar(200)," +
                    "endpoint varchar(200)," +
                    "sendername varchar(50),"+
                    "sendertel varchar(20)," +
                    "receivername varchar(20),"+
                    "receivertel varchar(20)," +
                    "fee float," +
                    "expressweight float," +
                    "currentstation varchar(50)," +
                    "state integer," +
                    "managerid varchar(20),"+
                    "expresstype integer," +
                    "addtime varchar(20)," +
                    "userid varchar(20))";
    private String CREATE_TABLE_Employee =
            "create table employee(" +
                    "empid varchar(10) primary key," +
                    "empname varchar(10)," +
                    "tel varchar(20))";
    private String CREATE_TABLE_Adress =
            "create table address(" +
                    "id integer primary key," +
                    "userid varchar(20)," +
                    "address varchar(200))";

    public MySqlite(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_User);//创建用户表
        db.execSQL(CREATE_TABLE_Express);//创建快递表
        db.execSQL(CREATE_TABLE_Employee);//创建员工表
        db.execSQL(CREATE_TABLE_Adress);//创建地址表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists express");
        db.execSQL("drop table if exists employee");
        db.execSQL("drop table if exists address");
        onCreate(db);
    }
}
