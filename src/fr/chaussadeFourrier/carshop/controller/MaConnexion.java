package fr.chaussadeFourrier.carshop.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

		private static String driver = "com.mysql.jdbc.Driver";
		private static String connection = "jdbc:mysql://localhost:3306/maDB";
		private static String user = "root";
		private static String pwd = "root";
		
		private static Connection connect = null;
		
		private static MaConnexion mc = new MaConnexion();
		
		private MaConnexion(){
			try{
				connect = DriverManager.getConnection(connection, user, pwd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public static Connection getInstance(){
			return mc.connect;
		}
}
