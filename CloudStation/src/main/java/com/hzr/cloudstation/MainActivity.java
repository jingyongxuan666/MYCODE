package com.hzr.cloudstation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.fragment.ExpressFragment;
import com.hzr.cloudstation.fragment.GroupFragment;
import com.hzr.cloudstation.fragment.MyselfFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private RadioGroup rg_menu;
    private RadioButton rb_express;
    private RadioButton rb_group;
    private RadioButton rb_myself;
    private Fragment frag_myInfo,frag_express,frag_emp,frag_current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        frag_emp = new GroupFragment();
        frag_myInfo = new MyselfFragment();
        frag_express = new ExpressFragment();
        setDefaultFragment(frag_express);
    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.getSerializable("user");
        String name = user.getUserName();
        String pwd = user.getPassword();
        UserDao userDao = new UserDao(this);
        User userInfo = userDao.queryItemByName("admin");
        //如果非管理员登录则不显示“员工”信息
        if (!name.equals("admin") && !pwd.equals(userInfo.getPassword())){
            rb_group.setVisibility(View.GONE );
        }
        super.onStart();
    }

    //初始化控件
    private void initView(){
        rg_menu = (RadioGroup) findViewById(R.id.rg_menuBar);
        rb_express = (RadioButton) findViewById(R.id.rb_express);
        rb_group = (RadioButton) findViewById(R.id.rb_group);
        rb_myself = (RadioButton) findViewById(R.id.rb_myself);
    }
    //初始化数据
    private void initData(){
        fm = getFragmentManager();
    }
    //设置监听
    private void initListener(){
        //radioButton改变监听
        rg_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //判断哪个radioButton被点击
                switch (checkedId){
                    case R.id.rb_express:
                        switchFragment(frag_express);
                        break;
                    case R.id.rb_group:
                        switchFragment(frag_emp);
                        break;
                    case R.id.rb_myself:
                        switchFragment(frag_myInfo);
                        break;
                }
            }
        });
    }
    //替换fragment
    private void replaceFragment(Fragment fragment){

    }
    //设置默认fragment
    private void setDefaultFragment(Fragment fragment){
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.ll_content,fragment);
        ft.commit();
        frag_current = fragment;
    }
    private void switchFragment(Fragment current){
        if (frag_current != current){
            FragmentTransaction ft = fm.beginTransaction();
            if (!current.isAdded()){
                ft.hide(frag_current);
                ft.add(R.id.ll_content,current);
                ft.commit();
            }else {
                ft.hide(frag_current);
                ft.show(current);
                ft.commit();
            }
            frag_current = current;
        }
    }

}
