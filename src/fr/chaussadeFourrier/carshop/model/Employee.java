package fr.chaussadeFourrier.carshop.model;

public class Employee
{

	private String email;
	private int employeeNumber;
	private String firstName;
	private String jobTitle;
	private String lastName;
	private String officeCode;
	private int reportsTo;

	public Employee(int employeeNumber, String lastName, String firstName, String email, String officeCode, int reportsTo, String jobTitle)
	{
		this.employeeNumber = employeeNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.officeCode = officeCode;
		this.reportsTo = reportsTo;
		this.jobTitle = jobTitle;
	}
	
	public Employee(){
	}

	public String getEmail()
	{
		return this.email;
	}

	public int getEmployeeNumber()
	{
		return this.employeeNumber;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public String getJobTitle()
	{
		return this.jobTitle;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public String getOfficeCode()
	{
		return this.officeCode;
	}

	public int getReportsTo()
	{
		return this.reportsTo;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setEmployeeNumber(int employeeNumber)
	{
		this.employeeNumber = employeeNumber;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setOfficeCode(String officeCode)
	{
		this.officeCode = officeCode;
	}

	public void setReportsTo(int reportsTo)
	{
		this.reportsTo = reportsTo;
	}

}
