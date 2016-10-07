package fr.chaussadeFourrier.carshop.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public final class Database
{

	/** The Connection to the Database. */
	private static Connection connection;
	/** URL to the Database. */
	private static final String DB_URL = "jdbc:mysql://localhost/schemadevobjet";
	/** Driver for the connection. */
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	/** User name and password to access the database. */
	private static final String USER = "root", PASSWORD = "";

	/** Terminates the connection to the database and exits the program. */
	public static void closeConnection()
	{
		if (connection != null) try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		System.out.println("Connection closed. Goodbye !");
		System.exit(0);
	}

	/** Initializes the connection to the database. */
	private static void createConnection()
	{
		try
		{
			Class.forName(DRIVER);
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		System.out.println("Connected !");
	}

	/** Creates a Statement for the Database.
	 * 
	 * @param connection - The connection to the Database.
	 * @return The new Statement. */
	public static Statement createStatement(Connection connection)
	{
		try
		{
			return connection.createStatement();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/** Executes the content of an SQL file. Values returned by SELECT queries are not returned.
	 * 
	 * @param connection - The connection to the Database.
	 * @param filepath - The path to the file containing SQL queries. */
	public static void executeSQLFile(Connection connection, String filepath)
	{
		Statement statement = createStatement(connection);
		if (statement == null) return;

		String[] statements = readStatementsFile(filepath);
		for (String s : statements)
		{
			System.out.println("Executing: " + s);
			try
			{
				statement.executeUpdate(s);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/** @return The connection to the current Database. */
	public static Connection getConnection()
	{
		if (connection == null) createConnection();
		return connection;
	}

	/** @param filepath - The path to the file containing SQL queries.
	 * @return A list of all SQL queries in the file. */
	public static String[] readStatementsFile(String filepath)
	{
		File file = new File(filepath);
		if (!file.exists())
		{
			System.err.println("Could not open file: " + filepath);
			return null;
		}

		ArrayList<String> statements = new ArrayList<String>();

		FileReader reader = null;
		try
		{
			reader = new FileReader(file);
			Scanner scanner = new Scanner(reader);

			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				if (!line.equals("")) statements.add(line);
			}
			scanner.close();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (reader != null) reader.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return statements.toArray(new String[statements.size()]);
	}
}
