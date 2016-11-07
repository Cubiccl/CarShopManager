package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import fr.chaussadeFourrier.carshop.model.Payment;
import fr.chaussadeFourrier.carshop.model.ProductLine;

public class PaymentDAO extends DAO<Payment> {

	public PaymentDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Payment obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"INSERT INTO payments(customerNumber, paymentDate, amount) VALUES ("
							+ obj.getCustomerNumber()
							+ ", "
							+ new SimpleDateFormat("yyyy-MM-dd").format(obj
									.getPaymentDate()) + ", " + obj.getAmount()
							+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Payment obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM payments WHERE checkNumber = '"
							+ obj.getCheckNumber() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Payment obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"UPDATE payments SET customerNumber = "
							+ obj.getCustomerNumber()
							+ " AND paymentDate = "
							+ new SimpleDateFormat("yyyy-MM-dd").format(obj
									.getPaymentDate()) + " AND amount = "
							+ obj.getAmount() + " WHERE checkNumber = '"
							+ obj.getCheckNumber() + "'");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Payment find(int id) {
		return null;
	}

	@Override
	public Payment find(String id) {
		Payment payments = new Payment();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM payments WHERE checkNumber = '" + id + "'");
			if (result.first()) {
				payments = new Payment(result.getInt("customerNumber"), id,
						result.getDate("paymentDate"),
						result.getDouble("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}

}
