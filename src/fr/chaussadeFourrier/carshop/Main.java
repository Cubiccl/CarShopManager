package fr.chaussadeFourrier.carshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.chaussadeFourrier.carshop.controller.Database;
import fr.chaussadeFourrier.carshop.view.Window;

public class Main
{

	/** Creates the database.
	 * 
	 * @param connection - The connection to the database. */
	@SuppressWarnings("unused")
	private static void createDatabase(Connection connection)
	{
		Statement statement = Database.createStatement(connection);
		if (statement == null) return;

		System.out.println("Creating database...");
		try
		{
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS schemadevobjet");
			System.out.println("Database 'schemadevojet' created !");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/** Creates the database tables.
	 * 
	 * @param connection - The connection to the database. */
	@SuppressWarnings("unused")
	private static void createTables(Connection connection)
	{
		System.out.println("Creating Tables...");
		Database.executeSQLFile(connection, "res/createTables.sql");
	}

	/** Inserts the data into the database.
	 * 
	 * @param connection - The connection to the database. */
	@SuppressWarnings("unused")
	private static void insertData(Connection connection)
	{
		System.out.println("Creating Data...");
		Database.executeSQLFile(connection, "res/SampleData2016.sql");
	}

	public static void main(String[] args)
	{
		Window window = new Window();
		window.setVisible(true);

		// Connection connection = Database.getConnection(); if (connection == null) Database.closeConnection(); TODO: uncomment this when done testing.
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
