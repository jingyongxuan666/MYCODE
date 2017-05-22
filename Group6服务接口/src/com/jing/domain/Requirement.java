package com.jing.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @name Requirement.java
 * @Description 实体类Requirement,和需求表对应
 * @version1.0
 * @author BaoHan
 * @Date 2016.5.28
 */
public class Requirement implements Serializable {
	private int id;
	
	/*和项目表关联*/
	private int projectid;
	
	private String name;
	private int priorityid;
	
	private String detail;
	private String begindate;
	private String enddate;
	private int laborHour;
	
	private int stateid;
	
	private int userid;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriorityid() {
		return priorityid;
	}

	public void setPriorityid(int priorityid) {
		this.priorityid = priorityid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public int getLaborHour() {
		return laborHour;
	}

	public void setLaborHour(int laborHour) {
		this.laborHour = laborHour;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


	public Requirement(int id, int projectid, String name, int priorityid,
			String detail, String begindate, String enddate, int laborHour,
			int stateid, int userid) {
		super();
		this.id = id;
		this.projectid = projectid;
		this.name = name;
		this.priorityid = priorityid;
		this.detail = detail;
		this.begindate = begindate;
		this.enddate = enddate;
		this.laborHour = laborHour;
		this.stateid = stateid;
		this.userid = userid;
	}

	public Requirement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/*toString加上后，报错，不知为什么*/

}
