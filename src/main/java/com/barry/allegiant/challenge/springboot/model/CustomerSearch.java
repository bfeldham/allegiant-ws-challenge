package com.barry.allegiant.challenge.springboot.model;

public class CustomerSearch {
	
	private String field;
	private String parameter;
	private String operator;
	

	public CustomerSearch() {
		// TODO Auto-generated constructor stub
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public String getParameter() {
		return parameter;
	}


	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}

}
