package com.hzr.cloudstation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hzr.cloudstation.dao.EmployeeDao;
import com.hzr.cloudstation.dao.ExpressDao;
import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.Employee;
import com.hzr.cloudstation.entities.Express;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.MyToast;

import java.util.List;

public class MyExpressActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv_myExpList;
    private TextView tv_isOrNull;
    private RadioButton rb_all;
    private RadioButton rb_pending;
    private RadioButton rb_handing;
    private RadioButton rb_solved;
    private RadioButton rb_abate;


    private User user;
    private List<Express> expList;
    private ExpressDao eDao = new ExpressDao(MyExpressActivity.this);
    private EmployeeDao emDao = new EmployeeDao(MyExpressActivity.this);
    private String[] choiceItem = {"查看详情","取消订单"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_express);
        initView();
        initData();

        lv_myExpList.setAdapter(new myExpAdapter());
        rb_all.setChecked(true);

        rb_all.setOnCheckedChangeListener(checkedChange);
        rb_pending.setOnCheckedChangeListener(checkedChange);
        rb_handing.setOnCheckedChangeListener(checkedChange);
        rb_solved.setOnCheckedChangeListener(checkedChange);
        rb_abate.setOnCheckedChangeListener(checkedChange);

    }
    CompoundButton.OnCheckedChangeListener checkedChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (rb_all.isChecked()){
                expList = eDao.queryItemByIdandState(user.getUserId(),-2);
                lv_myExpList.setAdapter(new myExpAdapter());
            }else if (rb_pending.isChecked()){
                expList = eDao.queryItemByIdandState(user.getUserId(),0);
                lv_myExpList.setAdapter(new myExpAdapter());
            }else if (rb_handing.isChecked()){
                expList = eDao.queryItemByIdandState(user.getUserId(),1);
                lv_myExpList.setAdapter(new myExpAdapter());
            }else if (rb_solved.isChecked()){
                expList = eDao.queryItemByIdandState(user.getUserId(),2);
                lv_myExpList.setAdapter(new myExpAdapter());
            }else if (rb_abate.isChecked()){
                expList = eDao.queryItemByIdandState(user.getUserId(),-1);
                lv_myExpList.setAdapter(new myExpAdapter());
            }
            if (expList == null){
                tv_isOrNull.setText("没有符合分类的项目");
            }else{
                tv_isOrNull.setText("");
            }
        }
    };
    class myExpAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            if (expList != null){
                return expList.size();
            }else{
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            viewHolder holder;
            if (convertView == null){
                holder = new viewHolder();
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_express_item,null);
                holder.tv_expId = (TextView) convertView.findViewById(R.id.tv_myExpId);
                holder.tv_recName = (TextView) convertView.findViewById(R.id.tv_myRecName);
                holder.tv_state = (TextView) convertView.findViewById(R.id.tv_myState);
                holder.tv_currentStation = (TextView) convertView.findViewById(R.id.tv_currentStation);
                convertView.setTag(holder);

            }else{
                holder = (viewHolder) convertView.getTag();
            }
            holder.tv_expId.setText("运单号："+expList.get(position).getExpressId());
            holder.tv_recName.setText("收件人："+expList.get(position).getReceiverName());

            final String state = getState(expList.get(position).getState());
            holder.tv_state.setText(state);
            if (!state.equals("待处理")){
                holder.tv_currentStation.setText("当前到达站点："
                        +expList.get(position).getCurrentStation());
            }
            //点击快递进行操作
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyExpressActivity.this);
                    builder.setTitle("请选择操作");
                    builder.setItems(choiceItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    String empId = expList.get(position).getManagerId();
                                    String empMsg;
                                    if (empId != null){
                                        Employee emp = emDao.queryItemById(empId);
                                        if (emp != null){
                                            empMsg = "\n派件员："+emp.getEmpName()+"  "+emp.getTel();
                                        }else {
                                            empMsg = "";
                                        }

                                    }else {
                                        empMsg = "";
                                    }
                                    LayoutInflater inflater = LayoutInflater.from(MyExpressActivity.this);
                                    View view = inflater.inflate(R.layout.my_express_detail,null);
                                    TextView tv_detail = (TextView) view.findViewById(R.id.tv_detail);
                                    tv_detail.setText("订单编号："+expList.get(position).getExpressId()
                                            +"\n收件人："+expList.get(position).getReceiverName()
                                            +"\n收件人电话："+expList.get(position).getReceiverTel()
                                            +"\n订单状态："+state
                                            +"\n当前到达站点："+expList.get(position).getCurrentStation()
                                            +"\n寄件人："+expList.get(position).getSenderName()
                                            +"\n寄件人电话："+expList.get(position).getSenderTel()
                                            +empMsg);

                                    new AlertDialog.Builder(MyExpressActivity.this)
                                            .setTitle("订单详情")
                                    .setView(view).setPositiveButton("我知道了",null).show();
                                    break;
                                case 1:
                                    if (state.equals("待处理")){
                                        long updateResult = eDao.updateState(
                                                expList.get(position).getExpressId(),-1);
                                        if (updateResult == 1){
                                            MyToast.MyToast(MyExpressActivity.this,"取消成功");

                                        }else {
                                            MyToast.MyToast(MyExpressActivity.this,"取消失败");
                                        }
                                    }else{
                                        Toast.makeText(MyExpressActivity.this,
                                                "订单已开始运输或者已经作废，无法取消",Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                        }
                    });
                    builder.show();
                }
            });
            return convertView;
        }
    }

    private String getState(int state) {
        switch (state){
            case 0:
                return "待处理";
            case 1:
                return "已发出";
            case 2:
                return "收件人已收货";
            case -1:
                return "已作废";
        }
        return null;
    }

    private class viewHolder{
        private TextView tv_expId;
        private TextView tv_recName;
        private TextView tv_state;
        private TextView tv_currentStation;
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");

        expList = eDao.queryItemByIdandState(user.getUserId(),-2);
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_me);
        if (toolbar != null) {
            toolbar.setTitle("我的快递");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyExpressActivity.this.finish();
                }
            });
        }
        lv_myExpList = (ListView) findViewById(R.id.lv_myExpress);
        rb_all = (RadioButton) findViewById(R.id.rb_all);
        rb_pending = (RadioButton) findViewById(R.id.rb_pending);
        rb_handing = (RadioButton) findViewById(R.id.rb_handing);
        rb_solved = (RadioButton) findViewById(R.id.rb_solved);
        rb_abate = (RadioButton) findViewById(R.id.rb_abate);
        tv_isOrNull = (TextView) findViewById(R.id.tv_isOrNull);
        rb_solved.setVisibility(View.GONE);
    }
}
