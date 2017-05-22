package com.hzr.cloudstation.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzr.cloudstation.db.MySqlite;
import com.hzr.cloudstation.entities.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzr on 2017/4/16.
 */
public class EmployeeDao {
    private MySqlite mOpenHelper;

    public EmployeeDao(Context context) {
        mOpenHelper = new MySqlite(context);
    }

    public long insert(Employee employee) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long insertResult = 0;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("empid", employee.getEmpId());
            values.put("empname", employee.getEmpName());
            values.put("tel", employee.getTel());
            insertResult = db.insert("employee", "empid", values);
            db.close();
        }
        return insertResult;
    }
    //删除功能
    public long deleteByEmpId(String id){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long deleteResult = 0;
        if (db.isOpen()){
            String tableName = "employee";
            String whereCause = "empid = ?";
            String[] whereArgs = {id};
            deleteResult = db.delete(tableName,whereCause,whereArgs);
            db.close();
        }
        return deleteResult;
    }

    public List<Employee> queryAll() {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            String[] columns = {"empid", "empname", "tel"};//需要查询的列
            String selection = null;//查询条件，给空查询所有
            String[] selectionArgs = null;
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("employee", columns, selection, selectionArgs, groupBy, having, orderBy);
            if (cursor != null && cursor.getCount() > 0) {
                List<Employee> empList = new ArrayList<>();
                String empId;
                String empName;
                String tel;
                while (cursor.moveToNext()) {
                    empId = cursor.getString(0);
                    empName = cursor.getString(1);
                    tel = cursor.getString(2);
                    empList.add(new Employee(empId, empName, tel));
                }
                cursor.close();
                db.close();
                return empList;

            }
        }
        return null;
    }
    //修改功能
    public long update(String Id,Employee emp){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long updateResult = 0;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("empname",emp.getEmpName());
            values.put("tel",emp.getTel());
            updateResult = db.update("employee",values,"empid = ?",new String[]{Id});
            db.close();
        }
        return updateResult;
    }
    //条件查询功能
    public List<Employee> queryByIdOrName(String idOrName) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            String[] columns = {"empid", "empname", "tel"};//需要查询的列
            String selection = "empid like ? or empname like ?";//查询条件，给空查询所有
            String[] selectionArgs = {"%"+idOrName+"%","%"+idOrName+"%"};
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("employee", columns, selection, selectionArgs, groupBy, having, orderBy);
            if (cursor != null && cursor.getCount() > 0) {
                List<Employee> empList = new ArrayList<>();
                String empId;
                String empName;
                String tel;
                while (cursor.moveToNext()) {
                    empId = cursor.getString(0);
                    empName = cursor.getString(1);
                    tel = cursor.getString(2);
                    empList.add(new Employee(empId, empName, tel));
                }
                cursor.close();
                db.close();
                return empList;

            }
        }
        return null;
    }
    //按id查询
    public Employee queryItemById(String id){
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            String[] columns = {"empid","empName","tel"};//需要查询的列
            String selection = "empid = ?";//查询条件，给空查询所有
            String[] selectionArgs = {id};
            String groupBy = null;
            String having = null;
            String orderBy = null;
            Cursor cursor = db.query("employee",columns,selection,selectionArgs,groupBy,having,orderBy);
            if (cursor != null && cursor.moveToFirst()){
                String expId = cursor.getString(0);
                String expName = cursor.getString(1);
                String tel = cursor.getString(2);
                cursor.close();
                db.close();
                return new Employee(expId,expName,tel);
            }
        }
        return null;
    }
}
