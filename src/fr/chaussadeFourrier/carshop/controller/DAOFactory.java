package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;

import fr.chaussadeFourrier.carshop.model.Employee;
import fr.chaussadeFourrier.carshop.model.Office;
import fr.chaussadeFourrier.carshop.model.Order;
import fr.chaussadeFourrier.carshop.model.OrderDetail;
import fr.chaussadeFourrier.carshop.model.Payment;
import fr.chaussadeFourrier.carshop.model.Product;
import fr.chaussadeFourrier.carshop.model.ProductLine;

public class DAOFactory {
	protected static final Connection conn = MaConnexion.getInstance();
	
	public static DAO<?> getCustomerDAO(){
		return new CustomerDAO(conn);
	}
	
	public static DAO<Employee> getEmployeeDAO(){
		return new EmployeeDAO(conn);
	}
	
	public static DAO<Office> getOfficeDAO(){
		return new OfficeDAO(conn);
	}
	
	public static DAO<Order> getOrderDAO(){
		return new OrderDAO(conn);
	}
	
	public static DAO<OrderDetail> getOrderDetailDAO(){
		return new OrderDetailDAO(conn);
	}
	
	public static DAO<Payment> getPaymentDAO(){
		return new PaymentDAO(conn);
	}
	
	public static DAO<Product> getProductDAO(){
		return new ProductDAO(conn);
	}
	
	public static DAO<ProductLine> getProductLineDAO(){
		return new ProductLineDAO(conn);
	}
}
