package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.toolsBean.Change;
import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class BlogUserInfo extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("defaultPage"));
		
		//String forward="";
		String forward=getInitParameter("defaultPage");
		try{
			UserDao userDao=new UserDao();
			int userid=Change.strToInt(request.getParameter("master"));
			
			userDao.setHitNum(userid);								//将博客的点击率加１
			UserSingle master=userDao.getMasterSingle(userid);		//获取博主信息
			
			if(master!=null){										//如果访问的用户存在
				HttpSession session=request.getSession();
				session.setAttribute("callBlogMaster",master);
				

				/* 获取显示在个人主页中侧栏位置上的点击率排行前20名的博客 */				
				List toplist=userDao.getTopList();
				session.setAttribute("toplist",toplist);				
				
				//forward=this.getServletContext().getInitParameter("BlogUser");
			}
			else{							//如果访问的用户不存在		
				forward=this.getServletContext().getInitParameter("messagePage");
				String message="<li>对不起，访问的用户不存在！</li>";
				request.setAttribute("message",message);
			}
		}catch(Exception e){
			forward=this.getServletContext().getInitParameter("messagePage");
			System.out.println("'获取信息错误！");
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
}