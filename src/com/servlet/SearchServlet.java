package com.servlet;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toolsBean.DB;
import com.valueBean.ArticleTitle;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String searchby = req.getParameter("searchby");
		System.out.println("searchby="+searchby);
		if (searchby.equals("1"))
			searchByTitle(req, resp);
		else if (searchby.equals("2"))
			searchByUname(req, resp);
		else
			searchByHash(req, resp);

	}

	public void searchByTitle(HttpServletRequest req, HttpServletResponse resp) {
		String str = req.getParameter("str");
		System.out.println("需要搜索的字符:"+str);
		String sql = "select tb_article.id,art_whoId,art_title,user_name,art_pubtime from tb_article, tb_user where tb_article.art_whoId=tb_user.id and art_title='" + str + "'";
	
		try {
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 String connectDB = "jdbc:sqlserver://localhost:1433;DatabaseName=db_mediaBlog";
		        String user = "sa";
		        // 这里只要注意用户名密码不要写错即可
		        String password = "123456";
		        Connection con = DriverManager.getConnection(connectDB, user,
		                password);
		        // 连接数据库对象
		        System.out.println("连接数据库成功");
		        Statement stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);

			String title = "";
			String author = "";
			String pubtime = "";
			String url;
			int id=0,whoId=0;
			List<ArticleTitle> list = new ArrayList<>();
			if (rs != null) {
				while (rs.next()) {
					System.out.println("找到一篇");
					id = rs.getInt(1);
					whoId = rs.getInt(2);
					title = rs.getString(3);
					author = rs.getString(4);
					pubtime = rs.getString(5);
					url="/02/goBlogContent?id="+id+"&master="+whoId;
					list.add(new ArticleTitle(title,author,pubtime,url));
				}
		        stmt.close(); // 关闭命令对象连接
		        con.close();// 关闭数据库连接
				System.out.println("搜索的内容：" + str);
				System.out.println("搜索的结果：title=" + title + " author=" + author + " pubtime=" + pubtime);
				if (list != null) {
					req.setAttribute("ArticleTitle", list);
					req.getRequestDispatcher("/allUserInfo.jsp").forward(req, resp);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchByUname(HttpServletRequest req, HttpServletResponse resp) {
		String str = req.getParameter("str");
		String sql = "select id from tb_user where user_name = '" + str + "'";

		DB mydb = new DB();
		mydb.doPstm(sql, null);
		ResultSet rs = null;
		try {
			rs = mydb.getRs();
			int uid = 0;
			if (rs != null) {
				if (rs.next()) {
					uid = rs.getInt(1);
				}
				rs.close();
				mydb.closed();
				System.out.println("搜索的内容：" + str);
				System.out.println("搜索的结果：" + uid);
				resp.sendRedirect("/02/goBlogIndex?master=" + uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchByHash(HttpServletRequest req, HttpServletResponse resp) {
		String str = req.getParameter("str");
		String sql = "select id,art_whoId from tb_article where art_hash = '" + str + "'";
		DB mydb = new DB();
		mydb.doPstm(sql, null);
		ResultSet rs = null;
		try {
			rs = mydb.getRs();
			int id = 0, hash = 0;
			if (rs != null) {
				if (rs.next()) {
					id = rs.getInt(1);
					hash = rs.getInt(2);
				}

				System.out.println("搜索的内容：" + str);
				System.out.println("搜索的结果：" + "id=" + id + "hash=" + hash);
				rs.close();
				mydb.closed();

				resp.sendRedirect("/02/goBlogContent?id=" + id + "&master=" + hash);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
