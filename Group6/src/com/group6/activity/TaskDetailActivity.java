package com.group6.activity;

import java.util.List;

import com.group6.domain.Requirement;
import com.group6.utils.JsonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDetailActivity extends Activity implements OnClickListener {

	private static final String tag = "TaskDetailActivity";

//	List<Requirement> taskList;
	Requirement req;
	String pName;

	private TextView tv_name = null;
	private TextView tv_project = null;
	private TextView tv_prio = null;
	private TextView tv_state = null;
	private TextView tv_bdate = null;
	private TextView tv_edate = null;
	private TextView tv_detail = null;

	private Button btn_start = null;
	private Button btn_end = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
		initView();
		Intent getIn = getIntent();
		req = new Requirement();
		req = (Requirement) getIn.getSerializableExtra("task");
//		taskList = (List<Requirement>) getIn.getSerializableExtra("taskList");
		pName = getIn.getStringExtra("projectName");
		initDate();
		Log.i(tag, "所属项目名称" + pName);
		Log.i(tag, "传递任务名" + req.getName());
	}

	private void initView() {
		tv_bdate = (TextView) findViewById(R.id.tv_task_bdate);
		tv_detail = (TextView) findViewById(R.id.tv_task_detail);
		tv_edate = (TextView) findViewById(R.id.tv_task_edate);
		tv_name = (TextView) findViewById(R.id.tv_task_name);
		tv_prio = (TextView) findViewById(R.id.tv_task_prio);
		tv_project = (TextView) findViewById(R.id.tv_task_project);
		tv_state = (TextView) findViewById(R.id.tv_task_state);

		btn_end = (Button) findViewById(R.id.btn_end);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_end.setOnClickListener(this);
		btn_start.setOnClickListener(this);
	}

	private void initDate() {
		int state = req.getStateid();
		if (state == 1) {
			tv_state.setText("未开始");
			btn_end.setVisibility(View.GONE);
		} else if (state == 2) {
			tv_state.setText("进行中");
			btn_start.setVisibility(View.GONE);
		} else {
			tv_state.setText("已完成");
			btn_end.setVisibility(View.GONE);
			btn_start.setVisibility(View.GONE);
		}
		tv_bdate.setText(req.getBegindate());
		tv_edate.setText(req.getEnddate());
		tv_name.setText(req.getName());
		tv_project.setText(pName);
		tv_detail.setText(req.getDetail());
		int pro = req.getPriorityid();
		if (pro == 1) {
			tv_prio.setTextColor(Color.RED);
			tv_prio.setText("A");
		} else if (pro == 2) {
			tv_prio.setTextColor(Color.MAGENTA);
			tv_prio.setText("B");
		} else {
			tv_prio.setText("C");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_start:
			new AlertDialog
			.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
			.setTitle("提示").setMessage("确认开始这个任务吗？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Log.i(tag, "任务id："+req.getId());
							final int u =JsonUtils.updateRequire("updateBeginDate", req.getId());
							runOnUiThread(new Runnable() {
								public void run() {
									if(u==1){
										btn_start.setVisibility(View.GONE);
										btn_end.setVisibility(View.VISIBLE);
										tv_state.setText("进行中");
										Toast.makeText(TaskDetailActivity.this, "任务已完成", 0).show();
									}
									
								}
							});
						}
					}).start();
					
				}
			}).setNegativeButton("取消", null).show();
			
			break;
		case R.id.btn_end:
			new AlertDialog
			.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
			.setTitle("提示").setMessage("确认结束这个任务吗？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							final int u = JsonUtils.updateRequire("updateEndDate", req.getId());
							runOnUiThread(new Runnable() {
								public void run() {
									if(u==1){
										btn_end.setVisibility(View.GONE);
										btn_start.setVisibility(View.GONE);
										btn_start.setBackgroundColor(Color.TRANSPARENT);
										Toast.makeText(TaskDetailActivity.this, "任务已完成", 0).show();
//										btn_start.setText("已完成");
										tv_state.setText("已完成");
										Log.i(tag, "几次");
									}
									
								}
							});
						}
					}).start();
					
					
				}
			}).setNegativeButton("取消", null).show();
			
			break;
		default:
			break;
		}
	}

}
