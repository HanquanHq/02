package com.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.toolsBean.Change;
import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class MyLogon extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		HttpSession session=request.getSession();
		
		UserSingle logoner=(UserSingle)session.getAttribute("logoner");	
		
											
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("userName");
		String pswd = request.getParameter("userPswd");
		
		UserDao userDao = new UserDao();

		try {
			
			
			logoner = userDao.getLogoner(new Object[] { name, pswd });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (logoner == null) { 
			// 账户或密码登录不正确
			message="{'state':'session miss','result':'1'}"; 
			
			
		} else { 
			
			// 登录成功
			session.setAttribute("logoner", logoner);
			
			int masterId=Change.strToInt(request.getParameter("master"));
			if(masterId==(-1)){
				masterId=0;
			}
			
			String userFlag="0";
			if(masterId==logoner.getId()){
				userFlag="1";
			}
			
			message="{'state': '成功', 'result': '0'," +
					"'userId':'"+logoner.getId()+"'," +
					"'userBlogName':'"+logoner.getUserBlogName()+"'," +
					"'userFlag':'"+userFlag+"'," +
					"'userHitNum':'"+logoner.getUserHitNum()+"' }"; 
		}
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(message);
			
	}
	
}
