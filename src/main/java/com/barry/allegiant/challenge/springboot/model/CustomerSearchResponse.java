package com.barry.allegiant.challenge.springboot.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchResponse {

	public String status;
	public String message;
	public Integer recordsFound;
	public List<Customer> customers;

	public CustomerSearchResponse() { }

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

	public Integer getRecordsFound() {
		return recordsFound;
	}

	public void setRecordsFound(Integer recordsFound) {
		this.recordsFound = recordsFound;
	}

	public List<Customer> getCustomers() {
		if (customers == null) {
			customers = new ArrayList<Customer>();
		}
		
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void addCustomer(Customer customer) {
		if (customers == null) {
			customers = new ArrayList<Customer>();
		}
		
		customers.add(customer);
	}
	
}
