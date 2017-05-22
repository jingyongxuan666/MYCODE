package com.jing.domain;


import java.io.Serializable;

/**
 * 
 * @name Department.java
 * @Description 实体类Department,和部门表对应
 * @version1.0
 * @author BaoHan
 * @Date 2016.5.28
 */
public class Department implements Serializable {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
