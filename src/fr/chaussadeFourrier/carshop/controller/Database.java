package fr.chaussadeFourrier.carshop.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public final class Database
{

	/** The Connection to the Database. */
	private static Connection connection;
	/** URL to the Database. */
	public static final String DB_URL = "jdbc:mysql://localhost", DB_NAME = "schemadevobjet";
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
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			createDatabase(connection);

			connection = DriverManager.getConnection(DB_URL + "/" + DB_NAME, USER, PASSWORD);
			// If we just created the tables, we also insert the data.
			if (createTables(connection)) insertData(connection);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		if (connection != null) System.out.println("Connected !");
		else System.out.println("Connection failed.");
	}

	/** Creates the database.
	 * 
	 * @param connection - The connection to the database. */
	private static void createDatabase(Connection connection)
	{
		Statement statement = Database.createStatement(connection);
		if (statement == null) return;
		try
		{
			ResultSet rs = connection.getMetaData().getCatalogs();
			boolean databasteExists = false;
			while (rs.next())
				if (rs.getString(1).equals(Database.DB_NAME)) databasteExists = true;

			if (databasteExists) return;

		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		System.out.println("Database doesn't exist! Creating it...");
		try
		{
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS schemadevobjet");
			System.out.println("Database 'schemadevojet' created !");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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

	/** Creates the database tables.
	 * 
	 * @param connection - The connection to the database.
	 * @return true if tables were created here, false if they already existed. */
	private static boolean createTables(Connection connection)
	{
		try
		{
			ResultSet rs = connection.getMetaData().getTables(null, null, "%", null);
			if (rs.next()) return false;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		System.out.println("Creating Tables...");
		executeSQLFile(connection, "res/createTables.sql");
		return true;
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

	/** Inserts the data into the database.
	 * 
	 * @param connection - The connection to the database. */
	private static void insertData(Connection connection)
	{
		System.out.println("Creating Data...");
		executeSQLFile(connection, "res/SampleData2016.sql");
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
