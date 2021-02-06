package com.accountingapp;

import java.sql.Connection;
import java.util.Date;

import com.accountingapp.utils.Utils;

public class Test {

	public static void main(String[] args) {
		Connection conn = Global.getConnection();
		System.out.println(conn);
	}

}
