package fr.chaussadeFourrier.carshop.model;

public class Customer
{

	private String city;
	private String contactLastName;
	private String contractFirstName;
	private String country;
	private String customerName;
	private int customerNumber;
	private String phone;
	private String postalCode;
	private int salesRepEmployeeNumber;
	private String state;

	public Customer(int customerNumber, String customerName, String contactLastName, String contractFirstName, String phone, String city, String state,
			String postalCode, String country, int salesRepEmployeeNumber)
	{
		this.customerNumber = customerNumber;
		this.customerName = customerName;
		this.contactLastName = contactLastName;
		this.contractFirstName = contractFirstName;
		this.phone = phone;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.salesRepEmployeeNumber = salesRepEmployeeNumber;
	}

	public Customer() {
	}

	public String getCity()
	{
		return this.city;
	}

	public String getContactLastName()
	{
		return this.contactLastName;
	}

	public String getContractFirstName()
	{
		return this.contractFirstName;
	}

	public String getCountry()
	{
		return this.country;
	}

	public String getCustomerName()
	{
		return this.customerName;
	}

	public int getCustomerNumber()
	{
		return this.customerNumber;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public String getPostalCode()
	{
		return this.postalCode;
	}

	public int getSalesRepEmployeeNumber()
	{
		return this.salesRepEmployeeNumber;
	}

	public String getState()
	{
		return this.state;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setContactLastName(String contactLastName)
	{
		this.contactLastName = contactLastName;
	}

	public void setContractFirstName(String contractFirstName)
	{
		this.contractFirstName = contractFirstName;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public void setCustomerNumber(int customerNumber)
	{
		this.customerNumber = customerNumber;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public void setSalesRepEmployeeNumber(int salesRepEmployeeNumber)
	{
		this.salesRepEmployeeNumber = salesRepEmployeeNumber;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
