<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

  <script type="text/javascript" src="js/jBox/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="js/jBox/jquery.jBox-2.3.min.js"></script>
  <script type="text/javascript" src="js/jBox/i18n/jquery.jBox-zh-CN.js"></script>
  
  <link type="text/css" rel="stylesheet" href="js/jBox/Skins2/Blue/jbox.css"/>
  
  <script type="text/javascript" src="js/toolTip/tooltip.js"></script>
  <link type="text/css" rel="stylesheet" href="js/toolTip/tooltip.css"/>
  
  
  
  <c:set var="master" value="${sessionScope.callBlogMaster}"/>
  <c:set var="logoner" value="${sessionScope.logoner}"/>
  
  <div id="isLogin" style="display:none">${logoner.id}</div>
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
    font-size:20px;
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
div.jbox .jbox-button {
    color: white;
}

#loginHtml{overflow:hidden;}
.left{width:220px;height:487px;  background:#f0f3f9; float:left;}
.right{width:425px; height:487px; background:#f0f3f9; float:right;}
.center{margin:0 410px 0 210px; background:#ffe6b8; height:487px;}

		</style>
		
		<script>
		
		$(function() {
			
	var isLogin=$('#isLogin').html();
	
	if(isLogin!==''){
		// 登录状态
		$('#unLogin').hide();
        $('#logined').show();
        
	}else{
		$('#unLogin').show();
        $('#logined').hide();
	}
	
});
		
		function logOff(){
			
			$.jBox.tip("正在退出...", 'loading');
			
			$.ajax({
            url: "logoff",
            type: "post",
            success: function(data) {
            	
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
		
		
		
		
		
		function login(){
			
		var html1 ='<div id="loginHtml">'+
    			   '<div class="left">' +
    			   '<div id="respond"><form  method="post" id="commentform">'+											
				   '<div class="comment-login">微信公众号</div>'+
				   '<div class="comment-login" style="font-size:16px;margin-top:-50px">mrkj_2013</div>'+
				   '<div class="comment-login" style="margin-top:-50px">'+
				   '<img src="images/ewm.jpg">'+
				   '</div>'+
				   '</form></div>'+
    			   '</div>'+
    			   '<div class="right">' +
    			   '<div id="respond"><form  method="post" id="commentform">'+											
					'<div class="comment-login">请登录</div>'+
					'<div class="comment-login" style="font-size:16px;margin-top:-40px">'+
					'<ul style="margin-left:-100px">'+
					'<li class="form-inline">'+
					'<label>账号：</label>'+
					'<input type="text" name="user" id="user" value="" tabindex="1" aria-required="true">'+
					'</li>'+
					'<li class="form-inline">'+
					'<label>密码：</label>'+
					'<input type="password" name="ps" id="ps" value="" tabindex="2" aria-required="true">'+
					'</li>'+
					'<li class="form-inline" style="margin-left:92px">'+
				    '<label>验证码：</label>'+
					'<input class="wenbenkuang" style="width:100px" name="verifycode" id="verifycode" type="text" value="" maxLength=2 size="3"  onkeydown="keyLogin();">'+
					'<input id="hiddencode" type="hidden">'+
					'<span id="showspan" name="showspan" style="border:5px;font-weight:bold;background:#131313;color:white"></span>'+
					'<a class="kanbuqing" href="javascript:void(0)" onClick="getverifycodeChange();">&nbsp;&nbsp;&nbsp;&nbsp;换一题</a>'+  												
					'</li>'+
					'</ul>'+
					'</div>'+
					'<div class="comment-login"><input name="submit" type="button" id="save" onclick="loginIn()"  class="submitButton" tabindex="5" value="登录">' +
					'</div>'+
				    '</form></div>'+
    			    '</div>'+
    			   '<div class="center"></div>'+
				   '</div>';
		

							
			var html2 = '<div id="respond"><form action="userReg?action=upIco"  method="post" enctype="multipart/form-data" onsubmit=" return regUser()" >'+											
						'<div class="comment-regists" style="text-align:center">请注册</div>'+
						'<div class="comment-regists">'+
						'<ul>'+
					    '<li class="form-inline">'+
						'<label><span style="color:red">*</span>账号：</label>'+
						'<input type="text" name="userName" id="userName" value="" tabindex="1" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label><span style="color:red">*</span>密码：</label>'+
						'<input type="password" name="userPs" id="userPs" value="" tabindex="2" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label><span style="color:red">*</span>确认密码：</label>'+
						'<input type="password" name="psRepeat" id="psRepeat" value="" tabindex="2" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label><span style="color:red">*</span>博客名称：</label>'+
						'<input type="text" name="regBlogName" id="regBlogName" value="" tabindex="2" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label><span style="color:red">*</span>个性签名：</label>'+
						'<input type="text" name="blogInfo" id="blogInfo" value="" tabindex="2" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label><span style="color:red">*</span>头像：</label>'+
						'<input type="file" name="userIco" size="50" value="">'+
					    '</li>'+
					    '</ul>'+
					    '</div>'+
					    '<div class="comment-login" style="padding-top:20px"><input name="submit" type="submit"  class="submitButton" tabindex="5" value="注册"></div>'+
						'</form></div>';

			var data = {};
			var states = {};
			
			states.state1 = {
			    content: html1,
			    buttons: {'忘记密码?':0,'没有账号? 请注册': 1},
			    submit: function (v, h, f) {
			        if (v == 1) {
			            $.jBox.nextState(); 
			        }
			        if (v == 0) {
			            $.jBox.tip("请联系系统管理员！", 'success');
			        }
			        return false;
			    }
			};
			states.state2 = {
			    content: html2,
			    buttons: { '若有账号，请直接登录': 1 },
			    buttonsFocus: 1, // focus on the second button
			    submit: function (v, o, f) {
			        if (v == 1) {
			             $.jBox.prevState() //go back
			        } 
			        return false;
			    }
			};
			
			$.jBox.open(states, '', 650, 550);
			
			var i=parseInt(10*Math.random());
			var j=parseInt(10*Math.random());
			var k=i+j;
			$("#hiddencode").val(k);
			$("#showspan").html(" " + i + " + " + j + " = ?");  
			
		}
		
		
		function  regUser(){
			if($('#userName').val()===''){
			   $.jBox.tip("账号不能为空", 'error');	
			   return false;
			}
			
			
			if($('#userPs').val()===''){
			   $.jBox.tip("密码不能为空", 'error');	
			   return false;
			}
			
			if($('#psRepeat').val()===''){
			   $.jBox.tip("确认密码不能为空", 'error');	
			   return false;
			}
			
			if($('#psRepeat').val()!==$('#userPs').val()){
			   $.jBox.tip("密码输入不一致", 'error');	
			   return false;
			}
			
			if($('#regBlogName').val()===''){
			   $.jBox.tip("博客名称不能为空", 'error');	
			   return false;
			}
			
			if($('#regBlogName').val().length>10){
			   $.jBox.tip("博客名称不能超过10个字", 'error');	
			   return false;
			}
			
			if($('#blogInfo').val()===''){
			   $.jBox.tip("个性标签不能为空", 'error');	
			   return false;
			}
			
			return true;
		}
		
		
		// 获取页面参数
		function GetQueryString(name) {
    		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
   			var r = window.location.search.substr(1).match(reg);
    		if (r != null) return unescape(r[2]);
   			return null;
		}
		
		
		function loginIn(){
			if($("#user").val()==="" || $("#ps").val()===""){
				$.jBox.tip("账户或密码不能为空。", 'error');
				return;
			}
			
			
			if($("#hiddencode").val()!=parseInt($("#verifycode").val())){
				$.jBox.tip("验证码错误!", 'error');
				return;
			}
			
			$.jBox.tip("正在保存...", 'loading');
			
			$.ajax({
            url: "logon",
            type: "post",
            data: {
				master:GetQueryString("master"),
                userName: $("#user").val(),
                userPswd: $("#ps").val()
      
            },
            success: function(data) {
            	
                // 关闭加载条
			    $.jBox.closeTip();
                
                arrData = eval("(" + data + ")");
                
                if(arrData.result=='1'){
                	$.jBox.tip("账户或密码不正确。", 'error');
                }
                else{
                	
                	$.jBox.close();
                	
                	$.jBox.tip("登录成功！", 'success');
                	
                	$("#userBlogName").html(arrData.userBlogName);
                	$("#blogUrl").attr({ href: "goBlogIndex?master="+arrData.userId});
                	
                	$('#unLogin').hide();
                	$('#logined').show();
                	
                	$('#isLogin').html("1");
                	
                	if(arrData.userFlag==='1'){
					// 当前blog与登录用户为同一个用户
						$('.delFlag').show();
						$('#addFlag').show();
						$('.editFlag').show();
	                 }
                }
            }
        });
			
		}
		</script>
		
		<header class="l-header" style="margin-top:-30px">
		
		<div>
			<a href="index"><span><img src="images/logo3.png"/></span></a>
			
			<span id="unLogin" ><a style="font-size: 16px;color:white;margin-left:500px"
					href="javascript:login();">我的博客<span style="color:red;font-size:12px">(登录)</span></a> </span>
					
			
			<span id="logined"  style="display:none" class="tooltip" onmouseover="tooltip.pop(this, '#textContent',{position:1, offsetX:-50,  effect:'slide'})" ><a
					style="font-size: 16px;color:white;margin-left:500px" >你好，<span
						id="userBlogName">${logoner.userBlogName}</span>（<span style="color:red">菜单</span>）</a>
			</span>

			<div style="display: none">
				<div id="textContent" style="width: 75px; height: 75px;">
					<a id="blogUrl" href="goBlogIndex?master=${logoner.id}">进入博客</a>
					<br />
					<a id="blogUrl" href="javascript:logOff();">退出</a>
				</div>
			</div>
			
		</div>
		
		<div class="m-about" style="background-image:url(images/banner2.png);">
			<div id="logo">
				<div class="playerd">
					<div id="pic" center="center;" class="">
						<img
							src="images/ico/${master.userIco}">
					</div>
					
				</div>
			</div>
			<h1 class="tit">
				<a href="#">${master.userBlogName}</a>
			</h1>
		</div>
		</header>
		<div id="m-nav" class="m-nav">
			<div class="m-nav-all">
				<div class="m-logo-url">
					<img
						src="images/ico/${master.userIco}">
					<h3>
						${master.userBlogName}
					</h3>
				</div>
				<ul class="nav">
					<li class="page_item page-item-1397">
						<a href="goBlogIndex?master=${master.id}">博文目录</a>
					</li>
					<li class="page_item page-item-1432">
						<a href="photo?master=${master.id}&action=listShow">图片</a>
					</li>
					<li class="page_item page-item-828">
						<a href="word?master=${master.id}&action=listShow">留言板</a>
					</li>
					<li class="page_item page-item-828">
						<a href="friend?master=${master.id}&action=listShow">好友</a>
					</li>
					<%--<li class="page_item page-item-828">
						<a href="userInfo.jsp">收藏</a>
					</li>
					--%>
					<li class="page_item page-item-828">
						<a href="goBlogMy?master=${master.id}">关于我</a>
					</li>
				</ul>
			</div>
		</div>
		
		<div id="m-header" class="m-header">
			<div id="showLeftPush" class="left m-header-button"></div>
			<h1>
				<a href="#">${master.userBlogName}</a>
			</h1>
			
		</div>
		
		<script>
		function addFriend(){
		$.jBox.tip("正在添加...", 'loading');
			$.ajax({
            url: "friend",
            type: "get",
            data: {
				master:GetQueryString("master"),
                action: "insert"
            },
            success: function(data) {
            	if(data==="1"){
			        $.jBox.closeTip();
            		$.jBox.tip("添加成功！", 'success');
            		return;
            	}
            	if(data==="0"){
            		$.jBox.closeTip();
            		$.jBox.tip("请先登录！", 'error');
            		return;
            	}
            	if(data==="2"){
            		$.jBox.closeTip();
            		$.jBox.tip("自己不能添加自己为好友！", 'error');
            		return;
            	}
            	if(data==="3"){
            		$.jBox.closeTip();
            		$.jBox.tip("好友已经添加！", 'error');
            		return;
            	}
            	if(data==="4"){
            		$.jBox.closeTip();
            		$.jBox.tip("添加失败！", 'error');
            		return;
            	} 	
            }
        });
	}
		</script>
		<script type="text/javascript" language="javascript">  
        function getverifycodeChange(){  
            var i = parseInt(10 * Math.random());  
            var j = parseInt(10 * Math.random());  
            var k = i + j;  
            $("#hiddencode").val(k);  
            $("#showspan").html(" " + i + " + " + j + " = ?");  
            $("#verifycode").focus();  
        }</script>  
			