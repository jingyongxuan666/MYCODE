package com.hzr.cloudstation.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzr.cloudstation.db.MySqlite;
import com.hzr.cloudstation.entities.Employee;
import com.hzr.cloudstation.entities.Express;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzr on 2017/4/1.
 */
public class ExpressDao {
    private MySqlite mOpenHelper;
    public ExpressDao(Context context){
        mOpenHelper = new MySqlite(context);
    }
    //快递表插入方法
    public long insert(Express express){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long insertResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("expressid",express.getExpressId());
            values.put("company",express.getCompany());
            values.put("receivername",express.getReceiverName());
            values.put("startpoint",express.getStartPoint());
            values.put("endpoint",express.getEndPoint());
            values.put("receivertel",express.getReceiverTel());
            values.put("sendername",express.getSenderName());
            values.put("sendertel",express.getSenderTel());
            values.put("managerid",express.getManagerId());
            values.put("fee",express.getFee());
            values.put("addtime",express.getAddTime());
            values.put("state",express.getState());
            values.put("currentstation",express.getCurrentStation());
            values.put("expressweight",express.getExpressWeight());
            values.put("expresstype",express.getExpressType());
            values.put("userid",express.getUserId());


            insertResult = db.insert("express","expressid",values);
            db.close();

        }
        return insertResult;
    }
    //查询所有
    public List<Express> queryAll(){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"expressid","company","receivername",
                    "startpoint","endpoint","receivertel","sendername",
                    "sendertel","managerid","fee","addtime","state",
                    "currentstation","expressweight","expresstype","userid"};//需要查询的列
            String selection = null;//查询条件，给空查询所有
            String[] selectionArgs = null;
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("express",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.getCount() > 0){
                List<Express> expressList = new ArrayList<>();
                String expressId;
                String company;
                String receiverName;
                String startPoint;
                String endPoint;
                String receiverTel;
                String senderName;
                String senderTel;
                String managerId;
                float fee = 0;
                String addTime;
                int state = 0;
                String currentStation;
                float weight = 0;
                int type = 0;
                String userId;
                while (cursor.moveToNext()){
                    expressId = cursor.getString(0);
                    company = cursor.getString(1);
                    receiverName = cursor.getString(2);
                    startPoint = cursor.getString(3);
                    endPoint = cursor.getString(4);
                    receiverTel = cursor.getString(5);
                    senderName = cursor.getString(6);
                    senderTel = cursor.getString(7);
                    managerId = cursor.getString(8);
                    fee = cursor.getFloat(9);
                    addTime = cursor.getString(10);
                    state = cursor.getInt(11);
                    currentStation = cursor.getString(12);
                    weight = cursor.getFloat(13);
                    type = cursor.getInt(14);
                    userId = cursor.getString(15);
                    expressList.add(new Express(expressId,company,receiverName,startPoint,
                            endPoint,receiverTel,senderName,senderTel,managerId,fee,addTime,
                            state,currentStation,weight,type,userId));

                }
                cursor.close();
                db.close();
                return expressList;
            }

        }
        return null;
    }
    //按状态查询
    public List<Express> queryItemByIdandState(String Id,int currentState){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"expressid","company","receivername",
                    "startpoint","endpoint","receivertel","sendername",
                    "sendertel","managerid","fee","addtime","state",
                    "currentstation","expressweight","expresstype","userid"};//需要查询的列
            String selection;
            String[] selectionArgs;
            if (currentState == -2){
               selection = "userid = ?";
                selectionArgs = new String[]{Id};
            }else{
                selection = "userid = ? and state = ?";
                selectionArgs = new String[]{Id, String.valueOf(currentState)};
            }
            String groupBy = null;
            String having = null;
            String orderBy = "addtime desc";
            Cursor cursor = db.query("express",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.getCount() > 0){
                List<Express> expressList = new ArrayList<>();
                String expressId;
                String company;
                String receiverName;
                String startPoint;
                String endPoint;
                String receiverTel;
                String senderName;
                String senderTel;
                String managerId;
                float fee = 0;
                String addTime;
                int state = 0;
                String currentStation;
                float weight = 0;
                int type = 0;
                String userId;
                while (cursor.moveToNext()){
                    expressId = cursor.getString(0);
                    company = cursor.getString(1);
                    receiverName = cursor.getString(2);
                    startPoint = cursor.getString(3);
                    endPoint = cursor.getString(4);
                    receiverTel = cursor.getString(5);
                    senderName = cursor.getString(6);
                    senderTel = cursor.getString(7);
                    managerId = cursor.getString(8);
                    fee = cursor.getFloat(9);
                    addTime = cursor.getString(10);
                    state = cursor.getInt(11);
                    currentStation = cursor.getString(12);
                    weight = cursor.getFloat(13);
                    type = cursor.getInt(14);
                    userId = cursor.getString(15);
                    expressList.add(new Express(expressId,company,receiverName,startPoint,
                            endPoint,receiverTel,senderName,senderTel,managerId,fee,addTime,
                            state,currentStation,weight,type,userId));

                }
                cursor.close();
                db.close();
                return expressList;
            }

        }
        return null;
    }

    //按运单号查询
    public List<Express> queryByExpressId(String Id){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"expressid","company","receivername",
                    "startpoint","endpoint","receivertel","sendername",
                    "sendertel","managerid","fee","addtime","state",
                    "currentstation","expressweight","expresstype","userid"};//需要查询的列
            String selection = "expressid = ?";//查询条件，给空查询所有
            String[] selectionArgs = {Id};
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("express",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.getCount() > 0){
                List<Express> expressList = new ArrayList<>();
                String expressId;
                String company;
                String receiverName;
                String startPoint;
                String endPoint;
                String receiverTel;
                String senderName;
                String senderTel;
                String managerId;
                float fee = 0;
                String addTime;
                int state = 0;
                String currentStation;
                float weight = 0;
                int type = 0;
                String userId;
                while (cursor.moveToNext()){
                    expressId = cursor.getString(0);
                    company = cursor.getString(1);
                    receiverName = cursor.getString(2);
                    startPoint = cursor.getString(3);
                    endPoint = cursor.getString(4);
                    receiverTel = cursor.getString(5);
                    senderName = cursor.getString(6);
                    senderTel = cursor.getString(7);
                    managerId = cursor.getString(8);
                    fee = cursor.getFloat(9);
                    addTime = cursor.getString(10);
                    state = cursor.getInt(11);
                    currentStation = cursor.getString(12);
                    weight = cursor.getFloat(13);
                    type = cursor.getInt(14);
                    userId = cursor.getString(15);
                    expressList.add(new Express(expressId,company,receiverName,startPoint,
                            endPoint,receiverTel,senderName,senderTel,managerId,fee,addTime,
                            state,currentStation,weight,type,userId));

                }
                cursor.close();
                db.close();
                return expressList;
            }

        }
        return null;
    }
    //修改数据
    public long updateState(String expressId,int state){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long updateResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("state",state);
            updateResult = db.update("express",values,"expressid = ?",new String[]{expressId});
            db.close();
        }
        return updateResult;
    }
    //修改数据
    public long updateMsg(String expressId,Express express){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long updateResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("currentstation",express.getCurrentStation());
            values.put("managerid",express.getManagerId());
            values.put("expresstype",express.getExpressType());
            values.put("expressweight",express.getExpressWeight());
            values.put("state",express.getState());
            values.put("fee",express.getFee());
            updateResult = db.update("express",values,"expressid = ?",new String[]{expressId});
            db.close();
        }
        return updateResult;
    }


}
