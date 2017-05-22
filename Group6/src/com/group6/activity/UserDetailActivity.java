package com.group6.activity;

import java.util.List;

import com.group6.domain.Users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class UserDetailActivity extends Activity {

	private static final String tag = "UserDetailActivity";
	private Users user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		Intent getIn = getIntent();
		user = (Users) getIn.getSerializableExtra("user");
		Log.i(tag, "用户的名字"+user.getName());
		Log.i(tag, "用户的密码"+user.getPassword());
	}
	

}
