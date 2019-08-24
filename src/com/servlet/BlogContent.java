package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ArtReviewDao;
import com.dao.ArticleDao;
import com.dao.UserDao;
import com.toolsBean.Change;
import com.valueBean.ArticleSingle;
import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class BlogContent extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("singleShowPage"));
		try {
			
			int userid=Change.strToInt(request.getParameter("master"));
			UserDao userDao =new UserDao();
			UserSingle master=userDao.getMasterSingle(userid);		//获取博主信息
			HttpSession session=request.getSession();
			session.setAttribute("callBlogMaster",master);
			
			ArticleDao articleDao=new ArticleDao();
			//文章id
			int id=Change.strToInt(request.getParameter("id"));

			articleDao.setArtCount(id);									// 将文章的访问次数加１
			ArticleSingle single=articleDao.getSingleArticle(id);		// 获取文章的详细内容			
			
			
			/* 获取该博客文章总数 */
			int countActicle=articleDao.getActicleCount(userid);
			session.setAttribute("countActicle",countActicle);
			
			/* 获取该博客评论总数 */
			int countRev=articleDao.getRevCount(userid);
			session.setAttribute("countRev",countRev);
			
			
			/* 获取该文章所有的评论 */
			ArtReviewDao artrDao=new ArtReviewDao();
			List artrlist=artrDao.getNewReviewList(id);
			
			/* 保存要阅读的文章、文章的评论及对评论进行分页的类 */
			request.setAttribute("articlesingle",single);
			
			request.setAttribute("artrCount",artrlist.size());
			request.setAttribute("artrlist",artrlist);
			
		} catch (Exception e) {
			System.out.println("获取文章详细内容失败！");
			e.printStackTrace();
		}
		
		String forward=getInitParameter("singleShowPage");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
}