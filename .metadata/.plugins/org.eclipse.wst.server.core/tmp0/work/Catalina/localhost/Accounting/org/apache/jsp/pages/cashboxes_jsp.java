/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.41
 * Generated at: 2021-01-27 19:20:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.accountingapp.model.User;

public final class cashboxes_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.accountingapp.model.User");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../include/headerandmenu.jsp", out, false);
      out.write("\r\n");
      out.write("<link href=\"../include/css/dataTables.bootstrap4.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"../include/select2/select2.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<script src=\"../include/js/jquery.dataTables.min.js\"></script>\r\n");
      out.write("<script src=\"../include/js/dataTables.bootstrap4.min.js\"></script>\r\n");
      out.write("<script src=\"../include/select2/select2.min.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("var sendAjaxCB;\r\n");
      out.write("var fillCashBoxTable;\r\n");
      out.write("var cashBoxTable;\r\n");
      out.write("var currentRowDataCashBox;\r\n");
      out.write("var getCashBoxes;\r\n");
      out.write("var fillCashBoxSelect;\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("\tsendAjaxCB = function(jsonreq,url,async,type){\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\turl : url,\r\n");
      out.write("\t\t\ttype : type,\r\n");
      out.write("\t\t\tdataType : 'json',\r\n");
      out.write("\t\t\tdata : JSON.stringify(jsonreq),\r\n");
      out.write("\t\t\tcontentType : 'application/json',\r\n");
      out.write("\t\t\tmimeType : 'application/json',\r\n");
      out.write("\t\t\tasync : async,\r\n");
      out.write("\t\t\tsuccess : function(data){\r\n");
      out.write("\t\t\t\tif(typeof data == 'string' && data.includes(\"redirect_url\")){\r\n");
      out.write("\t\t\t\t\twindow.location.href = data.split(\":\")[1];\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\tif(jsonreq.params[0] == \"get_cash_boxes\"){\r\n");
      out.write("\t\t\t\t\t\tfillCashBoxTable(data);\r\n");
      out.write("\t\t\t\t\t\tfillCashBoxSelect(data);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif(jsonreq.params[0] == \"delete_cash_box\"){\r\n");
      out.write("\t\t\t\t\t\tif(data == \"1\"){\r\n");
      out.write("\t\t\t\t\t\t\talert('İşlem başarılı')\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\talert('Silme işleminin gerçekleşmesi için kasanın hacmi 0 olmalıdır. Lütfen kasa transfer ekranından kasanızdaki parayı başka bir kasaya transfer ediniz. ')\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif(jsonreq.params[0] == \"save_cash_box\"){\r\n");
      out.write("\t\t\t\t\t\tif(data == \"1\"){\r\n");
      out.write("\t\t\t\t\t\t\talert(\"İşlem başarılı\")\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif(jsonreq.params[0] == \"transfer_cash_box\"){\r\n");
      out.write("\t\t\t\t\t\tif(data == \"2\"){\r\n");
      out.write("\t\t\t\t\t\t\talert(\"Transfer etmeye çalıtığınız tutar kasanızdaki tutardan fazla olduğundan işlem tamamlanamadı.\")\r\n");
      out.write("\t\t\t\t\t\t} else if(data == \"1\"){\r\n");
      out.write("\t\t\t\t\t\t\talert(\"Transfer işlemi başarı ile gerçekleşti\")\r\n");
      out.write("\t\t\t\t\t\t} else if(data == \"3\"){\r\n");
      out.write("\t\t\t\t\t\t\talert(\"Aynı kasalara arasında transfer işlemi gerçekleştiremezsiniz.\")\r\n");
      out.write("\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\talert(\"Bir hata oluştu!\")\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfillCashBoxSelect = function(data){\r\n");
      out.write("\t\tfor(var i = 0 ; i < data.length ; i++){\r\n");
      out.write("\t\t\tvar option = \"<option value=\"+data[i].id+\">\"+data[i].cashBoxName + \" - \" + data[i].cashBoxBalance +\"</option>\";\r\n");
      out.write("\t\t\t$('#cashBoxSelect').append(option);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfillCashBoxTable = function(data) {\r\n");
      out.write("\t\tif(typeof cashBoxTable == 'undefined'){\r\n");
      out.write("\t\t\tcashBoxTable = $('#cashBoxTable').DataTable({\r\n");
      out.write("\t\t\t\t\"language\": {\r\n");
      out.write("\t\t            \"lengthMenu\": \"Sayfa başına _MENU_ kayıt\",\r\n");
      out.write("\t\t            \"zeroRecords\": \"Nothing found - sorry\",\r\n");
      out.write("\t\t            \"info\": \"_PAGE_ sayfadan _PAGES_ gösteriliyor\",\r\n");
      out.write("\t\t            \"infoEmpty\": \"Data bulunamadı\",\r\n");
      out.write("\t\t            \"infoFiltered\": \"(filtered from _MAX_ total records)\",\r\n");
      out.write("\t\t            \"search\" : \"Arama\",\r\n");
      out.write("\t\t            \"paginate\": {\r\n");
      out.write("\t\t                \"previous\": \"Geri\",\r\n");
      out.write("\t\t                \"next\" : \"İleri\"\r\n");
      out.write("\t\t            }\r\n");
      out.write("\t\t        },\r\n");
      out.write("\t\t\t\tdata: data,\r\n");
      out.write("\t\t\t\t\"columns\" : [\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tdata: \"id\"\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tdata: \"cashBoxName\"\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tdata: \"cashBoxBalance\"\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t]\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tcashBoxTable.clear();\r\n");
      out.write("\t\t\tcashBoxTable.rows.add(data);\r\n");
      out.write("\t\t\tcashBoxTable.draw();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$('#cashBoxTable tbody').on('click', 'tr', function () {\r\n");
      out.write("\t\t\tif ($(this).hasClass('selectedRow') ) {\r\n");
      out.write("\t            $(this).removeClass('selectedRow');\r\n");
      out.write("\t            $('#updateCashBoxButton').attr(\"disabled\",true);\r\n");
      out.write("\t            $('#deleteCashBoxButton').attr(\"disabled\",true);\r\n");
      out.write("\t            $('#transferCashBoxButton').attr(\"disabled\",true);\r\n");
      out.write("\t        } else {\r\n");
      out.write("\t        \tcashBoxTable.$('tr.selectedRow').removeClass('selectedRow');\r\n");
      out.write("\t            $(this).addClass('selectedRow');\r\n");
      out.write("\t            var data = cashBoxTable.row(this).data();\r\n");
      out.write("\t            currentRowDataCashBox = data;\r\n");
      out.write("\t            $('#updateCashBoxButton').attr(\"disabled\",false);\r\n");
      out.write("\t            $('#deleteCashBoxButton').attr(\"disabled\",false);\r\n");
      out.write("\t            $('#transferCashBoxButton').attr(\"disabled\",false);\t\t\t\t\r\n");
      out.write("\t        }\r\n");
      out.write("\t        \r\n");
      out.write("\t    });\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tgetCashBoxes = function(){\r\n");
      out.write("\t\tvar jsonreq = new Object();\r\n");
      out.write("\t\tjsonreq.params = new Array();\r\n");
      out.write("\t\tjsonreq.params[0] = \"get_cash_boxes\";\r\n");
      out.write("\t\tsendAjaxCB(jsonreq,\"/Accounting/AccountingServlet\",false,\"POST\");\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$('#newCashBoxButton').on('click',function(){\r\n");
      out.write("\t\t$('#cashBoxIdInput').val('');\r\n");
      out.write("\t\t$('#cashBoxNameInput').val('');\r\n");
      out.write("\t\t$('#cashBoxBalanceInput').val('');\r\n");
      out.write("\t\t$('#cashBoxModal').modal('show');\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#updateCashBoxButton').on('click',function(){\r\n");
      out.write("\t\t$('#cashBoxIdInput').val(currentRowDataCashBox.id);\r\n");
      out.write("\t\t$('#cashBoxNameInput').val(currentRowDataCashBox.cashBoxName);\r\n");
      out.write("\t\t$('#cashBoxBalanceInput').val(currentRowDataCashBox.cashBoxBalance);\r\n");
      out.write("\t\t$('#cashBoxModal').modal('show');\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#deleteCashBoxButton').on('click',function(){\r\n");
      out.write("\t\tif(confirm(currentRowDataCashBox.cashBoxName + \" silinecek. Emin misiniz?\")){\r\n");
      out.write("\t\t\tvar jsonreq = new Object();\r\n");
      out.write("\t\t\tjsonreq.params = new Array();\r\n");
      out.write("\t\t\tjsonreq.params[0] = \"delete_cash_box\";\r\n");
      out.write("\t\t\tjsonreq.params[1] = currentRowDataCashBox.id;\r\n");
      out.write("\t\t\tsendAjaxCB(jsonreq,\"/Accounting/AccountingServlet\",false,\"POST\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#cashBoxSaveButton').on('click',function(){\r\n");
      out.write("\t\tvar cashObj = new Object();\r\n");
      out.write("\t\tif($('#cashBoxIdInput').val().length > 0){\r\n");
      out.write("\t\t\tcashObj.id = $('#cashBoxIdInput').val();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tcashObj.cashBoxName = $('#cashBoxNameInput').val();\r\n");
      out.write("\t\tcashObj.cashBoxBalance = $('#cashBoxBalanceInput').val();\r\n");
      out.write("\t\tvar jsonreq = new Object();\r\n");
      out.write("\t\tjsonreq.params = new Array();\r\n");
      out.write("\t\tjsonreq.params[0] = \"save_cash_box\";\r\n");
      out.write("\t\tjsonreq.params[1] = JSON.stringify(cashObj);\r\n");
      out.write("\t\tsendAjaxCB(jsonreq,\"/Accounting/AccountingServlet\",false,\"POST\");\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#transferCashBoxButton').on('click',function(){\r\n");
      out.write("\t\t$('#cashTransferLabel').html(\"<b>\\'\" + currentRowDataCashBox.cashBoxName + \"\\'</b> kasasından transfer işlemi\");\r\n");
      out.write("\t\t$('#transferBalanceLabel').text(\"Max: \" + currentRowDataCashBox.cashBoxBalance);\r\n");
      out.write("\t\t$('#casBoxTransferModal').modal('show');\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$('#cashBoxTransferButton').on('click',function(){\r\n");
      out.write("\t\tvar transferObj = new Object();\r\n");
      out.write("\t\ttransferObj.currentBoxId = currentRowDataCashBox.id;\r\n");
      out.write("\t\ttransferObj.transferedBoxId = $('#cashBoxSelect').val();\r\n");
      out.write("\t\ttransferObj.transferBalance = $('#cashBoxTransferBalanceInput').val();\r\n");
      out.write("\t\tvar jsonreq = new Object();\r\n");
      out.write("\t\tjsonreq.params = new Array();\r\n");
      out.write("\t\tjsonreq.params[0] = \"transfer_cash_box\";\r\n");
      out.write("\t\tjsonreq.params[1] = JSON.stringify(transferObj);\r\n");
      out.write("\t\tsendAjaxCB(jsonreq,\"/Accounting/AccountingServlet\",false,\"POST\");\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\tgetCashBoxes();\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("<!-- MAIN CONTENT START -->\r\n");
      out.write("<div style=\"margin-left:10px\">\r\n");
      out.write("<br>\r\n");
      out.write("\t<div class=\"card\">\r\n");
      out.write("\t\t<div class=\"card-header\">\r\n");
      out.write("\t\t\tKasalar\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"card-body\">\r\n");
      out.write("\t\t\t<table id=\"cashBoxTable\" class=\"table table-hover\" style=\"width:100%\">\r\n");
      out.write("\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th>ID</th>\r\n");
      out.write("\t\t\t\t\t\t<th>Kasa Adı</th>\r\n");
      out.write("\t\t\t\t\t\t<th>Kasa Miktarı</th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</thead>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t<div class=\"btn-group\" role=\"group\">\r\n");
      out.write("\t\t\t\t<button class=\"btn btn-success btn-sm\" id=\"newCashBoxButton\">Yeni Kayıt</button>\r\n");
      out.write("\t\t\t\t<button class=\"btn btn-primary btn-sm\" id=\"updateCashBoxButton\" disabled>Düzenle</button>\r\n");
      out.write("\t\t\t\t<button class=\"btn btn-danger btn-sm\" id=\"deleteCashBoxButton\" disabled>Sil</button>\r\n");
      out.write("\t\t\t\t<button class=\"btn btn-secondary btn-sm\" id=\"transferCashBoxButton\" disabled>Transfer Et</button>\r\n");
      out.write("\t\t\t</div>\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"modal fade\" id=\"cashBoxModal\" tabindex=\"-1\" role=\"dialog\" aria-hidden=\"true\">\r\n");
      out.write("\t\t<div class=\"modal-dialog modal-md\" role=\"document\">\r\n");
      out.write("\t\t\t<div class=\"modal-content\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-header\" style=\"background-color: #ECF0F1\">\r\n");
      out.write("\t\t\t\t\t<h5 class=\"modal-title\" id=\"userRoleUsernameLabel\">Kasa</h5>\r\n");
      out.write("\t\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n");
      out.write("\t\t\t\t\t\t\t<span aria-hidden=\"true\">&times;</span>\r\n");
      out.write("\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"cashBoxIdInput\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-4\">Kasa Adı:</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-8\"><input id=\"cashBoxNameInput\" class=\"form-control\"/></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-4\">Kasa Hacmi:</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-8\"><input type=\"number\" id=\"cashBoxBalanceInput\" class=\"form-control\"/></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-footer\" style=\"background-color: #ECF0F1\">\r\n");
      out.write("\t\t\t\t\t<button class=\"btn btn-sm btn-success\" id=\"cashBoxSaveButton\">Kaydet</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"modal fade\" id=\"casBoxTransferModal\" tabindex=\"-1\" role=\"dialog\" aria-hidden=\"true\">\r\n");
      out.write("\t\t<div class=\"modal-dialog modal-lg\" role=\"document\">\r\n");
      out.write("\t\t\t<div class=\"modal-content\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-header\" style=\"background-color: #ECF0F1\">\r\n");
      out.write("\t\t\t\t\t<h5 class=\"modal-title\" id=\"cashTransferLabel\"></h5>\r\n");
      out.write("\t\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n");
      out.write("\t\t\t\t\t\t\t<span aria-hidden=\"true\">&times;</span>\r\n");
      out.write("\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"cashBoxIdInput\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-4\">Transfer Miktarı:</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-6\"><input style=\"width:100%\" type=\"number\" id=\"cashBoxTransferBalanceInput\" class=\"form-control\"/></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-2\"><label id=\"transferBalanceLabel\"></label></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-4\">Kasa Hacmi:</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t<select class=\"form-select\" style=\"width:100%\" id=\"cashBoxSelect\"></select>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-footer\" style=\"background-color: #ECF0F1\">\r\n");
      out.write("\t\t\t\t\t<button class=\"btn btn-sm btn-success\" id=\"cashBoxTransferButton\">Kaydet</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<!-- MAIN CONTENT START -->\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../include/footer.jsp", out, false);
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
