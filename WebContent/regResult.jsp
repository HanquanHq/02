<%@ page language="java" contentType="text/html; charset=gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>欢乐博客-提示消息</title>
        <script type="text/javascript" src="js/jquery.min.js"></script>
		<style type="text/css">
body {
	font: 75% Arail;
	text-align: center;
}

#notice {
	width: 300px;
	background: #FFF;
	border: 1px solid #BBB;
	background: #EEE;
	padding: 3px;
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -155px;
	margin-top: -100px;
}

#notice div {
	background: #FFF;
	padding: 30px 0 20px;
	font-size: 1.2em;
	font-weight: bold
}

#notice p {
	background: #FFF;
	margin: 0;
	padding: 0 0 20px;
}

a {
	color: #f00
}

a:hover {
	text-decoration: none;
}
</style>
	</head>
	<body>
		<div id="notice">
		
		    <c:set var="userId" value="${requestScope.userId}"/>
			<c:set var="message" value="${requestScope.message}"/>
	        <div id="message" style="display:none">${message}</div>
			<present name="message">
			<p class="chenggong" style="font: italic bold 2cm cursive, serif; color: green">
				ok
			</p>
			
			<p class="shibai" style="font: italic bold 2cm cursive, serif; color: red">
				×
			</p>
			</present>
			<p class="shibai">
				<span id="errorText"></span> &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1);">返回</a>
			</p>
			<p class="chenggong">
				<a href="javascript:window.location='goBlogIndex?master=${userId}'">进入我的博客</a> 
			</p>
		</div>
		<script>
			if($('#message').html()==='1'){
				$('.chenggong').show();
				$('.shibai').hide();
			}else{
				$('#errorText').html($('#message').html());
				$('.chenggong').hide();
				$('.shibai').show();
				
			}
			
		</script>
	</body>
</html>