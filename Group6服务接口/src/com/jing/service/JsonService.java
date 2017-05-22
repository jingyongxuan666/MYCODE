package com.jing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jing.dao.Dao;
import com.jing.dao.RequireDao;
import com.jing.dao.UsersDao;
import com.jing.domain.Project;
import com.jing.domain.Requirement;
import com.jing.domain.Users;

public class JsonService {
	public static List<Project> getListProject() {
		Dao dao = new Dao();
		List<Project> plist = dao.queryAllFromProject();
//		int r;
//		for (int i = 0; i < 1000; i++) {
//			r = (new Random().nextInt(5))+1;
//			mlist.add(new Project(i, "大力牌痒痒挠", "大力公司", "大力牌痒痒挠，止体痒，更止心痒",
//					"2016-06-20", "2016-07-20", r));
//		}
		return plist;

	}
	public static List<Requirement> getListRequirement(){
		RequireDao rdao = new RequireDao();
		List<Requirement> rlist = rdao.queryAllFromRequirement();
		
		return rlist;
	}
	public static List<Users> getListUsers(){
		UsersDao udao = new UsersDao();
		List<Users> ulist = udao.queryAllFromUsers();
		
		return ulist;
	}
	public static List<Users> getListDevUsers(){
		UsersDao udao = new UsersDao();
		List<Users> ulist = udao.queryDevFromUsers();
		
		return ulist;
	}
}
