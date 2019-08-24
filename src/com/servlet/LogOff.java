package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class LogOff extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		HttpSession session=request.getSession();
	
		session.setAttribute("logoner",null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("0");
		
	}
}
