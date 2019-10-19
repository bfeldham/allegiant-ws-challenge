package com.barry.allegiant.challenge.springboot.dao;

import com.barry.allegiant.challenge.springboot.model.Customer;
import com.barry.allegiant.challenge.springboot.model.CustomerResponse;
import com.barry.allegiant.challenge.springboot.model.CustomerSearch;
import com.barry.allegiant.challenge.springboot.model.CustomerSearchResponse;
import com.barry.allegiant.challenge.springboot.model.StatusResponse;

public interface CustomerDAO {
	public CustomerResponse findByCustomerId(int id);
	public StatusResponse removeByCustomerId(int id);
	public StatusResponse editByCustomerId(int id, Customer customer);
	public StatusResponse addCustomer(Customer customer);
	public CustomerSearchResponse customerSearch(CustomerSearch customer);
}
