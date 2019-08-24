package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ArticleDao;
import com.dao.UserDao;
import com.toolsBean.Change;
import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class IndexServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("defaultPage"));
		String forward="";
		
		HttpSession session=request.getSession();
		int userid=Change.strToInt(request.getParameter("master"));
		UserSingle logoner=(UserSingle)session.getAttribute("logoner");	
		if(null!=logoner){
			session.setAttribute("logoner",logoner);
			if(userid==logoner.getId()){
				request.setAttribute("isSelf","1");
			}else{
				request.setAttribute("isSelf","0");
			}
		}
				
		try{

			// 获取最新的文章前10条
			List newArticlelist = new ArrayList();
			ArticleDao articleDao = new ArticleDao();
			newArticlelist = articleDao.getNewArticle();
			request.setAttribute("newArticlelist", newArticlelist);
			
			// 获取点击排行的文章前10条
			List mostArticlelist = new ArrayList();
			mostArticlelist = articleDao.getMostArticle();
			request.setAttribute("mostArticlelist", mostArticlelist);


			/* 获取右侧栏位置上的点击率排行前10名的博客 */
			UserDao userDao = new UserDao();
			List toplist = userDao.getTopList();
			session.setAttribute("toplist", toplist);

			
			forward = getInitParameter("index");
			
		}catch(Exception e){
			forward=this.getServletContext().getInitParameter("messagePage");
			System.out.println("'获取首页信息错误！");
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}