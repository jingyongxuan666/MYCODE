package com.group6.fragment;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import com.group6.activity.CreateUserActivity;
import com.group6.activity.R;
import com.group6.activity.UserDetailActivity;
import com.group6.domain.Users;
import com.group6.utils.JsonUtils;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class groupFragment extends Fragment implements OnChildClickListener, OnGroupClickListener {

	private static final String tag = "groupFragment";
	private View view;
	private ExpandableListView expandableListView;
	private ImageButton ib_create_person;
	private ArrayList<String> groupList;
	private ArrayList<List<String>> childList;
	private List<Users> uList;

	private MyexpandableListAdapter adapter;
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1){
				InitData();
				adapter = new MyexpandableListAdapter(getActivity());
				expandableListView.setAdapter(adapter);
			}
		}
		
	};
	
	public groupFragment() {
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
			view = inflater.inflate(R.layout.fragment_group, null);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		expandableListView = (ExpandableListView) view.findViewById(R.id.expandablelist);
		ib_create_person = (ImageButton) view.findViewById(R.id.ib_create_person);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				uList = JsonUtils.getListFromUsers();
				if(uList != null){
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}).start();
		
		
		expandableListView.setOnChildClickListener(this);
		expandableListView.setOnGroupClickListener(this);
		ib_create_person.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), CreateUserActivity.class);
				startActivity(intent);
			}
		});
	}
	/***
	 * InitData
	 */
	void InitData() {
		groupList = new ArrayList<String>();
		groupList.add("项目经理");
		groupList.add("需求部门");
		groupList.add("开发部门");
		childList = new ArrayList<List<String>>();
		for (int i = 0; i < groupList.size(); i++) {
			ArrayList<String> childTemp;
			if (i == 0) {
				childTemp = new ArrayList<String>();
				for (int j = 0; j < uList.size(); j++) {
					if(uList.get(j).getDeptId()==1){
						childTemp.add(uList.get(j).getName());
					}
				}
			} else if (i == 1) {
				childTemp = new ArrayList<String>();
				for (int j = 0; j < uList.size(); j++) {
					if(uList.get(j).getDeptId()==2){
						childTemp.add(uList.get(j).getName());
					}
				}
				
			} else {
				childTemp = new ArrayList<String>();
				for (int j = 0; j < uList.size(); j++) {
					if(uList.get(j).getDeptId()==3){
						childTemp.add(uList.get(j).getName());
					}
				}
			}
			childList.add(childTemp);
		}

	}

	class MyexpandableListAdapter extends BaseExpandableListAdapter {
		private Context context;
		private LayoutInflater inflater;

		public MyexpandableListAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		// 返回父列表个数
		@Override
		public int getGroupCount() {
			return groupList.size();
		}

		// 返回子列表个数
		@Override
		public int getChildrenCount(int groupPosition) {
			return childList.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {

			return groupList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {

			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupHolder groupHolder = null;
			if (convertView == null) {
				groupHolder = new GroupHolder();
				convertView = inflater.inflate(R.layout.group, null);
				groupHolder.textView = (TextView) convertView
						.findViewById(R.id.group);
				groupHolder.imageView = (ImageView) convertView
						.findViewById(R.id.image);
				groupHolder.textView.setTextSize(15);
				convertView.setTag(groupHolder);
			} else {
				groupHolder = (GroupHolder) convertView.getTag();
			}

			groupHolder.textView.setText(getGroup(groupPosition).toString());
			if (isExpanded)// ture is Expanded or false is not isExpanded
				groupHolder.imageView.setImageResource(R.drawable.expanded);
			else
				groupHolder.imageView.setImageResource(R.drawable.collapse);
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item, null);
			}
			TextView textView = (TextView) convertView.findViewById(R.id.item);
			textView.setTextSize(13);
			textView.setText(getChild(groupPosition, childPosition).toString());
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	@Override
	public boolean onGroupClick(final ExpandableListView parent, final View v,
			int groupPosition, final long id) {

		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
//		Toast.makeText(getActivity(),
//				childList.get(groupPosition).get(childPosition), 1).show();
		String name = childList.get(groupPosition).get(childPosition);
		int i = 0;
		for (i = 0; i < uList.size(); i++) {
			if(name.equals(uList.get(i).getName())){
				Log.i(tag, "传递的用户"+uList.get(i).getName());
				break;
			}
		}
		int deptId = uList.get(i).getDeptId();
		String dept;
		if(deptId == 1){
			dept="项目经理";
		}else if(deptId == 2){
			dept="需求部门";
		}else{
			dept="开发部门";
		}
		new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
				.setTitle("员工信息")
				.setMessage("员工姓名："+uList.get(i).getName()
									+"\n登录密码："+uList.get(i).getPassword()
									+"\n所属部门："+dept
									+"\n任务数量："+uList.get(i).getNumOfRs())
				.setPositiveButton("确定", null).show();
		
//		Intent intent = new Intent(getActivity(), UserDetailActivity.class);
////		intent.putExtra("userList", (Serializable)uList.get(i));
////		startActivity(intent);
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("user",uList.get(i));
//		intent.putExtras(bundle);
//		startActivity(intent);
		
		return false;
	}
	
	class GroupHolder {
		TextView textView;
		ImageView imageView;
	}

	
}
