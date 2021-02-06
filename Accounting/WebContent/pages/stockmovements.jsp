<%@page import="com.accountingapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/headerandmenu.jsp"/>
<div class="container-fluid">
<!-- MAIN CONTENT START -->
<link href="../include/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<link href="../include/select2/select2.min.css" rel="stylesheet">
<script src="../include/js/jquery.dataTables.min.js"></script>
<script src="../include/js/dataTables.bootstrap4.min.js"></script>
<script src="../include/select2/select2.min.js"></script>
<script>
		var sendAjaxSI;
		var getInventoryGroups;
		var getInventoryItems;
		var fillInventoryGroupTable;
		var fillInventoryItemTable;
		var inventoryGroupTable;
		var openUpdateModal;
		var currentRowDataInventoryGroup;
		var deleteBusinessPartner;
		var inventoryItemTable;
		var currentRowDataInventoryItem;
		var fillInventoryItemSelect;
		var getStockMovements;
		var stockMovementsTable;
		var fillStockMovementsTable;
		$(document).ready(function(){
			$('#inventoryItemGroupCodeSelect').select2();
			sendAjaxSI = function(jsonreq,url,async,type){
				$.ajax({
					url : url,
					type : type,
					dataType : 'json',
					data : JSON.stringify(jsonreq),
					contentType : 'application/json',
					mimeType : 'application/json',
					async : async,
					success : function(data){
						if(typeof data == "string" && data.includes("redirect_url")){
							window.location.href = data.split(":")[1];
						} else {
							if(jsonreq.params[0] == "get_inventory_groups"){
								fillInventoryGroupTable(data);
								fillInventoryItemSelect(data);
							}
							
							if(jsonreq.params[0] == "delete_inventory_group"){
								if(data == "1"){
									alert("Silme işlemi başarılı!")
								}
							}
							
							if(jsonreq.params[0] == "save_inventory_group"){
								if(data == "1"){
									alert('İşlem başarılı!')
								}
							}
							
							if(jsonreq.params[0] == "get_inventory_items"){
								fillInventoryItemTable(data);
							}
							
							if(jsonreq.params[0] == "delete_inventory_item"){
								if(data == "1"){
									alert("Silme işlemi başarılı!")
								}
							}
							
							if(jsonreq.params[0] == "save_inventory_item"){
								if(data == "1"){
									alert('İşlem başarılı!')
								}
							}
							
							if(jsonreq.params[0] == "get_stock_movements"){
								fillStockMovementsTable(data);
							}
						}
					},error : function(e){
						console.log(e);
		            	alert('ERROR '+e.responseText)
		            }
				});
			}
			
			fillStockMovementsTable = function(data){
				if(typeof stockMovementsTable == 'undefined'){
					stockMovementsTable = $('#stockMovementsTable').DataTable({
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
								data: "bpName"
							},
							{
								data: "itemGroupCode"
							},
							{
								data: "itemGroupName"
							},
							{
								data: "itemCode"
							},
							{
								data: "itemName"
							}
							,
							{
								data: "itemQty"
							}
							,
							{
								data: null,
								"render" : function(data,type,full,meta) {
									if(data.operationType == "0"){
										return "<font color='red'>Çıktı</font>";
									} else if(data.operationType == "1"){
										return "<font color='green'>Girdi</font>"
									} else {
										return "";
									}
								}
							}
							,
							{
								data: "operationTime"
							}
						]
					});
				} else {
					stockMovementsTable.clear();
					stockMovementsTable.rows.add(data);
					stockMovementsTable.draw();
				}
			}
			
			fillInventoryItemSelect = function(data){
				$('#inventoryItemGroupCodeSelect').empty();
				for(var i = 0 ; i < data.length ; i++){
					var options = "<option value='"+data[i].id+"' selected>"+data[i].groupName+"</option>";
					$('#inventoryItemGroupCodeSelect').append(options);
				}
			}
			
			getStockMovements = function(){
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "get_stock_movements";
				sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
			}
			
			fillInventoryItemTable = function(data){
				if(typeof inventoryItemTable == 'undefined'){
					inventoryItemTable = $('#inventoryItemTable').DataTable({
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
								data: "itemId"
							},
							{
								data: "itemName"
							},
							{
								data: "itemCode"
							},
							{
								data: "groupName"
							},
							{
								data: "itemQty"
							},
							{
								data: "itemPrice"
							}
						]
					});
				} else {
					inventoryItemTable.clear();
					inventoryItemTable.rows.add(data);
					inventoryItemTable.draw();
				}
				
				$('#inventoryItemTable tbody').on('click', 'tr', function () {
					if ($(this).hasClass('selectedRow') ) {
			            $(this).removeClass('selectedRow');
			            $('#updateInventoryItemButton').attr("disabled",true);
			            $('#deleteInventoryItemButton').attr("disabled",true);
			        } else {
			        	inventoryItemTable.$('tr.selectedRow').removeClass('selectedRow');
			            $(this).addClass('selectedRow');
			            var data = inventoryItemTable.row(this).data();
			            currentRowDataInventoryItem = data;
			            $('#updateInventoryItemButton').attr("disabled",false);
			            $('#deleteInventoryItemButton').attr("disabled",false);
						
			        }
			        
			    });
			}
			
			fillInventoryGroupTable = function(data){
				if(typeof inventoryGroupTable == 'undefined'){
					inventoryGroupTable = $('#inventoryGroupTable').DataTable({
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
								data: "groupName"
							},
							{
								data: "groupCode"
							}
						]
					});
				} else {
					inventoryGroupTable.clear();
					inventoryGroupTable.rows.add(data);
					inventoryGroupTable.draw();
				}
				
				$('#inventoryGroupTable tbody').on('click', 'tr', function () {
					if ($(this).hasClass('selectedRow') ) {
			            $(this).removeClass('selectedRow');
			            $('#updateInventoryGroup').attr("disabled",true);
			            $('#deleteInventoryGroup').attr("disabled",true);
			        } else {
			        	inventoryGroupTable.$('tr.selectedRow').removeClass('selectedRow');
			            $(this).addClass('selectedRow');
			            var data = inventoryGroupTable.row(this).data();
			            currentRowDataInventoryGroup = data;
			            $('#updateInventoryGroup').attr("disabled",false);
			            $('#deleteInventoryGroup').attr("disabled",false);
						
			        }
			        
			    });
			}
			
			getInventoryGroups = function(){
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "get_inventory_groups";
				sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
			}
			
			getInventoryItems = function(){
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "get_inventory_items";
				sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
			}
			
			$('#newInventoryGroup').on('click',function(){
				$('#inventoryGroupIdInput').val('');
				$('#inventoryGroupNameInput').val('');
				$('#inventoryGroupCodeInput').val('');
				$('#inventoryGroupModal').modal('show');
			});
			
			$('#updateInventoryGroup').on('click',function(){
				$('#inventoryGroupIdInput').val(currentRowDataInventoryGroup.id);
				$('#inventoryGroupNameInput').val(currentRowDataInventoryGroup.groupName);
				$('#inventoryGroupCodeInput').val(currentRowDataInventoryGroup.groupCode);
				$('#inventoryGroupModal').modal('show');
			});
			
			$('#deleteInventoryGroup').on('click',function(){
				if(confirm(currentRowDataInventoryGroup.groupName + " silinecek. Emin misiniz?")){
					var jsonreq = new Object();
					jsonreq.params = new Array();
					jsonreq.params[0] = "delete_inventory_group";
					jsonreq.params[1] = currentRowDataInventoryGroup.id;
					sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
				}
			});
			
			$('#inventoryGroupSaveButton').on('click',function(){
				var groupObj = new Object();
				if($('#inventoryGroupIdInput').val().length > 0){
					groupObj.id = $('#inventoryGroupIdInput').val();
				}
				groupObj.groupName = $('#inventoryGroupNameInput').val();
				groupObj.groupCode = $('#inventoryGroupCodeInput').val();
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "save_inventory_group";
				jsonreq.params[1] = JSON.stringify(groupObj);
				sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
			});
			
			$('#newInventoryItemButton').on('click',function(){
				$('#inventoryItemIdInput').val('');
				$('#inventoryItemCodeInput').val('');
				$('#inventoryItemNameInput').val('');
				$('#inventoryItemGroupCodeSelect').prop("selectedIndex", 0).trigger('change');
				$('#inventoryItemQtyInput').val('');
				$('#inventoryItemPriceInput').val('');
				$('#inventoryItemModal').modal('show');
			});
			
			$('#updateInventoryItemButton').on('click',function(){
				$('#inventoryItemIdInput').val(currentRowDataInventoryItem.itemId);
				$('#inventoryItemCodeInput').val(currentRowDataInventoryItem.itemCode);
				$('#inventoryItemNameInput').val(currentRowDataInventoryItem.itemName);
				$('#inventoryItemGroupCodeSelect').val(currentRowDataInventoryItem.itemGroup).trigger('change');
				$('#inventoryItemQtyInput').val(currentRowDataInventoryItem.itemQty);
				$('#inventoryItemPriceInput').val(currentRowDataInventoryItem.itemPrice);
				$('#inventoryItemModal').modal('show');
			});
			
			$('#deleteInventoryItemButton').on('click',function(){
				if(confirm(currentRowDataInventoryItem.itemName + " silinecek. Emin misiniz?")){
					var jsonreq = new Object();
					jsonreq.params = new Array();
					jsonreq.params[0] = "delete_inventory_item";
					jsonreq.params[1] = currentRowDataInventoryItem.itemId;
					sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
				}
			});
			
			$('#inventoryItemSaveButton').on('click',function(){
				var itemObj = new Object();
				if($('#inventoryItemIdInput').val().length > 0){
					itemObj.itemId = $('#inventoryItemIdInput').val();
				}
				itemObj.itemCode = $('#inventoryItemCodeInput').val();
				itemObj.itemName = $('#inventoryItemNameInput').val();
				itemObj.itemGroup = $('#inventoryItemGroupCodeSelect').val();
				itemObj.itemQty = $('#inventoryItemQtyInput').val();
				itemObj.itemPrice = $('#inventoryItemPriceInput').val().replace(',','.');
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "save_inventory_item";
				jsonreq.params[1] = JSON.stringify(itemObj);
				sendAjaxSI(jsonreq,"/Accounting/AccountingServlet",false,"POST");
			});
			getInventoryItems();
			getInventoryGroups();
			getStockMovements();
		});
	</script>
	<br>
	<div style="margin-left:10px">
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item waves-effect waves-light">
				<a class="nav-link active"  data-toggle="tab" href="#inventoryListTab" role="tab" aria-selected="true">Envanter İşlemleri</a>
			</li>
			<li class="nav-item waves-effect waves-light">
				<a class="nav-link" data-toggle="tab" href="#inventoryMovements" role="tab" aria-selected="false">Stok Hareketleri</a>
			</li>
    	</ul>
	</div>
	<div class="tab-content">
		<div class="tab-pane fade active show" id="inventoryListTab" role="tabpanel">
			<br>
			<div class="card">
				<div class="card-header">
    				Envanter Grupları
  				</div>
				<div class="card-body">
					<table id="inventoryGroupTable" class="table table-hover" style="width:100%">
						<thead>
							<tr>
								<th>ID</th>
								<th>Grup Adı</th>
								<th>Grup Kodu</th>
							</tr>
						</thead>
					</table>
					<div class="btn-group" role="group">
						<button class="btn btn-success btn-sm" id="newInventoryGroup">Yeni Kayıt</button>
						<button class="btn btn-primary btn-sm" id="updateInventoryGroup" disabled>Düzenle</button>
						<button class="btn btn-danger btn-sm" id="deleteInventoryGroup" disabled>Sil</button>
					</div>
				</div>
			</div>
			<br>
			<div class="card">
				<div class="card-header">
    				Envanter Listesi
  				</div>
				<div class="card-body">
					<table id="inventoryItemTable" class="table table-hover" style="width:100%">
						<thead>
							<tr>
								<th>ID</th>
								<th>Ürün Adı</th>
								<th>Ürün Kodu</th>
								<th>Ürün Grubu</th>
								<th>Ürün Adedi</th>
								<th>Ürün Fiyatı</th>
							</tr>
						</thead>
					</table>
					<div class="btn-group" role="group">
						<button class="btn btn-success btn-sm" id="newInventoryItemButton">Yeni Kayıt</button>
						<button class="btn btn-primary btn-sm" id="updateInventoryItemButton" disabled>Düzenle</button>
						<button class="btn btn-danger btn-sm" id="deleteInventoryItemButton" disabled>Sil</button>
					</div>
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="inventoryMovements" role="tabpanel">
			<br>
			<div class="card">
				<div class="card-header">
    				Hareketler
  				</div>
				<div class="card-body">
					<table id="stockMovementsTable" class="table table-hover" style="width:100%">
						<thead>
							<tr>
								<th>ID</th>
								<th>Müşteri</th>
								<th>Ürün Grup Kodu</th>
								<th>Ürün Grubu</th>
								<th>Ürün Kodu</th>
								<th>Ürün Adı</th>
								<th>Miktar</th>
								<th>İşlem</th>
								<th>Tarih</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="inventoryGroupModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title">Envanter Grubu Kayıt</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="inventoryGroupIdInput">
					<div class="row">
						<div class="col-sm-4">
							Grup Adı:
						</div>
						<div class="col-sm-8">
							<input class="form-controller" id="inventoryGroupNameInput" style="width:100%"/>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">
							Grup Kodu:
						</div>
						<div class="col-sm-8">
							<input class="form-controller" id="inventoryGroupCodeInput" style="width:100%"/>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="inventoryGroupSaveButton">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="inventoryItemModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title">Envanter Kayıt</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="inventoryItemIdInput">
					<div class="row">
						<div class="col-sm-4">
							Ürün:
						</div>
						<div class="col-sm-8">
							<input style="width:100%" class="form-controller" id="inventoryItemNameInput"/>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">
							Ürün Kodu:
						</div>
						<div class="col-sm-8">
							<input style="width:100%" class="form-controller" id="inventoryItemCodeInput"/>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">
							Ürün Grubu:
						</div>
						<div class="col-sm-8">
							<select style="width:100%" class="form-select" id="inventoryItemGroupCodeSelect"></select>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">
							Ürün Adedi:
						</div>
						<div class="col-sm-8">
							<input style="width:100%" type="number" class="form-controller" id="inventoryItemQtyInput"/>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">
							Ürün Fiyatı:
						</div>
						<div class="col-sm-8">
							<input style="width:100%" type="number" class="form-controller" id="inventoryItemPriceInput"/>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="inventoryItemSaveButton">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- MAIN CONTENT START -->
<jsp:include page="../include/footer.jsp"/>