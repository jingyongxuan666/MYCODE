package com.group6.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.demo.view.PieChartView;
import com.group6.activity.ItemListActivity;
import com.group6.activity.R;
import com.group6.domain.Project;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class itemFragment extends Fragment implements OnClickListener {

	private static final String TAG = "itemFragment";
	private View view;
	private List<Project> projectList;
	
	private int green = 0;
	private int orange = 0;
	private int red = 0;
	private int purple = 0;
	
	List<HashMap<String, Object>> notStartList;
	List<HashMap<String, Object>> finishList;
	List<HashMap<String, Object>> doingList;
	List<HashMap<String, Object>> delayList;
	private PieChartView pcv;
	private Button btn_finish = null;
	private Button btn_not_start = null;
	private Button btn_doing = null;
	private Button btn_delay = null;
	// private ListView lv_item = null;
	private int finish = 0;
	private int notStart = 0;
	private int doing = 0;
	private int delay = 0;

	public itemFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				Log.i(TAG, "长度" + projectList.size());
				notStartList = new ArrayList<HashMap<String, Object>>();
				finishList = new ArrayList<HashMap<String, Object>>();
				doingList = new ArrayList<HashMap<String, Object>>();
				delayList = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < projectList.size(); i++) {
					HashMap<String, Object> hmap = new HashMap<String, Object>();
					hmap.put("id", projectList.get(i).getId());
					hmap.put("name", projectList.get(i).getName());
					hmap.put("client", projectList.get(i).getClient());
					hmap.put("description", projectList.get(i).getDescription());
					hmap.put("beginDate", projectList.get(i).getBeginDate());
					hmap.put("endDate", projectList.get(i).getEndDate());
					hmap.put("stateId", projectList.get(i).getStateId());
					hmap.put("progress", projectList.get(i).getProgress());
					// 计算各种状态项目的数量
					if (projectList.get(i).getStateId() == 3) {
						finish++;
						finishList.add(hmap);
					} else if (projectList.get(i).getStateId() == 1) {
						notStart++;
						notStartList.add(hmap);
					} else if (projectList.get(i).getStateId() == 2) {
						doing++;
						doingList.add(hmap);
					} else {
						delay++;
						delayList.add(hmap);
					}
				}
				// test.setText(notStart+"");
				btn_delay.setText("已延期:"+delay);
				btn_doing.setText("进行中:"+doing);
				btn_not_start.setText("未开始:"+notStart);
				btn_finish.setText("已完成:"+finish);
				
				pcv.setDataCount(4);
				pcv.setColor(new int[] { orange, green, purple,
						red });
				pcv.setData(new float[] { delay, finish, doing, notStart });
				pcv.setSpecial(3);
				pcv.postInvalidate();
				
			} else {
				Toast.makeText(getActivity(), "出错了", 0).show();
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
			view = inflater.inflate(R.layout.fragment_item, null);
		}
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();

		new Thread(new Runnable() {

			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				projectList = JsonUtils.getListProject();
				if (projectList != null) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}
		}).start();
		

	}

	private void initView() {
		
		orange = getResources().getColor(R.color.main_orange);
		green = getResources().getColor(R.color.main_green);
		red = getResources().getColor(R.color.main_red);
		purple = getResources().getColor(R.color.main_purple);
		
		btn_finish = (Button) view.findViewById(R.id.btn_finish);
		btn_doing = (Button) view.findViewById(R.id.btn_doing);
		btn_delay = (Button) view.findViewById(R.id.btn_delay);
		btn_not_start = (Button) view.findViewById(R.id.btn_not_start);
		btn_delay.setBackgroundColor(orange);
		btn_finish.setBackgroundColor(green);
		btn_doing.setBackgroundColor(purple);
		btn_not_start.setBackgroundColor(red);
		
		pcv = (PieChartView) view.findViewById(R.id.pie);
		
		btn_delay.setOnClickListener(this);
		btn_doing.setOnClickListener(this);
		btn_not_start.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), ItemListActivity.class);
		switch (v.getId()) {
		case R.id.btn_delay:
			intent.putExtra("dataList", (Serializable)delayList);
			break;
		case R.id.btn_doing:
			intent.putExtra("dataList", (Serializable)doingList);
			break;
		case R.id.btn_finish:
			intent.putExtra("dataList", (Serializable)finishList);
			break;
		case R.id.btn_not_start:
			intent.putExtra("dataList", (Serializable)notStartList);
			break;

		}
		startActivity(intent);
		
	}

}
