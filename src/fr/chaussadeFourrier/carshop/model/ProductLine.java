package fr.chaussadeFourrier.carshop.model;

public class ProductLine
{

	private String description;
	private String productLine;

	public ProductLine(String productLine, String description)
	{
		this.productLine = productLine;
		this.description = description;
	}

	public String getDescription()
	{
		return this.description;
	}

	public String getProductLine()
	{
		return this.productLine;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setProductLine(String productLine)
	{
		this.productLine = productLine;
	}

}
