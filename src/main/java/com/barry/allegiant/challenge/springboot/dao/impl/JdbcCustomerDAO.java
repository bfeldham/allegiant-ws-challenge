package com.barry.allegiant.challenge.springboot.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barry.allegiant.challenge.springboot.dao.CustomerDAO;
import com.barry.allegiant.challenge.springboot.dto.CustomerDTO;
import com.barry.allegiant.challenge.springboot.model.Customer;
import com.barry.allegiant.challenge.springboot.model.CustomerResponse;
import com.barry.allegiant.challenge.springboot.model.CustomerSearch;
import com.barry.allegiant.challenge.springboot.model.CustomerSearchResponse;
import com.barry.allegiant.challenge.springboot.model.SearchField;
import com.barry.allegiant.challenge.springboot.model.SearchOperator;
import com.barry.allegiant.challenge.springboot.model.StatusResponse;

public class JdbcCustomerDAO implements CustomerDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerDAO.class);
	
	private static final String ADD = "add";
	private static final String DELETE = "delete";
	private static final String UPDATE = "update";
	
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";

	private static final char WILDCARD = '%';
	
	private DataSource dataSource;
	
	public JdbcCustomerDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CustomerResponse findByCustomerId(int id) {

		String sql = "SELECT * FROM customers WHERE id = ?";
		
		boolean isVaildId = false;
		CustomerResponse customerResponse = new CustomerResponse();
		
		Connection dbConn = null;
		try {
			dbConn = dataSource.getConnection();
			PreparedStatement ps = dbConn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				isVaildId = true;
				CustomerDTO customer = new CustomerDTO();
				customer.setId(rs.getInt("id"));
				customer.setEmail(rs.getString("email"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setIp(rs.getString("ip"));
				customer.setLongitude(rs.getBigDecimal("longitude"));
				customer.setLatitude(rs.getBigDecimal("latitude"));
				customer.setCreatedAt(rs.getDate("created_at"));
				customer.setUpdatedAt(rs.getDate("updated_at"));
				
				customerResponse.setCustomer(customer);
			}
	
			rs.close();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (dbConn != null) {
				try {
					if (!dbConn.isClosed()) {
						dbConn.close();
					}
				} catch (SQLException e) {}
			}
		}
		
		if (!isVaildId) {
			customerResponse.setStatus(FAILURE);
			customerResponse.setMessage("Customer not found");
			logger.info("Customer not found: id=[" + id + "]");
		} else {
			customerResponse.setStatus(SUCCESS);
			logger.info("Customer found: customer=[" + customerResponse.getCustomer() + "]");
		}
		
		return customerResponse;
	}
	
	@Override
	public StatusResponse removeByCustomerId(int id) {
		int rowCount;
		String sql = "DELETE FROM customers WHERE id = ?";

		Connection dbConn = null;
		
		try {
			dbConn = dataSource.getConnection();
			PreparedStatement ps = dbConn.prepareStatement(sql);
			ps.setInt(1, id);

			rowCount = ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (dbConn != null) {
				try {
					if (!dbConn.isClosed()) {
						dbConn.close();
					}
				} catch (SQLException e) {}
			}
		}
		
		StatusResponse response = new StatusResponse();
		response.setId(id);
		response.setRecordsAffected(rowCount);
		response.setAction(DELETE);
		
		if (rowCount == 0) {
			response.setStatus(FAILURE);
			response.setMessage("Customer not found");
			logger.info("Customer not found: id=[" + id + "]");
		} else {
			response.setStatus(SUCCESS);
			logger.info("" + rowCount + " Customer removed: id=[" + id + "]");
		}
		
		return (response);
	}
	
	@Override
	public StatusResponse editByCustomerId(int id, Customer customer) {
		int rowCount = 0;
				
		String sql = "UPDATE customers SET id = ?, email = LOWER(?), first_name = ?, last_name = ?, ip = ?, longitude = ?, latitude = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

		CustomerResponse customerResponse = findByCustomerId(id);
		if (StringUtils.equalsIgnoreCase(FAILURE, customerResponse.getStatus())
				|| customerResponse.getCustomer() == null) {
			StatusResponse response = new StatusResponse();
			response.setId(id);
			response.setRecordsAffected(0);
			response.setAction(UPDATE);
			response.setStatus(FAILURE);
			response.setMessage("Customer not found");
			return (response);
		}
		
		Connection dbConn = null;		
		try {
			
			CustomerDTO c = customerResponse.getCustomer();
			
			dbConn = dataSource.getConnection();
			PreparedStatement ps = dbConn.prepareStatement(sql);
			
			ps.setInt(1, (customer.getId() != null) ? customer.getId() : c.getId());
			ps.setString(2, (StringUtils.isNotBlank(customer.getEmail())) ? customer.getEmail() : c.getEmail());
			ps.setString(3, (StringUtils.isNotBlank(customer.getFirstName())) ? customer.getFirstName() : c.getFirstName());
			ps.setString(4, (StringUtils.isNotBlank(customer.getLastName())) ? customer.getLastName() : c.getLastName());
			ps.setString(5, (StringUtils.isNotBlank(customer.getIp())) ? customer.getIp() : c.getIp());
			ps.setBigDecimal(6, (customer.getLongitude() != null) ? customer.getLongitude() : c.getLongitude());
			ps.setBigDecimal(7, (customer.getLatitude() != null) ? customer.getLatitude() : c.getLatitude());
			ps.setInt(8, c.getId());

			rowCount = ps.executeUpdate();
			ps.close();	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (dbConn != null) {
				try {
					if (!dbConn.isClosed()) {
						dbConn.close();
					}
				} catch (SQLException e) {}
			}
		}
		
		StatusResponse response = new StatusResponse();
		response.setId(id);
		response.setRecordsAffected(rowCount);
		response.setAction(UPDATE);
		
		if (rowCount == 0) {
			response.setStatus(FAILURE);
			response.setMessage("Customer not updated");
			logger.info("Customer not updated: id=[" + id + "]");
		} else {
			response.setStatus(SUCCESS);
			logger.info("" + rowCount + " Customer updated: id=[" + id + "]");
		}
		
		return response;
	}
	
	@Override
	public StatusResponse addCustomer(Customer customer) {
		int rowCount = 0;
		int newId = 0;		
		String sql = "INSERT INTO customers (id, email, first_name, last_name, ip, longitude, latitude, created_at) VALUES (DEFAULT,LOWER(?),?,?,?,?,?,CURRENT_TIMESTAMP)";

		
		Connection dbConn = null;
		
		try {
			dbConn = dataSource.getConnection();

			PreparedStatement ps = dbConn.prepareStatement(sql, new String[]{"id"});		
			ps.setString(1, (StringUtils.isNotBlank(customer.getEmail())) ? customer.getEmail() : null);
			ps.setString(2, (StringUtils.isNotBlank(customer.getFirstName())) ? customer.getFirstName() : null);
			ps.setString(3, (StringUtils.isNotBlank(customer.getLastName())) ? customer.getLastName() : null);
			ps.setString(4, (StringUtils.isNotBlank(customer.getIp())) ? customer.getIp() : null);
			ps.setBigDecimal(5, (customer.getLongitude() != null) ? customer.getLongitude() : null);
			ps.setBigDecimal(6, (customer.getLatitude() != null) ? customer.getLatitude() : null);

			rowCount = ps.executeUpdate();
	
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				newId = rs.getInt(1);
				rs.close();
			}

			ps.close();	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (dbConn != null) {
				try {
					if (!dbConn.isClosed()) {
						dbConn.close();
					}
				} catch (SQLException e) {}
			}
		}
		
		StatusResponse response = new StatusResponse();
		response.setId(newId);
		response.setRecordsAffected(rowCount);
		response.setAction(ADD);
		
		if (rowCount == 0) {
			response.setStatus(FAILURE);
			response.setMessage("Customer not updated");
			logger.info("Customer not added");
		} else {
			response.setStatus(SUCCESS);
			logger.info("" + rowCount + " Customer updated: id=[" + newId + "]");
		}
		
		return response;
	}
	
	@Override
	public CustomerSearchResponse customerSearch(CustomerSearch customerSearch) {

		CustomerSearchResponse response = new CustomerSearchResponse();
				
		SearchField searchField = null;
		SearchOperator searchOperator = null;
		
		String field = customerSearch.getField();
		String parameter = customerSearch.getParameter();
		String operator = customerSearch.getOperator();
		
		StringBuilder expression = new StringBuilder();
			
		// Validate Search field
		if (StringUtils.equalsIgnoreCase(field, String.valueOf(SearchField.FIRSTNAME))) {
			searchField = SearchField.FIRSTNAME;
		} else if (StringUtils.equalsIgnoreCase(field, String.valueOf(SearchField.LASTNAME))) {
			searchField = SearchField.LASTNAME;
		} else if (StringUtils.equalsIgnoreCase(field, String.valueOf(SearchField.EMAIL))) {
			searchField = SearchField.EMAIL;
		} else if (StringUtils.equalsIgnoreCase(field, String.valueOf(SearchField.IP))) {
			searchField = SearchField.IP;
		} else {
			response.setRecordsFound(new Integer(0));
			response.setStatus(FAILURE);
			response.setMessage("Invalid search field: [" + searchField + "]");
			return response;
		}
				
		// Validate Search operator
		if (StringUtils.isBlank(operator)) {
			searchOperator = SearchOperator.EXACTLY;
		} else if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.BEGINS_WITH))) {
			searchOperator = SearchOperator.BEGINS_WITH;
		} else if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.ENDS_WITH))) {
			searchOperator = SearchOperator.ENDS_WITH;
		} else if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.CONTAINS))) {
			searchOperator = SearchOperator.CONTAINS;
		} else if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.EXACTLY))) {
			searchOperator = SearchOperator.EXACTLY;
		} else {
			response.setRecordsFound(new Integer(0));
			response.setStatus(FAILURE);
			response.setMessage("Invalid search operator: [" + operator + "]");
			return response;
		}
		
		// Validate Search parameter
		if (StringUtils.isBlank(parameter)) {
			response.setRecordsFound(new Integer(0));
			response.setStatus(FAILURE);
			response.setMessage("Invalid search parameter");
			return response;
		} else if (StringUtils.length(parameter) < 3
				&& searchOperator != SearchOperator.EXACTLY) {
			response.setRecordsFound(new Integer(0));
			response.setStatus(FAILURE);
			response.setMessage("Invalid search parameter; Search parameter must contain at least 3 characters for operator=[" + String.valueOf(operator).toLowerCase() + "]");
			return response;
		}
		
		// Create expression
		expression.append(parameter);
		if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.BEGINS_WITH))) {
			expression.append(WILDCARD);
		} else if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.ENDS_WITH))) {
			expression.insert(0, WILDCARD);
		} else if (StringUtils.equalsIgnoreCase(operator, String.valueOf(SearchOperator.CONTAINS))) {
			expression.insert(0, WILDCARD);
			expression.append(WILDCARD);
		} 
		logger.error("ERROR: expression=[" + expression + "]");
		logger.info("INFO: expression=[" + expression + "]");
		logger.debug("DEBUG: expression=[" + expression + "]");
		logger.info("logger.isDebugEnabled()->" + logger.isDebugEnabled());
		
		Connection dbConn = null;
		try {		
			// SQL Statement
			String sql = "SELECT * FROM customers WHERE LOWER(%column%) LIKE LOWER(?)";
			
			//Set search field
			sql = StringUtils.replace(sql, "%column%", searchField.getValue());
			
			dbConn = dataSource.getConnection();
			PreparedStatement ps = dbConn.prepareStatement(sql);
			ps.setString(1, String.valueOf(expression));
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setEmail(rs.getString("email"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setIp(rs.getString("ip"));
				customer.setLongitude(rs.getBigDecimal("longitude"));
				customer.setLatitude(rs.getBigDecimal("latitude"));
				customer.setCreatedAt(rs.getDate("created_at"));
				customer.setUpdatedAt(rs.getDate("updated_at"));
				
				response.addCustomer(customer);
			}
	
			rs.close();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (dbConn != null) {
				try {
					if (!dbConn.isClosed()) {
						dbConn.close();
					}
				} catch (SQLException e) {}
			}
		}
		
		// Populate successful response
		response.setStatus(SUCCESS);
		if (response.getCustomers().isEmpty()) {
			response.setRecordsFound(new Integer(0));
			response.setMessage("No customers found");
			logger.info("No customers found");
		
		} else {
			response.setRecordsFound(new Integer(response.getCustomers().size()));
			logger.info("Customers found: customers=" + response.getCustomers());
		}
		
		return response;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
