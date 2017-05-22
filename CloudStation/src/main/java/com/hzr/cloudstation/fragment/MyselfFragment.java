package com.hzr.cloudstation.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hzr.cloudstation.AddressManageActivity;
import com.hzr.cloudstation.ChangePwdActivity;
import com.hzr.cloudstation.FeedBackActivity;
import com.hzr.cloudstation.InfoSetActivity;
import com.hzr.cloudstation.LoginActivity;
import com.hzr.cloudstation.R;
import com.hzr.cloudstation.entities.User;
import com.hzr.cloudstation.utils.LoginUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzr on 2017/4/10.
 */
public class MyselfFragment extends Fragment {
    private View view;
    private ListView lv_menu;
    private TextView tv_loginName;
    private TextView tv_role;
    private Button btn_exit;

    private String loginName;
    private String role;
    private String[] menuList = {"资料设置","帮助","反馈","地址管理","修改密码"};
    private User user;
    public MyselfFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.fra_myself_menu_item,
                new String[]{"itemName"},
                new int[]{R.id.tv_menu_item});
        lv_menu.setAdapter(adapter);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intentToOther(getActivity(),InfoSetActivity.class);
                        break;
                    case 1:
                        new AlertDialog.Builder(getActivity())
                                .setTitle("信息")
                                .setMessage("如需帮助请发邮件至：\n840412052@qq.com")
                        .setPositiveButton("我知道了",null).show();
                        break;
                    case 2:
                        intentToOther(getActivity(), FeedBackActivity.class);
                        break;
                    case 3:
                        intentToOther(getActivity(), AddressManageActivity.class);
                        break;
                    case 4:
                        intentToOther(getActivity(), ChangePwdActivity.class);
                        break;
                }
            }
        });
        //退出当前账号
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("确认退出当前帐号？")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginUtils.cleraUserInfo(getActivity());
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消",null).show();
            }
        });
        //显示帐号和角色
        tv_loginName.setText(loginName);
        tv_role.setText(role);
    }

    private List<? extends Map<String,?>> getData() {
        ArrayList<HashMap<String,String>> nameList = new ArrayList<>();

        for (int i = 0;i < menuList.length;i++){
            HashMap<String,String> hMap = new HashMap<>();
            hMap.put("itemName",menuList[i]);
            nameList.add(hMap);
        }
        return nameList;
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
            view = inflater.inflate(R.layout.fragment_myself,null);
        }
        return view;
    }
    private void initView(){
        lv_menu = (ListView) view.findViewById(R.id.lv_menu);
        tv_loginName = (TextView) view.findViewById(R.id.tv_loginName);
        tv_role = (TextView) view.findViewById(R.id.tv_role);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
    }
    private void initData(){
        Intent getIntent = getActivity().getIntent();
        Bundle bundle = getIntent.getExtras();
        user = (User) bundle.getSerializable("user");
        String name = user.getUserName();
        String pwd = user.getPassword();
        loginName = name;
        if (name.equals("admin")){
            role = "管理员";
        }else{
            role = "用户";
        }

    }
    private void intentToOther(Context context,Class<?> class1){
        Intent intent = new Intent(context,class1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        intent.putExtra("type",0);
        startActivity(intent);
    }
}
