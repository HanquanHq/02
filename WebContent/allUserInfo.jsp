<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看用户信息</title>
<link href="/09-Manager/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/09-Manager/js/jquery.js"></script>

</head>

<body>
   
    <div >
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th>标题</th>
	        <th>作者</th>
	        <th>发布时间</th>
	        <th>文章链接</th>
        </tr>
        </thead>
        <tbody>
        
		<%-- 使用JSTL --%>
		<c:forEach items="${ArticleTitle}" var="oneArticle">
			<tr>
		       <td>${oneArticle.title}</td>
		       <td>${oneArticle.author}</td>
		       <td>${oneArticle.pubtime}</td>
		       <td><a href="${oneArticle.url}">${oneArticle.url}</a></td>
	        </tr>            
		</c:forEach>
        </tbody>
    </table>
    </div>
    


</body>

</html>
