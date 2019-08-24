package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.FriendDao;
import com.dao.UserDao;
import com.toolsBean.Change;
import com.valueBean.UserSingle;

@SuppressWarnings("serial")
public class FriendServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if ("listShow".equals(action))
			try {
				doListShow(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if ("adminList".equals(action))
			doAdminList(request, response);
		else if ("adminSingle".equals(action))
			doAdminSingle(request, response);
		else if ("insert".equals(action))
			doInsert(request, response);
		else if ("delete".equals(action))
			doDelete(request, response);
		else
			doOther(request, response);
	}
	
	
	@SuppressWarnings("unchecked")
	protected void doListShow(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, SQLException {
		HttpSession session=request.getSession();
		
		UserDao userDao=new UserDao();
		int masterId=Change.strToInt(request.getParameter("master"));
		UserSingle master=userDao.getMasterSingle(masterId);
		session.setAttribute("callBlogMaster",master);
		
		UserSingle logoner=(UserSingle)session.getAttribute("logoner");
		String forward="";
		
		if(null!=logoner){
			if(master.getId()==logoner.getId()){
				request.setAttribute("isSelf","1");
			}else{
				request.setAttribute("isSelf","0");
			}
		}
		
		int userid=master.getId();
		List friendList=null;
		FriendDao friendDao=new FriendDao();		
		try {
			friendList=friendDao.getListFriend(userid);
		} catch (SQLException e) {
			System.out.println("获取好友列表失败！");
			e.printStackTrace();			
		}
		request.setAttribute("friendList",friendList);
		
		/* 获取显示在个人主页中侧栏位置上的点击率排行前20名的博客 */				
		List toplist=userDao.getTopList();
		session.setAttribute("toplist",toplist);	
		
		forward=getInitParameter("listShowPage");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	@SuppressWarnings("unchecked")
	protected void doAdminList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setAttribute("mainPage",getInitParameter("adminListPage"));
		HttpSession session=request.getSession();
		UserSingle logoner=(UserSingle)session.getAttribute("logoner");

		int whoid=logoner.getId();
		List friendList=null;
		FriendDao friendDao=new FriendDao();		
		try {
			friendList=friendDao.getListFriend(whoid);
		} catch (SQLException e) {
			System.out.println("后台－获取好友列表失败！");
			e.printStackTrace();			
		}
		request.setAttribute("friendList",friendList);

		String forward=getServletContext().getInitParameter("adminTemp");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	protected void doAdminSingle(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		int id=Change.strToInt(request.getParameter("id"));
		UserSingle single=null;
		try {
			FriendDao friendDao=new FriendDao();		
			single=friendDao.getFriendInfo(id);
		} catch (SQLException e) {
			System.out.println("后台－获取好友详细信息失败！");
			e.printStackTrace();			
		}
		request.setAttribute("friendSingle",single);

		String forward=getInitParameter("adminSinglePage");
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	protected void doInsert(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session=request.getSession();
		String message="";
		
		UserSingle logoner=(UserSingle)session.getAttribute("logoner");
		
		if(null==logoner){
			// 提示先登录
			message="0";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(message);
			return;
		}
		
		int whoid=logoner.getId();									//获取当前登录用户ID
		int userid=Change.strToInt(request.getParameter("master"));     //获取被访问的博客的博主ID 						

		Object[] params={whoid,userid};
		
		if(whoid==userid){											
			//首先判断是否进行了将自己添加为好友的操作
			// 提示先登录
			message="2";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(message);
			return;
		}
		else{
			try {
				FriendDao friendDao=new FriendDao();
				boolean have=friendDao.getSingleFriend(params);		//判断要添加的好友是否已经存在好友列表中
				if(!have){
					//如果不存在
					friendDao.insert(params);								//向数据表中添加好友信息
					message="1";		
				}
				else{
					//好友已经存在
					message="3";
				}														
						
			} catch (SQLException e) {
				//添加失败
				message="4";	
			}			
		}		
		request.setAttribute("message",message);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(message);
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		HttpSession session=request.getSession();
		int whoid=((UserSingle)session.getAttribute("logoner")).getId();
		int userid=Change.strToInt(request.getParameter("id"));
		Object[] params=new Object[]{whoid,userid};
		
		int i=0;
		try {
			FriendDao friendDao=new FriendDao();
			i=friendDao.delete(params);
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		if(i<=0){
			message="<li>删除好友失败！</li><a href='javascript:window.history.go(-1)'>【返回】</a>";
			forward=getServletContext().getInitParameter("messagePage");
		}
		else{
			message="<li>删除好友成功！</li>";
			forward=getInitParameter("listShowPage");
		}
		
		try {
			doListShow(request, response);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	protected void doOther(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
	}
}
