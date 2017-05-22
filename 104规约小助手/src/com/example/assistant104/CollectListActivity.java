package com.example.assistant104;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.assistant104.dao.MessageDao;
import com.example.assistant104.entities.Message;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectListActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener, OnClickListener {
	private ListView lv_colList;
	private Button btn_pullout;
	private List<Message> msgList;
	MessageDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collect_list);
		lv_colList = (ListView) findViewById(R.id.lv_colList);
		btn_pullout = (Button) findViewById(R.id.btn_pullout);
		dao = new MessageDao(CollectListActivity.this);
		msgList = dao.queryAll();
		btn_pullout.setOnClickListener(this);
		lv_colList.setAdapter(new MyAdapter());
		lv_colList.setOnItemClickListener(this);
		lv_colList.setOnItemLongClickListener(this);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return msgList.size();
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
			viewHolder holder;
			LayoutInflater inflater = getLayoutInflater();
			if (convertView == null) {
				holder = new viewHolder();
				convertView = inflater.inflate(R.layout.collectlist_item, null);
				holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
				holder.tv_msg = (TextView) convertView
						.findViewById(R.id.tv_msg);
				convertView.setTag(holder);

			} else {
				holder = (viewHolder) convertView.getTag();
			}

			holder.tv_id.setText(msgList.get(position).getId());
			holder.tv_msg.setText(msgList.get(position).getPreMessage());
			return convertView;
		}

	}

	public final class viewHolder {
		public TextView tv_id;
		public TextView tv_msg;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(CollectListActivity.this)
				.setTitle("详细信息")
				.setMessage(
						"编号：" + msgList.get(position).getId() + "\n原始报文：\n"
								+ msgList.get(position).getPreMessage()
								+ "\n报文分析：\n"
								+ msgList.get(position).getAnaMessage()).show();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(CollectListActivity.this).setTitle("提示")
				.setMessage("确认删除此条收藏吗")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						long result = dao.delete(msgList.get(position).getId());
						if (result == 1) {
							msgList.remove(position);
							lv_colList.setAdapter(new MyAdapter());
							Toast.makeText(getBaseContext(), "删除成功",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getBaseContext(), "删除失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				}).setNegativeButton("取消", null).show();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String fileName = sdf.format(new Date()) + ".txt";
		String filePath = "/sdcard/104assistant/";
		String data;
		try {
			File dir = new File(filePath);
			File file =new File(filePath+fileName);
			dir.mkdir();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			for (Message message : msgList) {
				data = message.toString();
				fos.write(data.getBytes());
			}
			fos.flush();
			fos.close();
			Toast.makeText(CollectListActivity.this,
					"文件成功导出到：" + filePath + "路径下", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CollectListActivity.this, "导出文件失败",
					Toast.LENGTH_LONG).show();
		}

	}

}
