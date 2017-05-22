package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;

public class ChangePwdActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ClearEditText et_prePwd;
    private ClearEditText et_newPwd;
    private ClearEditText et_confirmNewPwd;
    private Button btn_changePwd;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        //载入控件
        initView();
        //接收数据
        initData();
        //设置标题栏
        if (toolbar != null) {
            toolbar.setTitle("修改密码");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangePwdActivity.this.finish();
                }
            });
        }
        btn_changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prePwd = et_prePwd.getText();
                String newPwd = et_newPwd.getText();
                String confirmNewPwd = et_confirmNewPwd.getText();
                if (prePwd.equals(user.getPassword())){
                    if(newPwd.equals(confirmNewPwd)){
                        UserDao uDao = new UserDao(ChangePwdActivity.this);
                        long updatePwdResult = uDao.updatePwd(user.getUserId(),newPwd);
                        if (updatePwdResult == 1){
                            MyToast.MyToast(ChangePwdActivity.this,"修改成功");
                            ChangePwdActivity.this.finish();
                        }else{
                            MyToast.MyToast(ChangePwdActivity.this,"修改失败");
                        }
                    }else{
                        MyToast.MyToast(ChangePwdActivity.this,"两次输入的新密码不一样");
                    }
                }else {
                    MyToast.MyToast(ChangePwdActivity.this,"请输入正确的原是秘密");
                }
            }
        });

    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_cp);
        et_prePwd = (ClearEditText) findViewById(R.id.et_prePwd);
        et_newPwd = (ClearEditText) findViewById(R.id.et_newPwd);
        et_confirmNewPwd = (ClearEditText) findViewById(R.id.et_confirmNewPwd);
        btn_changePwd = (Button) findViewById(R.id.btn_changePwd);
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
    }
}
