/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.41
 * Generated at: 2021-01-27 18:39:07 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.accountingapp.model.User;
import com.accountingapp.service.AccountingService;

public final class register_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.accountingapp.service.AccountingService");
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta charset=\"utf-8\">\r\n");
      out.write("\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("\t<title>AccountingApp Login</title>\r\n");
      out.write("\t<meta content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" name=\"viewport\">\r\n");
      out.write("  \r\n");
      out.write("\t<link href=\"../include/vendor/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("\t<link href=\"../include/font-awesome/css/font-awesome.min.css\" rel=\"stylesheet\">\r\n");
      out.write("\t<link href=\"../include/Ionicons/css/ionicons.min.css\" rel=\"stylesheet\">\r\n");
      out.write("\t<link href=\"../include/css/AdminLTE.min.css\" rel=\"stylesheet\">\r\n");
      out.write("\t<link href=\"../include/iCheck/square/blue.css\" rel=\"stylesheet\">\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic\">\r\n");
      out.write("  \r\n");
      out.write("\t<script src=\"../include/vendor/jquery/jquery.min.js\"></script>\r\n");
      out.write("\t<script src=\"../include/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\r\n");
      out.write("\t<script src=\"../include/iCheck/icheck.min.js\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\t\t$(function () {\r\n");
      out.write("\t\t\t$('input').iCheck({\r\n");
      out.write("\t\t\t\tcheckboxClass: 'icheckbox_square-blue',\r\n");
      out.write("\t\t\t\tradioClass: 'iradio_square-blue',\r\n");
      out.write("\t\t\t\tincreaseArea: '20%' /* optional */\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\tvar sendAjax;\r\n");
      out.write("\t\t$(document).ready(function(){\r\n");
      out.write("\t\t\tsendAjax = function(jsonreq,url,async,type){\r\n");
      out.write("\t\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t\turl : url,\r\n");
      out.write("\t\t\t\t\ttype : type,\r\n");
      out.write("\t\t\t\t\tdataType : 'json',\r\n");
      out.write("\t\t\t\t\tdata : JSON.stringify(jsonreq),\r\n");
      out.write("\t\t\t\t\tcontentType : 'application/json',\r\n");
      out.write("\t\t\t\t\tmimeType : 'application/json',\r\n");
      out.write("\t\t\t\t\tasync : async,\r\n");
      out.write("\t\t\t\t\tsuccess : function(data){\r\n");
      out.write("\t\t\t\t\t\tif(jsonreq.params[0] == \"register\"){\r\n");
      out.write("\t\t\t\t\t\t\tif(data == \"1\"){\r\n");
      out.write("\t\t\t\t\t\t\t\talert('Kayıt işlemi başarılı');\r\n");
      out.write("\t\t\t\t\t\t\t\twindow.location.href = \"../login.jsp\";\r\n");
      out.write("\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\talert('Kayıt işlemi başarısız');\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$('#registerBtn').on('click',function(){\r\n");
      out.write("\t\t\t\tvar email = $('#email').val();\r\n");
      out.write("\t\t\t\tvar password = $('#password').val();\r\n");
      out.write("\t\t\t\tvar password2 = $('#password2').val();\r\n");
      out.write("\t\t\t\tif(email.trim().length < 1){\r\n");
      out.write("\t\t\t\t\talert('Email kısmı boş olamaz!');\r\n");
      out.write("\t\t\t\t} else if(password.trim().length < 1 || password2.trim().length < 1){\r\n");
      out.write("\t\t\t\t\talert('Şifre kısmı boş olamaz!')\r\n");
      out.write("\t\t\t\t} else if(password != password2){\r\n");
      out.write("\t\t\t\t\talert('Şifreler birbirleri ile uyuşmuyor!')\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\tvar jsonreq = new Object();\r\n");
      out.write("\t\t\t\t\tjsonreq.params = new Array();\r\n");
      out.write("\t\t\t\t\tjsonreq.params[0] = \"register\";\r\n");
      out.write("\t\t\t\t\tvar userObj = new Object();\r\n");
      out.write("\t\t\t\t\tuserObj.userName = $('#userName').val();\r\n");
      out.write("\t\t\t\t\tuserObj.userSurname = $('#userSurname').val();\r\n");
      out.write("\t\t\t\t\tuserObj.userMail = email;\r\n");
      out.write("\t\t\t\t\tuserObj.userPassword = password\r\n");
      out.write("\t\t\t\t\tjsonreq.params[1] = JSON.stringify(userObj);\r\n");
      out.write("\t\t\t\t\tsendAjax(jsonreq,\"/Accounting/AccountingServlet\",true,\"POST\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"hold-transition login-page\">\r\n");
      out.write("\t<div class=\"login-box\">\r\n");
      out.write("\t\t<div class=\"login-logo\">\r\n");
      out.write("\t\t\t<b>Accounting</b>App\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"login-box-body\">\r\n");
      out.write("\t\t\t<p class=\"login-box-msg\">Kayıt Ol</p>\r\n");
      out.write("\t\t\t<div class=\"form-group has-feedback\">\r\n");
      out.write("\t\t\t\t<input id=\"userName\" class=\"form-control\" placeholder=\"İsim\" required=\"required\">\r\n");
      out.write("\t\t\t\t<span class=\"glyphicon glyphicon-envelope form-control-feedback\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group has-feedback\">\r\n");
      out.write("\t\t\t\t<input id=\"userSurname\" class=\"form-control\" placeholder=\"Soyisim\" required=\"required\">\r\n");
      out.write("\t\t\t\t<span class=\"glyphicon glyphicon-envelope form-control-feedback\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group has-feedback\">\r\n");
      out.write("\t\t\t\t<input id=\"email\" type=\"email\" class=\"form-control\" placeholder=\"Email\" required=\"required\">\r\n");
      out.write("\t\t\t\t<span class=\"glyphicon glyphicon-envelope form-control-feedback\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group has-feedback\">\r\n");
      out.write("\t\t\t\t<input id=\"password\" type=\"password\" class=\"form-control\" placeholder=\"Şifre\" required=\"required\">\r\n");
      out.write("\t\t\t\t<span class=\"glyphicon glyphicon-lock form-control-feedback\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group has-feedback\">\r\n");
      out.write("\t\t\t\t<input id=\"password2\" type=\"password\" class=\"form-control\" placeholder=\"Şifre Tekrar\" required=\"required\">\r\n");
      out.write("\t\t\t\t<span class=\"glyphicon glyphicon-lock form-control-feedback\"></span>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t<div class=\"col-md-12\">\r\n");
      out.write("\t\t\t\t\t<button id=\"registerBtn\" align=\"right\" class=\"btn btn-primary btn-block btn-flat\">Kayıt Ol</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
