package com.example.assistant104.entities;

public class Message {
	private String id;
	private String preMessage;
	private String anaMessage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPreMessage() {
		return preMessage;
	}
	public void setPreMessage(String preMessage) {
		this.preMessage = preMessage;
	}
	public String getAnaMessage() {
		return anaMessage;
	}
	public void setAnaMessage(String anaMessage) {
		this.anaMessage = anaMessage;
	}
	public Message(String id, String preMessage, String anaMessage) {
		super();
		this.id = id;
		this.preMessage = preMessage;
		this.anaMessage = anaMessage;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "\n编号："+id + "\n原始报文：\n\t" + preMessage
				+ "\n报文解析：\n\t" + anaMessage+"\n\n*******************\n";
	}
	
	
}
