package com.group6.domain;

public class Project {
	private int id;
	private String name;
	private String client;
	private String description;
	private String beginDate;
	private String endDate;
	private int stateId;
	private int progress;
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
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
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Project(int id, String name, String client, String description,
			String beginDate, String endDate, int stateId, int progress) {
		super();
		this.id = id;
		this.name = name;
		this.client = client;
		this.description = description;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.stateId = stateId;
		this.progress = progress;
	}
	
	
}
