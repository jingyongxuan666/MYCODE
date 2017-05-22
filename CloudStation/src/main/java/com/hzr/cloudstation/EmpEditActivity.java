package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hzr.cloudstation.dao.EmployeeDao;
import com.hzr.cloudstation.entities.Employee;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;

public class EmpEditActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btn_update;
    private ClearEditText et_name;
    private ClearEditText et_tel;

    private Employee emp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_edit);

        initView();
        initData();
        initState();
        if (toolbar != null) {
            toolbar.setTitle("修改资料");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmpEditActivity.this.finish();
                }
            });
        }
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText();
                String tel = et_tel.getText();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(tel)){
                    boolean checkTelNum = tel.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
                    if (checkTelNum){
                        EmployeeDao eDao = new EmployeeDao(EmpEditActivity.this);
                        long updateResult = eDao.update(emp.getEmpId(),new Employee("",name,tel));
                        if (updateResult == 1){
                            MyToast.MyToast(EmpEditActivity.this,"编辑成功");
                            setResult(RESULT_OK);
                            EmpEditActivity.this.finish();
                        }else{
                            MyToast.MyToast(EmpEditActivity.this,"编辑失败");
                        }
                    }else{
                        MyToast.MyToast(EmpEditActivity.this,"请输入正确的手机号");
                    }

                }else{
                    MyToast.MyToast(EmpEditActivity.this,"请填写完整资料");
                }
            }
        });
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_ee);
        btn_update = (Button) findViewById(R.id.btn_confirm_update);
        et_name = (ClearEditText) findViewById(R.id.et_edit_empName);
        et_tel = (ClearEditText) findViewById(R.id.et_edit_empTel);
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emp = (Employee) bundle.getSerializable("emp");
    }
    //初始状态
    private void initState(){
        et_name.setText(emp.getEmpName());
        et_tel.setText(emp.getTel());
        et_name.setGravity(Gravity.END);
        et_tel.setGravity(Gravity.END);
    }

}
