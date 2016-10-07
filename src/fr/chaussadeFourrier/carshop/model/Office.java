package fr.chaussadeFourrier.carshop.model;

public class Office
{

	private String officeCode, city, phone, addressLine, state, country, postalCode;

	public Office(String officeCode, String city, String phone, String addressLine, String state, String country, String postalCode)
	{
		this.officeCode = officeCode;
		this.city = city;
		this.phone = phone;
		this.addressLine = addressLine;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
	}

	public String getAddressLine()
	{
		return this.addressLine;
	}

	public String getCity()
	{
		return this.city;
	}

	public String getCountry()
	{
		return this.country;
	}

	public String getOfficeCode()
	{
		return this.officeCode;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public String getPostalCode()
	{
		return this.postalCode;
	}

	public String getState()
	{
		return this.state;
	}

	public void setAddressLine(String addressLine)
	{
		this.addressLine = addressLine;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setOfficeCode(String officeCode)
	{
		this.officeCode = officeCode;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
