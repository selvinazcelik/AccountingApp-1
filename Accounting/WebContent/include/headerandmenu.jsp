<%@page import="com.accountingapp.service.AccountingService"%>
<%@page import="com.accountingapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%
	User user = (User)request.getSession().getAttribute("currentUser");
	String userName = "";
	if(user == null){
		response.sendRedirect("/Accounting/login.jsp");
	} else {
		userName = user.getUserName() + " " + user.getUserSurname();
	}
%>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	
	
	<title>Muhasebe Uygulaması</title>
	
	<link href="../include/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="../include/css/simple-sidebar.css" rel="stylesheet">
	
	<script src="../include/vendor/jquery/jquery.min.js"></script>
	<script src="../include/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<style>
	.selectedRow {
		background-color:#D0D3D4
	}
	#menuHeaderDiv{
		cursor:pointer
	}
	</style>
	<script>
	var setCookie;
	var getCookie;
	var setFrame;
	var lastPage;
	var currentUserName = "<%=userName%>";
	var sendAjaxMP;
	var exit;
	var getUserMenu;
	var fillUserMenu;
	var loadPage;
	$(document).ready(function(){
		sendAjaxMP = function(jsonreq,url,async,type){
			$.ajax({
				url : url,
				type : type,
				dataType : 'json',
				data : JSON.stringify(jsonreq),
				contentType : 'application/json',
				mimeType : 'application/json',
				async : async,
				success : function(data){
					if(data.includes("redirect_url")){
						window.location.href = data.split(":")[1];
					} else {
						if(jsonreq.params[0] == "get_user_menu"){
							fillUserMenu(data);
						}
					}
					
				},
	            error : function(e){
	            	alert('ERROR '+e.responseText)
	            }
			});
		}
		
		fillUserMenu = function(data){
			for(var i = 0 ; i < data.length ; i++){
				var menuHtml = "<a href='#' onclick='loadPage(\""+data[i].menuSrc+"\");' class='list-group-item list-group-item-action bg-light'>" + data[i].menuLabel + "</a>";
				$('#mainPageMenuDiv').append(menuHtml);
			}
			var exitButton = '<a href="#" onclick="exit();" class="list-group-item list-group-item-action bg-light" style="vertical-align:text-bottom"><font color="#dc3545">Çıkış</font></a>';
			$('#mainPageMenuDiv').append(exitButton);
		}
		
		getUserMenu = function(){
			var jsonreq = new Object();
			jsonreq.params = new Array();
			jsonreq.params[0] = "get_user_menu"
			sendAjaxMP(jsonreq,"/Accounting/AccountingServlet",false,"POST");
		}
		
		$('#currentUserLabel').html(currentUserName);
		
		setCookie = function(cname, cvalue, exdays){
			var d = new Date();
			d.setTime(d.getTime() + (exdays*24*60*60*1000));
			var expires = "expires="+ d.toUTCString();
			document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
		}
		
		exit = function(){
			var jsonreq = new Object();
			jsonreq.params = new Array();
			jsonreq.params[0] = "exit";
			sendAjaxMP(jsonreq,"/Accounting/AccountingServlet",false,"POST");
			window.location.href = "/Accounting/login.jsp";
		}
		
		getCookie = function(cname){
			var name = cname + "=";
			var decodedCookie = decodeURIComponent(document.cookie);
			var ca = decodedCookie.split(';');
			for(var i = 0; i <ca.length; i++) {
				var c = ca[i];
				while (c.charAt(0) == ' ') {
					c = c.substring(1);
				}
				if (c.indexOf(name) == 0) {
				return c.substring(name.length, c.length);
				}
			}
			return "";
		}
		
		loadPage = function(src){
			window.location.href = src;
		}
		
		setFrame = function(src){
			$('#mainDiv').fadeOut(100,function(){
				$('#mainDiv').load(src);
	            $('#mainDiv').fadeIn(1000);
	            setCookie("last_page",src,100);
	        });
		}
		
		/*lastPage = getCookie("last_page");
		if(lastPage.length > 0){
			setFrame(lastPage);
		}*/
		getUserMenu();
	});
	</script>
</head>

<body>
	<div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading" id="menuHeaderDiv" onclick="loadPage('./index.jsp')"><b>AccountingApp</b> </div>
      <div class="list-group list-group-flush" id="mainPageMenuDiv">
      </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="#"><label id="currentUserLabel"></label> <span class="sr-only">(current)</span></a>
            </li>
          </ul>
        </div>
      </nav>