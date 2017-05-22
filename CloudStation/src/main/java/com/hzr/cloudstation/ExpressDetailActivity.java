package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hzr.cloudstation.R;
import com.hzr.cloudstation.dao.EmployeeDao;
import com.hzr.cloudstation.dao.ExpressDao;
import com.hzr.cloudstation.entities.Employee;
import com.hzr.cloudstation.entities.Express;
import com.hzr.cloudstation.myView.MyToast;
import com.hzr.cloudstation.myView.tvAndTv;

import java.util.HashMap;

public class ExpressDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private tvAndTv tt_expId,tt_recName,tt_recTel,tt_recAd,
                    tt_senderName,tt_senderTel,tt_senderAd,
                    tt_state,tt_type,tt_fee,tt_company,tt_addTime;
    private Toolbar toolbar;
    private EditText et_weight,et_curStation;
    private Button btn_save;
    private TextView tv_empMsg;

    private ExpressDao expDao;

    private Express express;
    private int expressType;
    private float fee = 0;
    private HashMap<String,Integer> feeSort;
    private int weight;
    private String empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);
        initView();
        initData();
        initState();

        et_weight.addTextChangedListener(textWatcher);
//        et_emp.setOnClickListener(this);
        tv_empMsg.setOnClickListener(this);
        btn_save.setOnClickListener(this);

    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                String stringWeight = et_weight.getText().toString().trim();
                if (!TextUtils.isEmpty(stringWeight)){
                    weight = Integer.parseInt(stringWeight);
                    fee = weight * feeSort.get(express.getCompany());
                    String type = "";
                    if (weight > 0 && weight <= 10){
                        type = "小型快件";
                        expressType = 1;
                    }else if (weight > 10 && weight <= 15){
                        type = "中型快件";
                        expressType = 2;
                    }else if (weight > 15){
                        type = "大型快件";
                        expressType = 3;
                    }else{
                        type = "未输入重量";
                    }
                    tt_type.setTv2Text(type);
                    tt_fee.setTv2Text(fee + "元");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
    private void initState(){
        tt_expId.setTv1Text("运单号：");
        tt_addTime.setTv1Text("添加时间：");
        tt_recName.setTv1Text("姓名：");
        tt_recTel.setTv1Text("电话：");
        tt_recAd.setTv1Text("地址：");
        tt_senderName.setTv1Text("姓名：");
        tt_senderTel.setTv1Text("电话：");
        tt_senderAd.setTv1Text("地址：");
        tt_state.setTv1Text("状态：");
        tt_type.setTv1Text("类型：");
        tt_fee.setTv1Text("费用：");
        tt_company.setTv1Text("快递公司：");
        tt_expId.setTv2Text(express.getExpressId());
        tt_company.setTv2Text(express.getCompany());
        tt_addTime.setTv2Text(express.getAddTime());
        tt_recName.setTv2Text(express.getReceiverName());
        tt_recTel.setTv2Text(express.getReceiverTel());
        tt_recAd.setTv2Text(express.getEndPoint());
        tt_senderName.setTv2Text(express.getSenderName());
        tt_senderTel.setTv2Text(express.getSenderTel());
        tt_senderAd.setTv2Text(express.getStartPoint());
        if (express.getCurrentStation() != null){
            et_curStation.setText(express.getCurrentStation());
        }
        tt_fee.setTv2Text(express.getFee()+"元");


    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_ed);
        tt_expId = (tvAndTv) findViewById(R.id.tt_expId);
        tt_recName = (tvAndTv) findViewById(R.id.tt_recName);
        tt_recTel = (tvAndTv) findViewById(R.id.tt_recTel);
        tt_recAd = (tvAndTv) findViewById(R.id.tt_recAddress);
        tt_senderName = (tvAndTv) findViewById(R.id.tt_senderName);
        tt_senderTel = (tvAndTv) findViewById(R.id.tt_senderTel);
        tt_senderAd = (tvAndTv) findViewById(R.id.tt_senderAddress);
        tt_state = (tvAndTv) findViewById(R.id.tt_state);
        tt_type = (tvAndTv) findViewById(R.id.tt_type);
        tt_fee = (tvAndTv) findViewById(R.id.tt_fee);
//        et_emp = (EditText) findViewById(R.id.et_choseEmp);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_curStation = (EditText) findViewById(R.id.et_curStation);
        btn_save = (Button) findViewById(R.id.btn_updateExpMsg);
        tv_empMsg = (TextView) findViewById(R.id.tv_empMsg);
        tt_company = (tvAndTv) findViewById(R.id.tt_company);
        tt_addTime = (tvAndTv) findViewById(R.id.tt_addTime);
        if (toolbar != null) {
            toolbar.setTitle("快递详情");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpressDetailActivity.this.finish();
                }
            });
        }
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        express = (Express) bundle.getSerializable("express");
        empId = express.getExpressId();
        //类型
        int type = express.getExpressType();
        tt_type.setTv2Text(turnType(type));
        //设置状态
        String state = getState(express.getState());
        tt_state.setTv2Text(state);
        //重量
        float weight = express.getExpressWeight();
        if (weight != 0){
            //如果重量不等0，则设置到对应edittext并且编辑框变为不可编辑
            et_weight.setText(weight+"");
            et_weight.setEnabled(false);
        }
        //快递员信息
        String empMsg = getEmpMsg(express.getManagerId());

//            et_emp.setVisibility(View.GONE);
        tv_empMsg.setText(empMsg);
        //公司
        feeSort = new HashMap<>();
        feeSort.put("顺丰",15);
        feeSort.put("圆通",9);
        feeSort.put("韵达",7);
        feeSort.put("申通",8);

    }
    //快递员信息获取
    private String getEmpMsg(String managerId) {
        String name = null;
        String tel = null;
        try {
            if (managerId != null){
                EmployeeDao eDao = new EmployeeDao(ExpressDetailActivity.this);
                Employee employee = eDao.queryItemById(managerId);
                name = employee.getEmpName();
                tel = employee.getTel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "点击选择快递员";
        }

        return name +" "+ tel;
    }
    //物流状态获取
    private String getState(int state) {
        switch (state){
            case 0:
                return "待处理";
            case 1:
                return "已发出";
            case 2:
                return "已收货";
            case -1:
                return "已作废";
        }
        return null;
    }
    //将快件类型准换成文字
    private String turnType(int type){
        String stringType = "";
        switch (type){
            case 1:
                stringType ="小型快递";
                break;
            case 2:
                stringType ="中型快递";
                break;
            case 3:
                stringType ="大型快递";
                break;
        }
        return stringType;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_empMsg:
                Intent intent = new Intent();
                intent.setClass(ExpressDetailActivity.this,ChoseEmpActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.btn_updateExpMsg:
                String stringWeight = et_weight.getText().toString().trim();
                String stringStation = et_curStation.getText().toString();
                if (TextUtils.isEmpty(stringWeight)){
                    MyToast.MyToast(ExpressDetailActivity.this,"请输入快件重量");
                }else if(TextUtils.isEmpty(empId)){
                    MyToast.MyToast(ExpressDetailActivity.this,"请选择员工");
                }else if(TextUtils.isEmpty(stringStation)){
                    MyToast.MyToast(ExpressDetailActivity.this,"请输入当前站点");
                }else{
                    Express upExpress = new Express();
                    expDao = new ExpressDao(ExpressDetailActivity.this);
                    upExpress.setState(1);
                    upExpress.setFee(fee);
                    upExpress.setCurrentStation(stringStation);
                    upExpress.setManagerId(empId);
                    upExpress.setExpressWeight(weight);
                    upExpress.setExpressType(expressType);
                    long updateResult = expDao.updateMsg(express.getExpressId(),upExpress);
                    if (updateResult == 1){
                        MyToast.MyToast(ExpressDetailActivity.this,"更新成功");
                        setResult(RESULT_OK);
                        ExpressDetailActivity.this.finish();
                    }else {
                        MyToast.MyToast(ExpressDetailActivity.this,"更新失败");
                    }

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Employee emp = (Employee) bundle.getSerializable("emp");
            empId = emp.getEmpId();
            tv_empMsg.setText(emp.getEmpName() + " " + emp.getTel());
        }
    }
}
