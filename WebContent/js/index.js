$(function() {
			
			var loginFlag=$('#logoner').html();
	
			if(loginFlag===''){
				// 未登录
				$('#unLogin').show();
                $('#logined').hide();
			}else{
				//已经登录
				$("#userBlogName").html('你好，'+$('#blogNameIndex').text()+'(菜单)');
                $("#blogUrl").attr({ href: "goBlogIndex?master="+$('#userIdIndex').text()});
				
				$('#unLogin').hide();
                $('#logined').show();
			}
		    
		});
			
			
			function loginIndex(){
				
				var html = '<div id="respond"><form  method="post" id="commentform">'+											
						'<div class="comment-login">请登录</div>'+
						'<div class="comment-login">'+
						'<ul>'+
					    '<li class="form-inline">'+
						'<label>账号：</label>'+
						'<input type="text" name="user" id="user" value="" tabindex="1" aria-required="true">'+
					    '</li>'+
					    '<li class="form-inline">'+
						'<label>密码：</label>'+
						'<input type="password" name="ps" id="ps" value="" tabindex="2" aria-required="true">'+
					    '</li>'+
					    '</ul>'+
					    '</div>'+
					    '<div class="comment-login"><input name="submit" type="button" id="save" onclick="loginIn()"  class="submitButton" tabindex="5" value="登录"></div>'+
						'</form></div>';
						
				var content = {

			    state1: {
			        content: html,
			
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
				$.jBox.open(content, '登录', 650, 550);		
						
			}
			
			function reg(){
				var html = '<div id="respond"><form action="userReg?action=upIco"  method="post" enctype="multipart/form-data" onsubmit=" return regUser()" >'+											
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
						
				var content = {

			    state1: {
			        content: html,
			
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
				$.jBox.open(content, '登录', 650, 550);	
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
                	
                	$("#userBlogName").html('你好，'+arrData.userBlogName+'(菜单)');
                	$("#blogUrl").attr({ href: "goBlogIndex?master="+arrData.userId});
                	
                	$('#unLogin').hide();
                	$('#logined').show();
                	
                	$('#isLogin').html("1");
                	
                }
            }
        });
			
		}
			
			
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