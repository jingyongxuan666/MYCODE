package com.group6.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.group6.activity.CreateReqActivity;
import com.group6.activity.R;
import com.group6.domain.Project;
import com.group6.domain.Requirement;
import com.group6.domain.Users;
import com.group6.utils.JsonUtils;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class requireFragment extends Fragment implements OnClickListener {

	protected static final String tag = "requireFragment";
	private View view;
	// private Spinner sp_req_sort = null;
	private List<Requirement> dataList;
	private List<Users> userList;
	private List<Project> plist;
	private ListView lv_requirement = null;
	private List<Requirement> reqList;
	private String testsp;
	private Button btn_sort_finish = null;
	private Button btn_sort_all = null;
	private Button btn_sort_doing = null;
	private Button btn_sort_notStart = null;
	private ImageButton ib_create_req = null;
	private TextView tv_num = null;

	private int num = 0;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				dataList = reqList;
				lv_requirement.setAdapter(new MyAdapter());
			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view != null) {
			container = (ViewGroup) view.getParent();
			if (container != null) {
				container.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.fragment_require, null);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		// sp_req_sort = (Spinner) view.findViewById(R.id.sp_req_sort);
		lv_requirement = (ListView) view.findViewById(R.id.lv_requirement);
		// ArrayAdapter<String> spada = new ArrayAdapter<String>(getActivity(),
		// R.layout.spinner_item, state);
		// spada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// sp_req_sort.setAdapter(spada);
		btn_sort_all = (Button) view.findViewById(R.id.btn_sort_all);
		btn_sort_finish = (Button) view.findViewById(R.id.btn_sort_finish);
		btn_sort_notStart = (Button) view.findViewById(R.id.btn_sort_notStart);
		btn_sort_doing = (Button) view.findViewById(R.id.btn_sort_doing);

		tv_num = (TextView) view.findViewById(R.id.tv_num);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				reqList = JsonUtils.getListRequirement();
				plist = JsonUtils.getListProject();
				userList = JsonUtils.getListFromUsers();
				// handler更稳定
				if (reqList != null && plist != null && userList != null) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
				// getActivity().runOnUiThread(new Runnable() {
				//
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub
				//
				// dataList = reqList;
				//
				// lv_requirement.setAdapter(new MyAdapter());
				//
				// }
				// });
			}
		}).start();
		btn_sort_all.setTextColor(Color.WHITE);
		btn_sort_finish.setTextColor(Color.BLACK);
		btn_sort_doing.setTextColor(Color.BLACK);
		btn_sort_notStart.setTextColor(Color.BLACK);

		btn_sort_all.setOnClickListener(this);
		btn_sort_finish.setOnClickListener(this);
		btn_sort_doing.setOnClickListener(this);
		btn_sort_notStart.setOnClickListener(this);

		// 新建需求
		ib_create_req = (ImageButton) view.findViewById(R.id.ib_create_req);
		ib_create_req.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CreateReqActivity.class);
				startActivity(intent);
			}
		});
		// 点击需求弹出需求细节
		lv_requirement.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// int id = dataList.get(position).getId();
				String name = dataList.get(position).getName();
				int projectId = dataList.get(position).getProjectid();
				int priorityId = dataList.get(position).getPriorityid();
				// int laborHour = dataList.get(position).getLaborHour();
				String beginDate = dataList.get(position).getBegindate();
				String endDate = dataList.get(position).getEnddate();
				String detail = dataList.get(position).getDetail();
				int stateId = dataList.get(position).getStateid();
				int userId = dataList.get(position).getUserid();
				String whofirst;
				String state;
				if (priorityId == 1) {
					whofirst = "非常紧急";
				} else if (priorityId == 2) {
					whofirst = "紧急";
				} else {
					whofirst = "普通";
				}
				if (stateId == 1) {
					state = "未开始";
				} else if (stateId == 2) {
					state = "进行中";
				} else {
					state = "已完成";
				}
				String proName = null;
				String uName = null;
				for (int i = 0; i < plist.size(); i++) {
					if (plist.get(i).getId() == projectId) {
						proName = plist.get(i).getName();
					}
				}
				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getId() == userId) {
						uName = userList.get(i).getName();
					}
				}
				new AlertDialog.Builder(getActivity(),
						AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
						.setTitle("需求详情")
						.setMessage(
								"需求名称：" + name + "\n所属项目：" + proName
										+ "\n需求级别：" + whofirst + "\n任务状态："
										+ state + "\n负责人员：" + uName + "\n开始时间："
										+ beginDate + "\n结束时间：" + endDate
										+ "\n具体描述：" + detail)
						.setPositiveButton("确定", null).show();

			}
		});

		// tv_num.setText("当前分类共"+i+"条数据");
		// Log.i(tag, "哈哈:" + STATE);

	}

	class MyAdapter extends BaseAdapter {

		private static final String tag = "MyAdapter";

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			// num = dataList.size();
			return dataList.size();
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

			Log.i(tag, "STATE:" + testsp);
			int id = dataList.get(position).getId();
			String name = dataList.get(position).getName();
			int projectId = dataList.get(position).getProjectid();
			int priorityId = dataList.get(position).getPriorityid();
			int laborHour = dataList.get(position).getLaborHour();
			String beginDate = dataList.get(position).getBegindate();
			String endDate = dataList.get(position).getEnddate();
			int stateId = dataList.get(position).getStateid();
			int userId = dataList.get(position).getUserid();
			TextView tv_req_id = (TextView) lview.findViewById(R.id.tv_req_id);
			TextView tv_req_name = (TextView) lview
					.findViewById(R.id.tv_req_name);
			TextView tv_req_client = (TextView) lview
					.findViewById(R.id.tv_req_client);
			TextView tv_req_user = (TextView) lview
					.findViewById(R.id.tv_req_user);
			// Log.i(tag, "编号:" + id);
			// Log.i(tag, "所属项目:" + plist.get(projectId).getName());
			// Log.i(tag, "需求名称:" + name);
			// Log.i(tag, "负责人:" + userList.get(userId-1).getName());
			if (priorityId == 1) {
				tv_req_id.setTextColor(Color.RED);
				tv_req_id.setText("A");
			} else if (priorityId == 2) {
				tv_req_id.setTextColor(Color.MAGENTA);
				tv_req_id.setText("B");
			} else {
				tv_req_id.setTextColor(getResources().getColor(
						R.color.main_blue));
				tv_req_id.setText("C");
			}
			// tv_req_id.setText(id + "");
			String projectName = null;
			String userName = null;
			for (int i = 0; i < plist.size(); i++) {
				if (plist.get(i).getId() == projectId) {
					projectName = plist.get(i).getName();
				}
			}
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getId() == userId) {
					userName = userList.get(i).getName();
				}
			}
			tv_req_name.setText("需求名称：" + name);
			tv_req_client.setText("所属项目：" + projectName);
			tv_req_user.setText("负责人：" + userName);
			if (dataList != null) {
				num = dataList.size();
			} else {
				num = 0;
			}

			Log.i(tag, "数据数" + num);
			tv_num.setText("当前分类共" + num + "条数据");
			return lview;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		dataList = new ArrayList<Requirement>();
		num = 0;
		// int i=0;
		switch (v.getId()) {

		case R.id.btn_sort_all:
			dataList = reqList;
			// i = dataList.size();
			btn_sort_all.setTextColor(Color.WHITE);
			btn_sort_finish.setTextColor(Color.BLACK);
			btn_sort_doing.setTextColor(Color.BLACK);
			btn_sort_notStart.setTextColor(Color.BLACK);
			break;
		case R.id.btn_sort_finish:
			for (int i = 0; i < reqList.size(); i++) {

				if (reqList.get(i).getStateid() == 3) {
					// dataList.remove(i);
					dataList.add(reqList.get(i));
				}
			}
			btn_sort_all.setTextColor(Color.BLACK);
			btn_sort_finish.setTextColor(Color.WHITE);
			btn_sort_doing.setTextColor(Color.BLACK);
			btn_sort_notStart.setTextColor(Color.BLACK);

			break;
		case R.id.btn_sort_doing:
			for (int i = 0; i < reqList.size(); i++) {

				if (reqList.get(i).getStateid() == 2) {
					// dataList.remove(i);
					dataList.add(reqList.get(i));
				}
			}
			btn_sort_all.setTextColor(Color.BLACK);
			btn_sort_finish.setTextColor(Color.BLACK);
			btn_sort_doing.setTextColor(Color.WHITE);
			btn_sort_notStart.setTextColor(Color.BLACK);

			break;
		case R.id.btn_sort_notStart:
			for (int i = 0; i < reqList.size(); i++) {

				if (reqList.get(i).getStateid() == 1) {
					// dataList.remove(i);
					dataList.add(reqList.get(i));
				}
			}
			btn_sort_all.setTextColor(Color.BLACK);
			btn_sort_finish.setTextColor(Color.BLACK);
			btn_sort_doing.setTextColor(Color.BLACK);
			btn_sort_notStart.setTextColor(Color.WHITE);

			break;
		default:
			break;
		}

		tv_num.setText("当前分类共" + num + "条数据");
		lv_requirement.setAdapter(new MyAdapter());

	}

}
