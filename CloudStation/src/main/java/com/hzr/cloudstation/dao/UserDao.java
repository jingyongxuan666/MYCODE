package com.hzr.cloudstation.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.transition.CircularPropagation;

import com.hzr.cloudstation.db.MySqlite;
import com.hzr.cloudstation.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzr on 2017/4/1.
 */
public class UserDao {
    private MySqlite mOpenHelper;
    public UserDao(Context context){
        mOpenHelper = new MySqlite(context);
    }
    //插入数据
    public long insert(User user){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long insertResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("userid",user.getUserId());
            values.put("username",user.getUserName());
            values.put("password",user.getPassword());
            insertResult = db.insert("user","userid",values);
            db.close();
        }
        return insertResult;
    }
    //修改数据
    public long update(String userId,String realName,String tel){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long updateResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("realname",realName);
            values.put("tel",tel);
            updateResult = db.update("user",values,"userid = ?",new String[]{userId});
            db.close();
        }
        return updateResult;
    }
    //查询全部
    public List<User> queryAll(){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"userid","username","password","realname","tel"};//需要查询的列
            String selection = null;//查询条件，给空查询所有
            String[] selectionArgs = null;
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("user",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.getCount() > 0){
                List<User> userList = new ArrayList<>();
                String userId;
                String userName;
                String password;
                String realName;
                String tel;
                while (cursor.moveToNext()){
                    userId = cursor.getString(0);
                    userName = cursor.getString(1);
                    password = cursor.getString(2);
                    realName = cursor.getString(3);
                    tel = cursor.getString(4);
                    userList.add(new User(userId,userName,password,realName,tel));
                }
                cursor.close();
                db.close();
                return userList;
            }
        }
        return null;
    }
    //按id查询
    public User queryItemById(String id){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"userid","username","password","realname","tel"};//需要查询的列
            String selection = "userid = ?";//查询条件，给空查询所有
            String[] selectionArgs = {id};
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("user",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.moveToFirst()){
                String userId = cursor.getString(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String realName = cursor.getString(3);
                String tel = cursor.getString(4);
                cursor.close();
                db.close();
                return new User(userId,userName,password,realName,tel);
            }
        }
        return null;
    }
    //按名字查询
    public User queryItemByName(String name){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"userid","username","password","realname","tel"};//需要查询的列
            String selection = "username = ?";//查询条件
            String[] selectionArgs = {name};//替代问号
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("user",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.moveToFirst()){
                String userId = cursor.getString(0);
                String userName = cursor.getString(1);
                String password = cursor.getString(2);
                String realName = cursor.getString(3);
                String tel = cursor.getString(4);
                cursor.close();
                db.close();
                return new User(userId,userName,password,realName,tel);
            }
        }
        return null;
    }
    //修改密码
    public long updatePwd(String userId,String newPwd){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long updateResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("password",newPwd);
            updateResult = db.update("user",values,"userid = ?",new String[]{userId});
            db.close();
        }
        return updateResult;
    }

}
