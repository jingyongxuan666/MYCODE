package com.hzr.cloudstation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzr.cloudstation.R;
import com.hzr.cloudstation.dao.AddressDao;
import com.hzr.cloudstation.dao.UserDao;
import com.hzr.cloudstation.entities.Address;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.myView.ClearEditText;
import com.hzr.cloudstation.myView.MyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressManageActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;//返回按钮
    private ListView lv_address;//地址列表
    private Button btn_del;
    private Button btn_add;

    private List<Address> addressList;
    private AddressDao aDao = new AddressDao(AddressManageActivity.this);
    private User user;
    private boolean isMulChoice = false;//全局变量，是否长按并多选
    private List<String> isDelete = new ArrayList<>();
    private String currentUserId;//当前登录账户id
    private int senderOrReceiver = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沉浸式状态栏的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        //初始化控件
        initView();
        //初始化数据
        initData();

        if (toolbar != null) {
            toolbar.setTitle("地址管理");
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            //设置返回按钮图标
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_holo_light_am);
            //监听返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddressManageActivity.this.finish();
                }
            });
        }
        btn_del.setVisibility(View.GONE);
        btn_add.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        lv_address.setAdapter(new AddressAdapter(this));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.tb_myTitle);
        btn_del = (Button) findViewById(R.id.btn_del);
        lv_address = (ListView) findViewById(R.id.lv_address);
        btn_add = (Button) findViewById(R.id.btn_addAddress);
    }

    private void initData() {
        Intent getIntent = getIntent();
        Bundle bundle = getIntent.getExtras();
        user = (User) bundle.getSerializable("user");
        senderOrReceiver = getIntent.getIntExtra("type",0);
        currentUserId = user.getUserId();
        addressList = aDao.qureyAddress(currentUserId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addAddress:
                showMyDialog(AddressManageActivity.this);
                break;
            case R.id.btn_del:
                if (isDelete == null) {
                    Toast.makeText(AddressManageActivity.this, "请选择要删除的项目", Toast.LENGTH_SHORT);
                } else {
                    new AlertDialog.Builder(AddressManageActivity.this)
                            .setTitle("提示")
                            .setMessage("确定要删除吗")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long delResult = 0;
                                    for (int i = 0; i < isDelete.size(); i++) {
                                        delResult = aDao.deleteById(isDelete.get(i));
                                        if (delResult == 1) {
                                            MyToast.MyToast(AddressManageActivity.this, "删除成功");
                                        } else {
                                            MyToast.MyToast(AddressManageActivity.this, "删除失败");
                                        }
                                    }
                                    addressList = aDao.qureyAddress(currentUserId);
                                    lv_address.setAdapter(new AddressAdapter(AddressManageActivity.this));
                                }
                            }).setNegativeButton("取消",null).show();

                }
                break;
        }
    }

    class AddressAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        public HashMap<Integer, Integer> isVisible;
        public HashMap<Integer, Boolean> isCheck;

        public AddressAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            isVisible = new HashMap<>();
            isCheck = new HashMap<>();
            if (isMulChoice) {
                //如果长按多选，显示所有checkbox
                for (int i = 0; i < getCount(); i++) {
                    isVisible.put(i, View.VISIBLE);
                    isCheck.put(i, false);
                }
            } else {
                //反之不显示
                for (int i = 0; i < getCount(); i++) {
                    isVisible.put(i, View.GONE);
                    isCheck.put(i, false);
                }
            }
        }

        @Override
        public int getCount() {
            if (addressList != null) {
                return addressList.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return addressList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final viewHolder holder;
            if (convertView == null) {
                holder = new viewHolder();
                convertView = inflater.inflate(R.layout.address_item, null);
                holder.cb_address = (CheckBox) convertView.findViewById(R.id.cb_address);
                holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
                holder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
                convertView.setTag(holder);
            } else {
                holder = (viewHolder) convertView.getTag();
            }
            holder.tv_address.setText(addressList.get(position).getAddress());
            holder.cb_address.setChecked(isCheck.get(position));
            if (isVisible.get(position) == View.VISIBLE) {
                holder.cb_address.setVisibility(View.VISIBLE);
            } else {
                holder.cb_address.setVisibility(View.GONE);
            }
            holder.cb_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isMulChoice) {
                        //如果正在进行批量删除操作，单击选择要删除的项目
                        if (holder.cb_address.isChecked()) {
                            //被选中了就把地址id加入数组
                            isDelete.add(String.valueOf(addressList.get(position).getId()));
                        } else {
                            //取消勾选就将id移出数组
                            isDelete.remove(String.valueOf(addressList.get(position).getId()));
                        }
                    }
                }
            });
            //点击编辑按钮
            holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context = AddressManageActivity.this;
                    LayoutInflater inflater = LayoutInflater.from(context);
                    final View updateAddress = inflater.inflate(R.layout.address_input, null);
                    final ClearEditText et_address_input = (ClearEditText) updateAddress.findViewById(R.id.et_address_input);
                    et_address_input.setMaxLines(5);
                    et_address_input.setText(String.valueOf(holder.tv_address.getText()));
                    et_address_input.setSelection(String.valueOf(holder.tv_address.getText()).length());
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("请编辑：");
                    builder.setView(updateAddress);
                    builder.setPositiveButton("编辑完成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String address = et_address_input.getText();
                            String id = String.valueOf(addressList.get(position).getId());
                            long updateResult = aDao.update(id,address);
                            if (updateResult == 1) {
                                MyToast.MyToast(context, "编辑成功");
                                addressList = aDao.qureyAddress(currentUserId);
                                lv_address.setAdapter(new AddressAdapter(AddressManageActivity.this));
                            } else {
                                MyToast.MyToast(context, "编辑失败");
                            }

                        }

                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();

                }
            });
            convertView.setOnLongClickListener(new onLongClick());
            //短按项目
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //显示详细信息
                    if (senderOrReceiver == 0){
                        new AlertDialog.Builder(AddressManageActivity.this)
                                .setTitle("详细信息")
                                .setMessage(addressList.get(position).getAddress())
                                .setPositiveButton("确定", null).show();
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("address",addressList.get(position).getAddress());
                        setResult(RESULT_OK,intent);
                        AddressManageActivity.this.finish();
                    }

                }
            });
            return convertView;
        }
    }

    private final class viewHolder {
        public CheckBox cb_address;
        public TextView tv_address;
        public Button btn_edit;
    }

    //长按单个项目进行批量删除
    class onLongClick implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            isMulChoice = true;
            btn_del.setVisibility(View.VISIBLE);
            for (int i = 0; i < addressList.size(); i++) {
                new AddressAdapter(AddressManageActivity.this).isVisible.put(i, View.VISIBLE);
            }
            lv_address.setAdapter(new AddressAdapter(AddressManageActivity.this));
            return true;
        }
    }

    //自定义输入地址弹窗
    private void showMyDialog(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View setAddress = inflater.inflate(R.layout.address_input, null);
        final ClearEditText et_address_input = (ClearEditText) setAddress.findViewById(R.id.et_address_input);
        et_address_input.setMaxLines(5);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请输入地址：");
        builder.setView(setAddress);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String address = et_address_input.getText();
                long addResult = aDao.insert(new Address(0, user.getUserId(), address));
                if (addResult > 0) {
                    MyToast.MyToast(context, "添加成功");
                    addressList = aDao.qureyAddress(currentUserId);
                    lv_address.setAdapter(new AddressAdapter(AddressManageActivity.this));
                } else {
                    MyToast.MyToast(context, "添加失败");
                }

            }

        });
        builder.show();
    }
}
