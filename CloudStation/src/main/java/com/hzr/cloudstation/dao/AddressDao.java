package com.hzr.cloudstation.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzr.cloudstation.db.MySqlite;
import com.hzr.cloudstation.entities.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzr on 2017/4/22.
 */
public class AddressDao {
    private MySqlite mOpenHelper;

    public AddressDao(Context context) {
        mOpenHelper = new MySqlite(context);
    }

    public long insert(Address address){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long insertResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("userid",address.getUserId());
            values.put("address",address.getAddress());
            insertResult = db.insert("address","id",values);
        }
        return insertResult;
    }

    public List<Address> qureyAddress(String userId){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"id","address"};//需要查询的列
            String selection = "userid = ?";//查询条件，给空查询所有
            String[] selectionArgs = {userId};
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("address", columns, selection, selectionArgs, groupBy, having, orderBy);
            if (cursor != null && cursor.getCount() > 0){
                List<Address> addressList = new ArrayList<>();
                String address;
                int id;
                while (cursor.moveToNext()){
                    id = cursor.getInt(0);
                    address = cursor.getString(1);
                    addressList.add(new Address(id,userId,address));
                }
                cursor.close();
                db.close();
                return addressList;

            }
        }
        return null;
    }
    //删除功能
    public long deleteById(String id){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long deleteResult = 0;
        if (db.isOpen()){
            String tableName = "address";
            String whereCause = "id = ?";
            String[] whereArgs = {id};
            deleteResult = db.delete(tableName,whereCause,whereArgs);
            db.close();
        }
        return deleteResult;
    }
    //修改功能
    public long update(String Id,String address){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long updateResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("address",address);
            updateResult = db.update("address",values,"id = ?",new String[]{Id});
            db.close();
        }
        return updateResult;
    }
}
