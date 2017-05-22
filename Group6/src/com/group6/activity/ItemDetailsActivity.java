package com.group6.activity;

import java.util.HashMap;

import com.android.demo.view.RoundProgressBar;
import com.group6.utils.JsonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailsActivity extends Activity implements OnClickListener {
	private static final String tag = "ItemDetailsActivity";
	private TextView tv_id = null;
	private TextView tv_name = null;
	private TextView tv_client = null;
	private TextView tv_beginDate = null;
	private TextView tv_endDate = null;
	private TextView tv_state = null;
	private TextView tv_description = null;
	private TextView tv_numOfReq = null;
	private RoundProgressBar rpb = null;
	private ImageButton ib_delete = null;
	private int progressBar = 0;
	private String name;
	private int id = 0;
	 private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		initView();
		//��������
		Intent getIn = getIntent();
		Bundle bundle = getIn.getExtras();
		HashMap<String, Object> dataList = (HashMap<String, Object>) bundle.get("details");
		name = (String) dataList.get("name");
		id = (int) dataList.get("id");
		String client = (String) dataList.get("client");
		String beginDate = (String) dataList.get("beginDate");
		String endDate = (String) dataList.get("endDate");
		String description = (String) dataList.get("description");
		int stateId = (int) dataList.get("stateId");
		final int progress = (int) dataList.get("progress");
		Log.i(tag, "����:"+progress);
		//��������
		//ͻ����ʾ״̬
		String state;
		if(stateId == 3){
			//�������Ŀ��ɫ����
			state = "�����";
			tv_state.setTextColor(Color.GREEN);
		}else if(stateId == 1){
			//δ��ʼ��ɫ����
			state = "δ��ʼ";
			beginDate = "------";
			endDate = "------";
			tv_state.setTextColor(Color.RED);
		}else if(stateId == 2){
			//��������Ŀ��ɫ����
			state = "������";
			endDate = "------";
			tv_state.setTextColor(Color.BLUE);
		}else{
			//������Ŀ��ɫ����
			state = "������";
			tv_state.setTextColor(Color.MAGENTA);
		}
		//���������
		new Thread(new Runnable() {
			
			
            public void run() {
            	
                while (progressBar < progress) {
                	progressBar++;
                	
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                        	rpb.setProgress(progressBar);
                        }
                    });
                }
            }
        }).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final int numOfReq = JsonUtils.queryNumOfRequire(id);
				runOnUiThread(new Runnable() {
					public void run() {
						tv_numOfReq.setText(numOfReq+"");
					}
				});
			}
		}).start();
		tv_state.setText(state);
		tv_name.setText(name);
		tv_id.setText(id+"");
		tv_beginDate.setText(beginDate);
		tv_endDate.setText(endDate);
		tv_client.setText(client);
		tv_description.setText(description);
		//���ü���
		ib_delete.setOnClickListener(this);
	}
	private void initView(){
		tv_name = (TextView) findViewById(R.id.tv_details_name);
		tv_id = (TextView) findViewById(R.id.tv_details_id);
		tv_client = (TextView) findViewById(R.id.tv_details_client);
		tv_state = (TextView) findViewById(R.id.tv_details_state);
		tv_beginDate = (TextView) findViewById(R.id.tv_details_beginDate);
		tv_endDate = (TextView) findViewById(R.id.tv_details_endDate);
		tv_description = (TextView) findViewById(R.id.tv_details_description);
		rpb = (RoundProgressBar) findViewById(R.id.rpb_progress);
		ib_delete = (ImageButton) findViewById(R.id.ib_delete);
		tv_numOfReq = (TextView) findViewById(R.id.tv_details_num);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(ItemDetailsActivity.this, 
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
		.setTitle("��ʾ")
		.setMessage("ȷ��ɾ��"+"'"+name+"'"+"�����Ŀ��")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						final int isDel = JsonUtils.deleteProject(id);
						runOnUiThread(new Runnable() {
							public void run() {
								if(isDel == 1){
									Toast.makeText(getBaseContext(), "ɾ���ɹ�", 0).show();
								}else{
									new AlertDialog
									.Builder(ItemDetailsActivity.this, 
											AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
									.setTitle("ɾ��ʧ��")
									.setMessage("�ѷ��������޷�ɾ����Ŀ")
									.setPositiveButton("��֪����", null)
									.show();
								}
							}
						});
					}
				}).start();
				
				
			}
		})
		.setNegativeButton("ȡ��", null)
		.show();
		
	}
	

}
