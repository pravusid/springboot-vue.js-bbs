package com.talsist.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.talsist.domain.User;

public class HttpSessionUtils {

	public static boolean isLogin(HttpSession session) {
		Object user = session.getAttribute("user");
		return user != null;
	}

	public static User getSessionUser(HttpSession session) {
		if (isLogin(session)) {
			return (User) session.getAttribute("user");
		}
		return null;
	}

	public static String redirctToLoginPage(HttpServletRequest request, HttpSession session) {
		String query = (request.getQueryString() != null)? "?" + request.getQueryString(): "";
		session.setAttribute("prevPage", request.getRequestURI() + query);
		return "redirect:/login";
	}

}
