package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.WordDao;
import com.toolsBean.Change;
import com.valueBean.UserSingle;

public class WordServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		request.setCharacterEncoding("GBK");
		
		String action=request.getParameter("action");
		if("linkTo".equals(action))
			doShowPage(request,response);
		else if("listShow".equals(action))
			doListShow(request,response);
		else if("review".equals(action))
			doReview(request,response);
		else if("delete".equals(action))
			doDelete(request,response);
		else
			doOther(request,response);
	}
	protected void doShowPage(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("leaveWordPage"));
		
		HttpSession session=request.getSession();
		UserSingle callMaster=(UserSingle)session.getAttribute("callBlogMaster");
		int userid=callMaster.getId();
		
		List wordList=null;
		WordDao wordDao=new WordDao();		
		try {
			wordList=wordDao.getNewWord(userid);
		} catch (SQLException e) {
			System.out.println("获取最新留言失败！");
			e.printStackTrace();			
		}
		request.setAttribute("wordList",wordList);
		
		String forward=this.getServletContext().getInitParameter("indexTemp");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	@SuppressWarnings("unchecked")
	protected void doListShow(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {		HttpSession session=request.getSession();
		UserSingle callMaster=(UserSingle)session.getAttribute("callBlogMaster");

		int userid=callMaster.getId();
		String showPage=request.getParameter("showPage");
		String goWhich="word?action=listShow";
		
		List wordList=null;
		WordDao wordDao=new WordDao();		
		try {
			wordList=wordDao.getMoreWord(userid,showPage, goWhich);
		} catch (SQLException e) {
			System.out.println("获取留言列表失败！");
			e.printStackTrace();			
		}
		request.setAttribute("wordList",wordList);
		request.setAttribute("createPage",wordDao.getPage());
		
		String forward=getInitParameter("listShowPage");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	
	protected void doReview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserSingle callMaster = (UserSingle) session.getAttribute("callBlogMaster");
		int userid=0;
		if(null==callMaster){
			String forward=this.getServletContext().getInitParameter("sessionMiss");
			RequestDispatcher rd=request.getRequestDispatcher(forward);
			rd.forward(request,response);
		}else{
			userid=callMaster.getId();
		}

		String content = request.getParameter("wordContent");
		String time = Change.dateTimeChange(new Date());
		String author = "";

		author = ((UserSingle) session.getAttribute("logoner")).getUserName();

		try {
			WordDao wordDao = new WordDao();
			wordDao.insert(new Object[] { userid, content, author, time });
			// message="<li>留言成功！</li>";
			// forward="/my/guest/word?action=linkTo";
			doListShow(request, response);
			return;
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}	
	protected void doModify(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	
	}
	protected void doOther(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
	}
	private String validateInsert(HttpServletRequest request) throws ServletException, IOException{
		String message="";
		String content=request.getParameter("wordContent");
		if(content==null||content.equals(""))
			message="<li>请输入 <b>留言内容！</b></li><a href='javascript:window.history.go(-1)'>【返回】</a>";
		return message; 
	}
}