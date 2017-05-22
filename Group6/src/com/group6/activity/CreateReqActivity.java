package com.group6.activity;

import java.util.List;

import com.group6.domain.Project;
import com.group6.domain.Requirement;
import com.group6.domain.Users;
import com.group6.utils.JsonUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateReqActivity extends Activity implements OnClickListener {
	protected static final String tag = "CreateReqActivity";
	// 声明控件
	private EditText et_name = null;
	private EditText et_detail = null;
	private Spinner sp_first = null;
	private Spinner sp_user = null;
	private Spinner sp_project = null;
	private Button btn_sure = null;

	// 声明数据
	private List<Users> uList;
	private List<Project> pList;
	private String userName;
	private String proName;
	private int proId;
	private int userId;
	private int prio;
	private int[] uid;
	private int[] pid;
	private String[] uname;
	private String[] pname;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				// 获取开发部门人员和项目名称列表，赋给String数组
				uname = new String[uList.size()];
				uid = new int[uList.size()];
				pname = new String[pList.size()];
				pid = new int[pList.size()];
				for (int i = 0; i < uname.length; i++) {
					uname[i] = uList.get(i).getName();
					uid[i] = uList.get(i).getId();
				}
				for (int j = 0; j < pname.length; j++) {
					pname[j] = pList.get(j).getName();
					pid[j] = pList.get(j).getId();
				}
				ArrayAdapter<String> uada = new ArrayAdapter<String>(
						CreateReqActivity.this,
						android.R.layout.simple_list_item_1, uname);
				ArrayAdapter<String> pada = new ArrayAdapter<String>(
						CreateReqActivity.this,
						android.R.layout.simple_list_item_1, pname);
				uada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				pada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_user.setAdapter(uada);
				sp_project.setAdapter(pada);

			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_req);
		initView();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				uList = JsonUtils.getListFromDevUsers();
				pList = JsonUtils.getListProject();
				if (uList != null && pList != null) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}).start();
		sp_user.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				userName = uname[position];
				userId = uid[position];
				Log.i(tag, "分配给" + userName);
				Log.i(tag, "员工编号" + userId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		sp_project.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				proName = pname[position];
				proId = pid[position];
				Log.i(tag, "所属项目为：" + proName);
				Log.i(tag, "项目编号：" + proId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		sp_first.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				prio = position + 1;
				Log.i(tag, "优先级" + prio);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		btn_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final String reqName = et_name.getText().toString();
		final String detail = et_detail.getText().toString();
		if (TextUtils.isEmpty(reqName)) {
			Toast.makeText(CreateReqActivity.this, "需求名称不能为空", 0).show();
		} else if (TextUtils.isEmpty(detail)) {
			Toast.makeText(CreateReqActivity.this, "需求细节不能为空", 0).show();
		} else {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					final int result = JsonUtils.insertRequire(new Requirement(
							0, proId, reqName, prio, detail, "", "", 0, 1,
							userId));
					runOnUiThread(new Runnable() {
						public void run() {
							if (result == 1) {
								Toast.makeText(CreateReqActivity.this, "创建成功",
										0).show();
								finish();
							} else {
								Toast.makeText(CreateReqActivity.this, "创建失败",
										0).show();
							}
						}
					});
				}
			}).start();
		}

		// Log.i(tag, reqName+detail+prio);

	}

	private void initView() {
		// TODO Auto-generated method stub
		sp_user = (Spinner) findViewById(R.id.sp_user);
		sp_project = (Spinner) findViewById(R.id.sp_project);
		sp_first = (Spinner) findViewById(R.id.sp_pri);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		et_name = (EditText) findViewById(R.id.et_req_name);
		et_detail = (EditText) findViewById(R.id.et_req_detail);
	}

}
