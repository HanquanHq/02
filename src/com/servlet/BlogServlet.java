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
public class BlogServlet extends HttpServlet {
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
			UserDao userDao=new UserDao();
			
			
			userDao.setHitNum(userid);								//将博客的点击率加１
			UserSingle master=userDao.getMasterSingle(userid);		//获取博主信息
			
			if(master!=null){										//如果访问的用户存在
				
				session.setAttribute("callBlogMaster",master);
				
				
				// 获取博客的文章，每页显示5条记录
				List articlelist=new ArrayList();
				ArticleDao articleDao=new ArticleDao();
				String showPage=request.getParameter("showPage");
				articlelist=articleDao.getListArticle(userid,showPage,"goBlogIndex?master="+userid);
				request.setAttribute("articlelist",articlelist);
				request.setAttribute("createPage",articleDao.getPage());
				
				
				/* 获取显示在个人主页中侧栏位置上的点击率排行前20名的博客 */				
				List toplist=userDao.getTopList();
				request.setAttribute("toplist",toplist);		
				
				/* 获取该博客文章总数 */
				int countActicle=articleDao.getActicleCount(userid);
				session.setAttribute("countActicle",countActicle);
				
				/* 获取该博客评论总数 */
				int countRev=articleDao.getRevCount(userid);
				session.setAttribute("countRev",countRev);
				
				forward=this.getServletContext().getInitParameter("indexTemp");
			}
			else{							//如果访问的用户不存在		
				forward=this.getServletContext().getInitParameter("messagePage");
				String message="<li>对不起，访问的用户不存在！</li>";
				request.setAttribute("message",message);
			}
		}catch(Exception e){
			forward=this.getServletContext().getInitParameter("messagePage");
			System.out.println("'获取博文信息错误！");
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);		
	}
}