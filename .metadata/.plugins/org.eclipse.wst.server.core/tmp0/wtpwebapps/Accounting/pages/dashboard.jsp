<%@page import="com.accountingapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="../include/headerandmenu.jsp"/>
<link href="../include/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<link href="../include/select2/select2.min.css" rel="stylesheet">
<script src="../include/js/jquery.dataTables.min.js"></script>
<script src="../include/js/dataTables.bootstrap4.min.js"></script>
<script src="../include/select2/select2.min.js"></script>
<script>
var sendAjaxDB;
var getUserMenuTable;
var fillUserMenuTable;
var userMenuTable;
var userRoleTable;
var currentRowDataMenu;
var currentRowDataRoles;
var getUserRoles;
var fillUserRoleTable;
var getRoleNames;
var fillUserRoleSelect;
var rolesTable;
var fillRolesTable;
var currentRowDataRolesTable;
$(document).ready(function(){
	$('#userRolesSelect').select2();
	sendAjaxDB = function(jsonreq,url,async,type){
		$.ajax({
			url : url,
			type : type,
			dataType : 'json',
			data : JSON.stringify(jsonreq),
			contentType : 'application/json',
			mimeType : 'application/json',
			async : async,
			success : function(data){
				if(typeof data == 'string' && data.includes("redirect_url")){
					window.location.href = data.split(":")[1];
				} else {
					if(jsonreq.params[0] == "get_user_menus_with_roles"){
						fillUserMenuTable(data);
					}
					if(jsonreq.params[0] == "get_role_names"){
						fillUserRoleSelect(data);
						fillRolesTable(data);
					}
					if(jsonreq.params[0] == "usermenu_save"){
						if(data != "0"){
							alert("İşlem başarılı");
						}
					}
					if(jsonreq.params[0] == "usermenu_delete"){
						if(data != "0"){
							alert('İşlem başarılı');
						}
					}
					if(jsonreq.params[0] == "get_user_roles"){
						fillUserRoleTable(data);
					}
					if(jsonreq.params[0] == "user_role_save"){
						if(data == "1"){
							alert("İşlem başarılı.");
						}
					}
					if(jsonreq.params[0] == "update_user_role"){
						if(data == "1"){
							alert("İşlem başarılı.");
						}
					}
				}
				
			}
		});
	}
	
	fillRolesTable = function(data){
		if(typeof rolesTable == 'undefined'){
			rolesTable = $('#rolesTable').DataTable({
				"language": {
		            "lengthMenu": "Sayfa başına _MENU_ kayıt",
		            "zeroRecords": "Nothing found - sorry",
		            "info": "_PAGE_ sayfadan _PAGES_ gösteriliyor",
		            "infoEmpty": "Data bulunamadı",
		            "infoFiltered": "(filtered from _MAX_ total records)",
		            "search" : "Arama",
		            "paginate": {
		                "previous": "Geri",
		                "next" : "İleri"
		            }
		        },
				data: data,
				"columns" : [
					{
						data: "id"
					},
					{
						data: "roleName"
					},
					{
						data: "roleCode"
					}
				]
			});
		} else {
			rolesTable.clear();
			rolesTable.rows.add(data);
			rolesTable.draw();
		}
		
		$('#rolesTable tbody').on('click', 'tr', function () {
			if ($(this).hasClass('selectedRow') ) {
	            $(this).removeClass('selectedRow');
	            $('#updateRoleButton').attr("disabled",true);
	        } else {
	        	rolesTable.$('tr.selectedRow').removeClass('selectedRow');
	            $(this).addClass('selectedRow');
	            var data = rolesTable.row(this).data();
	            currentRowDataRolesTable = data;
	            $('#updateRoleButton').attr("disabled",false);
				
	        }
	        
	    });
	}
	getUserRoles = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_user_roles"
		sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	$('#userMenuRoleUpdateBtn').on('click',function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "update_user_role";
		jsonreq.params[1] = $('#userRoleIdInput').val();
		jsonreq.params[2] = $('#userRolesMenuSelect').val();
		sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	});
	
	$('#updateRoleButton').on('click',function(){
		$('#roleIdInput').val(currentRowDataRolesTable.id);
		$('#roleNameInput').val(currentRowDataRolesTable.roleName);
		$('#roleCodeInput').val(currentRowDataRolesTable.roleCode);
		$('#rolesModal').modal('show');
	});
	
	fillUserRoleTable = function(data){
		if(typeof userRoleTable == 'undefined'){
			userRoleTable = $('#rolesMenuTable').DataTable({
				"language": {
		            "lengthMenu": "Sayfa başına _MENU_ kayıt",
		            "zeroRecords": "Nothing found - sorry",
		            "info": "_PAGE_ sayfadan _PAGES_ gösteriliyor",
		            "infoEmpty": "Data bulunamadı",
		            "infoFiltered": "(filtered from _MAX_ total records)",
		            "search" : "Arama",
		            "paginate": {
		                "previous": "Geri",
		                "next" : "İleri"
		            }
		        },
				data: data,
				"columns" : [
					{
						data: "id"
					},
					{
						data: "userMail"
					},
					{
						data: "roleName"
					}
				]
			});
		} else {
			userRoleTable.clear();
			userRoleTable.rows.add(data);
			userRoleTable.draw();
		}
		
		$('#rolesMenuTable tbody').on('click', 'tr', function () {
			if ($(this).hasClass('selectedRow') ) {
	            $(this).removeClass('selectedRow');
	            $('#updateUserRoleButton').attr("disabled",true);
	        } else {
	        	userRoleTable.$('tr.selectedRow').removeClass('selectedRow');
	            $(this).addClass('selectedRow');
	            var data = userRoleTable.row(this).data();
	            currentRowDataRoles = data;
	            $('#userRoleIdInput').val(currentRowDataRoles.id);
	            $('#userRoleUsernameLabel').text(currentRowDataRoles.userMail);
	            $('#userRolesMenuSelect').val(currentRowDataRoles.userRole);
	            $('#updateUserRoleButton').attr("disabled",false);
				
	        }
	        
	    });
	}
	
	fillUserRoleSelect = function(data){
		for(var i = 0 ; i < data.length ; i++){
			var selectOption = "<option value='"+data[i].roleCode+"'>"+data[i].roleName+"</option>";
			$('#userRolesSelect').append(selectOption);
			$('#userRolesMenuSelect').append(selectOption);
		}
	}
	
	getUserMenuTable = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_user_menus_with_roles"
		sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",true,"POST");
	}
	
	getRoleNames = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_role_names";
		sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	fillUserMenuTable = function(data){
		if(typeof userMenuTable == 'undefined'){
			userMenuTable = $('#userMenuTable').DataTable({
				"language": {
		            "lengthMenu": "Sayfa başına _MENU_ kayıt",
		            "zeroRecords": "Nothing found - sorry",
		            "info": "_PAGE_ sayfadan _PAGES_ gösteriliyor",
		            "infoEmpty": "Data bulunamadı",
		            "infoFiltered": "(filtered from _MAX_ total records)",
		            "search" : "Arama",
		            "paginate": {
		                "previous": "Geri",
		                "next" : "İleri"
		            }
		        },
				data: data,
				"columns" : [
					{
						data: "id"
					},
					{
						data: "menuLabel"
					},
					{
						data: "menuSrc"
					}
				]
			});
		} else {
			userMenuTable.clear();
			userMenuTable.rows.add(data);
			userMenuTable.draw();
		}
		
		$('#userMenuTable tbody').on('click', 'tr', function () {
			if ($(this).hasClass('selectedRow') ) {
	            $(this).removeClass('selectedRow');
	            $('#updateMenuBtn').attr("disabled",true);
	            $('#deleteMenuBtn').attr("disabled",true);
	        } else {
	        	userMenuTable.$('tr.selectedRow').removeClass('selectedRow');
	            $(this).addClass('selectedRow');
	            var data = userMenuTable.row(this).data();
	            currentRowDataMenu = data;
	            $('#updateMenuBtn').attr("disabled",false);
	            $('#deleteMenuBtn').attr("disabled",false);
				
	        }
	        
	    });
	}
	
	$('#userMenuSaveButton').on('click',function(){
		var menuObj = new Object();
		menuObj.roles = new Array();
		if($('#userMenuIdInput').val().length > 0){
			menuObj.id = $('#userMenuIdInput').val();
		}
		menuObj.menuLabel = $('#menuLabelInput').val();
		menuObj.menuSrc = $('#menuSrcInput').val();
		menuObj.roles = $('#userRolesSelect').val();
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "usermenu_save";
		jsonreq.params[1] = JSON.stringify(menuObj);
		sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	});
	
	$('#newMenuBtn').on('click',function(){
		$('#userMenuIdInput').val('');
		$('#menuLabelInput').val('');
		$('#menuSrcInput').val('');
		$('#userRolesSelect').val('').trigger('change');
		$('#userMenuModal').modal('show');
		
	});
	
	$('#updateMenuBtn').on('click',function(){
		$('#userMenuIdInput').val(currentRowDataMenu.id);
		$('#menuLabelInput').val(currentRowDataMenu.menuLabel);
		$('#menuSrcInput').val(currentRowDataMenu.menuSrc);
		$('#userRolesSelect').val(currentRowDataMenu.roles).trigger('change');
		$('#userMenuModal').modal('show');
		console.log(currentRowDataMenu)
	});
	
	$('#deleteMenuBtn').on('click',function(){
		if(confirm(currentRowDataMenu.menuLabel + " silinecek. Emin misiniz?")){
			var jsonreq = new Object();
			jsonreq.params = new Array();
			jsonreq.params[0] = "usermenu_delete";
			jsonreq.params[1] = currentRowDataMenu.id
			sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
		}
	});
	
	$('#updateUserRoleButton').on('click',function(){
		$('#userRoleIdInput').val(currentRowDataRoles.id);
		$('#userRoleModal').modal('show');
	});
	
	$('#userMenuRoleSaveBtn').on('click',function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "user_role_save";
		var roleObj = new Object();
		if($('#roleIdInput').val().length > 0){
			roleObj.id = $('#roleIdInput').val();
		}
		roleObj.roleName = $('#roleNameInput').val();
		roleObj.roleCode = $('#roleCodeInput').val();
		jsonreq.params[1] = JSON.stringify(roleObj);
		sendAjaxDB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	});
	
	$('#insertNewRoleButton').on('click',function(){
		$('#rolesModal').modal('show');
	})
	
	getUserMenuTable();
	getRoleNames();
	getUserRoles();
});
</script>
<div class="container-fluid">
<!-- MAIN CONTENT START -->
<br>
<div style="margin-left: 10px">
	<ul class="nav nav-tabs" role="tablist">
		<li class="nav-item waves-effect waves-light">
			<a class="nav-link active"  data-toggle="tab" href="#menuOperationsTab" role="tab" aria-selected="true">Menü İşlemleri</a>
		</li>
		<li class="nav-item waves-effect waves-light">
			<a class="nav-link" data-toggle="tab" href="#rolesMenuTab" role="tab" aria-selected="false">Yetki İşlemleri</a>
		</li>
    </ul>
    <div class="tab-content" id="menuOperationsTabContent">
    	<div class="tab-pane fade active show" id="menuOperationsTab" role="tabpanel" >
    		<br>
			<div class="card">
				<div class="card-header">
    				Menüler
  				</div>
				<div class="card-body">
					<table id="userMenuTable" class="table table-hover" style="width:100%">
						<thead>
							<tr>
								<th>ID</th>
								<th>Menü Adı</th>
								<th>Menü Adresi</th>
							</tr>
						</thead>
					</table>
					<div class="btn-group" role="group">
						<button class="btn btn-success btn-sm" id="newMenuBtn">Yeni Kayıt</button>
						<button class="btn btn-primary btn-sm" id="updateMenuBtn" disabled>Düzenle</button>
						<button class="btn btn-danger btn-sm" id="deleteMenuBtn" disabled>Sil</button>
					</div>
				</div>
			</div>
    	</div>
    	<div class="tab-pane fade" id="rolesMenuTab" role="tabpanel" >
    		<br>
    		<div class="row">
    			<div class="col-sm-6">
    				<div class="card">
						<div class="card-header">
    						Yetkiler
  						</div>
						<div class="card-body">
							<table id="rolesMenuTable" class="table table-hover" style="width:100%">
								<thead>
									<tr>
										<th>ID</th>
										<th>Kullanıcı Adı</th>
										<th>Kullanıcı Rolü</th>
									</tr>
								</thead>
							</table>
							<div class="btn-group" role="group">
								<button class="btn btn-primary btn-sm" id="updateUserRoleButton" disabled>Düzenle</button>
							</div>
						</div>
					</div>
    			</div>
    			<div class="col-sm-6">
    				<div class="card">
						<div class="card-header">
    						Roller
  						</div>
						<div class="card-body">
							<table id="rolesTable" class="table table-hover" style="width:100%">
								<thead>
									<tr>
										<th>ID</th>
										<th>Rol Adı</th>
										<th>Rol Kodu</th>
									</tr>
								</thead>
							</table>
							<div class="btn-group" role="group">
								<button class="btn btn-success btn-sm" id="insertNewRoleButton">Yeni Rol Tanımla</button>
								<button class="btn btn-primary btn-sm" id="updateRoleButton" disabled>Düzenle</button>
							</div>
						</div>
					</div>
    			</div>
    		</div>
    	</div>
    </div>
	
	<div class="modal fade" id="userMenuModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title" id="userMenuModalLabel">Müşteri Ekleme</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="userMenuIdInput">
					<div class="row">
						<div class="col-sm-4">Menü Adı</div>
						<div class="col-sm-8"><input class="form-controller" id="menuLabelInput" style="width:100%"></div>
					</div>
						<br>
					<div class="row">
						<div class="col-sm-4">Menü Adresi</div>
						<div class="col-sm-8"><input class="form-controller" id="menuSrcInput" style="width:100%"></div>
					</div>
						<br>
					<div class="row">
						<div class="col-sm-4">Yetkisi Olanlar</div>
						<div class="col-sm-8">
							<select class="js-example-basic-multiple" id="userRolesSelect" multiple="multiple" style="width:100%">
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="userMenuSaveButton">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="userRoleModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title" id="userRoleUsernameLabel">Rol Güncelleme</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="userRoleIdInput">
					<div class="row">
						<div class="col-sm-12">
							<select style="width:100%" id="userRolesMenuSelect" class="form-controller form-select form-select-lg mb-3">
							
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="userMenuRoleUpdateBtn">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="rolesModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title" id="userRoleUsernameLabel">Rol Tanımlama</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="roleIdInput">
					<div class="row">
						<div class="col-sm-4">Rol Adı:</div>
						<div class="col-sm-8"><input id="roleNameInput" class="form-control"/></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">Rol Kodu:</div>
						<div class="col-sm-8"><input type="number" id="roleCodeInput" class="form-control"/></div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="userMenuRoleSaveBtn">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- MAIN CONTENT START -->
<jsp:include page="../include/footer.jsp"/>