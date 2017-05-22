package com.group6.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class IndexActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��ͷ��lable
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//�����ӭ����
				Intent intent =new Intent(IndexActivity.this, LoginActivity.class);
				startActivity(intent);
				//Ϊ������һ��activity�а����ؼ�ʱ��������һ��activity
				IndexActivity.this.finish();
			}
		}, 3000);//3�������¼����
	}

	
}
