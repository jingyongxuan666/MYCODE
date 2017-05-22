package com.group6.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.group6.domain.Project;
import com.group6.domain.Requirement;
import com.group6.domain.Users;

public class JsonUtils {
	private static final String tag = "JsonUtils";
	private static HttpURLConnection conn = null;
	private static String URL = "http://192.168.120.118:8080/ServerJing/DataServlet";

	/**
	 * ��ѯ����ȡ���ݿ�����Ŀ�б�
	 * 
	 * @return ����һ��list
	 */
	public static List<Project> getListProject() {
		try {
			int responseCode = visitToService("queryFromProject", 0);
//			Log.i(tag, "��Ӧ�룺" + responseCode);
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				List<Project> projectList = new ArrayList<Project>();
				byte[] data = readParse(is);
				JSONArray jarray = new JSONArray(new String(data));
				int id;
				String name;
				String client;
				String description;
				String beginDate;
				String endDate;
				int stateId;
				int progress;
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject item = jarray.getJSONObject(i);
					id = item.getInt("id");
					name = item.getString("name");
					client = item.getString("client");
					description = item.getString("description");
					beginDate = item.getString("beginDate");
					endDate = item.getString("endDate");
					stateId = item.getInt("stateId");
					progress = item.getInt("progress");
					projectList.add(new Project(id, name, client, description,
							beginDate, endDate, stateId, progress));
				}
				return projectList;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static List<Requirement> getListRequirement() {
		try {
			int responseCode = visitToService("queryFromRequirement", 0);
//			Log.i(tag, "��Ӧ�룺" + responseCode);
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				List<Requirement> requirementList = new ArrayList<Requirement>();
				byte[] data = readParse(is);
				JSONArray jarray = new JSONArray(new String(data));
				int id;
				int projectId;
				String name;
				int priorityId;
				String detail;
				int laborHour;
				String beginDate;
				String endDate;
				int stateId;
				int userId;
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject item = jarray.getJSONObject(i);
					id = item.getInt("id");
					name = item.getString("name");
					projectId = item.getInt("projectId");
					priorityId = item.getInt("priorityId");
					detail = item.getString("detail");
					laborHour = item.getInt("laborHour");
					beginDate = item.getString("beginDate");
					endDate = item.getString("endDate");
					stateId = item.getInt("stateId");
					userId = item.getInt("userId");
					requirementList.add(new Requirement(id, projectId, name,
							priorityId, detail, beginDate, endDate, laborHour,
							stateId, userId));
				}
				return requirementList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static List<Users> getListFromUsers(){
		try {
			int responseCode = visitToService("queryFromUsers", 0);
//			Log.i(tag, "��ȡ�û���Ӧ�룺" + responseCode);
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				List<Users> userList = new ArrayList<Users>();
				byte[] data = readParse(is);
				JSONArray jarray = new JSONArray(new String(data));
				int id;
				String name;
				String pwd;
				int deptId;
				int numOfRs;
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject item = jarray.getJSONObject(i);
					id = item.getInt("id");
					name = item.getString("name");
					pwd = item.getString("password");
					deptId = item.getInt("deptId");
					numOfRs = item.getInt("numOfRs");
					userList.add(new Users(id, name, pwd,deptId, numOfRs));
				}
				return userList;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static List<Users> getListFromDevUsers(){
		try {
			int responseCode = visitToService("queryFromDevUsers", 0);
//			Log.i(tag, "��ȡ�û���Ӧ�룺" + responseCode);
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				List<Users> userList = new ArrayList<Users>();
				byte[] data = readParse(is);
				JSONArray jarray = new JSONArray(new String(data));
				int id;
				String name;
				String pwd;
				int deptId;
				int numOfRs;
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject item = jarray.getJSONObject(i);
					id = item.getInt("id");
					name = item.getString("name");
					pwd = item.getString("password");
					deptId = item.getInt("deptId");
					numOfRs = item.getInt("numOfRs");
					userList.add(new Users(id, name, pwd,deptId, numOfRs));
				}
				return userList;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public static int deleteProject(int id) {
		int responseCode = visitToService("deleteFormProject", id);
		int data = 0;
		if (responseCode == 200) {
			try {
				InputStream is = conn.getInputStream();
				data = is.read();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return data;
	}
	public static int updateRequire(String order,int id){
//		Log.i(tag, "��ȡ������Ӧ�룺" + visitToService(order, id));
		int responseCode = visitToService(order, id);
		int data = 0;
		if (responseCode == 200) {
			try {
				InputStream is = conn.getInputStream();
				data = is.read();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return data;
	}
	public static int insertRequire(Requirement requirement){
		
		try {
			URL url = new URL(URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(60000);
			conn.setDoOutput(true);
			// post�������
			String data = "order=insertToRequire&name="+requirement.getName()
					+"&projectId="+requirement.getProjectid()
					+"&priorityId="+requirement.getPriorityid()
					+"&detail="+requirement.getDetail()
					+"&userId="+requirement.getUserid();
			
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			Log.i(tag, "������Ӧ�룺" + conn.getResponseCode());
			int responseCode = conn.getResponseCode();
			int result = 0;
			if(responseCode == 200){
				InputStream is = conn.getInputStream();
				result = is.read();
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	public static int insertUser(Users users){
		
		try {
			URL url = new URL(URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(60000);
			conn.setDoOutput(true);
			// post�������
			String data = "order=insertToUser&name="+users.getName()
					+"&pwd="+users.getPassword()
					+"&deptId="+users.getDeptId();
			
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			Log.i(tag, "������Ӧ�룺" + conn.getResponseCode());
			int responseCode = conn.getResponseCode();
			int result = 0;
			if(responseCode == 200){
				InputStream is = conn.getInputStream();
				result = is.read();
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	public static int queryNumOfRequire(int id){
		int responseCode = visitToService("queryNumOfRequire", id);
		int data = 0;
		if(responseCode == 200){
			try {
				InputStream is = conn.getInputStream();
				data = is.read();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * @param order
	 *            ����������ͷ�������
	 * @return ������Ӧ��
	 */
	private static int visitToService(String order, int id) {
		int responseCode = 0;
		try {
			URL url = new URL(URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(60000);
			conn.setDoOutput(true);
			// post�������
			String data;
			if (id != 0) {
				data = "order=" + order + "&id=" + id;
			} else {
				data = "order=" + order;
			}
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
//			Log.i(tag, "��Ӧ�룺" + conn.getResponseCode());
			responseCode = conn.getResponseCode();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseCode;
	}

	/**
	 * ��ȡ������
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private static byte[] readParse(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[2048];
		int len = 0;

		while ((len = is.read(data)) != -1) {
			baos.write(data, 0, len);
		}

		is.close();
		byte[] byteArray = baos.toByteArray();
		baos.close();
		return byteArray;
	}
}
