package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.chaussadeFourrier.carshop.model.ProductLine;

public class ProductLineDAO extends DAO<ProductLine> {

	public ProductLineDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(ProductLine obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"INSERT INTO productLines(textDescription) VALUES ('"
							+ obj.getTextDescription() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(ProductLine obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM productLines WHERE productLine = '"
							+ obj.getProductLine() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(ProductLine obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"UPDATE productLines SET textDescription = '"
							+ obj.getTextDescription()
							+ "' WHERE productLine = '" + obj.getProductLine()
							+ "'");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ProductLine find(int id) {
		return null;
	}

	@Override
	public ProductLine find(String id) {
		ProductLine productLine = new ProductLine();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM productLines WHERE productLine = '" + id
							+ "'");
			if (result.first()) {
				productLine = new ProductLine(id,
						result.getString("textDescription"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productLine;
	}

}
