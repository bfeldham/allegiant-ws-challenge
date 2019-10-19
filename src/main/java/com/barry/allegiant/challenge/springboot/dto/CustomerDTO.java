package com.barry.allegiant.challenge.springboot.dto;

import java.lang.Integer;
import java.math.BigDecimal;
import java.sql.Date;

public class CustomerDTO {
	
	private Integer id;
	private String email;
	private String firstName;
	private String lastName;
	private String ip;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Date createdAt;
	private Date updatedAt;
	
	public CustomerDTO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Customer->id=[" + getId() + "], ");
		sb.append("email=[" + getEmail() + "], ");
		sb.append("firstName=[" + getFirstName() + "], ");
		sb.append("lastName=[" + getLastName() + "], ");
		sb.append("ip=[" + getIp() + "], ");
		sb.append("latitude=[" + getLatitude() + "], ");
		sb.append("longitude=[" + getLongitude() + "], ");
		sb.append("createdAt=[" + getCreatedAt() + "], ");
		sb.append("updatedAt=[" + getUpdatedAt() + "]");
		return sb.toString();
	}

}
