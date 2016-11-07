package fr.chaussadeFourrier.carshop.model;

public class OrderDetail
{

	private int orderLineNumber;
	private int orderNumber;
	private double priceEach;
	private String productCode;
	private int quantityOrdered;

	public OrderDetail(int orderNumber, String productCode, int quantityOrdered, double priceEach, int orderLineNumber)
	{
		this.orderNumber = orderNumber;
		this.productCode = productCode;
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
		this.orderLineNumber = orderLineNumber;
	}
	
	public OrderDetail(){
	}

	public int getOrderLineNumber()
	{
		return this.orderLineNumber;
	}

	public int getOrderNumber()
	{
		return this.orderNumber;
	}

	public double getPriceEach()
	{
		return this.priceEach;
	}

	public String getProductCode()
	{
		return this.productCode;
	}

	public int getQuantityOrdered()
	{
		return this.quantityOrdered;
	}

	public void setOrderLineNumber(int orderLineNumber)
	{
		this.orderLineNumber = orderLineNumber;
	}

	public void setOrderNumber(int orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public void setPriceEach(double priceEach)
	{
		this.priceEach = priceEach;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public void setQuantityOrdered(int quantityOrdered)
	{
		this.quantityOrdered = quantityOrdered;
	}

}
