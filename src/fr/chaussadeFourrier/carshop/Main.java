package fr.chaussadeFourrier.carshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import fr.chaussadeFourrier.carshop.controller.Database;
import fr.chaussadeFourrier.carshop.view.Window;

public class Main
{
	private static Window window;

	public static Window getWindow()
	{
		return window;
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				window = new Window();
				window.setVisible(true);
			}
		});

		Connection connection = Database.getConnection();
		if (connection == null) Database.closeConnection();
	}

	/** Requests and displays the Customers from a country that ordered in a year.
	 * 
	 * @param connection - The connection to the database.
	 * @param country - A country
	 * @param year - A year */
	@SuppressWarnings("unused")
	private static void requestCustomers(Connection connection, String country, String year)
	{
		System.out.println("Customers from " + country + " who ordered in the year " + year + " :");

		try
		{
			PreparedStatement statement = connection
					.prepareStatement("SELECT customerName FROM customers, orders WHERE customers.customerNumber = orders.customerNumber AND country = ? AND orderDate LIKE ?");
			statement.setString(1, "'" + country + "'");
			statement.setString(2, "'" + year + "%'");

			ResultSet result = statement.executeQuery();
			if (result.first()) do
				System.out.println(result.getString(0));
			while (result.next());
			else System.out.println("None.");

		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

}
