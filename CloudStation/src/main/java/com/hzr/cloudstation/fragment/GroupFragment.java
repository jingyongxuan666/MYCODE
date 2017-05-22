package com.hzr.cloudstation.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hzr.cloudstation.EmpEditActivity;
import com.hzr.cloudstation.InsertEmpActivity;
import com.hzr.cloudstation.R;
import com.hzr.cloudstation.dao.EmployeeDao;
import com.hzr.cloudstation.entities.Employee;
import com.hzr.cloudstation.myView.MyToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzr on 2017/4/10.
 */
public class GroupFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_addEmp;
    private ListView lv_emp;
    private EditText et_searchEmp;
    private Button btn_search;

    private List<Employee> empList;
    private EmployeeDao empDao;
    public GroupFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null){
            container = (ViewGroup) view.getParent();
            if (container != null){
                container.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.fragment_group,null);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        btn_addEmp.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        lv_emp.setAdapter(new EmpAdapter());
    }
    private void initView(){
        btn_addEmp = (Button) view.findViewById(R.id.ib_addEmp);
        et_searchEmp = (EditText) view.findViewById(R.id.et_empSearch);
        lv_emp = (ListView) view.findViewById(R.id.lv_emp);
        btn_search = (Button) view.findViewById(R.id.btn_search);
    }
    private void initData(){
        empDao = new EmployeeDao(getActivity());
        empList = empDao.queryAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_addEmp:
                Intent intent = new Intent();
                intent.setClass(getActivity(), InsertEmpActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.btn_search:
                String condition = et_searchEmp.getText().toString();
                if (!TextUtils.isEmpty(condition)){
                    empList = empDao.queryByIdOrName(condition);
                    if (empList == null){
                        MyToast.MyToast(getActivity(),"没有符合条件的项目");
                    }
                    lv_emp.setAdapter(new EmpAdapter());
                }else{
                    empList = empDao.queryAll();
                    lv_emp.setAdapter(new EmpAdapter());
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1){
           empList = empDao.queryAll();
            lv_emp.setAdapter(new EmpAdapter());
        }
    }

    class EmpAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            if (empList == null){
                return 0;
            }else{
                return empList.size();
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
                LayoutInflater inflater = getActivity().getLayoutInflater();
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
            holder.tv_empId.setText("员工编号："+empList.get(position).getEmpId());
            holder.tv_empName.setText(empList.get(position).getEmpName());
            holder.tv_empTel.setText(empList.get(position).getTel());
            //删除按钮
            holder.btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("确认要删除<"+empList.get(position).getEmpName()+">的所有信息吗？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long delResult = empDao.deleteByEmpId(empList.get(position).getEmpId());
                                    if (delResult == 1){
                                        MyToast.MyToast(getActivity(),"删除成功");
                                        empList = empDao.queryAll();
                                        lv_emp.setAdapter(new EmpAdapter());
                                    }else{
                                        MyToast.MyToast(getActivity(),"删除失败");
                                    }
                                }
                            }).setNegativeButton("取消",null).show();
                }
            });
            //编辑按钮
            holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转至编辑界面
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), EmpEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("emp",empList.get(position));
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
                }
            });
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
}
