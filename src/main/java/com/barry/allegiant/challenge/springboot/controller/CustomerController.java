package com.barry.allegiant.challenge.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.barry.allegiant.challenge.springboot.model.Customer;
import com.barry.allegiant.challenge.springboot.model.CustomerResponse;
import com.barry.allegiant.challenge.springboot.model.CustomerSearch;
import com.barry.allegiant.challenge.springboot.model.CustomerSearchResponse;
import com.barry.allegiant.challenge.springboot.model.StatusResponse;
import com.barry.allegiant.challenge.springboot.service.CustomerService;

import flexjson.JSONDeserializer;


@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customer/read/{customerId}")
	public CustomerResponse findCustomerById(@PathVariable String customerId) {
		return customerService.findCustomer(Integer.parseInt(customerId));
	}
	
	@GetMapping("/customer/delete/{customerId}")
	public StatusResponse removeCustomerById(@PathVariable String customerId) {
		return customerService.removeCustomer(Integer.parseInt(customerId));
	}
		
	@PostMapping("/customer/update/{customerId}")
	public StatusResponse editCustomerById(@PathVariable String customerId, @RequestBody(required = true) String body) {
		
		Customer customer = new JSONDeserializer<Customer>().use(null, Customer.class).deserialize(body);
		
		return customerService.editCustomer(Integer.parseInt(customerId), customer);
	}
	
	@PostMapping("/customer/add")
	public StatusResponse addCustomer(@RequestBody(required = true) String body) {
		
		Customer customer = new JSONDeserializer<Customer>().use(null, Customer.class).deserialize(body);
		
		return customerService.addCustomer(customer);
	}
	
	@PostMapping("/customer/search")
	public CustomerSearchResponse customerSearch(@RequestBody(required = true) String body) {
		
		CustomerSearch customerSearch = new JSONDeserializer<CustomerSearch>().use(null, CustomerSearch.class).deserialize(body);
		
		return customerService.customerSearch(customerSearch);
	}
}
