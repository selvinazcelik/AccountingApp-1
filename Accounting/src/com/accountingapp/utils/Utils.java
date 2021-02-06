package com.accountingapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.accountingapp.model.RequestModel;
import com.accountingapp.model.User;
import com.accountingapp.service.AccountingService;
import com.google.gson.Gson;

public class Utils {
	public static RequestModel getRequestModel(HttpServletRequest request) {
		String reqBody = "";
		try {
			request.setCharacterEncoding("UTF-8");
			reqBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		RequestModel requestModel = gson.fromJson(reqBody, RequestModel.class);
		return requestModel;
	}
	
	public static void sessionControl(HttpServletRequest request) {
		if (request.getCookies() != null) {
			Cookie[] ck = request.getCookies();
			for (int i = 0; i < ck.length; i++) {
				Cookie c = ck[i];
				if(c.getName().equals("userId")) {
					AccountingService as = new AccountingService();
					String userid = c.getValue();
					User user = as.getUserWithId(Integer.valueOf(userid));
					request.getSession().setAttribute("currentUser", user);
				}
			}
		}
	}
	
	public static String convertTimeStamptoString(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(new Date(timestamp));
		return dateStr;
	}
}
