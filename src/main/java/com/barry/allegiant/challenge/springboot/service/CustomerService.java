package com.barry.allegiant.challenge.springboot.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.barry.allegiant.challenge.springboot.dao.CustomerDAO;
import com.barry.allegiant.challenge.springboot.model.Customer;
import com.barry.allegiant.challenge.springboot.model.CustomerResponse;
import com.barry.allegiant.challenge.springboot.model.CustomerSearch;
import com.barry.allegiant.challenge.springboot.model.CustomerSearchResponse;
import com.barry.allegiant.challenge.springboot.model.StatusResponse;

@Component
public class CustomerService {
	
	ApplicationContext context;

	public CustomerService() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	public CustomerResponse findCustomer(int id) {
		
    	CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
    	
    	CustomerResponse response = customerDAO.findByCustomerId(id);

		return response;
	}
	
	public StatusResponse removeCustomer(int id) {
		
    	CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
    	
    	StatusResponse response = customerDAO.removeByCustomerId(id);

		return response;
	}
	
	public StatusResponse editCustomer(int id, Customer customer) {

    	CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
    	
    	StatusResponse response = customerDAO.editByCustomerId(id, customer);

		return response;
	}
	
	public StatusResponse addCustomer(Customer customer) {

    	CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
    	
    	StatusResponse response = customerDAO.addCustomer(customer);

		return response;
	}
	
	public CustomerSearchResponse customerSearch(CustomerSearch customerSearch) {

    	CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
    	
    	CustomerSearchResponse response = customerDAO.customerSearch(customerSearch);

		return response;
	}

}
