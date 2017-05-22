package com.group6.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group6.domain.Users;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author ������
 *��¼������
 */
public class LoginUtils {

	private static final String tag = "LoginUtils";

	
	/**
	 * ��¼��֤
	 * @param name �û���
	 * @param pwd ����
	 * @param dept ����
	 * @return ���ط���������Ϣ
	 */
	public static String loginOfPost(String name,String pwd,String dept){ 
		HttpURLConnection conn = null;
		try {
			//������ʷ�����
			URL url = new URL("http://192.168.120.118:8080/ServerJing/LoginServlet");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");//���ͷ�ʽ
			conn.setConnectTimeout(10000);//�������ӳ�ʱ
			conn.setReadTimeout(5000);//���ö�ȡ��ʱ
			conn.setDoOutput(true);//���ÿ�д��
			
			//post���������
			String data = "username="+name+"&password="+pwd+"&dept="+dept;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());//д����
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {//�����Ӧ��Ϊ200,���շ�����Ϣ
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(tag, "����ʧ��" + responseCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "����δ����";
		} finally {//�ر�����
			if (conn != null) {
				conn.disconnect(); 
			}
		}

		return null;
	}
	
	/**
	 * �����¼��Ϣ
	 * @param context
	 * @param username
	 * @param pwd
	 * @param role
	 * @param isRem_Atl
	 * @return
	 */
	public static boolean saveUserInfo(Context context,String username,String pwd, String role,int isRem_Atl){
		try {
			SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			//���һ���༭����
			Editor edit = sp.edit();
			edit.putString("username", username);
			edit.putString("password", pwd);
			edit.putString("role", role);
			edit.putInt("isRem_Atl", isRem_Atl);
			edit.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ���Ե�¼��Ϣ
	 * @param context
	 * @return ����һ��list����
	 */
	public static List<Users> getUserInfo(Context context){
		SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		List<Users> userList = new ArrayList<Users>();
		String name = sp.getString("username", null);
		String pwd = sp.getString("password", null);
		String role = sp.getString("role", null);
		int isRem_Atl = sp.getInt("isRem_Atl", 0);
		Users users = new Users();
		if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(role)){
			users.setName(name);
//			Log.i("����", name);
			users.setPassword(pwd);
			users.setRole(role);
			users.setIsRem_Atl(isRem_Atl);
			userList.add(users);
			return userList;
		}
		return null;
	}
	
	/**
	 * ��ȡ������������Ϣ
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String getStringFromInputStream(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[]  buffer = new byte[1024];
		int len = -1;
		while((len = is.read(buffer)) != -1){
			baos.write(buffer,0,len);
		}
		is.close();
//		String state = baos.toString();
		//������Ϣת����
		String state = new String(baos.toByteArray(), "GBK");
		baos.close();
		return state;
	}
}
