<%@ page language="java" contentType="text/html" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="gbk">
		<title>ITCLUB 博客</title>
		<link href="css/base.css" rel="stylesheet">
		<link href="css/index.css" rel="stylesheet">
		<link href="css/new.css" rel="stylesheet">

		<script
			src="http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=407687">
</script>
		<link rel="stylesheet"
			href="http://bdimg.share.baidu.com/static/api/css/share_style1_32.css">

		<script type="text/javascript" src="js/jBox/jquery-1.4.2.min.js">
</script>
		<script type="text/javascript" src="js/jBox/jquery.jBox-2.3.min.js">
</script>
		<script type="text/javascript" src="js/jBox/i18n/jquery.jBox-zh-CN.js">
</script>
		<link type="text/css" rel="stylesheet" href="js/jBox/Skins2/Blue/jbox.css"/>
		
		<script type="text/javascript" src="js/toolTip/tooltip.js">
</script>
		<link type="text/css" rel="stylesheet" href="js/toolTip/tooltip.css" />
		<style>
#respond {
	position: relative;
	overflow: hidden;
	padding: 20px 40px;
	font-size: 13px;
}

#respond div {
	margin: 6px 0;
	color: #7F9BC0;
	font-size: 20px;
}

.comment-login {
	padding: 60px 0 0;
	text-align: center;
}

.comment-login li {
	margin: 20px 20px;
}

.comment-regists {
	padding: 10px 130px 0;
	text-align: right;
}

.comment-regists li {
	margin: 20px 20px;
}

#respond label {
	position: relative;
	top: 5px;
}

#respond input {
	width: 150px;
	height: 18px;
	border-top-style: initial;
	border-right-style: initial;
	border-left-style: initial;
	border-top-color: initial;
	border-right-color: initial;
	border-left-color: initial;
	border-image-source: initial;
	border-image-slice: initial;
	border-image-width: initial;
	border-image-outset: initial;
	border-image-repeat: initial;
	color: rgb(79, 79, 79);
	font-size: 13px;
	font-family: 微软雅黑;
	overflow: hidden;
	padding: 2px;
	outline: 0px;
	border-width: 0px 0px 1px;
	border-bottom: 1px dashed rgb(127, 155, 192);
	background: 0px 0px;
}

#respond input.submitButton {
	margin-right: 14px;
	width: 66px;
	height: 26px;
	border-image-source: initial;
	border-image-slice: initial;
	border-image-width: initial;
	border-image-outset: initial;
	border-image-repeat: initial;
	color: rgb(255, 255, 255);
	font-size: 13px;
	font-family: 微软雅黑;
	cursor: pointer;
	outline: 0px;
	border-width: 0px;
	border-style: initial;
	border-color: initial;
	border-radius: 0px;
	background: rgb(127, 155, 192);
}

</style>
	</head>

	<body
		style="width: 1100px; margin: auto; background: url(images/bg.png) repeat">
		<c:set var="logoner" value="${sessionScope.logoner}" />
		<div id="logoner" style="display: none">
			${logoner}
		</div>
		<div id="blogNameIndex" style="display: none">
			${logoner.userBlogName}
		</div>
		<div id="userIdIndex" style="display: none">
			${logoner.id}
		</div>
		<header>
		<div id="logo">
			<a href="index"></a>
		</div>
		<nav class="topnav" id="topnav">

		<div id="logined" style="border: none; display: none" class="tooltip"
			onmouseover="tooltip.pop(this, '#textContent',{position:1, offsetX:-50,  effect:'slide'})">
			<a href="javascript:loginIndex();" id="topnav_current"><span
				id="userBlogName" style="margin-left:600px">你好，${logoner.userBlogName}（菜单）</span><span
				class="en"></span>
		</div>

		<div style="display: none">
			<div id="textContent" style="width: 75px; height:auto;">
				<a id="blogUrl" href="goBlogIndex?master=${logoner.id}">进入博客</a>
				<br />
				<a id="blogUrl" href="javascript:logOff();">退出</a>
			</div>
		</div>

		<div id="unLogin" style="margin-left: 500px;">
			<a href="javascript:loginIndex();" id="topnav_current"><span
				class="menuTitle">快速登录</span><span class="en"></span> </a>
			<a href="javascript:reg();" style="color: #e15782"><span
				class="menuTitle">注册</span><span class="en"></span> </a>
		</div>


		</nav>
		</header>
 		<div class="banner">

 		</div>
<!-- 		<div class="template"> -->
<!-- 			<div class="box"> -->
<!-- 				<h3> -->
<!-- 					<p> -->
<!-- 						<span>关于明日</span> -->
<!-- 					</p> -->
<!-- 				</h3> -->
<!-- 				<ul> -->
<!-- 					<li> -->
<!-- 						<a href="http://www.mingrisoft.com/" target="_black"><img -->
<!-- 								src="images/ad01.png"> </a><span>明日主站</span> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="http://www.mingribook.com/booksjs.php?stypeid=50" target="_black"><img -->
<!-- 								src="images/ad02.png"> </a><span>从入门到精通系列</span> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="http://www.mingribook.com/booksjs.php?stypeid=46" target="_black"><img -->
<!-- 								src="images/ad03.png"> </a><span>编程实战系列</span> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="http://www.mingribook.com/bookjs.php?typeid=7" target="_black"><img -->
<!-- 								src="images/ad04.png"> </a><span>编程词典</span> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<a href="http://www.mingribook.com/bookjs.php?typeid=8" target="_black"><img -->
<!-- 								src="images/ad05.png"> </a><span>动物系列书</span> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->

		<article>
		<h1 class="t_nav">
			<a href="#" class="n1">精选博文</a>
		</h1>


		<style>
#cssClip {
	width: 600px;
	height: 60px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-overflow: ellipsis; /* IE/Safari */
	-ms-text-overflow: ellipsis;
	-o-text-overflow: ellipsis; /* Opera */
	-moz-binding: url("ellipsis.xml#ellipsis"); /*FireFox*/
}
</style>

		<c:set var="mostArticlelist" value="${requestScope.mostArticlelist}" />

		<div class="bloglist left">

			<c:if test="${!empty mostArticlelist}">
				<c:forEach var="article" items="${mostArticlelist}">
					<h3>
						<a
							href="goBlogContent?id=${article.id}&master=${article.artWhoId}">${article.artTitle}</a>
					</h3>

					<ul>
						<p id="cssClip">
							${article.artContent}
						</p>
						<a
							href="goBlogContent?id=${article.id}&master=${article.artWhoId}"
							target="_blank" class="readmore">阅读全文&gt;&gt;</a>
					</ul>
					<p class="dateview">
						<span>${article.artPubTime}</span><span>作者：${article.userName}</span>
					</p>
				</c:forEach>
			</c:if>


		</div>

		<aside class="right">
		<div class="news">
			<h3>
				<p>
					最新
					<span>文章</span>
				</p>
			</h3>

			<c:set var="newArticlelist" value="${requestScope.newArticlelist}" />

			<ul class="rank">
				<c:if test="${!empty newArticlelist}">
					<c:forEach var="article" items="${newArticlelist}">
						<li>
							<a
								href="goBlogContent?id=${article.id}&master=${article.artWhoId}"
								title="${article.artTitle}" target="_blank">${article.artTitle}</a>
						</li>
					</c:forEach>
				</c:if>
			</ul>


			<h3 class="ph">
				<p>
					博客
					<span>排行</span>
				</p>
			</h3>

			<c:set var="toplist" value="${sessionScope.toplist}" />
			<ul class="paih">
				<c:if test="${!empty toplist}">
					<c:forEach var="topUser" items="${toplist}">
						<ul>
							<li>
								<a style="color: #5EA51B"
									href="goBlogIndex?master=${topUser.id}">${topUser.userName}</a>
								<span style="float: right">${topUser.userHitNum}次阅读</span>
							</li>
						</ul>
					</c:forEach>
				</c:if>
			</ul>
			<h3 class="links">
				<p>

					<span>分享</span>
				</p>
			</h3>

			<div class="bdsharebuttonbox bdshare-button-style1-32"
				data-bd-bind="1467678661338">
				<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
					class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#"
					class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#"
					class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#"
					class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#"
					class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
			</div>

			<script>
// 验证用户是否登录
$(function() {

	var loginFlag = $('#logoner').html();

});

function loginIndex() {

	var html = '<div id="respond"><form  method="post" id="commentform">'
			+ '<div class="comment-login">请登录</div>'
			+ '<div class="comment-login">'
			+ '<ul>'
			+ '<li class="form-inline">'
			+ '<label>账号：</label>'
			+ '<input type="text" name="user" id="user" value="" tabindex="1" aria-required="true">'
			+ '</li>'
			+ '<li class="form-inline">'
			+ '<label>密码：</label>'
			+ '<input type="password" name="ps" id="ps" value="" tabindex="2" aria-required="true">'
			+ '</li>'
			+ '</ul>'
			+ '</div>'
			+ '<div class="comment-login"><input name="submit" type="button" id="save" onclick="loginIn()"  class="submitButton" tabindex="5" value="登录"></div>'
			+ '</form></div>';

	var content = {

		state1 : {
			content : html,

			buttons : {
				'取消' : 0
			},

			buttonsFocus : 0,

			submit : function(v, h, f) {
				if (v == 0) {
					return true; // close the window
		}
		return false;
	}
		}
	};
	$.jBox.open(content, '登录', 650, 550);

}

function reg() {
	var html = '<div id="respond"><form action="userReg?action=upIco"  method="post" enctype="multipart/form-data" onsubmit=" return regUser()" >'
			+ '<div class="comment-regists" style="text-align:center">请注册</div>'
			+ '<div class="comment-regists">'
			+ '<ul>'
			+ '<li class="form-inline">'
			+ '<label><span style="color:red">*</span>账号：</label>'
			+ '<input type="text" name="userName" id="userName" value="" tabindex="1" aria-required="true">'
			+ '</li>'
			+ '<li class="form-inline">'
			+ '<label><span style="color:red">*</span>密码：</label>'
			+ '<input type="password" name="userPs" id="userPs" value="" tabindex="2" aria-required="true">'
			+ '</li>'
			+ '<li class="form-inline">'
			+ '<label><span style="color:red">*</span>确认密码：</label>'
			+ '<input type="password" name="psRepeat" id="psRepeat" value="" tabindex="2" aria-required="true">'
			+ '</li>'
			+ '<li class="form-inline">'
			+ '<label><span style="color:red">*</span>博客名称：</label>'
			+ '<input type="text" name="regBlogName" id="regBlogName" value="" tabindex="2" aria-required="true">'
			+ '</li>'
			+ '<li class="form-inline">'
			+ '<label><span style="color:red">*</span>个性签名：</label>'
			+ '<input type="text" name="blogInfo" id="blogInfo" value="" tabindex="2" aria-required="true">'
			+ '</li>'
			+ '<li class="form-inline">'
			+ '<label><span style="color:red">*</span>头像：</label>'
			+ '<input type="file" name="userIco" size="50" value="">'
			+ '</li>'
			+ '</ul>'
			+ '</div>'
			+ '<div class="comment-login" style="padding-top:20px"><input name="submit" type="submit"  class="submitButton" tabindex="5" value="注册"></div>'
			+ '</form></div>';

	var content = {

		state1 : {
			content : html,

			buttons : {
				'取消' : 0
			},

			buttonsFocus : 0,

			submit : function(v, h, f) {
				if (v == 0) {
					return true; // close the window
		}
		return false;
	}
		}
	};
	$.jBox.open(content, '登录', 650, 550);
}

function regUser() {
	if ($('#userName').val() === '') {
		$.jBox.tip("账号不能为空", 'error');
		return false;
	}

	if ($('#userPs').val() === '') {
		$.jBox.tip("密码不能为空", 'error');
		return false;
	}

	if ($('#psRepeat').val() === '') {
		$.jBox.tip("确认密码不能为空", 'error');
		return false;
	}

	if ($('#psRepeat').val() !== $('#userPs').val()) {
		$.jBox.tip("密码输入不一致", 'error');
		return false;
	}

	if ($('#regBlogName').val() === '') {
		$.jBox.tip("博客名称不能为空", 'error');
		return false;
	}

	if ($('#regBlogName').val().length > 10) {
		$.jBox.tip("博客名称不能超过10个字", 'error');
		return false;
	}

	if ($('#blogInfo').val() === '') {
		$.jBox.tip("个性标签不能为空", 'error');
		return false;
	}

	return true;
}

// 获取页面参数
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function loginIn() {
	if ($("#user").val() === "" || $("#ps").val() === "") {
		$.jBox.tip("账户或密码不能为空。", 'error');
		return;
	}

	$.jBox.tip("正在保存...", 'loading');

	$.ajax( {
		url : "logon",
		type : "post",
		data : {
			master : GetQueryString("master"),
			userName : $("#user").val(),
			userPswd : $("#ps").val()

		},
		success : function(data) {

			// 关闭加载条
		$.jBox.closeTip();

		arrData = eval("(" + data + ")");

		if (arrData.result == '1') {
			$.jBox.tip("账户或密码不正确。", 'error');
		} else {

			$.jBox.close();

			$.jBox.tip("登录成功！", 'success');

			$("#userBlogName").html('你好，' + arrData.userBlogName + '(菜单)');
			$("#blogUrl").attr( {
				href : "goBlogIndex?master=" + arrData.userId
			});

			$('#unLogin').hide();
			$('#logined').show();

			$('#isLogin').html("1");

		}
	}
	});

}

function logOff() {

	$.jBox.tip("正在退出...", 'loading');

	$.ajax( {
		url : "logoff",
		type : "post",
		success : function(data) {

			// 关闭加载条
		$.jBox.closeTip();

		$.jBox.tip("成功退出！", 'success');

		$('#unLogin').show();
		$('#logined').hide();

		$('.delFlag').hide();
		$('#addFlag').hide();
		$('.editFlag').hide();

	}
	});
}
</script>

			<script>
window._bd_share_config = {
	"common" : {
		"bdSnsKey" : {},
		"bdText" : "",
		"bdMini" : "2",
		"bdMiniList" : false,
		"bdPic" : "",
		"bdStyle" : "1",
		"bdSize" : "32"
	},
	"share" : {}
};
with (document)
	0[(getElementsByTagName('head')[0] || body)
			.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
</script>


		</div>
		</aside>
		</article>
		<footer>
		<p>
			<span style="color:white">技术支持</span>
			<a href="http://www.mingrisoft.com" style="color:#FF7F50" target="_blank">HanquanHq<a>
		</p>
		</footer>
		<script src="js/silder.js">
</script>
	</body>
</html>