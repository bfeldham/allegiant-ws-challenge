package com.barry.allegiant.challenge.springboot.model;

public class StatusResponse {

	public String action;
	public String status;
	public String message;
	public Integer recordsAffected;
	public Integer id;
	
	public StatusResponse() {}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getRecordsAffected() {
		return recordsAffected;
	}

	public void setRecordsAffected(Integer recordsAffected) {
		this.recordsAffected = recordsAffected;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
