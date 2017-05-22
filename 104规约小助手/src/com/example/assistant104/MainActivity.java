package com.example.assistant104;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.assistant104.dao.MessageDao;
import com.example.assistant104.dao.TranslateDao;
import com.example.assistant104.entities.Message;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author 静永萱
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private ImageButton ib_clear1;
	private ImageButton ib_clear2;
	private EditText et_before;
	private EditText et_after;
	private Button btn_lockAndEdit1;
	private Button btn_lockAndEdit2;
	private Button btn_collectList;
	private Button btn_collect;
	private Button btn_analyse;
	private String textBefore = null;
	private String textAfter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		// initValue();
		setListener();

	}

	// private void initValue(){
	//
	//
	// }

	/**
	 * 载入控件并初始化
	 */
	private void initView() {
		ib_clear1 = (ImageButton) findViewById(R.id.ib_clear1);
		ib_clear2 = (ImageButton) findViewById(R.id.ib_clear2);

		et_after = (EditText) findViewById(R.id.et_after);
		et_before = (EditText) findViewById(R.id.et_before);

		btn_lockAndEdit1 = (Button) findViewById(R.id.btn_lockAndEdit1);
		btn_lockAndEdit2 = (Button) findViewById(R.id.btn_lockAndEdit2);
		btn_collectList = (Button) findViewById(R.id.btn_collectList);
		btn_analyse = (Button) findViewById(R.id.btn_analyse);

		ib_clear1.setVisibility(View.GONE);
		ib_clear2.setVisibility(View.GONE);

		btn_collect = (Button) findViewById(R.id.btn_collect);

	}

	private void setListener() {
		// 文本框内容改变监听
		et_after.addTextChangedListener(watcher);
		et_before.addTextChangedListener(watcher);
		// 按钮点击监听
		ib_clear1.setOnClickListener(this);
		ib_clear2.setOnClickListener(this);
		btn_lockAndEdit1.setOnClickListener(this);
		btn_lockAndEdit2.setOnClickListener(this);
		btn_collectList.setOnClickListener(this);
		btn_analyse.setOnClickListener(this);

		btn_collect.setOnClickListener(this);

	}

	TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			textBefore = et_before.getText().toString();
			textAfter = et_after.getText().toString();
			if (textBefore.length() != 0) {
				ib_clear1.setVisibility(View.VISIBLE);
			} else {
				ib_clear1.setVisibility(View.INVISIBLE);
			}
			if (textAfter.length() != 0) {
				ib_clear2.setVisibility(View.VISIBLE);
			} else {
				ib_clear2.setVisibility(View.GONE);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String text1 = et_before.getText().toString();
		String text2 = et_after.getText().toString();
		switch (v.getId()) {
		case R.id.ib_clear1:
			et_before.setText("");
			break;

		case R.id.ib_clear2:
			et_after.setText("");
			break;
		case R.id.btn_lockAndEdit1:
			String btnText1 = btn_lockAndEdit1.getText().toString();
			if ("锁定".equals(btnText1)) {
				btn_lockAndEdit1.setText("解锁");
				et_before.setEnabled(false);
				ib_clear1.setVisibility(View.GONE);
			} else if ("解锁".equals(btnText1)) {
				btn_lockAndEdit1.setText("锁定");
				et_before.setEnabled(true);
				if (text1.length() != 0) {
					ib_clear1.setVisibility(View.VISIBLE);
				}
			}
			break;
		case R.id.btn_lockAndEdit2:
			String btnText2 = btn_lockAndEdit2.getText().toString();
			if ("锁定".equals(btnText2)) {
				btn_lockAndEdit2.setText("解锁");
				et_after.setEnabled(false);
				ib_clear2.setVisibility(View.GONE);
			} else if ("解锁".equals(btnText2)) {
				btn_lockAndEdit2.setText("锁定");
				et_after.setEnabled(true);
				if (text2.length() != 0) {
					ib_clear2.setVisibility(View.VISIBLE);
				}
			}
			break;
		case R.id.btn_collectList:
			Intent intent = new Intent(MainActivity.this,
					CollectListActivity.class);
			startActivity(intent);

			break;
		case R.id.btn_collect:
			MessageDao dao = new MessageDao(MainActivity.this);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String id = df.format(new Date());
			if (TextUtils.isEmpty(text1) || TextUtils.isEmpty(text2)) {
				myDialog("收藏内容不能为空");
			}else{
				long rowId = dao.insert(new Message(id, text1, text2));
				if(rowId == -1){
					myToast("收藏失败");
				}else{
					myToast("收藏成功");
				}
			}
			break;
		case R.id.btn_analyse:
			TranslateDao.msgSet(text1, et_after, MainActivity.this);
			break;
		}

	}

	private void myDialog(String msg) {
		new AlertDialog.Builder(this).setTitle("提示").setMessage(msg)
				.setPositiveButton("确定", null).show();
	}
	private void myToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
