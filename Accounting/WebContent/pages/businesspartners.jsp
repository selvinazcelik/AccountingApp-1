<%@page import="com.accountingapp.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/headerandmenu.jsp"/>
<div class="container-fluid">
<!-- MAIN CONTENT START -->
<link href="../include/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<script src="../include/js/jquery.dataTables.min.js"></script>
<script src="../include/js/dataTables.bootstrap4.min.js"></script>
<script>
		var sendAjaxBP;
		var getBusinessPartners;
		var fillBusinessPartnersTable;
		var businessPartnerTable = undefined;
		var openUpdateModal;
		var currentRowData;
		var deleteBusinessPartner;
		$(document).ready(function(){
			sendAjaxBP = function(jsonreq,url,async,type){
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
							if(jsonreq.params[0] == "save_bp"){
								if(data == "1"){
									if(jsonreq.params[2] == "update"){
										alert("Güncelleme işlemi başarılı.");
									} else if(jsonreq.params[2] == "save"){
										alert("Ekleme işlemi başarılı.");
									}
									$('#businessPartnerModal').modal('hide');
									getBusinessPartners();
								}
							}
							if(jsonreq.params[0] == "get_all_bp"){
								fillBusinessPartnersTable(data);
								console.log(data)
							}
							if(jsonreq.params[0] == "delete_bp"){
								if(data == "1"){
									alert('Silme işemi başarılı.')
									getBusinessPartners();
								} else {
									alert('Bir hata oluştu!')
								}
							}
						}
					},error : function(e){
						console.log(e);
		            	alert('ERROR '+e.responseText)
		            }
				});
			}
			
			$('#newBusinessPartnerBtn').on('click',function(){
				$('#bpId').val('');
				$('#bpName').val('');
				$('#bpTelNo').val('');
				$('#bpMail').val('');
				$('#bpAddress').val('');
				$('#bpNote').val('');
				$('#businessPartnerModalLabel').text('Müşteri Ekleme - Yeni Kayıt');
				$('#businessPartnerModal').modal('show');
			});
			
			$('#updateBusinessPartnerBtn').on('click',function(){
				openUpdateModal();
			});
			
			$('#businessPartnerSaveBtn').on('click',function(){
				var bpObj = new Object();
				if($('#bpId').val().length > 0){
					bpObj.id = $('#bpId').val();
				}
				bpObj.partnerName = $('#bpName').val();
				bpObj.telno = $('#bpTelNo').val();
				bpObj.email = $('#bpMail').val();
				bpObj.note = $('#bpNote').val();
				bpObj.address = $('#bpAddress').val();
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "save_bp";
				jsonreq.params[1] = JSON.stringify(bpObj);
				if($('#bpId').val().length > 0){
					bpObj.id = $('#bpId').val();
					jsonreq.params[2] = "update";
				} else {
					jsonreq.params[2] = "save";
				}
				sendAjaxBP(jsonreq,"/Accounting/AccountingServlet",true,"POST");
			});
			
			$('#deleteBusinessPartnerBtn').on('click',function(){
				deleteBusinessPartner();
			});
			
			openUpdateModal = function(){
				$('#bpId').val(currentRowData.id);
				$('#bpName').val(currentRowData.partnerName);
				$('#bpTelNo').val(currentRowData.telno);
				$('#bpMail').val(currentRowData.email);
				$('#bpAddress').val(currentRowData.address);
				$('#bpNote').val(currentRowData.note);
				$('#businessPartnerModalLabel').text('Müşteri Ekleme - Güncelleme');
				$('#businessPartnerModal').modal('show');
			}
			
			getBusinessPartners = function(){
				var jsonreq = new Object();
				jsonreq.params = new Array();
				jsonreq.params[0] = "get_all_bp";
				sendAjaxBP(jsonreq,"/Accounting/AccountingServlet",true,"POST");
				console.log('loaded');
			}
			
			deleteBusinessPartner = function(){
				if(confirm(currentRowData.partnerName + ' isimli müşteriyi silmek istediğinize emin misiniz?')){
					var deleteId = currentRowData.id;
					var jsonreq = new Object();
					jsonreq.params = new Array();
					jsonreq.params[0] = "delete_bp";
					jsonreq.params[1] = deleteId;
					sendAjaxBP(jsonreq,"/Accounting/AccountingServlet",false,"POST");
				}
			}
			
			fillBusinessPartnersTable = function(data){
				if(typeof businessPartnerTable == 'undefined'){
					businessPartnerTable = $('#businessPartnersTable').DataTable({
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
								data: "partnerName"
							},
							{
								data: "telno"
							},
							{
								data: "email"
							},
							{
								data: "address"
							},
							{
								data: "note"
							}
						]
					});
				} else {
					businessPartnerTable.clear();
					businessPartnerTable.rows.add(data);
					businessPartnerTable.draw();
				}
				
				$('#businessPartnersTable tbody').on('click', 'tr', function () {
					if ($(this).hasClass('selectedRow') ) {
			            $(this).removeClass('selectedRow');
			            $('#updateBusinessPartnerBtn').attr("disabled",true);
			            $('#deleteBusinessPartnerBtn').attr("disabled",true);
			        } else {
			        	businessPartnerTable.$('tr.selectedRow').removeClass('selectedRow');
			            $(this).addClass('selectedRow');
			            var data = businessPartnerTable.row(this).data();
			            currentRowData = data;
			            $('#updateBusinessPartnerBtn').attr("disabled",false);
			            $('#deleteBusinessPartnerBtn').attr("disabled",false);
						
			        }
			        
			    });
			}
			getBusinessPartners();
		});
	</script>
	<br>
	<div class="card">
		<div class="card-header">
    		Müşteriler
  		</div>
  		<div class="card-body">
  			<div style="padding:10px;height:100%">
				<table id="businessPartnersTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>ID</th>
							<th>Müşteri Adı</th>
							<th>Telefon No.</th>
							<th>E-Mail</th>
							<th>Adress</th>
							<th>Müşteri Notu</th>
						</tr>
					</thead>
				</table>
				<div class="btn-group" role="group">
					<button class="btn btn-success btn-sm" id="newBusinessPartnerBtn">Yeni Kayıt</button>
					<button class="btn btn-primary btn-sm" id="updateBusinessPartnerBtn" disabled>Düzenle</button>
					<button class="btn btn-danger btn-sm" id="deleteBusinessPartnerBtn" disabled>Sil</button>
				</div>
			</div>
  		</div>
	</div>
	<div class="modal fade" id="businessPartnerModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #ECF0F1">
					<h5 class="modal-title" id="businessPartnerModalLabel">Müşteri Ekleme</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
				</div>
				<div class="modal-body">
					<input id="bpId" type="hidden">
					<div class="row">
						<div class="col-sm-4">Müşteri Adı:</div>
						<div class="col-sm-8"><input class="form-control" id="bpName"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">Telefon No:</div>
						<div class="col-sm-8"><input class="form-control" id="bpTelNo"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">E-mail:</div>
						<div class="col-sm-8"><input class="form-control" id="bpMail"></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">Adress:</div>
						<div class="col-sm-8"><textarea style="resize:none" class="form-control" id="bpAddress"></textarea></div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4">Müşteri Notu:</div>
						<div class="col-sm-8"><textarea style="resize:none" class="form-control" id="bpNote"></textarea></div>
					</div>
					<br>
				</div>
				<div class="modal-footer" style="background-color: #ECF0F1">
					<button class="btn btn-sm btn-success" id="businessPartnerSaveBtn">Kaydet</button>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- MAIN CONTENT START -->
<jsp:include page="../include/footer.jsp"/>