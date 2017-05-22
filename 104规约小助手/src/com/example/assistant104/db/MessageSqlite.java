package com.example.assistant104.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageSqlite extends SQLiteOpenHelper {

	public MessageSqlite(Context context) {
		super(context, "message.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ´´½¨message±í
		String sql = "create table message(" +
				     "id varchar(20) primary key," +
				     "preMessage text," +
				     "anaMessage text)";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
