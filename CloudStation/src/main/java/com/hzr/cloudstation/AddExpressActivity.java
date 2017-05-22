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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hzr.cloudstation.dao.ExpressDao;
import com.hzr.cloudstation.entities.Express;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.MyToast;
import com.hzr.cloudstation.utils.TelCheck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddExpressActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText et_senderName,et_senderTel,et_senderAddress,
                     et_recName,et_recTel,et_recAddress;
    private Button btn_addExp;
    private TextView tv_expId,tv_fee;
    private Spinner spinner;

    private User user;
    private String expressId;

    private int SENDER  = 1;
    private int RECEIVER = 2;
    private int senderOrReceiver = 0;
    private List<String> companies = null;
    private HashMap<String,Integer> fee;
    private String company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_express);
        initView();
        initData();

        if (toolbar != null) {
            toolbar.setTitle("添加快递");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddExpressActivity.this.finish();
                }
            });
        }
        //设置下拉列表
        spinnerSet();
        if (user.getRealName() != null){
            et_senderName.setText(user.getRealName());
        }
        if (user.getTel() != null){
            et_senderTel.setText(user.getTel());
        }
        tv_expId.setText("快递单号："+expressId);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
                expressId = sdf.format(new Date());
                company = companies.get(position);
                tv_fee.setText(fee.get(company)+"元/千克");
                tv_expId.setText("快递单号："+fee.get(company)+expressId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_recAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    senderOrReceiver = RECEIVER;
                    Intent intent2 = new Intent();
                    intent2.setClass(AddExpressActivity.this,AddressManageActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("user",user);
                    intent2.putExtras(bundle2);
                    intent2.putExtra("type",senderOrReceiver);
                    startActivityForResult(intent2,0);
                }
            }
        });
        et_senderAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    senderOrReceiver = SENDER;
                    Intent intent1 = new Intent();
                    intent1.setClass(AddExpressActivity.this,AddressManageActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("user",user);
                    intent1.putExtras(bundle1);
                    intent1.putExtra("type",senderOrReceiver);
                    startActivityForResult(intent1,0);
                }
            }
        });
        btn_addExp.setOnClickListener(this);

    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_aex);
        et_recAddress = (EditText) findViewById(R.id.et_recAddress);
        et_recName = (EditText) findViewById(R.id.et_recName);
        et_recTel = (EditText) findViewById(R.id.et_recTel);
        et_senderName = (EditText) findViewById(R.id.et_senderName);
        et_senderTel = (EditText) findViewById(R.id.et_senderTel);
        et_senderAddress = (EditText) findViewById(R.id.et_senderAddress);
        btn_addExp = (Button) findViewById(R.id.btn_addExp);
        tv_expId = (TextView) findViewById(R.id.tv_expId);
        spinner = (Spinner) findViewById(R.id.sp_company);
        tv_fee = (TextView) findViewById(R.id.tv_fee);
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String address = data.getStringExtra("address");
            if (senderOrReceiver == SENDER){
                et_senderAddress.setText(address);
            }else if(senderOrReceiver == RECEIVER){
                et_recAddress.setText(address);
            }
        }
    }
    private void spinnerSet(){
        spinner.setPrompt("-请选择快递公司-");
        companies = new ArrayList<>();
        companies.add("顺丰");
        companies.add("圆通");
        companies.add("韵达");
        companies.add("申通");
        ArrayAdapter adapter = new ArrayAdapter(AddExpressActivity.this,
                android.R.layout.simple_spinner_item,companies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //设置单价
        fee = new HashMap<>();
        fee.put("顺丰",15);
        fee.put("圆通",9);
        fee.put("韵达",7);
        fee.put("申通",8);

    }


    @Override
    public void onClick(View v) {
        String currentUserId = user.getUserId();
        String senderName = et_senderName.getText().toString();
        String senderTel = et_senderTel.getText().toString();
        String senderAddress = et_senderAddress.getText().toString();
        String recName = et_recName.getText().toString();
        String recTel = et_recTel.getText().toString();
        String recAddress = et_recAddress.getText().toString();
        boolean recTelIsTel = TelCheck.checkTel(recTel);
        boolean senderTelIsTel = TelCheck.checkTel(senderTel);
        if (TextUtils.isEmpty(senderName) || TextUtils.isEmpty(senderTel) ||
                TextUtils.isEmpty(senderAddress) || TextUtils.isEmpty(recName) ||
                TextUtils.isEmpty(recTel) || TextUtils.isEmpty(recAddress)){
            MyToast.MyToast(AddExpressActivity.this,"请输入完整信息");
        }else{
            if (recTelIsTel && senderTelIsTel){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String addTime = sdf.format(new Date());
                ExpressDao exDao = new ExpressDao(AddExpressActivity.this);
                Express express = new Express();
                express.setExpressId(fee.get(company)+expressId);
                express.setSenderName(senderName);
                express.setSenderTel(senderTel);
                express.setStartPoint(senderAddress);
                express.setEndPoint(recAddress);
                express.setReceiverName(recName);
                express.setReceiverTel(recTel);
                express.setAddTime(addTime);
                express.setCompany(company);
                express.setState(0);
                express.setUserId(user.getUserId());
                long insertResult = exDao.insert(express);
                if (insertResult > 0){
                    Toast.makeText(AddExpressActivity.this,
                            "添加成功，请到“我的快递”菜单中进行其他操作",Toast.LENGTH_LONG).show();
                    AddExpressActivity.this.finish();

                }else{
                    MyToast.MyToast(AddExpressActivity.this,"添加失败");
                }
            }else {
                MyToast.MyToast(AddExpressActivity.this,"请输入正确的手机号");
            }
        }
    }
}
