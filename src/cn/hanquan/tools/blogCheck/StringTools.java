package cn.hanquan.tools.blogCheck;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StringTools {
	/**
	 * 根据博客内容返回百度搜索结果
	 * 
	 * @param userBlogText 用户输入的博客内容
	 * @return
	 * @throws IOException
	 */
	public static String getSearchResult(String userBlogText) throws IOException {
		StringBuilder allStr = new StringBuilder();// 过滤后的搜索结果（最终）
		String baiduQuick = RegexTools.getBaiduQuickURL(userBlogText);// 所有快照链接
		String[] baiduUrls = baiduQuick.split("\n");
		int baiduUrlCount = 0;
		boolean shouldPurifyURL = false;

		if (userBlogText.length() < 60) {// 少于60字
			userBlogText = userBlogText.substring(0, userBlogText.length() > 20 ? 20 : userBlogText.length());
			for (String baiduUrl : baiduUrls) {
				System.out.println("baiduUrlCount = " + baiduUrlCount);
				if (baiduUrlCount > 1) {// 合格链接总数小于3，不抛弃
					shouldPurifyURL = true;
				}
				String htmlCode = getHtmlCode(baiduUrl);// 获取html代码
				String purifiedStr = RegexTools.purifyStr(htmlCode, shouldPurifyURL);// 正则
				System.out.println(baiduUrl + "\n");
				purifiedStr = DpTools.removeSign(purifiedStr);// 删除非字符
				allStr.append(purifiedStr);// 删除空格并追加
				baiduUrlCount++;
				if(allStr.length()>=2000) {//避免计算时间过长
					return allStr.toString();
				}
			}
		} else {
			for (int i = 0; i + 51 < userBlogText.length(); i += 50) {
				System.out.println("count=" + baiduUrlCount);
				if (baiduUrlCount > 3) {// 合格链接总数小于3，不抛弃
					shouldPurifyURL = true;
				}
				String partialBlog = userBlogText.substring(i, i + 20);
				String purifiedStr = getSearchResult(partialBlog);// 递归调用自己
				allStr.append(purifiedStr);
			}
		}
		System.out.println("----------------------已经获取所有搜索结果，并过滤得到纯净字符串---------------------------");
		return allStr.toString();
	}

	/**
	 * 读取链接对应的网页，返回字符串（处理编码问题）
	 * 
	 * @param url 你要访问的链接
	 * 
	 * @return 你所访问链接的HTML代码
	 * 
	 * @throws IOException
	 */
	public static String getHtmlCode(String url) throws IOException {
		InputStream is = doGet(url);
		String pageStr = inputStreamToString(is, "gb2312");// 百度快照 固定gb2312
		System.out.println("解码方式：gb2312");
		is.close();
		return pageStr;
	}

	/**
	 * 根据url返回html源码（不处理编码问题）
	 * 
	 * @param urlstr 你要访问的链接
	 * @return
	 * @throws IOException
	 */
	public static InputStream doGet(String urlstr) throws IOException {
		URL url = new URL(urlstr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(30000);// 设置相应超时时间
		conn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		InputStream inputStream = conn.getInputStream();
		return inputStream;
	}

	/**
	 * 将inputStream转换为String
	 * 
	 * @param is
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream is, String charset) throws IOException {
		byte[] bytes = new byte[1024];
		int byteLength = 0;
		StringBuffer sb = new StringBuffer();
		while ((byteLength = is.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, byteLength, charset));
		}
		return sb.toString();
	}

}
