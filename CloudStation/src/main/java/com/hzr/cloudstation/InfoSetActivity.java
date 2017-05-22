package com.hzr.cloudstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;

public class InfoSetActivity extends AppCompatActivity {
    private ClearEditText et_realName;
    private ClearEditText et_userTel;
    private Button btn_saveInfo;
    private Toolbar toolbar;

    private User currentUser;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_set);
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_is);
        if (toolbar != null){
            toolbar.setTitle("资料设置");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ifInputContext();
                }
            });
        }
        //初始化控件
        initView();
        btn_saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newName = et_realName.getText();
                final String newTel = et_userTel.getText();

                boolean checkTelNum = newTel.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
                //判断是否为正确手机号
                if (checkTelNum){
                new AlertDialog.Builder(InfoSetActivity.this)
                        .setTitle("提示")
                        .setMessage("确认更新你的资料吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                long updateResult = userDao.update(currentUser.getUserId(),newName,newTel);
                                if (updateResult == 1){
                                    MyToast.MyToast(InfoSetActivity.this,"更新资料成功");
                                    InfoSetActivity.this.finish();
                                }else{
                                    MyToast.MyToast(InfoSetActivity.this,"更新资料失败");
                                }

                            }
                        })
                        .setNegativeButton("取消",null).show();
                }else{
                    MyToast.MyToast(InfoSetActivity.this,"请输入正确的手机号");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        Intent getIntent = getIntent();
        Bundle bundle = getIntent.getExtras();
        currentUser = (User) bundle.getSerializable("user");
        userDao = new UserDao(InfoSetActivity.this);
        User user = userDao.queryItemById(currentUser.getUserId());
        if (user.getRealName() != null || user.getTel() != null){
            et_realName.setText(user.getRealName());
            et_userTel.setText(user.getTel());
        }
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        ifInputContext();
    }

    private void initView(){
        et_realName = (ClearEditText) findViewById(R.id.et_realName);
        et_userTel = (ClearEditText) findViewById(R.id.et_userTel);
        btn_saveInfo = (Button) findViewById(R.id.btn_saveInfo);
    }
    private void ifInputContext(){
        String name = et_realName.getText();
        String tel = et_userTel.getText();
        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(tel)){
            new AlertDialog.Builder(InfoSetActivity.this)
                    .setTitle("提示")
                    .setMessage("确认放弃更新吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            InfoSetActivity.this.finish();
                        }
                    })
                    .setNegativeButton("取消",null).show();
        }else{
            InfoSetActivity.this.finish();
        }
    }
}
