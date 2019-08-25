package cn.hanquan.tools.blogCheck;

import java.io.IOException;

import org.apache.log4j.Logger;

public class Checker {
	/**
	 * 根据博客内容自动搜索，计算重复率
	 * @param blog 博客内容
	 * @return 经过调整的重复率
	 * @throws IOException
	 */
	public static double calcSameRatio(String blog) throws IOException {
		Logger logger = Logger.getLogger(Checker.class);
		blog = DpTools.removeSign(blog);// 删除非字符

		String searchResult = StringTools.getSearchResult(blog);
		searchResult = DpTools.removeSign(searchResult);
		logger.debug("没有符号的allStr：" + searchResult);

		double sameRatio = DpTools.calcSameRatio(blog, searchResult);
		sameRatio = sameRatio >= 0.99 ? 0.99 : sameRatio * 0.7;// 毫无根据的手动调整
		
		System.out.println("调整的重复率：" + sameRatio);
		return sameRatio;
	}

	/**
	 * 用于测试的主函数
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		double ratio = calcSameRatio("测试一下这句话会不会查重");
		System.out.println(ratio);
	}
}
