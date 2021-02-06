package com.accountingapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class Global {
	public static String DBUSERNAME = "admin";
	public static String DBUSERPASS = "root";
	public static String DBURL = "jdbc:mysql://localhost/accountingapp";
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(Global.DBURL,Global.DBUSERNAME,Global.DBUSERPASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
