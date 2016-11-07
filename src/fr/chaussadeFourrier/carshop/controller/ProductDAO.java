package fr.chaussadeFourrier.carshop.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;

import fr.chaussadeFourrier.carshop.model.Product;

public class ProductDAO extends DAO<Product> {

	public ProductDAO(Connection conn) {
		super(conn);
	}

	public static Blob imageIconToBlob(ImageIcon icon) {
		Blob blob = null;
		try {
			Image img = icon.getImage();
			BufferedImage bi = new BufferedImage(img.getWidth(null),
					img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			WritableRaster raster = bi.getRaster();
			DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
			byte[] tab = data.getData();
			blob = new SerialBlob(tab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blob;
	}

	@Override
	public boolean create(Product obj) {

		try {
			this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"INSERT INTO products(productName, productLine, photo, productVendor, productDescription, quantityInStock, buyPrice, MSRP VALUES ('"
									+ obj.getProductName()
									+ "', '"
									+ obj.getProductLine()
									+ "', "
									+ imageIconToBlob(obj.getPhoto())
									+ ", '"
									+ obj.getProductVendor()
									+ "', '"
									+ obj.getProductDescription()
									+ "', "
									+ obj.getQuantityInStock()
									+ ", "
									+ obj.getBuyPrice()
									+ ", "
									+ obj.getMSRP()
									+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Product obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"DELETE FROM products WHERE productCode = '"
							+ obj.getProductCode() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Product obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"UPDATE products SET productName = '"
							+ obj.getProductName() + "' AND productLine = '"
							+ obj.getProductLine() + "' AND photo = "
							+ imageIconToBlob(obj.getPhoto())
							+ " AND productVendor = '" + obj.getProductVendor()
							+ "' AND productDescription = '"
							+ obj.getProductDescription()
							+ "' AND quantityInStock = "
							+ obj.getQuantityInStock() + " AND buyPrice = "
							+ obj.getBuyPrice() + " AND MSRP = "
							+ obj.getMSRP() + " WHERE productCode = '"
							+ obj.getProductCode() + "'");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Product find(String id) {
		Product product = new Product();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM products WHERE productCode = '" + id + "'");
			if (result.first()) {
				Blob blob = result.getBlob("photo");
				ImageIcon photo = new ImageIcon(blob.getBytes(1L,
						(int) blob.length()));
				product = new Product(id, result.getString("productName"),
						result.getString("productLine"), photo,
						result.getString("productVendor"),
						result.getString("productDescription"),
						result.getInt("quantityInStock"),
						result.getDouble("buyPrice"), result.getDouble("msrp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product find(int id) {
		return null;
	}

}
