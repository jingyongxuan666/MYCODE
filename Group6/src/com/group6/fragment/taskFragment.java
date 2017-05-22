package com.group6.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.group6.activity.R;
import com.group6.activity.TaskDetailActivity;
import com.group6.domain.Project;
import com.group6.domain.Requirement;
import com.group6.domain.Users;
import com.group6.utils.JsonUtils;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class taskFragment extends Fragment {

	protected static final String tag = "taskFragment";
	private View view;
	private List<Requirement> rList;
	private List<Requirement> tList;
	private List<Users> uList;
	private List<Project> pList;
	private ListView lv_task;
	private String username;
	private int userid = 0;
	
	public taskFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1){
				
				if(msg.obj!=null){
					int us = (int) msg.obj;
					Log.i(tag, "用户数"+us);
					//遍历所有用户，如果等于登录的名字就赋值给userid
					for (int i = 0; i < us; i++) {
						if(uList.get(i).getName().equals(username)){
							userid = uList.get(i).getId();
						}
					}
					//遍历需求数组，把等于当前用户id的加近tList
					tList = new ArrayList<Requirement>();
					for (int j = 0; j < rList.size(); j++) {
						if(rList.get(j).getUserid()==userid){
							
							tList.add(rList.get(j));
						}
					}
					Log.i(tag, "任务数："+tList.size());
				}
				
				Log.i(tag, "用户id"+userid);
//				Log.i(tag, "用户数"+uList.size());
				
				lv_task.setAdapter(new MyAdapter());
			}
		}
		
	};
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
			view = inflater.inflate(R.layout.fragment_task, null);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Intent intent = getActivity().getIntent();
		username = intent.getStringExtra("username");
		lv_task = (ListView) view.findViewById(R.id.lv_task);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				rList = JsonUtils.getListRequirement();
				uList = JsonUtils.getListFromUsers();
				pList = JsonUtils.getListProject();
				if(rList != null && uList!=null && pList!=null){
					Message msg = new Message();
					msg.what = 1;
					msg.obj = uList.size();
					myHandler.sendMessage(msg);
				}
			}
		}).start();
		lv_task.setOnItemClickListener(itemClick);
	}
	OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(),TaskDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("task", tList.get(position));
			
//			startActivity(intent);
//			intent.putExtra("taskList",(Serializable)tList);
			//根据项目id查询项目名称
			String pname2 = null;
			for (int i = 0; i < pList.size(); i++) {
				//如果等于当前id就获取名字
				if(pList.get(i).getId()==tList.get(position).getProjectid()){
					pname2 = pList.get(i).getName();
				}
			}
			intent.putExtra("projectName", pname2);
			intent.putExtras(bundle);
			startActivity(intent);
			
		}
	};
	class MyAdapter extends BaseAdapter {

		private static final String tag = "MyAdapter";

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			return tList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View lview = null;
			LayoutInflater inflater = getActivity().getLayoutInflater();
			if (convertView == null) {
				lview = inflater.inflate(R.layout.lv_requirement_list_item,
						null);
			} else {
				lview = convertView;
			}
//			Log.i(tag, "STATE:" + testsp);
			int id =tList.get(position).getId();
			String name = tList.get(position).getName();
			int projectId = tList.get(position).getProjectid();
			int priorityId = tList.get(position).getPriorityid();
			int laborHour = tList.get(position).getLaborHour();
			String beginDate = tList.get(position).getBegindate();
			String endDate = tList.get(position).getEnddate();
			int stateId = tList.get(position).getStateid();
			int userId = tList.get(position).getUserid();
			TextView tv_req_id = (TextView) lview.findViewById(R.id.tv_req_id);
			TextView tv_req_name = (TextView) lview
					.findViewById(R.id.tv_req_name);
			TextView tv_req_client = (TextView) lview
					.findViewById(R.id.tv_req_client);
			TextView tv_req_user = (TextView) lview
					.findViewById(R.id.tv_req_user);
			//设置任务等级ABC三级
			if(priorityId == 1){
				tv_req_id.setTextColor(Color.RED);
				tv_req_id.setText("A");
			}else if(priorityId == 2){
				tv_req_id.setTextColor(Color.MAGENTA);
				tv_req_id.setText("B");
			}else{
				tv_req_id.setTextColor(getResources().getColor(R.color.main_blue));
				tv_req_id.setText("C");
			}
			tv_req_name.setText("需求名称："+name);
			if(stateId == 1){
				tv_req_user.setText("状态：未开始");
			}else if(stateId == 2){
				tv_req_user.setText("状态：进行中");
			}else{
				tv_req_user.setText("状态：已完成");
			}
			//根据项目id查询项目名称
			String pname = null;
			for (int i = 0; i < pList.size(); i++) {
				//如果等于当前id就获取名字
				if(pList.get(i).getId()==tList.get(position).getProjectid()){
					pname = pList.get(i).getName();
				}
			}
			
			tv_req_client.setText("所属项目："+pname);
			
			return lview;
		}
	}
}
