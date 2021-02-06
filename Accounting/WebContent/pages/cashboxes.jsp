<%@page import="com.accountingapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="../include/headerandmenu.jsp"/>
<link href="../include/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<link href="../include/select2/select2.min.css" rel="stylesheet">
<script src="../include/js/jquery.dataTables.min.js"></script>
<script src="../include/js/dataTables.bootstrap4.min.js"></script>
<script src="../include/select2/select2.min.js"></script>
<script>
var sendAjaxCB;
var fillCashBoxTable;
var cashBoxTable;
var currentRowDataCashBox;
var getCashBoxes;
var fillCashBoxSelect;
$(document).ready(function(){
	sendAjaxCB = function(jsonreq,url,async,type){
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
					if(jsonreq.params[0] == "get_cash_boxes"){
						fillCashBoxTable(data);
						fillCashBoxSelect(data);
					}
					if(jsonreq.params[0] == "delete_cash_box"){
						if(data == "1"){
							alert('İşlem başarılı')
						} else {
							alert('Silme işleminin gerçekleşmesi için kasanın hacmi 0 olmalıdır. Lütfen kasa transfer ekranından kasanızdaki parayı başka bir kasaya transfer ediniz. ')
						}
					}
					if(jsonreq.params[0] == "save_cash_box"){
						if(data == "1"){
							alert("İşlem başarılı")
						}
					}
					if(jsonreq.params[0] == "transfer_cash_box"){
						if(data == "2"){
							alert("Transfer etmeye çalıtığınız tutar kasanızdaki tutardan fazla olduğundan işlem tamamlanamadı.")
						} else if(data == "1"){
							alert("Transfer işlemi başarı ile gerçekleşti")
						} else if(data == "3"){
							alert("Aynı kasalara arasında transfer işlemi gerçekleştiremezsiniz.")
						} else {
							alert("Bir hata oluştu!")
						}
					}
				}
			}
		});
	}
	
	fillCashBoxSelect = function(data){
		for(var i = 0 ; i < data.length ; i++){
			var option = "<option value="+data[i].id+">"+data[i].cashBoxName + " - " + data[i].cashBoxBalance +"</option>";
			$('#cashBoxSelect').append(option);
		}
	}
	
	fillCashBoxTable = function(data) {
		if(typeof cashBoxTable == 'undefined'){
			cashBoxTable = $('#cashBoxTable').DataTable({
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
						data: "cashBoxName"
					},
					{
						data: "cashBoxBalance"
					}
				]
			});
		} else {
			cashBoxTable.clear();
			cashBoxTable.rows.add(data);
			cashBoxTable.draw();
		}
		
		$('#cashBoxTable tbody').on('click', 'tr', function () {
			if ($(this).hasClass('selectedRow') ) {
	            $(this).removeClass('selectedRow');
	            $('#updateCashBoxButton').attr("disabled",true);
	            $('#deleteCashBoxButton').attr("disabled",true);
	            $('#transferCashBoxButton').attr("disabled",true);
	        } else {
	        	cashBoxTable.$('tr.selectedRow').removeClass('selectedRow');
	            $(this).addClass('selectedRow');
	            var data = cashBoxTable.row(this).data();
	            currentRowDataCashBox = data;
	            $('#updateCashBoxButton').attr("disabled",false);
	            $('#deleteCashBoxButton').attr("disabled",false);
	            $('#transferCashBoxButton').attr("disabled",false);				
	        }
	        
	    });
	}
	
	getCashBoxes = function(){
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "get_cash_boxes";
		sendAjaxCB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	}
	
	$('#newCashBoxButton').on('click',function(){
		$('#cashBoxIdInput').val('');
		$('#cashBoxNameInput').val('');
		$('#cashBoxBalanceInput').val('');
		$('#cashBoxModal').modal('show');
	});
	
	$('#updateCashBoxButton').on('click',function(){
		$('#cashBoxIdInput').val(currentRowDataCashBox.id);
		$('#cashBoxNameInput').val(currentRowDataCashBox.cashBoxName);
		$('#cashBoxBalanceInput').val(currentRowDataCashBox.cashBoxBalance);
		$('#cashBoxModal').modal('show');
	});
	
	$('#deleteCashBoxButton').on('click',function(){
		if(confirm(currentRowDataCashBox.cashBoxName + " silinecek. Emin misiniz?")){
			var jsonreq = new Object();
			jsonreq.params = new Array();
			jsonreq.params[0] = "delete_cash_box";
			jsonreq.params[1] = currentRowDataCashBox.id;
			sendAjaxCB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
		}
	});
	
	$('#cashBoxSaveButton').on('click',function(){
		var cashObj = new Object();
		if($('#cashBoxIdInput').val().length > 0){
			cashObj.id = $('#cashBoxIdInput').val();
		}
		cashObj.cashBoxName = $('#cashBoxNameInput').val();
		cashObj.cashBoxBalance = $('#cashBoxBalanceInput').val();
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "save_cash_box";
		jsonreq.params[1] = JSON.stringify(cashObj);
		sendAjaxCB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	});
	
	$('#transferCashBoxButton').on('click',function(){
		$('#cashTransferLabel').html("<b>\'" + currentRowDataCashBox.cashBoxName + "\'</b> kasasından transfer işlemi");
		$('#transferBalanceLabel').text("Max: " + currentRowDataCashBox.cashBoxBalance);
		$('#casBoxTransferModal').modal('show');
	});
	
	$('#cashBoxTransferButton').on('click',function(){
		var transferObj = new Object();
		transferObj.currentBoxId = currentRowDataCashBox.id;
		transferObj.transferedBoxId = $('#cashBoxSelect').val();
		transferObj.transferBalance = $('#cashBoxTransferBalanceInput').val();
		var jsonreq = new Object();
		jsonreq.params = new Array();
		jsonreq.params[0] = "transfer_cash_box";
		jsonreq.params[1] = JSON.stringify(transferObj);
		sendAjaxCB(jsonreq,"/Accounting/AccountingServlet",false,"POST");
	});
	
	getCashBoxes();
});
</script>
<div class="container-fluid">
<!-- MAIN CONTENT START -->
<div style="margin-left:10px">
<br>
	<div class="card">
		<div class="card-header">
			Kasalar
		</div>
		<div class="card-body">
			<table id="cashBoxTable" class="table table-hover" style="width:100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Kasa Adı</th>
						<th>Kasa Miktarı</th>
					</tr>
				</thead>
			</table>
			<div class="btn-group" role="group">
				<button class="btn btn-success btn-sm" id="newCashBoxButton">Yeni Kayıt</button>
				<button class="btn btn-primary btn-sm" id="updateCashBoxButton" disabled>Düzenle</button>
				<button class="btn btn-danger btn-sm" id="deleteCashBoxButton" disabled>Sil</button>
				<button class="btn btn-secondary btn-sm" id="transferCashBoxButton" disabled>Transfer Et</button>
			</div>	
		</div>
	</div>
	<div class="modal fade" id="cashBoxModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title" id="userRoleUsernameLabel">Kasa</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="cashBoxIdInput">
					<div class="row">
						<div class="col-sm-4">Kasa Adı:</div>
						<div class="col-sm-8"><input id="cashBoxNameInput" class="form-control"/></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">Kasa Hacmi:</div>
						<div class="col-sm-8"><input type="number" id="cashBoxBalanceInput" class="form-control"/></div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="cashBoxSaveButton">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="casBoxTransferModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title" id="cashTransferLabel"></h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="cashBoxIdInput">
					<div class="row">
						<div class="col-sm-4">Transfer Miktarı:</div>
						<div class="col-sm-6"><input style="width:100%" type="number" id="cashBoxTransferBalanceInput" class="form-control"/></div>
						<div class="col-sm-2"><label id="transferBalanceLabel"></label></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">Kasa Hacmi:</div>
						<div class="col-sm-8">
							<select class="form-select" style="width:100%" id="cashBoxSelect"></select>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="cashBoxTransferButton">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- MAIN CONTENT START -->
<jsp:include page="../include/footer.jsp"/>