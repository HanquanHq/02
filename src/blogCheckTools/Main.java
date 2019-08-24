package blogCheckTools;

import java.io.IOException;

import org.apache.log4j.Logger;

public class Main {
	public static double myMain(String blog) throws IOException {

		Logger logger = Logger.getLogger(Main.class);
		blog = DynamicCalc.removeSign(blog);// 删除非字符
//		String userBlogText = new String(blog.getBytes("iso-8859-1"), "utf-8");// 解决乱码
//		System.out.println("用户输入："+userBlogText);
		String allStr = StringTools.getSearchResult(blog);
		allStr = DynamicCalc.removeSign(allStr);
		allStr = allStr.replaceAll(" ", "");
		logger.debug("没有符号的allStr：" + allStr);

		double sameRatio = DynamicCalc.calcSameRatio(blog, allStr);
		if (sameRatio >= 0 && sameRatio < 1) {
			sameRatio *= 0.8;
		} else {
			sameRatio = 0.9;
		}
		System.out.println("重复率：" + sameRatio);
		return sameRatio;
	}

	public static void main(String[] args) throws IOException {
		double ratio = myMain("用户可以在form表单中输入用户名和密码\r\n" + "点击“改变用户名”、“改变密码”、“改变隐藏值”按钮后，相应input中的值被改变\r\n"
				+ "注意：此处以一个隐藏值，为了证明隐藏值有效，可以点击“提交一下”按钮，将表单数据提交到服务器中。\r\n"
				+ "从服务器中可以接收到隐藏值，说明隐藏值也有效。本来是要做一个博客查重，目标是：在前端点击“计算重复率ratio”之后，通过在JS使用Ajax将查重结果显示在页面上。\r\n"
				+ "版权声明：本文为CSDN博主「寒泉Hq」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。\r\n"
				+ "原文链接：https://blog.csdn.net/sinat_42483341/article/details/100056115");
		System.out.println(ratio);
	}
}
