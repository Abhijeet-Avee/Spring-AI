package com.spring.ollama.chatbot_demo_ollama.entity;

public class BasicResponseModel {

	private String title;
	private String content;
	private String createdYear;

	public BasicResponseModel() {
		super();
	}

	public BasicResponseModel(String title, String content, String createdYear) {
		super();
		this.title = title;
		this.content = content;
		this.createdYear = createdYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedYear() {
		return createdYear;
	}

	public void setCreatedYear(String createdYear) {
		this.createdYear = createdYear;
	}

}
