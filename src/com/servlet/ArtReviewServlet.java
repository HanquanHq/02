package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ArtReviewDao;
import com.dao.UserDao;
import com.toolsBean.Change;
import com.valueBean.ArtReviewSingle;
import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class ArtReviewServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		
		String action=request.getParameter("action");
		if("listShow".equals(action))
			doListShow(request,response);
		else if("review".equals(action))
			doReview(request,response);
		else if("adminList".equals(action))
			doAdminList(request,response);
		else if("delete".equals(action))
			doDelete(request,response);
		else if("getReview2".equals(action))
			try {
				getReview2(request,response);
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			doOther(request,response);
	}
	
	@SuppressWarnings("unchecked")
	protected void getReview2(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, NumberFormatException, SQLException {
		
		@SuppressWarnings("unused")
		HttpSession session=request.getSession();
		
		String reviewId = request.getParameter("reviewId");
		
		@SuppressWarnings("unused")
		String str="";
		
		ArtReviewDao artrDao=new ArtReviewDao();

		
			List<ArtReviewSingle> list=new ArrayList();
			
			list = artrDao.getReview2(Integer.parseInt(reviewId));
		
		if (list.size() == 0) { 
			// 账户或密码登录不正确
			str="0"; 
			
		} else { 
			
			StringBuffer strList=new StringBuffer();
			//返回列表数据
			for(int i=0;i<list.size();i++){
				if(i==(list.size()-1)){
					strList.append("{'id': '"+list.get(i).getId()+"', " +
							       "'blogname': '"+list.get(i).getArtRAuthor()+"', " +
							       "'reviewContent': '"+list.get(i).getArtRContent()+"', " +
								   "'reviewTime' : '"+list.get(i).getArtRTime()+"'}");	
				}else{
					try {
						strList.append("{'id': '"+list.get(i).getId()+"', " +
								       "'blogname': '"+list.get(i).getArtRAuthor()+"', " +
							           "'reviewContent': '"+list.get(i).getArtRContent()+"', " +
									   "'reviewTime':  '"+list.get(i).getArtRTime()+"'};");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			str=strList.toString();
		}
		
		response.setContentType("text/html;charset=GBK");
		response.getWriter().write(str);
		
	}
	
	protected void doListShow(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		int rootId=Change.strToInt(request.getParameter("id"));
		String showPage=request.getParameter("showPage");
		String goWhich="my/guest/artReview?action=listShow&id="+rootId;
		
		List artrlist=null;
		ArtReviewDao artrDao=new ArtReviewDao();		
		try {
			artrlist=artrDao.getMoreReviewList(rootId, showPage, goWhich);
		} catch (SQLException e) {
			System.out.println("获取文章评论列表失败！");
			e.printStackTrace();			
		}
		request.setAttribute("artrlist",artrlist);
		request.setAttribute("createPage",artrDao.getPage());
		
		String forward=getInitParameter("artrListPage");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	protected void doReview(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		String message="";
		String forward="";
		HttpSession session=request.getSession();
		
		String masterId = request.getParameter("masterId");
		String rootId = request.getParameter("rootId");
		String content = request.getParameter("artRContent");
		String type = request.getParameter("type");
		String time = Change.dateTimeChange(new Date());
		UserSingle logoner=(UserSingle)session.getAttribute("logoner");
		if(null==logoner){
			message="1";
			request.setAttribute("message",message);
			forward = "goBlogContent?id="+rootId+"&master="+masterId;
			RequestDispatcher rd=request.getRequestDispatcher(forward);
			rd.forward(request,response);
			return;
		}
		

		ArtReviewDao artReviewDao = new ArtReviewDao();
		try {
			if(type.equals("0")){
				artReviewDao.insert(new Object[] { rootId, logoner.getUserBlogName(), content, time});
			}else{
				artReviewDao.insert2(new Object[] { Integer.parseInt(type), logoner.getUserBlogName(), content, time});
			}
			
			forward = "goBlogContent?id="+rootId+"&master="+masterId;
		} catch (SQLException e) {
			forward = this.getServletContext().getInitParameter("messagePage");
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	protected void doAdminList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		int rootId=Change.strToInt(request.getParameter("id"));
		String showPage=request.getParameter("showPage");
		String goWhich="my/admin/artReview?action=adminList&id="+rootId;
		
		List artrlist=null;
		ArtReviewDao artrDao=new ArtReviewDao();		
		try {
			artrlist=artrDao.getMoreReviewList(rootId, showPage, goWhich);
		} catch (SQLException e) {
			System.out.println("后台－获取文章评论列表失败！");
			e.printStackTrace();			
		}
		request.setAttribute("artrlist",artrlist);
		request.setAttribute("createPage",artrDao.getPage());
		
		String forward=getInitParameter("adminArtrListPage");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		int rootId=Change.strToInt(request.getParameter("rootId"));
		int id=Change.strToInt(request.getParameter("id"));
		
		int i=0;
		try {
			ArtReviewDao artReviewDao=new ArtReviewDao();
			i=artReviewDao.delete(id);
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		if(i<=0){
			message="<li>删除评论失败！</li><a href='javascript:window.history.go(-1)'>【返回】</a>";
			forward=getServletContext().getInitParameter("messagePage");
		}
		else{
			message="<li>删除评成功！</li>";
			forward="/my/admin/artReview?action=adminList&id="+rootId;
		}
		
		request.setAttribute("message",message);		
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	protected void doOther(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
	}
	private String validateInsert(HttpServletRequest request) throws ServletException, IOException{
		String message="";
		String content=request.getParameter("artRContent");
		if(content==null||content.equals(""))
			message="<li>请输入 <b>评论内容！</b></li><a href='javascript:window.history.go(-1)'>【返回】</a>";
		return message; 
	}
}