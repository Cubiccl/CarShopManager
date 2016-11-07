package fr.chaussadeFourrier.carshop.controller;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import fr.chaussadeFourrier.carshop.model.Product;

public class ProductDAO extends DAO<Product>{
	
	/** See parent for methods description*/
	
	public ProductDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Product obj) {
		try{
			
		}
		return false;
	}

	@Override
	public boolean delete(Product obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Product obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Product find(String id) {
		Product product = new Product();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM product WHERE prod_id = " + id);
			if(result.first()){
				Blob blob = result.getBlob("photo");
				ImageIcon photo = new ImageIcon( blob.getBytes( 1L, (int) blob.length() ) );
				product = new Product(id,result.getString("productName"), result.getString("productLine"), 
						photo, result.getString("productVendor"), result.getString("productDescription"),
						result.getInt("quantityInStock"), result.getDouble("buyPrice"), result.getDouble("msrp"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return product;
	}

	@Override
	public Product find(int id) {
		return null;
	}

}
