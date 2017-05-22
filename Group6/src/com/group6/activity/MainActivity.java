package com.group6.activity;

import com.group6.fragment.groupFragment;
import com.group6.fragment.itemFragment;
import com.group6.fragment.myInfoFragment;
import com.group6.fragment.requireFragment;
import com.group6.fragment.taskFragment;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author 静永萱
 *
 */
public class MainActivity extends Activity implements OnCheckedChangeListener {

	private static final String tag = "MainActivity";
	private RadioGroup rg_menu = null;
	private FragmentManager fm;
	private RadioButton rb_task = null;
	private RadioButton rb_myInfo = null;
	private RadioButton rb_item = null;
	private RadioButton rb_group = null;
	private RadioButton rb_require = null;
	private int flag = 0;
	private int flag2 = 0;
	private int LEFT = 1;
	private int RIGHT = 2;
	private int TOP = 3;
	private int BOTTOM = 4;
	private int blue = 0;
	private int gray = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initListener();

	}
	

	

	private void initView() {
		rg_menu = (RadioGroup) findViewById(R.id.rg_menu);
		rb_task = (RadioButton) findViewById(R.id.rb_task);
		rb_myInfo = (RadioButton) findViewById(R.id.rb_myinfo);
		rb_item = (RadioButton) findViewById(R.id.rb_item);
		rb_group = (RadioButton) findViewById(R.id.rb_group);
		rb_require = (RadioButton) findViewById(R.id.rb_require);
	}

	private void initListener() {
		rg_menu.setOnCheckedChangeListener(this);
	}

	private void initData() {
		gray = getResources().getColor(R.color.main_gray);
		blue = getResources().getColor(R.color.main_blue);
		fm = getFragmentManager();
		Intent intent = getIntent();
		String role = intent.getStringExtra("role");
		if ("项目经理".equals(role)) {
//			rb_task.setVisibility(View.GONE);
			
			// 设置进入时默认fragment
			flag = 1;	
			flag2 = 1;
			replaceFrag(new itemFragment());
			rb_item.setChecked(true);
			// 设置默认按钮样式
			afterChangedStyle(rb_item, R.drawable.ic_item_after);
			beforeChangedStyle(rb_task, R.drawable.ic_task);
			beforeChangedStyle(rb_myInfo, R.drawable.ic_person);
			beforeChangedStyle(rb_require, R.drawable.ic_require);
			beforeChangedStyle(rb_group, R.drawable.ic_group);
		}else if("开发部门".equals(role)){
			rb_item.setVisibility(View.GONE);
			rb_group.setVisibility(View.GONE);
			rb_require.setVisibility(View.GONE);
			
			// 设置进入时默认fragment
			rb_task.setChecked(true);
//			flag = 2;
//			onCheckedChanged(rg_menu, R.id.rb_task);
			replaceFrag(new taskFragment());
			// 设置默认按钮样式
			afterChangedStyle(rb_task, R.drawable.ic_task_after);
			beforeChangedStyle(rb_myInfo, R.drawable.ic_person);
		}else{
			rb_item.setVisibility(View.GONE);
			rb_group.setVisibility(View.GONE);
			rb_task.setVisibility(View.GONE);
			
			// 设置进入时默认fragment
//			flag = 3;
			rb_require.setChecked(true);
			replaceFrag(new requireFragment());
			// 设置默认按钮样式
			afterChangedStyle(rb_require, R.drawable.ic_require_after);
			beforeChangedStyle(rb_myInfo, R.drawable.ic_person);
		}
		

	}

	/**
	 * 替换控件函数
	 * 
	 * @param fragment
	 *            定义好的fragment
	 */
	private void replaceFrag(Fragment fragment) {
		FragmentTransaction ft = fm.beginTransaction();
		
		ft.replace(R.id.ll_content, fragment);
		ft.commit();
	}

	/**
	 * 底部radiogroup里的radiobutton改变监听
	 */
//	OnCheckedChangeListener rgChange = new OnCheckedChangeListener() {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			// TODO Auto-generated method stub
////			replaceFrag(new taskFragment());
//			
//
//		}
//	};

	private void beforeChangedStyle(TextView view, int drawId) {
		// 图标
		setIconToDraw(view, drawId, TOP);
		// 字体颜色
		view.setTextColor(gray);
	}

	private void afterChangedStyle(TextView view, int drawId) {

		setIconToDraw(view, drawId, TOP);
		view.setTextColor(blue);
	}

	/**
	 * 动态设置空间中图片位置函数，相当于setCompoundDrawables
	 * 
	 * @param view
	 *            要使用图片的控件
	 * @param id
	 *            图片id
	 * @param position
	 *            要设置的图片位置
	 */
	private void setIconToDraw(TextView view, int id, int position) {
		Drawable drawable;
		drawable = getResources().getDrawable(id);
		drawable.setBounds(0, 0, drawable.getMinimumWidth() - 10,
				drawable.getMinimumHeight() - 10);
		if (position == LEFT) {
			view.setCompoundDrawables(drawable, null, null, null);
		} else if (position == TOP) {
			view.setCompoundDrawables(null, drawable, null, null);
		} else if (position == RIGHT) {
			view.setCompoundDrawables(null, null, drawable, null);
		} else if (position == BOTTOM) {
			view.setCompoundDrawables(null, null, null, drawable);
		}
	}

	/*
	 * 重写返回键，返回直接退出程序
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 重写返回键，返回直接退出程序
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog
			.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
			.setTitle("提示")
			.setMessage("确认退出Group6吗？")
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).setNegativeButton("取消", null).show();
			
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		rb_require.isChecked();
//		Log.i(tag, "需求被选中");
		//从其他activity返回时显示跳转前的fragment并刷新数据
		if(rb_require.isChecked()){
			replaceFrag(new requireFragment());
		}else if(rb_item.isChecked()){
			replaceFrag(new itemFragment());
		}else if(rb_task.isChecked()){
			replaceFrag(new taskFragment());
		}else if(rb_group.isChecked()){
			replaceFrag(new groupFragment());
		}else{
			replaceFrag(new myInfoFragment());
		}
	}




	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		beforeChangedStyle(rb_task, R.drawable.ic_task);
		beforeChangedStyle(rb_myInfo, R.drawable.ic_person);
		beforeChangedStyle(rb_item, R.drawable.ic_item);
		beforeChangedStyle(rb_group, R.drawable.ic_group);
		beforeChangedStyle(rb_require, R.drawable.ic_require);
		switch (checkedId) {
		case R.id.rb_task:
			replaceFrag(new taskFragment());
			afterChangedStyle(rb_task, R.drawable.ic_task_after);
			break;
		case R.id.rb_myinfo:
			replaceFrag(new myInfoFragment());
			afterChangedStyle(rb_myInfo, R.drawable.ic_person_after);
			break;
		case R.id.rb_item:
			replaceFrag(new itemFragment());
			afterChangedStyle(rb_item, R.drawable.ic_item_after);
			break;
		case R.id.rb_group:
			replaceFrag(new groupFragment());
			afterChangedStyle(rb_group, R.drawable.ic_group_after);
			break;
		case R.id.rb_require:
			replaceFrag(new requireFragment());
			afterChangedStyle(rb_require, R.drawable.ic_require_after);
			break;

		default:
			break;
		}
	}

}
