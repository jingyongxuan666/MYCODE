package com.group6.activity;

import java.util.List;

import com.group6.domain.Users;
import com.group6.utils.LoginUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	protected static final String tag = "LoginActivity";
	//声明
	private String role = null;//登录角色
	private String[] dept = null;//部门名称
	private EditText login_name = null;//用户名
	private EditText login_pwd = null;//密码
	private Button login_btn = null;//登录按钮
	private Button forget_pwd_btn =null;//忘记密码
	private Spinner sp_dept = null;//下拉spinner
	private CheckBox cb_rem_pwd = null;//记住密码
	private CheckBox cb_autoLogin = null;//自动登录
	private ImageButton ib_name = null;
	private ImageButton ib_pwd = null;
	//图片绑定变量声明
	private Drawable personAfter,personBefore,pwdBefore,pwdAfter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// 初始化控件
		initView();
		
		//建立角色数据源
		dept = getResources().getStringArray(R.array.dept);
		//为spinner绑定数据
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dept);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_dept.setAdapter(adapter);
		//显示保存的用户信息
		setUserInfo();
		//设置监听器
		sp_dept.setOnItemSelectedListener(itemSelect);
		login_btn.setOnClickListener(this);
		forget_pwd_btn.setOnClickListener(this);
		
		//复选框改变监听
		cb_autoLogin.setOnCheckedChangeListener(cbChange_autoLogin);
		cb_rem_pwd.setOnCheckedChangeListener(cbChange_remPwd);
		
		//edittext文本改变监听
		login_name.addTextChangedListener(watcher);
		login_pwd.addTextChangedListener(watcher);
		//imagebutton监听
		ib_name.setOnClickListener(this);
		ib_pwd.setOnClickListener(this);
		
		/*载入界面检查edittext是否有内容,开始写在了initview()函数里，
		     没有成功，是因为，initview()在setuserinfo()之前，edittext中还没有值
		   而且不能做非空判断，只能对文本长度判断是否为0,判断文本最好用TextUtils.isEmpty
		*/
		initImg();
		if(!TextUtils.isEmpty(login_name.getText().toString())){
			ib_name.setVisibility(View.VISIBLE);
			login_name.setCompoundDrawables(personAfter, null, null, null);
		}
		if(!TextUtils.isEmpty(login_pwd.getText().toString())){
			ib_pwd.setVisibility(View.VISIBLE);
			login_pwd.setCompoundDrawables(pwdAfter, null, null, null);
		}
		
		Log.i(tag, "密码内容："+login_pwd.getText());
		Log.i(tag, "密码长度："+login_pwd.getText().toString().length());
		
//		//设置初始图片
//		ib_name.setImageResource(R.drawable.search_clear_normal);
//		ib_pwd.setImageResource(R.drawable.search_clear_normal);
	}
	/**
	 * 初始化控件
	 */
	private void initView(){
		sp_dept = (Spinner) findViewById(R.id.sp_dept);
		login_name = (EditText) findViewById(R.id.login_name);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		login_btn = (Button) findViewById(R.id.login_btn);
		forget_pwd_btn = (Button) findViewById(R.id.forget_pwd_btn);
		//复选框
		cb_rem_pwd = (CheckBox) findViewById(R.id.cb_rem_pwd);
		cb_autoLogin = (CheckBox) findViewById(R.id.cb_autoLogin);
		
		//imagebutton
		ib_name = (ImageButton) findViewById(R.id.ib_name);
		ib_pwd = (ImageButton) findViewById(R.id.ib_pwd);
		//设置初始状态
		ib_name.setVisibility(View.INVISIBLE);
		ib_pwd.setVisibility(View.INVISIBLE);
		
		
	}
	
	/**
	 * 初始化控件
	 */
	private void initImg(){
		
		Resources res = getResources();
		personAfter = res.getDrawable(R.drawable.ic_person_after);
		personBefore = res.getDrawable(R.drawable.ic_person);
		pwdAfter = res.getDrawable(R.drawable.ic_secure_after);
		pwdBefore = res.getDrawable(R.drawable.ic_secure);
		personAfter.setBounds(0, 0, personAfter.getMinimumWidth(), personAfter.getMinimumHeight());
		personBefore.setBounds(0, 0, personBefore.getMinimumWidth(), personBefore.getMinimumHeight());
		pwdAfter.setBounds(0, 0, pwdAfter.getMinimumWidth(), pwdAfter.getMinimumHeight());
		pwdBefore.setBounds(0, 0, pwdBefore.getMinimumWidth(), pwdBefore.getMinimumHeight());
	}
	/*
	 * edittext内容改变监听
	 * 用于清除按钮以及图片样式
	 */
	TextWatcher watcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			String LoginName = login_name.getText().toString();
			String passWord = login_pwd.getText().toString();
			initImg();
			if(LoginName.length()!=0){
				login_name.setCompoundDrawables(personAfter, null, null, null);
				ib_name.setVisibility(View.VISIBLE);
			}else{
				login_name.setCompoundDrawables(personBefore, null, null, null);
				ib_name.setVisibility(View.INVISIBLE);
			}
			if(passWord.length()!=0){
				login_pwd.setCompoundDrawables(pwdAfter, null, null, null);
				ib_pwd.setVisibility(View.VISIBLE);
			}else{
				login_pwd.setCompoundDrawables(pwdBefore, null, null, null);
				ib_pwd.setVisibility(View.INVISIBLE);
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	/**
	 * 回显用户信息
	 */
	private void setUserInfo(){
		//回显保存的登录信息
		List<Users> userlist = LoginUtils.getUserInfo(this);
		if (userlist != null) {//如果不为空则继续取
			int roleNum;//登录角色
			for (Users users : userlist) {
				//根据不同角色roleNum，以回显角色的spinner
				if ("开发部门".equals(users.getRole())) {
					roleNum = 1;
				} else if ("需求部门".equals(users.getRole())) {
					roleNum = 2;
				} else {
					roleNum = 0;
				}
				// 判断上次选中状态IsRem_Atl，状态包含记住密码才会显示数据
				if (users.getIsRem_Atl() == 1) {// 只有记住密码
					cb_rem_pwd.setChecked(true);// 记住密码勾选
					cb_autoLogin.setChecked(false);

					// 显示所有信息
					login_name.setText(users.getName());//用户名
					login_pwd.setText(users.getPassword());//密码
					sp_dept.setSelection(roleNum);//显示spinner位置
				} else if (users.getIsRem_Atl() == 2) {// 记住密码和自动登录
					cb_rem_pwd.setChecked(true);// 记住密码勾选
					cb_autoLogin.setChecked(true);// 自动登录勾选
					// 显示所有信息
					login_name.setText(users.getName());
					login_pwd.setText(users.getPassword());
					sp_dept.setSelection(roleNum);
				} else {
					cb_rem_pwd.setChecked(false);
					cb_autoLogin.setChecked(false);// 都不勾选
					// 不显示密码
					login_name.setText(users.getName());
					sp_dept.setSelection(roleNum);
				}

				Log.i(tag, "名字" + users.getName());
			}
		} else {
			Log.i(tag, "失败");
		}
	}
	/**
	 * 复选框状态改变监听
	 */
	OnCheckedChangeListener cbChange_autoLogin = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// 自动登录框被选中的同时，记住密码框会自动勾选
			 if(isChecked){
				cb_rem_pwd.setChecked(true);
			}
		}
	};
	OnCheckedChangeListener cbChange_remPwd = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// 取消记住密码框的勾选，自动登录框的勾选也会被取消
			 if(!isChecked){
				cb_autoLogin.setChecked(false);
			}
		}
	};
	/**
	 * spinner选择监听器
	 */
	OnItemSelectedListener itemSelect =  new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			role = dept[position];
			Log.i(tag,role);
			/*监听载入loginActivity时回显用户信息
			（此监听调用后才回显spinner内容，所以再次调用）后，
			自动登录是否被选中，如果被选中，则调用登录按钮点击事件*/
			if(cb_autoLogin.isChecked()){
				onClick(login_btn);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	};

	
	@Override
	public void onClick(View v) {
		// 按钮的点击事件，通过判断按钮id进行不同动作
		switch (v.getId()) {
		case R.id.login_btn:
			final String username = login_name.getText().toString();
			final String pwd = login_pwd.getText().toString();
			if(TextUtils.isEmpty(username)){
				easyToast(LoginActivity.this, "用户名不能为空");
			}else if(TextUtils.isEmpty(pwd)){
				easyToast(LoginActivity.this, "密码不能为空");
			}else{
				new Thread(new Runnable() {
					@Override
					public void run() {
						// 在子线程中进行与服务器交互
						final String state = LoginUtils.loginOfPost(username, pwd,role);
						runOnUiThread(new  Runnable() {//更新主线程UI
							public void run() {
								
								if("登录成功".equals(state)){
									Intent intent = new Intent(LoginActivity.this, MainActivity.class);
									intent.putExtra("username", username);
									intent.putExtra("pwd", pwd);
									intent.putExtra("role", role);
									int isRem_Atl;
									//存储checkbox状态
									if(!cb_rem_pwd.isChecked()){
										isRem_Atl = 0;//两个都没勾选
									}else if(cb_autoLogin.isChecked()){
										isRem_Atl = 2;//两个都勾选了
									}else{
										isRem_Atl = 1;//只勾选了“记住密码”
									}
									if(LoginUtils.saveUserInfo(LoginActivity.this, username, pwd,role,isRem_Atl)){
										Log.i(tag, "保存信息成功");
									}else{
										Log.i(tag, "保存信息失败");
									}
									//为了在下一个activity中按返回键时不返回上一个activity
									finish();
									//跳转
									startActivity(intent);
								}
								easyToast(LoginActivity.this, state);
							}
						});
					}
				}).start();
			}
			break;
		case R.id.forget_pwd_btn:
			new AlertDialog.Builder(LoginActivity.this, 
					AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
			.setTitle("提示")
			.setMessage("请联系你的账户管理员进行查询或更改")
			.setPositiveButton("我知道了", null)
			.show();
			break;
		case R.id.ib_name:
			login_name.setText("");//清空帐号的EditText
			break;
		case R.id.ib_pwd:
			login_pwd.setText("");//清空密码的EditText
			break;
		default:
			break;
		}
	}
	/**
	 * 自定义Toast
	 */
	private Toast toast;
	public void easyToast(Context context,String message){
//		Toast.makeText(context, message, 0).show();
		//将自定义的xml转化成view控件
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.my_toast, null);
		TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
		ImageView iv_toast = (ImageView) view.findViewById(R.id.iv_toast);
		//防止toast重复显示，做是否为空判断
		if(toast==null){
			//如果toast为空，则新建一个toast
			toast = new Toast(context);
			tv_toast.setText(message);
		}else{
			//不为空直接设置属性
			tv_toast.setText(message);
			
		}
		//设置toast显示时间
		toast.setDuration(Toast.LENGTH_SHORT);
		//设置toast位置
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP,0, 100);//位置，x轴坐标，y轴坐标
		//给toast加上自定义样式
		toast.setView(view);
		//根据message内容设置图片
		if(!"登录成功".equals(message)){
			iv_toast.setImageResource(android.R.drawable.stat_notify_error);
		}else{
			iv_toast.setVisibility(View.INVISIBLE);
		}
		//显示toast
		toast.show();
		
	}
}
