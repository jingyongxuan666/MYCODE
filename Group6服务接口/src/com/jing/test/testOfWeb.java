package com.jing.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.jing.dao.Dao;
import com.jing.dao.ProjectDao;
import com.jing.dao.RequireDao;
import com.jing.dao.UsersDao;
import com.jing.domain.Project;
import com.jing.domain.Requirement;
import com.jing.domain.Users;

public class testOfWeb {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testInsertToProject() {
		Dao dao = new Dao();
		int r;
		int d;
		for (int i = 0; i < 20; i++) {
			r = (new Random().nextInt(4))+1;
			d=dao.insertToProject(new Project(3+i, "testData"+i, 
					"g"+i+"��˾", "��һ������д����������", 
					"2013-07-06 00:00:00", "2015-03-01 00:00:00", r,60));
			System.out.println(d);
		}
//		fail("Not yet implemented");
	}
	@Test
	public void testDeleteToProject() {
		Dao dao = new Dao();
		int isDel = dao.deleteFromProject(14);
		System.out.println("ɾ�����ܣ�"+isDel);
//		fail("Not yet implemented");
	}
	@Test
	public void testInsertToRequirement() {
		RequireDao rdao = new RequireDao();
		Date date = new Date();
		
		int a=rdao.insertToRequire(new Requirement(1092, 2, "����2", 2, "����", "2016-01-01 00:00:00", "2016-01-01 00:00:00", 10, 3, 1));
		System.out.println("���������ܣ�"+a);
//		fail("Not yet implemented");
	}

	@Test
	public void testQueryItemFromUser() {
		UsersDao udao = new UsersDao();
		
		List<Users> ulist = udao.queryItemFromUsers("d1");
		
		System.out.println("��ѯ�û����룺"+ulist.get(0).getPassword());
//		fail("Not yet implemented");
	}
	@Test
	public void testupdateRequire() {
		RequireDao rdao = new RequireDao();
		int isUp = rdao.updateRequirement("enddate", 1084);
		System.out.println("���¹��ܣ�"+isUp);
//		fail("Not yet implemented");
	}
	@Test
	public void testInsertToUser() {
		UsersDao udao = new UsersDao();
		int a=udao.insertToUser(new Users(0, "������", "54250", 2, 0));
		System.out.println("�����û����ܣ�"+a);
//		fail("Not yet implemented");
	}
	@Test
	public void testQueryRequire() {
		RequireDao rdao = new RequireDao();
		rdao.queryAllFromRequirement();
//		List<Users> ulist = udao.queryItemFromUsers("d1");
//		
//		System.out.println("��ѯ�û����룺"+ulist.get(0).getPassword());
//		fail("Not yet implemented");
	}
	@Test
	public void testCountRequire() {
		ProjectDao pdao = new ProjectDao();
//		int p= pdao.queryNumOfRequire(1095);
//		List<Users> ulist = udao.queryItemFromUsers("d1");
		int p = pdao.updateProgressFromProject(1095);
		System.out.println("�ٷֱȣ�"+p);
//		fail("Not yet implemented");
	}

}
