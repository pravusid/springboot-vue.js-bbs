package com.talsist.util;

import com.talsist.domain.User;
import com.talsist.exception.NotLoggedInException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtils {

    private static boolean isLogin(HttpSession session) {
        Object user = session.getAttribute("user");
        return user != null;
    }

    public static void loginCheck(HttpSession session) throws NotLoggedInException {
        if (!HttpUtils.isLogin(session)) {
            throw new NotLoggedInException();
        }
    }

    public static User getSessionUser(HttpSession session) throws NotLoggedInException {
        if (isLogin(session)) {
            return (User) session.getAttribute("user");
        }
        throw new NotLoggedInException();
    }

    public static String getQueryString(HttpServletRequest request) {
        return (request.getQueryString() != null) ? "?" + request.getQueryString() : "";
    }

    public static String getPreviousPage(HttpServletRequest request) {
        return request.getRequestURI() + getQueryString(request);
    }

    public static String redirctToLoginPage(HttpServletRequest request) {
        request.getSession().setAttribute("prevPage", getPreviousPage(request));
        return "redirect:/login";
    }

}
