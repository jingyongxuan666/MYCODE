package com.hzr.cloudstation;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hzr.cloudstation.dao.EmployeeDao;
import com.hzr.cloudstation.entities.Employee;

import java.util.List;

public class ChoseEmpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv_emp;

    private List<Employee> empList;
    private EmployeeDao eDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_emp);
        initView();
        initData();
        lv_emp.setAdapter(new EmpAdapter());
        lv_emp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("emp",empList.get(position));
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                ChoseEmpActivity.this.finish();
            }
        });
    }
    class EmpAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if (empList != null){
                return empList.size();
            }else {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            viewHolder holder;
            if (convertView == null){
                holder = new viewHolder();
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.employee_item,null);
                holder.tv_empId = (TextView) convertView.findViewById(R.id.tv_empId);
                holder.tv_empName = (TextView) convertView.findViewById(R.id.tv_empName);
                holder.btn_edit = (Button) convertView.findViewById(R.id.btn_empEdit);
                holder.btn_del = (Button) convertView.findViewById(R.id.btn_empDel);
                holder.tv_empTel = (TextView) convertView.findViewById(R.id.tv_empTel);
                convertView.setTag(holder);
            }else {
                holder = (viewHolder) convertView.getTag();
            }
            holder.btn_del.setVisibility(View.GONE);
            holder.btn_edit.setVisibility(View.GONE);
            holder.tv_empId.setText("员工编号："+empList.get(position).getEmpId());
            holder.tv_empName.setText(empList.get(position).getEmpName());
            holder.tv_empTel.setText(empList.get(position).getTel());
            return convertView;
        }
    }
    private class viewHolder{
        private TextView tv_empId;
        private TextView tv_empName;
        private Button btn_edit;
        private Button btn_del;
        private TextView tv_empTel;
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle_ce);
        lv_emp = (ListView) findViewById(R.id.lv_choseEmp);
        if (toolbar != null) {
            toolbar.setTitle("选择快递员");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChoseEmpActivity.this.finish();
                }
            });
        }
    }
    private void initData(){
        eDao = new EmployeeDao(ChoseEmpActivity.this);
        empList = eDao.queryAll();
    }
}
