package blogCheckTools;

import java.text.NumberFormat;
import java.util.Locale;

public class DynamicCalc {
	/**
	 * 计算两个字符串的重复率
	 * 
	 * @param strA
	 * @param strB
	 * @return
	 */
	public static double calcSameRatio(String AA, String BB) {
		String temp_strA = AA;
		String temp_strB = BB;
		String strA, strB;
		// 如果两个textarea都不为空且都不全为符号，则进行相似度计算，否则提示用户进行输入数据或选择文件
		if (!(removeSign(temp_strA).length() == 0 && removeSign(temp_strB).length() == 0)) {
			if (temp_strA.length() >= temp_strB.length()) {
				strA = temp_strA;
				strB = temp_strB;
			} else {
				strA = temp_strB;
				strB = temp_strA;
			}
			double result = SimilarDegree(strA, strB);
			System.out.println("计算结果：" + result);
			return result;
		}
		return -1;
	}

	/**
	 * 计算相似度
	 * 
	 * @param strA 短字符串
	 * @param strB 长字符串
	 * @return
	 */
	private static double SimilarDegree(String strA, String strB) {
		String newStrA = removeSign(strA);
		String newStrB = removeSign(strB);
		String sameStr = longestCommonSubstring(newStrA, newStrB);

		System.out.println("没有符号的长字符串：" + newStrA);
		System.out.println("没有符号的短字符串：" + sameStr);
		// 用较大的字符串长度作为分母，相似子串作为分子计算出字串相似度
		int temp = newStrB.length();
		int temp2 = sameStr.length();
		return temp2 * 1.000 / temp;
	}

	/*
	 * 将字符串的所有数据依次写成一行
	 */
	static String removeSign(String str) {
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
	private static boolean charReg(char charValue) {
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
	private static String longestCommonSubstring(String strA, String strB) {
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
	private static String similarityResult(double resule) {
		return NumberFormat.getPercentInstance(new Locale("en ", "US ")).format(resule);
	}
}