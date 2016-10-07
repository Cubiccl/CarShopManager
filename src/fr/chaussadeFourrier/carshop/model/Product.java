package fr.chaussadeFourrier.carshop.model;

import javax.swing.ImageIcon;

public class Product
{

	private double buyPrice;
	private double msrp;
	private ImageIcon photo;
	private String productCode;
	private String productDescription;
	private String productLine;
	private String productName;
	private String productVendor;
	private int quantityInStock;

	public Product(String productCode, String productName, String productLine, ImageIcon photo, String productVendor, String productDescription,
			int quantityInStock, double buyPrice, double msrp)
	{
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.productLine = productLine;
		this.photo = photo;
		this.productVendor = productVendor;
		this.productDescription = productDescription;
		this.quantityInStock = quantityInStock;
		this.buyPrice = buyPrice;
		this.msrp = msrp;
	}

	public double getBuyPrice()
	{
		return this.buyPrice;
	}

	public double getMSRP()
	{
		return this.msrp;
	}

	public ImageIcon getPhoto()
	{
		return this.photo;
	}

	public String getProductCode()
	{
		return this.productCode;
	}

	public String getProductDescription()
	{
		return this.productDescription;
	}

	public String getProductLine()
	{
		return this.productLine;
	}

	public String getProductName()
	{
		return this.productName;
	}

	public String getProductVendor()
	{
		return this.productVendor;
	}

	public int getQuantityInStock()
	{
		return this.quantityInStock;
	}

	public void setBuyPrice(double buyPrice)
	{
		this.buyPrice = buyPrice;
	}

	public void setMSRP(double msrp)
	{
		this.msrp = msrp;
	}

	public void setPhoto(ImageIcon photo)
	{
		this.photo = photo;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public void setProductDescription(String productDescription)
	{
		this.productDescription = productDescription;
	}

	public void setProductLine(String productLine)
	{
		this.productLine = productLine;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public void setProductVendor(String productVendor)
	{
		this.productVendor = productVendor;
	}

	public void setQuantityInStock(int quantityInStock)
	{
		this.quantityInStock = quantityInStock;
	}

}
