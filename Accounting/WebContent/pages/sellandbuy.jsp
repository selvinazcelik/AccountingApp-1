<%@page import="com.accountingapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="../include/headerandmenu.jsp"/>
<link href="../include/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<link href="../include/select2/select2.min.css" rel="stylesheet">
<script src="../include/js/jquery.dataTables.min.js"></script>
<script src="../include/js/dataTables.bootstrap4.min.js"></script>
<script src="../include/select2/select2.min.js"></script>
<script>
var sendAjaxSB;
var getBusinessPartners;
var getInventoryItems;
var fillInventoryItems;
var fillBusinessPartners;
var currentItemInfos;
var getItemPrice;
var fillCashBoxSelect;
var getCashBoxes;
var getLastSell;
var getSell;
var getBuy;
var getLastBuy;
var fillBuyingOperationsTable;
var fillSellingOperationsTable;
var buyingTable;
var sellingTable;
$(document).ready(function(){
	$('#businessPartnersSelect').select2();
	$('#inventorySelect').select2();
	$('#operationCashBox').select2();
	sendAjaxSB = function(jsonreq,url,async,type){
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
					if(jsonreq.params[0] == "get_all_bp"){
						fillBusinessPartners(data);
					}
					
					if(jsonreq.params[0] == "get_inventory_items"){
						currentItemInfos = data;
						fillInventoryItems(data);
					}
					
					if(jsonreq.params[0] == "get_cash_boxes"){
						fillCashBoxSelect(data);
					}
					if(jsonreq.params[0] == "save_selling_operation"){
						if(data == "1"){
							alert('İşlem başarılı');
						} else if(data == "2"){
							alert('Miktarı karşılayacak yeterli stok bulunmamaktadır.');
						} else if(data == "3"){
							alert('İşlem başarısız');
						} else if(data == "4"){
							alert('İşlem başarısız');
						} else if(data == "5"){
							alert('İşlem başarısız');
						}
					}
					if(jsonreq.params[0] == "save_buying_operation"){
						if(data == "1"){
							alert('İşlem başarılı');
						} else if(data == "2"){
							alert('İşlem başarılı');
						} else if(data == "3"){
							alert('İşlemi gerçekleştirmek için kasanızda yeterli hacim bulunmamaktadır.');
						} else if(data == "4"){
							alert('İşlem başarısız');
						} else if(data == "5"){
							alert('İşlem başarısız');
						}
					}
					if(jsonreq.params[0] == "get_last_operation"){
						if(jsonreq.params[1] == "1"){
							$('#sellLabelBp').text(data.bpName);
							$('#sellLabelItem').text(data.itemName);
							$('#sellLabelQty').text(data.itemQty);
							$('#sellLabelBalance').text(data.totalPrice);
							$('#sellLabelTime').text(data.operationTime);
						}
						if(jsonreq.params[1] == "0"){
							$('#buyLabelBp').text(data.bpName);
							$('#buyLabelItem').text(data.itemName);
							$('#buyLabelQty').text(data.itemQty);
							$('#buyLabelBalance').text(data.totalPrice);
							$('#buyLabelTime').text(data.operationTime);
						}
					}
					
					if(jsonreq.params[0] == "get_operations"){
						if(jsonreq.params[1] == "1"){
							fillBuyingOperationsTable(data);
						}
						if(jsonreq.params[1] == "0"){
							fillSellingOperationsTable(data);
						}
					}
				}
			},error : function(e){
				console.log(e);
            	alert('ERROR '+e.responseText)
            }
		});
	}
	
	fillBuyingOperationsTable = function(data){
		if(typeof buyingTable == 'undefined'){
			buyingTable = $('#buyingTable').DataTable({
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
						data: null,
						"render" : function(data,type,full,meta) {
							if(data.operationType == "0"){
								return "Satış"
							} else if(data.operationType == "1"){
								return "Alış";
							} else {
								return "";
							}
							
						}
					},
					{
						data: "itemName"
					},
					{
						data: "itemQty"
					},
					{
						data: "totalPrice"
					},
					{
						data: "operationTime"
					}
				]
			});
		} else {
			buyingTable.clear();
			buyingTable.rows.add(data);
			buyingTable.draw();
		}
	}
	
	fillSellingOperationsTable = function(data){
		if(typeof sellingTable == 'undefined'){
			sellingTable = $('#sellingTable').DataTable({
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
						data: null,
						"render" : function(data,type,full,meta) {
							if(data.operationType == "0"){
								return "Satış"
							} else if(data.operationType == "1"){
								return "Alış";
							} else {
								return "";
							}
							
						}
					},
					{
						data: "itemName"
					},
					{
						data: "itemQty"
					},
					{
						data: "totalPrice"
					},
					{
						data: "operationTime"
					}
				]
			});
		} else {
			sellingTable.clear();
			sellingTable.rows.add(data);
			sellingTable.draw();
		}
	}
	
	getSell = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_operations";
		jsonreq.params[1] = "0"
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	getBuy = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_operations";
		jsonreq.params[1] = "1"
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	getLastSell = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_last_operation";
		jsonreq.params[1] = "0"
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	getLastBuy = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_last_operation";
		jsonreq.params[1] = "1"
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	fillCashBoxSelect = function(data){
		$('[name=operationCashBoxnm]').empty();
		for(var i = 0 ; i < data.length ; i++){
			var option = "<option value="+data[i].id+">"+data[i].cashBoxName + " - " + data[i].cashBoxBalance +"</option>";
			$('[name=operationCashBoxnm]').append(option);
		}
		$('[name=operationCashBoxnm]').prop("selectedIndex", 0).trigger('change');
	}
	
	getCashBoxes = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_cash_boxes";
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	getItemPrice = function(itemId){
		for(var i = 0 ; i < currentItemInfos.length ; i++){
			if(currentItemInfos[i].itemId == itemId){
				return currentItemInfos[i].itemPrice;
			}
		}
	}
	
	fillBusinessPartners = function(data){
		$('[name=businessPartnersSelectnm]').empty();
		for(var i = 0 ; i < data.length ; i++){
			var options = "<option value='"+data[i].id+"'>"+data[i].partnerName+"</option>";
			$('[name=businessPartnersSelectnm]').append(options);
		}
		$('[name=businessPartnersSelectnm]').prop("selectedIndex", 0).trigger('change');
	}
	
	fillInventoryItems = function(data){
		$('[name=inventorySelectnm]').empty();
		for(var i = 0 ; i < data.length ; i++){
			var options = "<option value='"+data[i].itemId+"'>"+data[i].itemName+"</option>";
			$('[name=inventorySelectnm]').append(options);
		}
		$('[name=inventorySelectnm]').prop("selectedIndex", 0).trigger('change');
	}
	
	getInventoryItems = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_inventory_items";
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	getBusinessPartners = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_all_bp";
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",true,"POST");
	}
	
	$('#itemQtyInput').on('keyup',function(){
		var totalInput = $('#itemQtyInput').val() * getItemPrice($('#inventorySelect').val());
		$('#totalPriceInput').val(totalInput);
	});
	
	$('#itemQtyInputBuy').on('keyup',function(){
		var totalInput = $('#itemQtyInputBuy').val() * $('#unitPriceInputBuy').val();
		$('#totalPriceInputBuy').val(totalInput);
	});
	
	$('#inventorySelect').on('change',function(){
		$('#unitPriceInput').val(getItemPrice($('#inventorySelect').val()));
	});
	
	$('#newSellButton').on('click',function(){
		$('#sellModal').modal('show');
	});
	
	$('#newBuyButton').on('click',function(){
		$('#buyModal').modal('show');
	});
	
	$('#sellItemButton').on('click',function(){
		var sellingObj = new Object();
		sellingObj.businessPartner = $('[name=businessPartnersSelectnm]').val();
		sellingObj.item = $('[name=inventorySelectnm]').val();
		sellingObj.itemQty = $('#itemQtyInput').val();
		sellingObj.cashBox = $('[name=operationCashBoxnm]').val();
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "save_selling_operation";
		jsonreq.params[1] = JSON.stringify(sellingObj);
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",true,"POST");
	});
	
	$('#buyItemButton').on('click',function(){
		var sellingObj = new Object();
		sellingObj.businessPartner = $('[name=businessPartnersSelectnm]').val();
		sellingObj.item = $('[name=inventorySelectnm]').val();
		sellingObj.itemQty = $('#itemQtyInputBuy').val();
		sellingObj.cashBox = $('[name=operationCashBoxnm]').val();
		sellingObj.itemPrice = $('#unitPriceInputBuy').val();
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "save_buying_operation";
		jsonreq.params[1] = JSON.stringify(sellingObj);
		sendAjaxSB(jsonreq,"/Accounting/AccountingServlet",true,"POST");
	});
	
	getBusinessPartners();
	getInventoryItems();
	getCashBoxes();
	getLastSell();
	getLastBuy();
	getSell();
	getBuy();
});
</script>
<div class="container-fluid">
<!-- MAIN CONTENT START -->
	<div style="margin-left:10px">
	<br>
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item waves-effect waves-light">
				<a class="nav-link active"  data-toggle="tab" href="#buyingOperationsTab" role="tab" aria-selected="true">Alış İşlemleri</a>
			</li>
			<li class="nav-item waves-effect waves-light">
				<a class="nav-link" data-toggle="tab" href="#sellingOperationstab" role="tab" aria-selected="false">Satış İşlemleri</a>
			</li>
		</ul>
    	<div class="tab-content">
    		<div class="tab-pane fade active show" id="buyingOperationsTab" role="tabpanel" >
    			<br>
				<div class="card">
					<div class="card-header">
    					Satın Alma Geçmişi
  					</div>
					<div class="card-body">
						<table id="buyingTable" class="table table-hover" style="width:100%">
							<thead>
								<tr>
									<th>ID</th>
									<th>Müşteri Adı</th>
									<th>Yapılan İşlem</th>
									<th>Ürün Adı</th>
									<th>Ürün Adedi</th>
									<th>Toplam Tutar</th>
									<th>Tarih</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
    		</div>
    		<div class="tab-pane fade" id="sellingOperationstab" role="tabpanel" >
    			<br>
				<div class="card">
					<div class="card-header">
    					Satış Geçmişi
  					</div>
					<div class="card-body">
						<table id="sellingTable" class="table table-hover" style="width:100%">
							<thead>
								<tr>
									<th>ID</th>
									<th>Müşteri Adı</th>
									<th>Yapılan İşlem</th>
									<th>Ürün Adı</th>
									<th>Ürün Adedi</th>
									<th>Toplam Tutar</th>
									<th>Tarih</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
    		</div>
		</div>
    	<br>
		<div class="card">
			<div class="card-header">
				İşlemler
			</div>
			<div class="card-body">
				<div align="center" style="text-align:center">
					<div class="btn-group" role="group">
						<button class="btn btn-success " id="newSellButton">Satış Gir</button>
						<button class="btn btn-warning " id="newBuyButton"><font color="white">Alış Gir</font></button>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-6">
						<h6>Son Satış</h6>
						<br>
						<b>Kime: </b> <label id="buyLabelBp"></label>
						<br>
						<b>Ürün: </b><label id="buyLabelItem"></label>
						<br>
						<b>Miktar: </b><label id="buyLabelQty"></label>
						<br>
						<b>Tutar: </b><label id="buyLabelBalance"></label>
						<br>
						<b>Tarih: </b><label id="buyLabelTime"></label>
					</div>
					<div class="col-sm-6" style="text-align:right;">
						<h6>Son Alış</h6>
						<br>
						<b>Kimden: </b> <label id="sellLabelBp"></label>
						<br>
						<b>Ürün: </b><label id="sellLabelItem"></label>
						<br>
						<b>Miktar: </b><label id="sellLabelQty"></label>
						<br>
						<b>Tutar: </b><label id="sellLabelBalance"></label>
						<br>
						<b>Tarih: </b><label id="sellLabelTime"></label>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="sellModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #ECF0F1">
						<h5 class="modal-title">Satış Ekranı</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-4">Müşteri:</div>
							<div class="col-sm-8">
								<select name="businessPartnersSelectnm" id="businessPartnersSelect" style="width:100%" class="form-select"></select>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-4">Ürün:</div>
							<div class="col-sm-4">
								<select name="inventorySelectnm" id="inventorySelect" style="width:100%" class="form-select"></select>
							</div>
							<div class="col-sm-2">
								Birim Fiyat: 
							</div>
							<div class="col-sm-2">
								<input class="form-control" id="unitPriceInput" disabled>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-4">Miktar:</div>
							<div class="col-sm-4">
								<input class="form-controller" id="itemQtyInput" style="width:100%">
							</div>
							<div class="col-sm-2">
								Toplam Fiyat: 
							</div>
							<div class="col-sm-2">
								<input class="form-control" id="totalPriceInput" disabled>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-4">İşlem Yapılacak Kasa:</div>
							<div class="col-sm-8">
								<select name="operationCashBoxnm" id="operationCashBox" style="width:100%" class="form-select"></select>
							</div>
						</div>
					</div>
					<div class="modal-footer" style="background-color: #ECF0F1">
						<button class="btn btn-sm btn-success" id="sellItemButton">Satış Yap</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="buyModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #ECF0F1">
						<h5 class="modal-title">Alış Ekranı</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-4">Müşteri:</div>
							<div class="col-sm-8">
								<select name="businessPartnersSelectnm" id="businessPartnersSelectBuy" style="width:100%" class="form-select"></select>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-4">Ürün:</div>
							<div class="col-sm-4">
								<select name="inventorySelectnm" id="inventorySelectBuy" style="width:100%" class="form-select"></select>
							</div>
							<div class="col-sm-2">
								Alış Birim Fiyat: 
							</div>
							<div class="col-sm-2">
								<input class="form-control" id="unitPriceInputBuy">
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-4">Miktar:</div>
							<div class="col-sm-4">
								<input class="form-controller" id="itemQtyInputBuy" style="width:100%">
							</div>
							<div class="col-sm-2">
								Toplam Fiyat: 
							</div>
							<div class="col-sm-2">
								<input class="form-control" id="totalPriceInputBuy" disabled>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-4">İşlem Yapılacak Kasa:</div>
							<div class="col-sm-8">
								<select name="operationCashBoxnm" id="operationCashBoxBuy" style="width:100%" class="form-select"></select>
							</div>
						</div>
					</div>
					<div class="modal-footer" style="background-color: #ECF0F1">
						<button class="btn btn-sm btn-warning" id="buyItemButton"><font color="white">Alış Yap</font></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- MAIN CONTENT START -->
<jsp:include page="../include/footer.jsp"/>