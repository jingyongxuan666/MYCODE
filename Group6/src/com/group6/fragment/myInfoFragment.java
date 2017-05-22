package com.group6.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.group6.activity.LoginActivity;
import com.group6.activity.MainActivity;
import com.group6.activity.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class myInfoFragment extends Fragment implements OnClickListener {
	private static final String tag = "myInfoFragment";
	private View view;
	private Button btn_exitLogin = null;
	private TextView tv_login_name = null;
	private TextView tv_login_role = null;
	private ListView lv_help_menu = null;
	private TextView tv_menu_name = null;
	private String [] helplist = {"编辑资料","帮助","反馈","设置"};

	public myInfoFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view != null){
			container = (ViewGroup) view.getParent();
			if(container != null){
				container.removeView(view);
			}
		}else{
			view = inflater.inflate(R.layout.fragment_myinfo, null);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Intent intent = getActivity().getIntent();
		String username = intent.getStringExtra("username");
		Log.i(tag, "用户名："+username);
		String role = intent.getStringExtra("role");
		String pwd = intent.getStringExtra("pwd");
		
		initView();
		tv_login_name.setText(username);
		tv_login_role.setText(role);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, helplist);
		SimpleAdapter adapter = new SimpleAdapter(
				getActivity(), 
				getData(),
				R.layout.lv_help_menu_item, 
				new String[]{"mname"},
				new int[]{R.id.tv_menu_name});
		lv_help_menu.setAdapter(adapter);
		lv_help_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "开发中，敬请期待...", 0).show();
			}
		});
		btn_exitLogin.setOnClickListener(this);
	}

	private List<? extends Map<String, ?>> getData() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> mylist= new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < helplist.length; i++) {
			HashMap<String, String> hmap = new HashMap<String, String>();
			hmap.put("mname", helplist[i]);
			mylist.add(hmap);
		}
		
		return mylist;
	}

	private void initView() {
		btn_exitLogin = (Button) view.findViewById(R.id.btn_exitLogin);
		lv_help_menu = (ListView) view.findViewById(R.id.lv_help_menu);
		tv_login_name = (TextView) view.findViewById(R.id.tv_login_name);
		tv_login_role = (TextView) view.findViewById(R.id.tv_login_role);
		tv_menu_name = (TextView) view.findViewById(R.id.tv_menu_name);
	}

	@Override
	public void onClick(View v) {
		// 退出提示
		new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("提示")
				.setMessage("确定退出当前账号？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 获取保存的帐号信息
						SharedPreferences sp = getActivity()
								.getSharedPreferences("userInfo",
										Context.MODE_PRIVATE);
						SharedPreferences.Editor edit = sp.edit();
						edit.clear();// 清空
						edit.commit();// 提交
						// 跳转回登录页面并清空登录信息
						Intent intent = new Intent(getActivity(),
								LoginActivity.class);
						getActivity().finish();// 结束MainAcitvity,防止返回键返回
						startActivity(intent);
					}
				}).setNegativeButton("取消", null).show();

	}

}
