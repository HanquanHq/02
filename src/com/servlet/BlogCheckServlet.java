package com.servlet;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.hanquan.tools.blogCheck.Checker;

@WebServlet("/BlogCheckServlet")
public class BlogCheckServlet extends HttpServlet {
	Logger logger = Logger.getLogger(BlogCheckServlet.class);

	/**
	 * 复写service方法
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 设置请求响应编码格式
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		// 获取请求信息
		String blogStr = req.getParameter("mytest");

		// 处理请求信息
		System.out.println(blogStr);
		if (blogStr == "") {
			resp.getWriter().write("博客内容不能为空！");
			return;
		}

		// 格式化字符串
		double ratio = Checker.calcSameRatio(blogStr);
		DecimalFormat df = new DecimalFormat("#0.00");
		String ratioStr = df.format(ratio * 100)+" %";

		// 发送响应
		resp.getWriter().write(ratioStr);
	}
}