package fr.chaussadeFourrier.carshop.model;

import java.sql.Date;

public class Payment
{

	private double amount;
	private String checkNumber;
	private int customerNumber;
	private Date paymentDate;

	public Payment(int customerNumber, String checkNumber, Date paymentDate, double amount)
	{
		this.customerNumber = customerNumber;
		this.checkNumber = checkNumber;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}
	
	public Payment(){
	}

	public double getAmount()
	{
		return this.amount;
	}

	public String getCheckNumber()
	{
		return this.checkNumber;
	}

	public int getCustomerNumber()
	{
		return this.customerNumber;
	}

	public Date getPaymentDate()
	{
		return this.paymentDate;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public void setCheckNumber(String checkNumber)
	{
		this.checkNumber = checkNumber;
	}

	public void setCustomerNumber(int customerNumber)
	{
		this.customerNumber = customerNumber;
	}

	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

}
