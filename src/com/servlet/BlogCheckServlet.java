package com.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.Request;

@WebServlet("/BlogCheckServlet")
public class BlogCheckServlet extends HttpServlet {

	/**
	 * 计算重复率的主函数
	 * 
	 * @param blog 博客的字符串
	 * @return 重复率
	 * @throws IOException
	 */
	public static double myMain(String blog) throws IOException {
		String utextout = new String(blog.getBytes("iso-8859-1"), "utf-8");// 解决乱码
		String allStr = getSearchResult(utextout);
		//allStr = doRegex(allStr, "[\\u4e00-\\u9fa5]");
		System.out.println(allStr);
		double sameRatio = calcSameRatio(utextout, allStr);
		if (sameRatio >= 0 && sameRatio < 1) {
			sameRatio *= 0.8;
		} else {
			sameRatio = 0.9;
		}
		System.out.println("重复率：" + sameRatio);
		return sameRatio;
	}

	/**
	 * 复写service方法
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 获取请求信息
		String blogStr = req.getParameter("mytest");
		System.out.println(blogStr);
		if (blogStr == "") {
			resp.getWriter().write("博客内容不能为空！");
			return;
		}

		double ratio = myMain(blogStr);
		resp.getWriter().write("" + ratio);
	}

	/**
	 * 找出百度搜索页中的所有快照链接
	 * 
	 * @param mess 搜索结果页的html代码
	 * @return 所有快照链接
	 * @throws IOException
	 */
	public static String craw(String mess) throws IOException {
		String smess = URLEncoder.encode(mess, "UTF-8");
		String urll = "http://www.baidu.com/s?wd=" + smess;
		URL url = new URL(urll);
		InputStream input = url.openStream();

		// InputStream转化为String
		String sb = new BufferedReader(new InputStreamReader(input)).lines()
				.collect(Collectors.joining(System.lineSeparator()));

		String pattern = "href=\"(http://cache.baiducontent.com/c\\?m=.+?)\".+$";// 正则匹配百度快照
		String h3_pattern = "(a data-click)"; // 每个链接之前都有一个a data-click标签
		Pattern pat = Pattern.compile(pattern);
		Pattern h3_pat = Pattern.compile(h3_pattern);

		String[] sbArr = sb.toString().split("\n");

		Matcher mach;
		Matcher machh3;

		StringBuilder result = new StringBuilder();

		boolean in = false;
		for (String str : sbArr) {
			machh3 = h3_pat.matcher(str); // 先看能不能匹配到h3 class标签，如果可以则进行匹配链接
			while (machh3.find()) {
				if (machh3.groupCount() > 0) {
					in = true;
				}
			}
			if (in) {
				mach = pat.matcher(str); // 匹配链接
				while (mach.find()) {
					in = false;
					System.out.println("结果" + mach.group(1));
					result.append(mach.group(1) + "\n");
				}
			}
		}
		System.out.println("----------------------search end, begin visit each---------------------------");

		return result.toString();
	}

	/**
	 * 读取链接对应的网页，返回字符串
	 * 
	 * @param url the URL you want to get
	 * 
	 * @return the HTML Script of your URL
	 * 
	 * @throws IOException
	 */
	public static String get(String url) throws IOException {
		// 解决编码问题
		InputStream is = doGet(url);
		String pageStr = inputStreamToString(is, "gb2312");// 百度快照 固定gb2312
		System.out.println("解码方式：gb2312");
		is.close();
		return pageStr;
	}

	public static InputStream doGet(String urlstr) throws IOException {
		URL url = new URL(urlstr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(30000);// 设置相应超时时间
		conn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		InputStream inputStream = conn.getInputStream();
		return inputStream;
	}

	public static String inputStreamToString(InputStream is, String charset) throws IOException {
		byte[] bytes = new byte[1024];
		int byteLength = 0;
		StringBuffer sb = new StringBuffer();
		while ((byteLength = is.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, byteLength, charset));
		}
		return sb.toString();
	}



	/**
	 * 对正则表达式的封装
	 * 
	 * @param str   要匹配的字符串
	 * @param regex 正则表达式
	 * @return 匹配结果（第一个）
	 */
	public static String doRegex(String str, String regex) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			list.add(m.group());
		}
		if (null == list || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 筛选的字符串
	 * 
	 * @param str 带有html标签冗余的字符串
	 * @return 提取出的纯净字符串
	 */
	public static String purifyStr(String str, boolean shouldPurifyURL) {
		if (str.length() == 0)
			return "";
		String htmlStr = str; // 含html标签的字符串
		
		// 获取真实地址 过滤博客
		if(shouldPurifyURL) {
			String realURL = "";
			int i1 = str.indexOf("href") > 0 ? str.indexOf("href") : 100000;
			int i2 = str.indexOf("HREF") > 0 ? str.indexOf("HREF") : 100000;

			int index = i1 <= i2 ? i1 : i2;
			if (index > 0 && index < 200) {
				realURL = str.substring(0, 200);
				realURL = doRegex(realURL, "(?<=href=\")[a-z]+://[^\\s]*(?=\")");
				System.out.println("realURL = " + realURL);
				if (realURL != null && realURL.indexOf("blog") < 0
						&& realURL.indexOf("jianshu") < 0 && realURL.indexOf("douban") < 0 && realURL.indexOf("baidu") < 0
						&& realURL.indexOf("csdn") < 0) {
					System.out.println("抛弃此链接");
					return "";
				}
			} else {
				System.out.println("找不到源链接，无法判断是否过滤" + str.substring(0, 200));
				return "";
			}
		}
		

		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		// 剔除空格行
		textStr = textStr.replaceAll("[ ]+", "");
		textStr = textStr.replaceAll("1", "").replaceAll("2", "").replaceAll("3", "").replaceAll("4", "")
				.replaceAll("5", "").replaceAll("6", "").replaceAll("7", "").replaceAll("8", "").replaceAll("9", "")
				.replaceAll("0", "");
		textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
		textStr = textStr.replaceAll("\t", "");
		textStr = textStr.replaceAll("&nbsp;", "").replace("&gt;", "").replace("&mdash;", "").replace("&lt;", "")
				.replace("&quot;", "");
		textStr = textStr.replaceAll("\\\\", "");// 正则表达式中匹配一个反斜杠要用四个反斜杠
		textStr = textStr.replaceAll("\r\n", "");
		textStr = textStr.replaceAll("\n", "");
		textStr = textStr.substring(textStr.indexOf("不代表被搜索网站的即时页面") + 14, textStr.length() - 1);
		return textStr;// 返回文本字符串
	}



	/**
	 * 根据博客内容返回百度搜索结果
	 * 
	 * @param utextout
	 * @return
	 * @throws IOException
	 */
	public static String getSearchResult(String utextout) throws IOException {

		StringBuilder allStr = new StringBuilder();
		String result = craw(utextout);// 搜索字符串
		String[] urls = result.split("\n");// 跨平台的时候注意一下\r\n
		int count = 0;
		boolean shouldPurifyURL = true;
		if (utextout.length() < 60) {// 输入过少的情况
			utextout = utextout.substring(0, utextout.length() > 20 ? 20 : utextout.length());

			for (String u : urls) {
				if (count > 3) {// 合格链接总数小于3，则不抛弃
					shouldPurifyURL = true;
				}
				String htmlStr = get(u);// 获取html代码
				String str = purifyStr(htmlStr, shouldPurifyURL);// 正则
				System.out.println(u + "\n");
				allStr.append(str);// 追加
				count++;
			}
		} else {
			// 20个字查一次 全部结果保存
			for (int i = 0; i + 51 < utextout.length(); i += 50) {
				if (count > 3) {// 合格链接总数小于3，则不抛弃
					shouldPurifyURL = true;
				}
				String split = utextout.substring(i, i + 20);
				craw(split);// 搜索字符串
				for (String u : urls) {
					String htmlStr = get(u);// 获取html代码
					String str = purifyStr(htmlStr, shouldPurifyURL);// 正则
					System.out.println(u + "\n");
					allStr.append(str + "\n");// 追加
					count++;
				}
			}
		}
		System.out.println("----------------------visit each end---------------------------");
		return allStr.toString();
	}

	/**
	 * 计算两个字符串的重复率
	 * 
	 * @param AA
	 * @param BB
	 * @return
	 */
	public static double calcSameRatio(String AA, String BB) {
		String temp_strA = AA;
		String temp_strB = BB;
		String strA, strB;
		// 如果两个textarea都不为空且都不全为符号，则进行相似度计算，否则提示用户进行输入数据或选择文件
		if (!(behind.removeSign(temp_strA).length() == 0 && behind.removeSign(temp_strB).length() == 0)) {
			if (temp_strA.length() >= temp_strB.length()) {
				strA = temp_strA;
				strB = temp_strB;
			} else {
				strA = temp_strB;
				strB = temp_strA;
			}
			double result = behind.SimilarDegree(strA, strB);
			return result;
		}
		return -1;
	}

	static class behind {
		/*
		 * 计算相似度
		 */
		public static double SimilarDegree(String strA, String strB) {
			String newStrA = removeSign(strA);
			String newStrB = removeSign(strB);
			// 用较大的字符串长度作为分母，相似子串作为分子计算出字串相似度
			int temp = newStrB.length();
			int temp2 = longestCommonSubstring(newStrA, newStrB).length();
			return temp2 * 1.0 / temp;
		}

		/*
		 * 将字符串的所有数据依次写成一行
		 */
		public static String removeSign(String str) {
			StringBuffer sb = new StringBuffer();
			// 遍历字符串str,如果是汉字数字或字母，则追加到ab上面
			for (char item : str.toCharArray())
				if (charReg(item)) {
					sb.append(item);
				}
			return sb.toString();
		}

		/*
		 * 判断字符是否为汉字，数字和字母， 因为对符号进行相似度比较没有实际意义，故符号不加入考虑范围。
		 */
		public static boolean charReg(char charValue) {
			return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z')
					|| (charValue >= 'A' && charValue <= 'Z') || (charValue >= '0' && charValue <= '9');
		}

		/**
		 * 动态规划求公共子串
		 * 
		 * @param strA
		 * @param strB
		 * @return
		 */
		public static String longestCommonSubstring(String strA, String strB) {
			char[] chars_strA = strA.toCharArray();
			char[] chars_strB = strB.toCharArray();
			int m = chars_strA.length;
			int n = chars_strB.length;

			int[][] matrix = new int[m + 1][n + 1];
			for (int i = 1; i <= m; i++) {
				for (int j = 1; j <= n; j++) {
					if (chars_strA[i - 1] == chars_strB[j - 1])
						matrix[i][j] = matrix[i - 1][j - 1] + 1;
					else
						matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
				}
			}

			char[] result = new char[matrix[m][n]];
			int currentIndex = result.length - 1;
			while (matrix[m][n] != 0) {
				if (matrix[n] == matrix[n - 1])
					n--;
				else if (matrix[m][n] == matrix[m - 1][n])
					m--;
				else {
					result[currentIndex] = chars_strA[m - 1];
					currentIndex--;
					n--;
					m--;
				}
			}
			return new String(result);
		}

		/**
		 * 结果转换成百分比
		 * 
		 * @param resule
		 * @return
		 */
		public static String similarityResult(double resule) {
			return NumberFormat.getPercentInstance(new Locale("en ", "US ")).format(resule);
		}
	}
}
