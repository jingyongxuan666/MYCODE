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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉头部lable
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//软件欢迎界面
				Intent intent =new Intent(IndexActivity.this, LoginActivity.class);
				startActivity(intent);
				//为了在下一个activity中按返回键时不返回上一个activity
				IndexActivity.this.finish();
			}
		}, 3000);//3秒后进入登录界面
	}

	
}
