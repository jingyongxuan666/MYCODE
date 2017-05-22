package com.jing.domain;

public class Users {

	private int id;
	private String name;
	private String password;
	private String role;
	private int deptId;
	private int isRem_Atl;
	private int numOfRs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	
	
	public int getNumOfRs() {
		return numOfRs;
	}
	public void setNumOfRs(int numOfRs) {
		this.numOfRs = numOfRs;
	}
	public int getIsRem_Atl() {
		return isRem_Atl;
	}
	public void setIsRem_Atl(int isRem_Atl) {
		this.isRem_Atl = isRem_Atl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	public Users(int id, String name, String password, int deptId, int numOfRs) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.deptId = deptId;
		this.numOfRs = numOfRs;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Users [name=" + name + ", password=" + password + ", role="
				+ role + ", isRem_Atl=" + isRem_Atl + "]";
	}
	
}
