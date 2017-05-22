package com.group6.activity;

import com.group6.domain.Users;
import com.group6.utils.JsonUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateUserActivity extends Activity {
	private Button btn_create_user;
	private EditText et_user_name;
	private EditText et_user_pwd;
	private Spinner sp_dept;
	private int deptId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);
		btn_create_user = (Button) findViewById(R.id.btn_user_create);
		et_user_name = (EditText) findViewById(R.id.et_user_name);
		et_user_pwd = (EditText) findViewById(R.id.et_user_pwd);
		sp_dept = (Spinner) findViewById(R.id.sp_user_dept);
		
		sp_dept.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				deptId = position + 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		btn_create_user.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String name = et_user_name.getText().toString();
				final String password = et_user_pwd.getText().toString();
				if(TextUtils.isEmpty(name)){
					Toast.makeText(CreateUserActivity.this, "用户名不能为空", 0).show();
				}else if(TextUtils.isEmpty(password)){
					Toast.makeText(CreateUserActivity.this, "密码不能为空", 0).show();
				}else{
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							final int result = JsonUtils.insertUser(new Users(0, name, password, deptId, 0));
							runOnUiThread(new  Runnable() {
								public void run() {
									if(result == 1){
										Toast.makeText(CreateUserActivity.this, "添加用户成功", 0).show();
										finish();
									}else{
										Toast.makeText(CreateUserActivity.this, "添加用户失败", 0).show();
									}
								}
							});
						}
					}).start();
				}
				
			}
		});
	}

}
