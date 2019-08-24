package test;

import java.io.IOException;

import org.junit.Test;

import com.servlet.BlogCheckServlet;

public class BlogCheckServletTest {
	BlogCheckServlet bc;
	@Test
	public void test() throws IOException {
		double result=bc.myMain("隐藏值也有效，将啊上表单阿斯顿发数据提交到服务器中。 从服务器中可以接收到隐藏值，说明隐藏值也有效，将表单数据提交到服务器");
		System.out.println(result);
	}

}
