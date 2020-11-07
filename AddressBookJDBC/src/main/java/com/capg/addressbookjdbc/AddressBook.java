package com.capg.addressbookjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddressBook {
	public static final String URL = "jdbc:mysql://localhost:3306/address_book";
	public static final String USER = "root";
	public static final String PASSWORD = "";
	private static Connection con = null;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
