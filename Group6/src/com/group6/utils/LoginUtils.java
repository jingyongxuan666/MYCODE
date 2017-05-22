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
 * @author 静永萱
 *登录工具类
 */
public class LoginUtils {

	private static final String tag = "LoginUtils";

	
	/**
	 * 登录验证
	 * @param name 用户名
	 * @param pwd 密码
	 * @param dept 部门
	 * @return 返回服务器的信息
	 */
	public static String loginOfPost(String name,String pwd,String dept){ 
		HttpURLConnection conn = null;
		try {
			//请求访问服务器
			URL url = new URL("http://192.168.120.118:8080/ServerJing/LoginServlet");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");//发送方式
			conn.setConnectTimeout(10000);//设置连接超时
			conn.setReadTimeout(5000);//设置读取超时
			conn.setDoOutput(true);//设置可写出
			
			//post的请求参数
			String data = "username="+name+"&password="+pwd+"&dept="+dept;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());//写数据
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {//如果响应码为200,接收返回信息
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(tag, "访问失败" + responseCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "服务未开启";
		} finally {//关闭连接
			if (conn != null) {
				conn.disconnect(); 
			}
		}

		return null;
	}
	
	/**
	 * 保存登录信息
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
			//获得一个编辑对象
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
	 * 回显登录信息
	 * @param context
	 * @return 返回一个list数组
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
//			Log.i("名字", name);
			users.setPassword(pwd);
			users.setRole(role);
			users.setIsRem_Atl(isRem_Atl);
			userList.add(users);
			return userList;
		}
		return null;
	}
	
	/**
	 * 读取服务器返回信息
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
		//返回信息转中文
		String state = new String(baos.toByteArray(), "GBK");
		baos.close();
		return state;
	}
}
