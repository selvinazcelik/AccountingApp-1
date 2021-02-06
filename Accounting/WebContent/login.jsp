<%@page import="com.accountingapp.model.User"%>
<%@page import="com.accountingapp.service.AccountingService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Cookie[]  cookies = request.getCookies();
	if(cookies != null){
		for(Cookie item : cookies){
			if(item.getName().equals("userId") && item.getValue() != null && item.getValue().length() > 0){
				AccountingService as = new AccountingService();
				User currUser = as.getUserWithId(Integer.valueOf(item.getValue()));
				request.getSession().setAttribute("currentUser", currUser);
				response.sendRedirect("/Accounting/pages/index.jsp");
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>AccountingApp Login</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
	<link href="include/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="include/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href="include/Ionicons/css/ionicons.min.css" rel="stylesheet">
	<link href="include/css/AdminLTE.min.css" rel="stylesheet">
	<link href="include/iCheck/square/blue.css" rel="stylesheet">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
  
	<script src="include/vendor/jquery/jquery.min.js"></script>
	<script src="include/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="include/iCheck/icheck.min.js"></script>
	<script>
		$(function () {
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%' /* optional */
			});
		});
	</script>
	<script type="text/javascript">
		var sendAjax;
		var login;
		$(document).ready(function(){
			sendAjax = function(jsonreq,url,async,type){
				$.ajax({
					url : url,
					type : type,
					dataType : 'json',
					data : JSON.stringify(jsonreq),
					contentType : 'application/json',
					mimeType : 'application/json',
					async : async,
					success : function(data){
						if(jsonreq.params[0] == "login"){
							if(typeof data != 'undefined' && data != null && data == "1"){
								window.location.href = "/Accounting/pages/index.jsp";
							} else {
								alert("Kullanıcı adı ve şifrenizi kontrol ederek tekrar deneyiniz.");
							}
						}
					}
				});
			}
			
			login = function(data){
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "login";
				jsonreq.params[1] = data.userMail;
				jsonreq.params[2] = data.password;
				jsonreq.params[3] = data.remember;
				sendAjax(jsonreq,"/Accounting/AccountingServlet",true,"POST");
			}
			
			$('#loginBtn').on('click',function(){
				var loginObj = new Object();
				loginObj.userMail = $('#email').val();
				loginObj.password = $('#password').val();
				loginObj.remember = document.getElementById('remember').checked;
				login(loginObj);
			});
		});
	</script>
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<b>Accounting</b>App
		</div>
		<div class="login-box-body">
			<p class="login-box-msg">Lütfen giriş yapınız</p>
			<div class="form-group has-feedback">
				<input id="email" type="email" class="form-control" placeholder="Email" required="required">
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input id="password" type="password" class="form-control" placeholder="Şifre" required="required">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="checkbox icheck">
						<label>
							<input id="remember" type="checkbox" value="on" id="defaultCheck1"> Beni Hatırla
						</label>
					</div>
				</div>
				<div class="col-md-4">
					<button id="loginBtn" align="right" class="btn btn-primary btn-block btn-flat">Giriş</button>
				</div>
			</div>
			<a href='#'>Şifremi unuttum</a><br>
			<a href='./pages/register.jsp' class="text-center">Kayıt ol</a>
		</div>
	</div>
</body>
</html>
