package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;
import com.hzr.cloudstation.utils.LoginUtils;

import java.io.Serializable;
import java.util.List;
//后台
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register;
    private Button btn_login;
    private com.hzr.cloudstation.myView.ClearEditText et_name;
    private com.hzr.cloudstation.myView.ClearEditText et_pwd;
    private String userName;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        setListener();


    }

    @Override
    protected void onStart() {

        userName = et_name.getText().toString();
        password = et_pwd.getText().toString();
        //如果用户名或密码为空，登录按钮则不能点击
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            btn_login.setClickable(false);
            btn_login.setTextColor(Color.GRAY);
        }
        //如果上次登录成功，则直接进行用户检测
        List<User> userList = LoginUtils.getUserInfo(LoginActivity.this);
        if (userList != null){
            String uName = userList.get(0).getUserName();
            String uPwd = userList.get(0).getPassword();
            login(uName,uPwd);
        }
        //插入管理员帐号
        addManager();
        super.onStart();

    }

    //载入控件
    private void initView(){
        //注册按钮
        btn_register = (Button) findViewById(R.id.btn_register);
        //登录按钮
        btn_login = (Button) findViewById(R.id.btn_login);
        //用户名编辑框
        et_name = (ClearEditText) findViewById(R.id.et_uName);
        //密码编辑框
        et_pwd = (ClearEditText) findViewById(R.id.et_pwd);
    }
    //设置监听
    private void setListener(){
        //登录按钮点击事件监听
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        //用户名和密码编辑框内容改变监听
        et_name.addTextChangedListener(watcher);
        et_pwd.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String name = et_name.getText();
                String pwd = et_pwd.getText();
                login(name,pwd);
                break;

        }
    }
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            userName = et_name.getText().toString();
            password = et_pwd.getText().toString();
            //用户名和密码均不为空的时候登录按钮才可以点击
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){
                btn_login.setClickable(true);
                btn_login.setTextColor(Color.WHITE);
            }else{
                btn_login.setClickable(false);
                btn_login.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void login(String name,String pwd){
        UserDao uDao = new UserDao(LoginActivity.this);
        User user = uDao.queryItemByName(name);
        if (user != null){
            if (!name.equals(user.getUserName()) || !pwd.equals(user.getPassword())) {
                //Toast.makeText(getBaseContext(),"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                MyToast.MyToast(getBaseContext(),"用户名或密码错误!");
            }else{
//                        Toast.makeText(getBaseContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                MyToast.MyToast(getBaseContext(),"登录成功!");
                Intent toMain = new Intent(LoginActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                toMain.putExtras(bundle);
                startActivity(toMain);
                //保存登录信息
                LoginUtils.SaveUserInfo(LoginActivity.this,user);
                LoginActivity.this.finish();
            }
        }else{
//                    Toast.makeText(getBaseContext(),"不存在此用户！",Toast.LENGTH_SHORT).show();
            MyToast.MyToast(getBaseContext(),"用户名或密码错误!");
        }
    }
    private void addManager(){
        UserDao dao = new UserDao(LoginActivity.this);
        User user = dao.queryItemByName("admin");
        if (user == null){
            dao.insert(new User("1","admin","admin","管理员",null));
        }
    }
}
