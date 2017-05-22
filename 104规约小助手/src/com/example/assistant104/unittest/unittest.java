package com.example.assistant104.unittest;

import java.util.ArrayList;
import java.util.List;

import com.example.assistant104.dao.MessageDao;
import com.example.assistant104.dao.TranslateDao;
import com.example.assistant104.db.MessageSqlite;
import com.example.assistant104.entities.Message;


import android.test.AndroidTestCase;
import android.util.Log;

public class unittest extends AndroidTestCase {
	private static final String tag = "Jing";
	public void test() {
		// 数据库创建
		MessageSqlite openHelper = new MessageSqlite(getContext());
		openHelper.getReadableDatabase();
	}

	public void testInsert() {
		MessageDao dao = new MessageDao(getContext());
		long result = dao.insert(new Message("aaa", "ff", "好的"));
		Log.i(tag, "ID:"+result);
//		Log.i(tag, msg)

	}
	public void testDelete(){
		MessageDao dao = new MessageDao(getContext());
		long id = dao.delete("dd");
		Log.i(tag, "ID:"+id);
	}
	public void testQueryAll(){
		MessageDao dao = new MessageDao(getContext());
		List<Message> messageList = dao.queryAll();
		for (Message message : messageList) {
			Log.i(tag, "message:"+message.toString());
		}
	}
	public void testMsgCut(){
//		ArrayList<String> msgList = TranslateDao.msgCut("32490hjkhkljhkl384", null);
//		for (int i = 0; i < msgList.size(); i++) {
//			Log.i(tag, "message:"+msgList.get(i));
//		}
	}
}
