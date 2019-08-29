<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="SearchServlet" ><input type="text" name="str" id="str">
<input id="submit" type="submit" name="submit" value="搜索" > <br>
 <input type="radio" name="searchby" value="1" checked="checked"/>按标题检索
 <br>
<input type="radio" name="searchby" value="2"/>按作者检索 <br>
<input type="radio" name="searchby" value="3"/>按文章编号检索
</form>

</body>
</html>