package com.group6.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.group6.domain.Project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ItemListActivity extends Activity {
	private List<HashMap<String, Object>> myList;
	private ListView lv_list_item = null;
	private TextView tv_list_title = null;
	private TextView tv_three_title = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		
		Intent getIn = getIntent();
		
		myList = (List<HashMap<String, Object>>) getIn.getSerializableExtra("dataList");
		lv_list_item = (ListView) findViewById(R.id.lv_item_list);
		tv_list_title = (TextView) findViewById(R.id.tv_list_title);
		tv_three_title = (TextView) findViewById(R.id.tv_three_title);
		lv_list_item.setAdapter(new MyAdapter());
		lv_list_item.setOnItemClickListener(itemClick);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ItemListActivity.this, ItemDetailsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("details", myList.get(position));
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myList.size();
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
			View view = null;
			LayoutInflater inflater = getLayoutInflater();
			if(convertView == null){
				view = inflater.inflate(R.layout.item_list_item, null);
			}else{
				view = convertView;
			}
			int id = (int) myList.get(position).get("id");
			String name = (String) myList.get(position).get("name");
			String beginDate = (String) myList.get(position).get("beginDate");
			String endDate = (String) myList.get(position).get("endDate");
			String client = (String) myList.get(position).get("client");
			int stateId = (int) myList.get(position).get("stateId");
			TextView tv_id = (TextView) view.findViewById(R.id.tv_item_id);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_item_name);
			TextView tv_three = (TextView) view.findViewById(R.id.tv_item_three);
			//不同类型，第三列显示不同数据
			if(stateId == 3){
				//已完成项目显示结束时间
				tv_three.setText(endDate);
				tv_list_title.setText("已完成");
				tv_three_title.setText("结束时间");
			}else if(stateId == 1){
				//未开始项目显示委托方
				tv_three.setText(client);
				tv_list_title.setText("未开始");
				tv_three_title.setText("委托方");
			}else if(stateId == 2){
				//进行中项目显示开始时间
				tv_three.setText(beginDate);
				tv_list_title.setText("进行中");
				tv_three_title.setText("开始时间");
			}else{
				//延期项目显示开始时间
				tv_three.setText(beginDate);
				tv_list_title.setText("延期中");
				tv_three_title.setText("开始时间");
			}
			//显示项目编号
			tv_id.setText(id+"");
			//显示项目名称
			tv_name.setText(name);
			return view;
		}
		
	}
}
