package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.chaussadeFourrier.carshop.model.Office;

public class OfficeDAO extends DAO<Office> {

	public OfficeDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Office obj) {
		try {
			this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"INSERT INTO offices(city, phone, adressLine, state, country, postalCode) VALUES ('"
									+ obj.getCity()
									+ "', '"
									+ obj.getPhone()
									+ "', '"
									+ obj.getAddressLine()
									+ "', '"
									+ obj.getState()
									+ "', '"
									+ obj.getCountry()
									+ "', '"
									+ obj.getPostalCode() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Office obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM offices WHERE officeCode = '"
							+ obj.getOfficeCode() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Office obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"UPDATE offices SET city = '" + obj.getCity()
							+ "' , phone = '" + obj.getPhone()
							+ "' , adressLine = '" + obj.getAddressLine()
							+ "' , state = '" + obj.getState()
							+ "' , country = '" + obj.getCountry()
							+ "' , postalCode = '" + obj.getPostalCode()
							+ "' WHERE officeCode = '" + obj.getOfficeCode()
							+ "'");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Office find(int id) {
		return null;
	}

	@Override
	public Office find(String id) {
		Office office = new Office();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM offices WHERE officeCode = '" + id + "'");
			if (result.first()) {
				office = new Office(id, result.getString("city"),
						result.getString("phone"),
						result.getString("adressLine"),
						result.getString("state"), result.getString("country"),
						result.getString("postalCode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return office;
	}

}
