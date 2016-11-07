package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.chaussadeFourrier.carshop.model.Customer;

public class CustomerDAO extends DAO<Customer> {

	public CustomerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Customer obj) {
		try {
			this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"INSERT INTO customers(customerName, contactLastName, contactFirstName, phone, city, state, postalCode, country, salesRepEmployeeNumber) VALUES ('"
									+ obj.getCustomerName()
									+ "', '"
									+ obj.getContactLastName()
									+ "', '"
									+ obj.getContractFirstName()
									+ "', '"
									+ obj.getPhone()
									+ "', '"
									+ obj.getCity()
									+ "', '"
									+ obj.getState()
									+ "', '"
									+ obj.getPostalCode()
									+ "', '"
									+ obj.getCountry()
									+ "', "
									+ obj.getSalesRepEmployeeNumber() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Customer obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM customers WHERE customerNumber = "
							+ obj.getCustomerNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Customer obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"UPDATE customers SET customerName = '"
									+ obj.getCustomerName()
									+ "' AND contactLastName = '"
									+ obj.getContactLastName()
									+ "' AND contactFirstName = '"
									+ obj.getContractFirstName()
									+ "' AND phone = '" + obj.getPhone()
									+ "' AND city = '" + obj.getCity()
									+ "' AND state = '" + obj.getState()
									+ "' AND postalCode = '"
									+ obj.getPostalCode() + "' AND country = '"
									+ obj.getCountry()
									+ "' AND salesRepEmployeeNumber = "
									+ obj.getSalesRepEmployeeNumber()
									+ " WHERE productLine = "
									+ obj.getCustomerNumber());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Customer find(int id) {
		Customer customer = new Customer();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM customers WHERE customerNumber = " + id);
			if (result.first()) {
				customer = new Customer(id, result.getString("customerName"),
						result.getString("contactLastName"),
						result.getString("contactFirstName"),
						result.getString("phone"), result.getString("city"),
						result.getString("state"),
						result.getString("postalCode"),
						result.getString("country"),
						result.getInt("salesRepEmployeeNumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Customer find(String id) {
		return null;
	}

}
