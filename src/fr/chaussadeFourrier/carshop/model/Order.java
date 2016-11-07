package fr.chaussadeFourrier.carshop.model;

import java.sql.Date;

public class Order
{

	private String comments;
	private int customerNumber;
	private Date orderDate;
	private int orderNumber;
	private Date requiredDate;
	private Date shippedDate;
	private String status;

	public Order(int orderNumber, Date orderDate, Date requiredDate, Date shippedDate, String status, String comments, int customerNumber)
	{
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.shippedDate = shippedDate;
		this.status = status;
		this.comments = comments;
		this.customerNumber = customerNumber;
	}
	
	public Order(){
	}

	public String getComments()
	{
		return comments;
	}

	public int getCustomerNumber()
	{
		return customerNumber;
	}

	public Date getOrderDate()
	{
		return orderDate;
	}

	public int getOrderNumber()
	{
		return orderNumber;
	}

	public Date getRequiredDate()
	{
		return requiredDate;
	}

	public Date getShippedDate()
	{
		return shippedDate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public void setCustomerNumber(int customerNumber)
	{
		this.customerNumber = customerNumber;
	}

	public void setOrderDate(Date orderDate)
	{
		this.orderDate = orderDate;
	}

	public void setOrderNumber(int orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public void setRequiredDate(Date requiredDate)
	{
		this.requiredDate = requiredDate;
	}

	public void setShippedDate(Date shippedDate)
	{
		this.shippedDate = shippedDate;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}
