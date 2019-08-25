package cn.hanquan.tools.blogCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexTools {
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
		if (shouldPurifyURL) {
			String realURL = "";
			int i1 = str.indexOf("href") > 0 ? str.indexOf("href") : 100000;
			int i2 = str.indexOf("HREF") > 0 ? str.indexOf("HREF") : 100000;

			int index = i1 <= i2 ? i1 : i2;
			if (index > 0 && index < 200) {
				realURL = str.substring(0, 200);
				realURL = doRegex(realURL, "(?<=href=\")[a-z]+://[^\\s]*(?=\")");
				System.out.println("realURL = " + realURL);
				if (realURL != null && realURL.indexOf("blog") < 0 && realURL.indexOf("jianshu") < 0
						&& realURL.indexOf("douban") < 0 && realURL.indexOf("csdn") < 0) {
					System.out.println("抛弃此链接");
					return "";
				}
			} else {
				System.out.println("找不到源链接，无法判断是否过滤");
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
	 * 找出百度搜索页中的所有快照链接
	 * 
	 * @param mess 搜索结果页的html代码
	 * @return 所有快照链接
	 * @throws IOException
	 */
	public static String getBaiduQuickURL(String mess) throws IOException {
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
}
