package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import fr.chaussadeFourrier.carshop.model.Order;
import fr.chaussadeFourrier.carshop.model.ProductLine;

public class OrderDAO extends DAO<Order> {

	public OrderDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Order obj) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"INSERT INTO orders(orderDate, requiredDate, shippedDate, status, comments, customerNumber) VALUES ("
									+ df.format(obj.getOrderDate())
									+ ", "
									+ df.format(obj.getRequiredDate())
									+ ", "
									+ df.format(obj.getShippedDate())
									+ ", '"
									+ obj.getStatus()
									+ "', '"
									+ obj.getComments()
									+ "', "
									+ obj.getCustomerNumber() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Order obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM orders WHERE orderNumber = "
							+ obj.getOrderNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Order obj) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"UPDATE orders SET orderDate = "
							+ df.format(obj.getOrderDate())
							+ " AND requiredDate = "
							+ df.format(obj.getRequiredDate())
							+ " AND shippedDate = "
							+ df.format(obj.getShippedDate())
							+ " AND status = '" + obj.getStatus()
							+ "' AND comments = '" + obj.getComments()
							+ "' AND customerNumber = "
							+ obj.getCustomerNumber() + " WHERE OrderNumber = "
							+ obj.getOrderNumber());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Order find(int id) {
		Order order = new Order();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM orders WHERE orderNumber = " + id);
			if (result.first()) {
				order = new Order(id, result.getDate("orderDate"),
						result.getDate("requiredDate"),
						result.getDate("shippedDate"),
						result.getString("status"),
						result.getString("comments"),
						result.getInt("customerNumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public Order find(String id) {
		return null;
	}

}
