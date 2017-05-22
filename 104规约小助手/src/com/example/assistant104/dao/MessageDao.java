package com.example.assistant104.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.assistant104.db.MessageSqlite;
import com.example.assistant104.entities.Message;

public class MessageDao {
	private MessageSqlite mOpenHelper;

	public MessageDao(Context context) {
		mOpenHelper = new MessageSqlite(context);
	}
	
	/*
	 * 插入数据
	 */
	public long insert(Message message){
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long result = 0;
		if(db.isOpen()){//如果数据库打开
			//执行插入数据到数据库的操作
			ContentValues Values = new ContentValues();
			Values.put("id", message.getId());
			Values.put("preMessage", message.getPreMessage());
			Values.put("anaMessage", message.getAnaMessage());
			
			result = db.insert("message", "id", Values);
			db.close();
			return result;
		}
		return 0;
	}
	public long delete(String id){
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long result = 0;
		if(db.isOpen()){
			String whereClause = "id = ?";
			String[] whereArgs = {id};
			result = db.delete("message", whereClause, whereArgs);
			db.close();
			return result;
		}
		return 0;
	}
	public List<Message> queryAll(){
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		if(db.isOpen()){
			String[] columns = {"id","preMessage","anaMessage"};
			String selection = null;
			String[] selectionArgs = null;
			String groupBy = null;
			String having = null;
			String orderBy = null;
			Cursor cursor = db.query("message", columns, selection, selectionArgs, groupBy, having, orderBy);
			
			String id;
			String preMessage;
			String anaMessage;
			if(cursor != null && cursor.getCount() > 0){
				List<Message> messageList = new ArrayList<Message>();
				while(cursor.moveToNext()){
					id = cursor.getString(0);
					preMessage = cursor.getString(1);
					anaMessage = cursor.getString(2);
					messageList.add(new Message(id, preMessage, anaMessage));
				}
				cursor.close();
				return messageList;
			}
			db.close();
		}
		return null;
	}
}
