package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.chaussadeFourrier.carshop.model.Employee;

public class EmployeeDAO extends DAO<Employee> {

	public EmployeeDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Employee obj) {
		try {
			this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"INSERT INTO employees(lastName, firstName, email, officeCode, reportsTo, jobTitle) VALUES ('"
									+ obj.getLastName()
									+ "', '"
									+ obj.getFirstName()
									+ "', '"
									+ obj.getEmail()
									+ "', '"
									+ obj.getOfficeCode()
									+ "', "
									+ obj.getReportsTo()
									+ ", '"
									+ obj.getJobTitle() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Employee obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM employees WHERE employeeNumber = "
							+ obj.getEmployeeNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Employee obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"UPDATE employees SET lastName = '" + obj.getLastName()
							+ "' , firstName = '" + obj.getFirstName()
							+ "' , email = '" + obj.getEmail()
							+ "' , officeCode = '" + obj.getOfficeCode()
							+ "' , reportsTo = " + obj.getReportsTo()
							+ " , jobTitle = '" + obj.getJobTitle()
							+ "' WHERE employeeNumber = "
							+ obj.getEmployeeNumber());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Employee find(int id) {
		Employee employee = new Employee();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM productLines WHERE productLine = " + id);
			if (result.first()) {
				employee = new Employee(id, result.getString("lastName"),
						result.getString("firstName"),
						result.getString("email"),
						result.getString("officeCode"),
						result.getInt("reportsTo"),
						result.getString("jobTitle"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public Employee find(String id) {
		return null;
	}

}
