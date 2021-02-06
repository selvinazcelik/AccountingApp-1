<%@page import="com.accountingapp.model.User"%>
<%@page import="com.accountingapp.service.AccountingService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>AccountingApp Login</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
	<link href="../include/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="../include/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href="../include/Ionicons/css/ionicons.min.css" rel="stylesheet">
	<link href="../include/css/AdminLTE.min.css" rel="stylesheet">
	<link href="../include/iCheck/square/blue.css" rel="stylesheet">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
  
	<script src="../include/vendor/jquery/jquery.min.js"></script>
	<script src="../include/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="../include/iCheck/icheck.min.js"></script>
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
						if(jsonreq.params[0] == "register"){
							if(data == "1"){
								alert('Kayıt işlemi başarılı');
								window.location.href = "../login.jsp";
							} else {
								alert('Kayıt işlemi başarısız');
							}
						}
					}
				});
			}
			
			
			$('#registerBtn').on('click',function(){
				var email = $('#email').val();
				var password = $('#password').val();
				var password2 = $('#password2').val();
				if(email.trim().length < 1){
					alert('Email kısmı boş olamaz!');
				} else if(password.trim().length < 1 || password2.trim().length < 1){
					alert('Şifre kısmı boş olamaz!')
				} else if(password != password2){
					alert('Şifreler birbirleri ile uyuşmuyor!')
				} else {
					var jsonreq = new Object();
					jsonreq.params = new Array();
					jsonreq.params[0] = "register";
					var userObj = new Object();
					userObj.userName = $('#userName').val();
					userObj.userSurname = $('#userSurname').val();
					userObj.userMail = email;
					userObj.userPassword = password
					jsonreq.params[1] = JSON.stringify(userObj);
					sendAjax(jsonreq,"/Accounting/AccountingServlet",true,"POST");
				}
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
			<p class="login-box-msg">Kayıt Ol</p>
			<div class="form-group has-feedback">
				<input id="userName" class="form-control" placeholder="İsim" required="required">
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input id="userSurname" class="form-control" placeholder="Soyisim" required="required">
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input id="email" type="email" class="form-control" placeholder="Email" required="required">
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input id="password" type="password" class="form-control" placeholder="Şifre" required="required">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input id="password2" type="password" class="form-control" placeholder="Şifre Tekrar" required="required">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="row">
				<div class="col-md-12">
					<button id="registerBtn" align="right" class="btn btn-primary btn-block btn-flat">Kayıt Ol</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
