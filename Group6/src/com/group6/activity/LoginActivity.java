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
	//����
	private String role = null;//��¼��ɫ
	private String[] dept = null;//��������
	private EditText login_name = null;//�û���
	private EditText login_pwd = null;//����
	private Button login_btn = null;//��¼��ť
	private Button forget_pwd_btn =null;//��������
	private Spinner sp_dept = null;//����spinner
	private CheckBox cb_rem_pwd = null;//��ס����
	private CheckBox cb_autoLogin = null;//�Զ���¼
	private ImageButton ib_name = null;
	private ImageButton ib_pwd = null;
	//ͼƬ�󶨱�������
	private Drawable personAfter,personBefore,pwdBefore,pwdAfter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// ��ʼ���ؼ�
		initView();
		
		//������ɫ����Դ
		dept = getResources().getStringArray(R.array.dept);
		//Ϊspinner������
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dept);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_dept.setAdapter(adapter);
		//��ʾ������û���Ϣ
		setUserInfo();
		//���ü�����
		sp_dept.setOnItemSelectedListener(itemSelect);
		login_btn.setOnClickListener(this);
		forget_pwd_btn.setOnClickListener(this);
		
		//��ѡ��ı����
		cb_autoLogin.setOnCheckedChangeListener(cbChange_autoLogin);
		cb_rem_pwd.setOnCheckedChangeListener(cbChange_remPwd);
		
		//edittext�ı��ı����
		login_name.addTextChangedListener(watcher);
		login_pwd.addTextChangedListener(watcher);
		//imagebutton����
		ib_name.setOnClickListener(this);
		ib_pwd.setOnClickListener(this);
		
		/*���������edittext�Ƿ�������,��ʼд����initview()�����
		     û�гɹ�������Ϊ��initview()��setuserinfo()֮ǰ��edittext�л�û��ֵ
		   ���Ҳ������ǿ��жϣ�ֻ�ܶ��ı������ж��Ƿ�Ϊ0,�ж��ı������TextUtils.isEmpty
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
		
		Log.i(tag, "�������ݣ�"+login_pwd.getText());
		Log.i(tag, "���볤�ȣ�"+login_pwd.getText().toString().length());
		
//		//���ó�ʼͼƬ
//		ib_name.setImageResource(R.drawable.search_clear_normal);
//		ib_pwd.setImageResource(R.drawable.search_clear_normal);
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView(){
		sp_dept = (Spinner) findViewById(R.id.sp_dept);
		login_name = (EditText) findViewById(R.id.login_name);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		login_btn = (Button) findViewById(R.id.login_btn);
		forget_pwd_btn = (Button) findViewById(R.id.forget_pwd_btn);
		//��ѡ��
		cb_rem_pwd = (CheckBox) findViewById(R.id.cb_rem_pwd);
		cb_autoLogin = (CheckBox) findViewById(R.id.cb_autoLogin);
		
		//imagebutton
		ib_name = (ImageButton) findViewById(R.id.ib_name);
		ib_pwd = (ImageButton) findViewById(R.id.ib_pwd);
		//���ó�ʼ״̬
		ib_name.setVisibility(View.INVISIBLE);
		ib_pwd.setVisibility(View.INVISIBLE);
		
		
	}
	
	/**
	 * ��ʼ���ؼ�
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
	 * edittext���ݸı����
	 * ���������ť�Լ�ͼƬ��ʽ
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
	 * �����û���Ϣ
	 */
	private void setUserInfo(){
		//���Ա���ĵ�¼��Ϣ
		List<Users> userlist = LoginUtils.getUserInfo(this);
		if (userlist != null) {//�����Ϊ�������ȡ
			int roleNum;//��¼��ɫ
			for (Users users : userlist) {
				//���ݲ�ͬ��ɫroleNum���Ի��Խ�ɫ��spinner
				if ("��������".equals(users.getRole())) {
					roleNum = 1;
				} else if ("������".equals(users.getRole())) {
					roleNum = 2;
				} else {
					roleNum = 0;
				}
				// �ж��ϴ�ѡ��״̬IsRem_Atl��״̬������ס����Ż���ʾ����
				if (users.getIsRem_Atl() == 1) {// ֻ�м�ס����
					cb_rem_pwd.setChecked(true);// ��ס���빴ѡ
					cb_autoLogin.setChecked(false);

					// ��ʾ������Ϣ
					login_name.setText(users.getName());//�û���
					login_pwd.setText(users.getPassword());//����
					sp_dept.setSelection(roleNum);//��ʾspinnerλ��
				} else if (users.getIsRem_Atl() == 2) {// ��ס������Զ���¼
					cb_rem_pwd.setChecked(true);// ��ס���빴ѡ
					cb_autoLogin.setChecked(true);// �Զ���¼��ѡ
					// ��ʾ������Ϣ
					login_name.setText(users.getName());
					login_pwd.setText(users.getPassword());
					sp_dept.setSelection(roleNum);
				} else {
					cb_rem_pwd.setChecked(false);
					cb_autoLogin.setChecked(false);// ������ѡ
					// ����ʾ����
					login_name.setText(users.getName());
					sp_dept.setSelection(roleNum);
				}

				Log.i(tag, "����" + users.getName());
			}
		} else {
			Log.i(tag, "ʧ��");
		}
	}
	/**
	 * ��ѡ��״̬�ı����
	 */
	OnCheckedChangeListener cbChange_autoLogin = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// �Զ���¼��ѡ�е�ͬʱ����ס�������Զ���ѡ
			 if(isChecked){
				cb_rem_pwd.setChecked(true);
			}
		}
	};
	OnCheckedChangeListener cbChange_remPwd = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// ȡ����ס�����Ĺ�ѡ���Զ���¼��Ĺ�ѡҲ�ᱻȡ��
			 if(!isChecked){
				cb_autoLogin.setChecked(false);
			}
		}
	};
	/**
	 * spinnerѡ�������
	 */
	OnItemSelectedListener itemSelect =  new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			role = dept[position];
			Log.i(tag,role);
			/*��������loginActivityʱ�����û���Ϣ
			���˼������ú�Ż���spinner���ݣ������ٴε��ã���
			�Զ���¼�Ƿ�ѡ�У������ѡ�У�����õ�¼��ť����¼�*/
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
		// ��ť�ĵ���¼���ͨ���жϰ�ťid���в�ͬ����
		switch (v.getId()) {
		case R.id.login_btn:
			final String username = login_name.getText().toString();
			final String pwd = login_pwd.getText().toString();
			if(TextUtils.isEmpty(username)){
				easyToast(LoginActivity.this, "�û�������Ϊ��");
			}else if(TextUtils.isEmpty(pwd)){
				easyToast(LoginActivity.this, "���벻��Ϊ��");
			}else{
				new Thread(new Runnable() {
					@Override
					public void run() {
						// �����߳��н��������������
						final String state = LoginUtils.loginOfPost(username, pwd,role);
						runOnUiThread(new  Runnable() {//�������߳�UI
							public void run() {
								
								if("��¼�ɹ�".equals(state)){
									Intent intent = new Intent(LoginActivity.this, MainActivity.class);
									intent.putExtra("username", username);
									intent.putExtra("pwd", pwd);
									intent.putExtra("role", role);
									int isRem_Atl;
									//�洢checkbox״̬
									if(!cb_rem_pwd.isChecked()){
										isRem_Atl = 0;//������û��ѡ
									}else if(cb_autoLogin.isChecked()){
										isRem_Atl = 2;//��������ѡ��
									}else{
										isRem_Atl = 1;//ֻ��ѡ�ˡ���ס���롱
									}
									if(LoginUtils.saveUserInfo(LoginActivity.this, username, pwd,role,isRem_Atl)){
										Log.i(tag, "������Ϣ�ɹ�");
									}else{
										Log.i(tag, "������Ϣʧ��");
									}
									//Ϊ������һ��activity�а����ؼ�ʱ��������һ��activity
									finish();
									//��ת
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
			.setTitle("��ʾ")
			.setMessage("����ϵ����˻�����Ա���в�ѯ�����")
			.setPositiveButton("��֪����", null)
			.show();
			break;
		case R.id.ib_name:
			login_name.setText("");//����ʺŵ�EditText
			break;
		case R.id.ib_pwd:
			login_pwd.setText("");//��������EditText
			break;
		default:
			break;
		}
	}
	/**
	 * �Զ���Toast
	 */
	private Toast toast;
	public void easyToast(Context context,String message){
//		Toast.makeText(context, message, 0).show();
		//���Զ����xmlת����view�ؼ�
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.my_toast, null);
		TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
		ImageView iv_toast = (ImageView) view.findViewById(R.id.iv_toast);
		//��ֹtoast�ظ���ʾ�����Ƿ�Ϊ���ж�
		if(toast==null){
			//���toastΪ�գ����½�һ��toast
			toast = new Toast(context);
			tv_toast.setText(message);
		}else{
			//��Ϊ��ֱ����������
			tv_toast.setText(message);
			
		}
		//����toast��ʾʱ��
		toast.setDuration(Toast.LENGTH_SHORT);
		//����toastλ��
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP,0, 100);//λ�ã�x�����꣬y������
		//��toast�����Զ�����ʽ
		toast.setView(view);
		//����message��������ͼƬ
		if(!"��¼�ɹ�".equals(message)){
			iv_toast.setImageResource(android.R.drawable.stat_notify_error);
		}else{
			iv_toast.setVisibility(View.INVISIBLE);
		}
		//��ʾtoast
		toast.show();
		
	}
}
