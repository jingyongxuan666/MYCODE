package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hzr.cloudstation.dao.EmployeeDao;
import com.hzr.cloudstation.entities.Employee;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertEmpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btn_add;
    private ClearEditText et_empName;
    private ClearEditText et_empTel;
    private TextView tv_empId;

    private String empId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_emp);
        initView();
        //生成员工编号
        newEmpId();
        if (toolbar != null) {
            toolbar.setTitle("添加员工");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InsertEmpActivity.this.finish();
                }
            });
        }
        tv_empId.setText("编号：" + empId);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_empName.getText();
                String tel = et_empTel.getText();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(tel)){
                    boolean checkTelNum = tel.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
                    if (checkTelNum){
                        EmployeeDao eDao = new EmployeeDao(InsertEmpActivity.this);
                        long insertResult = eDao.insert(new Employee(empId,name,tel));
                        if (insertResult > 0){
                            MyToast.MyToast(InsertEmpActivity.this,"添加成功");
                            setResult(RESULT_OK);
                            InsertEmpActivity.this.finish();
                        }else{
                            MyToast.MyToast(InsertEmpActivity.this,"添加失败");
                        }
                    }else{
                        MyToast.MyToast(InsertEmpActivity.this,"请输入正确的手机号");
                    }

                }else{
                    MyToast.MyToast(InsertEmpActivity.this,"请输入员工完整资料");
                }
            }
        });


    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_ie);
        btn_add = (Button) findViewById(R.id.btn_addEmp);
        tv_empId = (TextView) findViewById(R.id.tv_newEmpId);
        et_empName = (ClearEditText) findViewById(R.id.et_empName);
        et_empTel = (ClearEditText) findViewById(R.id.et_empTel);
    }
    private void newEmpId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        empId = sdf.format(date);
    }
}
