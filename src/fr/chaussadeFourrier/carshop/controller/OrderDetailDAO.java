package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.chaussadeFourrier.carshop.model.OrderDetail;

public class OrderDetailDAO extends DAO<OrderDetail> {

	public OrderDetailDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(OrderDetail obj) {
		try {
			this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"INSERT INTO orderdetails(productCode, quantityOrdered, priceEach, orderLineNumber VALUES ('"
									+ obj.getProductCode()
									+ "', "
									+ obj.getQuantityOrdered()
									+ ", "
									+ obj.getPriceEach()
									+ ", "
									+ obj.getOrderLineNumber() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(OrderDetail obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM orderdetails WHERE orderNumber = "
							+ obj.getOrderNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(OrderDetail obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"UPDATE orderdetails SET productCode = '"
							+ obj.getProductCode() + "' , quantityOrdered = "
							+ obj.getQuantityOrdered() + " , priceEach = "
							+ obj.getPriceEach() + " , orderLineNumber = "
							+ obj.getOrderLineNumber()
							+ " WHERE orderNumber = " + obj.getOrderNumber());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public OrderDetail find(int id) {
		OrderDetail orderDetail = new OrderDetail();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM orderdetails WHERE orderNumber = " + id);
			if (result.first()) {
				orderDetail = new OrderDetail(id,
						result.getString("productCode"),
						result.getInt("quantityOrdered"),
						result.getDouble("priceEach"),
						result.getInt("orderLineNumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDetail;
	}

	@Override
	public OrderDetail find(String id) {
		return null;
	}

}
