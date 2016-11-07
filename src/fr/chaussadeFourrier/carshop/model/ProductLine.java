package fr.chaussadeFourrier.carshop.model;

public class ProductLine
{

	private String textDescription;
	private String productLine;

	public ProductLine(String productLine, String description)
	{
		this.productLine = productLine;
		this.textDescription = description;
	}
	public ProductLine(){
	}

	public String getTextDescription()
	{
		return this.textDescription;
	}

	public String getProductLine()
	{
		return this.productLine;
	}

	public void setTextDescription(String description)
	{
		this.textDescription = description;
	}

	public void setProductLine(String productLine)
	{
		this.productLine = productLine;
	}

}
