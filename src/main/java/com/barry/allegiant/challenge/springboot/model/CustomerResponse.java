package com.barry.allegiant.challenge.springboot.model;

import com.barry.allegiant.challenge.springboot.dto.CustomerDTO;

public class CustomerResponse {
	
	public String status;
	public String message;
	public CustomerDTO customer;

	public CustomerResponse() { }

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

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

}
