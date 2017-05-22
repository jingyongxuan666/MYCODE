package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;

import java.text.SimpleDateFormat;
import java.util.Date;
//注册界面后台
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ClearEditText et_name;//用户名
    private ClearEditText et_pwd;//密码
    private ClearEditText et_confirmPwd;//确认密码
    private Button btn_register;//注册按钮
    private CheckBox cb_agree;//勾选框
    private String textName;
    private String textPwd;
    private String textConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        setListener();
    }

    @Override
    protected void onStart() {
        beforeAllFillIn();
        super.onStart();
    }

    //初始化组件
    private void initView() {
        et_name = (ClearEditText) findViewById(R.id.et_newName);
        et_pwd = (ClearEditText) findViewById(R.id.et_newPwd);
        et_confirmPwd = (ClearEditText) findViewById(R.id.et_confirmPwd);
        btn_register = (Button) findViewById(R.id.btn_reg);
        cb_agree = (CheckBox) findViewById(R.id.cb_agree);
    }

    //设置监听器
    private void setListener() {

        et_name.addTextChangedListener(watcher);
        et_pwd.addTextChangedListener(watcher);
        et_confirmPwd.addTextChangedListener(watcher);
        btn_register.setOnClickListener(this);
        cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ifRegisterBtnCanBeClicked();
                }else {
                    beforeAllFillIn();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        String name = et_name.getText();
        String pwd = et_pwd.getText();
        String confirmPwd = et_confirmPwd.getText();
        UserDao uDao = new UserDao(RegisterActivity.this);
        User user = uDao.queryItemByName(name);
        if (user != null){
            if (user.getUserName().equals(name)){
//                Toast.makeText(getBaseContext(),"该用户名已被注册！", Toast.LENGTH_SHORT).show();
                MyToast.MyToast(getBaseContext(),"该用户名已被注册！");
            }
        }else{
            if (!pwd.equals(confirmPwd)){
//                Toast.makeText(getBaseContext(),"两次输入的密码不同！", Toast.LENGTH_SHORT).show();
                MyToast.MyToast(getBaseContext(),"两次输入的密码不同！");
            }else{


                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String userId = sdf.format(new Date());
                long result = uDao.insert(new User(userId,name,pwd,null,null));
                if (result > 0){
//                    Toast.makeText(getBaseContext(),"注册成功，请登录！", Toast.LENGTH_LONG).show();
                    MyToast.MyToast(getBaseContext(),"注册成功，请登录!");
                    RegisterActivity.this.finish();
                }else{
//                    Toast.makeText(getBaseContext(),"注册失败！", Toast.LENGTH_LONG).show();
                    MyToast.MyToast(getBaseContext(),"注册失败！");
                }

            }
        }

    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ifRegisterBtnCanBeClicked();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    //如果编辑框都不为空而且同意协议注册按钮就变为可点击
    //否则不可点击
    private void ifRegisterBtnCanBeClicked() {
        textName = et_name.getText().toString();
        textPwd = et_pwd.getText().toString();
        textConfirm = et_confirmPwd.getText().toString();
        if (!TextUtils.isEmpty(textName)
                && !TextUtils.isEmpty(textPwd)
                && !TextUtils.isEmpty(textConfirm)
                && cb_agree.isChecked()){
            afterAllFillIn();
        }else{
            beforeAllFillIn();
        }
    }

    private void beforeAllFillIn() {
        btn_register.setTextColor(Color.GRAY);
        btn_register.setClickable(false);
    }

    private void afterAllFillIn() {
        btn_register.setTextColor(Color.WHITE);
        btn_register.setClickable(true);
    }

}
