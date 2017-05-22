package com.hzr.cloudstation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hzr.cloudstation.dao.ExpressDao;
import com.hzr.cloudstation.entities.Express;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.MyToast;

import java.util.List;

public class ManageExpressActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btn_search;
    private EditText et_expid;
    private ListView lv_express;

    private User user;
    private List<Express> expressList;

    private ExpressDao eDao = new ExpressDao(ManageExpressActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_express);
        initView();
        initData();
        lv_express.setAdapter(new ExpressAdapter());
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressId = et_expid.getText().toString();
                if (TextUtils.isEmpty(expressId)){
                    MyToast.MyToast(ManageExpressActivity.this,"请输入运单号");
                }else{
                    expressList = eDao.queryByExpressId(expressId);
                    if (expressList == null){
                        MyToast.MyToast(ManageExpressActivity.this,"无符合条件的快件");
                    }
                    lv_express.setAdapter(new ExpressAdapter());

                }
            }
        });
    }
    //重写onActivityResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            expressList = eDao.queryAll();
            lv_express.setAdapter(new ExpressAdapter());
        }
    }

    //listview适配器
    class ExpressAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            if (expressList != null){
                return expressList.size();
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
                convertView = inflater.inflate(R.layout.my_express_item,null);
                holder.tv_expId = (TextView) convertView.findViewById(R.id.tv_myExpId);
                holder.tv_recName = (TextView) convertView.findViewById(R.id.tv_myRecName);
                holder.tv_state = (TextView) convertView.findViewById(R.id.tv_myState);
                holder.tv_currentStation = (TextView) convertView.findViewById(R.id.tv_currentStation);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder) convertView.getTag();
            }
            String state = getState(expressList.get(position).getState());
            String currentStation = expressList.get(position).getCurrentStation();
            String station;
            if (currentStation != null){
                station = "\n当前站点："+ currentStation;
            }else{
                station = "";
            }
            holder.tv_expId.setText("运单号："+expressList.get(position).getExpressId());
            holder.tv_state.setText(state);
            holder.tv_recName.setText("目的地："+expressList.get(position).getEndPoint());
            holder.tv_currentStation.setText("收件地址："
                    + expressList.get(position).getEndPoint()
                    + station);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(ManageExpressActivity.this, ExpressDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("express",expressList.get(position));
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
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
                return "已收货";
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
    //载入控件
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_mae);
        btn_search = (Button) findViewById(R.id.btn_searchById);
        et_expid = (EditText) findViewById(R.id.et_searchExpId);
        lv_express = (ListView) findViewById(R.id.lv_manExp);
        if (toolbar != null) {
            toolbar.setTitle("快递管理");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManageExpressActivity.this.finish();
                }
            });
        }

    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
        expressList = eDao.queryAll();

    }
}
