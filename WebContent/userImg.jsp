<%@ page language="java" contentType="text/html; charset=gbk"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=gbk">
		<title>欢乐博客-我的图片</title>
		<meta name="viewport" content="initial-scale=1.0,user-scalable=no">
		<link rel="stylesheet" id="css-css" href="css/main.css"
			type="text/css" media="screen">
		<link rel="stylesheet" href="css/default.css" type="text/css">
		<script type="text/javascript" src="js/qtip.js"></script>

	</head>
	<body style="width: 1000px; margin: auto; background: url(images/bg.png) repeat">
	<c:set var="master" value="${sessionScope.callBlogMaster}"/>
	
	<c:set var="isSelf" value="${requestScope.isSelf}"/>
	<div id="userFlag" style="display:none">${isSelf}</div>
	
	    <jsp:include page="commonUserHeader.jsp"/>					
		
		<div id="m-container" class="container">
			<aside class="sidebar">
			<div class="cbl-logo">
				<div class="cbl-logo-info">
					<div style="font-size:20px">
						<a href="#">${master.userBlogName}</a>
					</div>
					<div class="cbl-logo-like">
						<a href="javascript:addFriend();" data-action="ding" data-id="676767"
							class="favorite"> <span class="c-like-text">加为好友</span></a>
					</div>
					<c:set var="countActicle" value="${sessionScope.countActicle}"/>
					<c:set var="countRev" value="${sessionScope.countRev}"/>
					<ul class="artist-data">
						<li>
							<strong>${countActicle}</strong>
							<span class="label">文章</span>
						</li>
						<li class="last">
							<strong>${countRev}</strong>
							<span class="label">评论</span>
						</li>
					</ul>
				</div>
			</div>
			<div class="m-sidebar">
			
				<section class="topspaceinfo">
				<h1>
					个性签名:
				</h1>
				<p style="text-align: left;margin-left:50px">
					${master.userMotto}
				</p>
				
				</section>
				

				<c:set var="toplist" value="${sessionScope.toplist}"/>
				
				<div class="cbl-comment">
					<div class="cbl-head">
						博客排行榜
					</div>

					<c:if test="${!empty toplist}">
						<c:forEach var="topUser" items="${toplist}">
							<ul>
								<li>
									<a
										href="goBlogIndex?master=${topUser.id}"
										title="${topUser.userName}">
										<img alt=""
											src="images/ico/${topUser.userIco}"
											class="avatar avatar-40 photo" height="40" width="40">
											<span
										class="muted">${topUser.userName}</span> <span style="float:right">阅读：${topUser.userHitNum}次</span> </a>
								</li>
							</ul>
						</c:forEach>
					</c:if>
				</div>
			</div>
			</aside>	

			<div id="content" class="clearfix">
				<section id="primary">
				<div class="main">
					<article id="post-1378"
						class="post-1378 page type-page status-publish hentry post post-article">
					<h2 class="title">
						<a class="slow" title="我的图片">我的图片</a><span class="edit"></span>
						<a id="addFlag" href="javascript:uploadImg();" class="slow" style="color: #339966;margin-left:400px">添加图片</a>
					</h2>
					<style>
					.friendIcon{
						width: 30%;float:left;margin-right:20px
					}
					</style>
					
					<c:set var="photolist" value="${requestScope.photolist}"/>

					<ul class="text">
					
						<c:if test="${empty photolist}">
							<div style="margin-top: 10px">
									<div class="friendIcon"
										style="width: 30%; float: left; margin-right: 20px; margin-bottom: 10px">
										<div style="font-size: 20px; text-align: center">
											暂时没有图片
										</div>
									</div>
							</div>
						</c:if>
						
						<c:if test="${!empty photolist}">
							<c:forEach var="photo" items="${photolist}">
								<div style="margin-top: 10px">
									<div class="friendIcon"
										style="width: 30%; float: left; margin-right: 20px; margin-bottom: 10px">
										<a  class="imgBig" target="_blank" href="photo?action=singleShow&id=${photo.id}">
										<img style="display: inline"
											src="images/photo/${photo.photoSrc}"
											style="width: 200px;">
										</a>
																			
										<div style="font-size: 20px; text-align: left">
											<a style="font-size:16px" href="goBlogIndex?master=${photo.id}">${photo.photoInfo}</a>
											<span class="delFlag"><a href="photo?action=delete&id=${photo.id}" style="color:red;font-size:12px">[删除]</a></span>
										</div>
										
									</div>
								</div>
							</c:forEach>
							<jsp:include page="page.jsp"/>	
						</c:if>
						
					</ul>
					<div class="textfoot">
						<span></span>
					</div>
					</article>
				</div>
				</section>
			</div>
		</div>
		
		<footer id="m-footer">
		<div class="Copyright">
			<p>
				技术支持
				<a href="http://www.mingrisoft.com" target="_blank">明日科技</a>
			</p>
		</div>
		</footer>
		
		<c:set var="message" value="${requestScope.message}"/>
		<div id="uploadFlag" style="display:none">${message}</div>
		<script type="text/javascript">
		$(function() {
			
			var userFlag=$('#userFlag').html();
	
			if(userFlag==='1'){
				// 当前blog与登录用户为同一个用户
				$('.delFlag').show();
				$('#addFlag').show();
			}else{
				$('.delFlag').hide();
				$('#addFlag').hide();
			}
	
			var uploadFlag=$('#uploadFlag').html();
		    if(uploadFlag!==''){
		    	$.jBox.tip(uploadFlag);
		    }
		    
		    
		});
		
		function submitImg(){
			if($('#info').val()===''){
				$.jBox.tip('图片说明不能为空','error');
				return false;
			}
			
			return true;
		}
		
		function uploadImg(){
			var html2 = '<div id="respond"><form action="photo?action=insert&type=upload" method="post" enctype="multipart/form-data" id="commentform" onsubmit="return submitImg();">'+											
						'<div class="comment-regists" style="text-align:center">上传图片</div>'+
						'<div class="comment-regists">'+
						'<ul>'+					   								    					   
					    '<li class="form-inline">'+
						'<label>图片说明：</label>'+
						'<input type="text" name="info" id="info" value="" tabindex="2" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label>图片：</label>'+
						'<input type="file" name="userImg" size="50" >'+
					    '</li>'+
					    '</ul>'+
					    '</div>'+
					    '<div class="comment-login" style="padding-top:20px"><input name="submit" type="submit" class="submitButton" tabindex="5" value="上传"></div>'+
						'</form></div>';
			
			var content = {

			    state1: {
			        content: html2,
			
			        buttons: { '取消': 0 },
			
			        buttonsFocus: 0,
			
			        submit: function (v, h, f) {
			            if (v == 0) {
			                return true; // close the window
			            }
			            return false;
			        }
			    }
			};
			$.jBox.open(content, '上传图片', 650, 550);
		}
		
		//<![CDATA[
	
function wptdb_isIE() {
	return /msie/i.test(navigator.userAgent)
			&& !/opera/i.test(navigator.userAgent);
}

function wptdb_getBrowserWidthHeight(w_or_h) {
	var intH = 0;
	var intW = 0;
	if (typeof window.innerWidth == 'number') {
		intH = window.innerHeight;
		intW = window.innerWidth;
	} else if (document.documentElement
			&& (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
		intH = document.documentElement.clientHeight;
		intW = document.documentElement.clientWidth;
	} else if (document.body
			&& (document.body.clientWidth || document.body.clientHeight)) {
		intH = document.body.clientHeight;
		intW = document.body.clientWidth;
	}
	if (w_or_h == 'w') {
		return parseInt(intW);
	}
	if (w_or_h == 'h') {
		return parseInt(intH);
	}
}

function wptdb_getScrollXY(x_or_y) {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    //Netscape compliant
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    //DOM compliant
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  if (x_or_y == 'x') {
  return scrOfX;
  }
  if (x_or_y == 'y') {
  return scrOfY;
  }
}

function wptdb_resize_demobar_forie() {
var myWidth = wptdb_getBrowserWidthHeight('w');
document.getElementById('wpthemedemobar').style.width = myWidth+'px';
}

function wptdb_close() {
document.getElementById('wpthemedemobar').style.display = 'none'
document.getElementsByTagName('html')[0].style.paddingTop = '0px';

}

//]]>
</script>
		

		<script type="text/javascript"
			src="js/functionall.js?ver=1.0">
</script>
		<script type="text/javascript"
			src="js/home.js?ver=1.0">
</script>
		<!--[if lt IE 9]>
	<script src="js/html5shiv.min.js"></script>
	<script src="js/respond.min.js"></script>
<![endif]-->


		<div id="wptdb_qTip" style="left: 1525px; top: 881px;"></div>
	</body>
</html>