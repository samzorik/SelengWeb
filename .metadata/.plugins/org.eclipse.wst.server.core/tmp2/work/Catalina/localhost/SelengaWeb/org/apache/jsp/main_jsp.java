/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.28
 * Generated at: 2015-11-27 04:02:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
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

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
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
      out.write("<head>\r\n");
      out.write("<title>Выбор данных</title>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<!--  <script type=\"text/javascript\" src=\"jquery-2.1.4.js\"> -->\r\n");
      out.write("<!--  </script>  -->\r\n");
      out.write(" <script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js\">\r\n");
      out.write(" </script> \r\n");
      out.write("<!--  <script  type=\"text/javascript\"> -->\r\n");
      out.write("<!-- //     alert('blaaaa'); -->\r\n");
      out.write("<!--  </script> -->\r\n");
      out.write("<link type=\"text/css\" href=\"style.css\" rel=\"stylesheet\" >\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write(" <!--  <div class=\"header\">Выбрать данные по</div> -->\r\n");
      out.write("\r\n");
      out.write("\t<form method=\"post\">\r\n");
      out.write("\t\t<fieldset>\r\n");
      out.write("\t\t\t<legend>\r\n");
      out.write("\t\t\t\t<b>Выбрать данные по:</b>\r\n");
      out.write("\t\t\t</legend>\r\n");
      out.write("\t\t\t<div class=\"bord\">\r\n");
      out.write("\t\t\t\t<div class=\"col1\">\r\n");
      out.write("\t\t\t\t\t<input type=\"radio\" name=\"data_type\" value=\"by_year\"> По\r\n");
      out.write("\t\t\t\t\tгоду\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col2\">\r\n");
      out.write("\t\t\t\t\t<input type=\"radio\" name=\"data_type\" value=\"by_sp\"> По\r\n");
      out.write("\t\t\t\t\tсельскому поселению\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col3\">\r\n");
      out.write("\t\t\t\t\t<input type=\"radio\" name=\"data_type\" value=\"by_district\">\r\n");
      out.write("\t\t\t\t\tПо району в целом\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t\t<fieldset>\r\n");
      out.write("\t\t\t<legend>\r\n");
      out.write("\t\t\t\t<b>Год</b>\r\n");
      out.write("\t\t\t</legend>\r\n");
      out.write("\t\t\tName: <input type=\"text\" size=\"20\"><br> Email: <input\r\n");
      out.write("\t\t\t\ttype=\"text\" size=\"20\">\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t\t<p></p>\r\n");
      out.write("\t\t<fieldset>\r\n");
      out.write("\t\t\t<legend>\r\n");
      out.write("\t\t\t\t<b>Сельское поселение</b>\r\n");
      out.write("\t\t\t</legend>\r\n");
      out.write("\t\t\tFavorite Color: Red: <input type=\"radio\"> Blue: <input\r\n");
      out.write("\t\t\t\ttype=\"radio\"> Green: <input type=\"radio\">\r\n");
      out.write("\t\t\t<p>\r\n");
      out.write("\t\t\t\tFavorite Toothpaste: Crest: <input type=\"checkbox\">\r\n");
      out.write("\t\t\t\tClose-Up: <input type=\"checkbox\"> Gleem: <input\r\n");
      out.write("\t\t\t\t\ttype=\"checkbox\">\r\n");
      out.write("\t\t\t</p>\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t\t<p></p>\r\n");
      out.write("\t\t<fieldset>\r\n");
      out.write("\t\t\t<legend>\r\n");
      out.write("\t\t\t\t<b>Группы показателей</b>\r\n");
      out.write("\t\t\t</legend>\r\n");
      out.write("\t\t\t<br> <input type=\"button\"\r\n");
      out.write("\t\t\t\tonclick=\"alert('Stop clicking the buttons')\" value=\"Click to Submit\">\r\n");
      out.write("\t\t\t<br> <br>\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t<select id=\"someselect\"></select>\r\n");
      out.write("\t<div></div>\r\n");
      out.write("\t<!--  <div class=\"layout\">\r\n");
      out.write("   <div class=\"col1 cc\">Колонка 1</div>\r\n");
      out.write("   <div class=\"col2 cc\">Колонка 2</div>\r\n");
      out.write("   <div class=\"col3 cc\">Колонка 3</div>\r\n");
      out.write("  </div>-->\r\n");
      out.write(" <script  type=\"text/javascript\">\r\n");
      out.write("//     alert('blaaaa');   \r\n");
      out.write("\t\t$('input').bind('click',function() {\r\n");
      out.write("\t\t\tif  ($(this).attr(\"value\")==\"by_year\")\r\n");
      out.write("\t \t\t\t {\r\n");
      out.write("\t\t\t\t\t$.get(\"getYears\", function(responseJson) {                 // Execute Ajax GET request on URL of \"someservlet\" and execute the following function with Ajax response JSON...\r\n");
      out.write("\t\t\t\t        var $select = $(\"#someselect\");                           // Locate HTML DOM element with ID \"someselect\".\r\n");
      out.write("\t\t\t\t        $select.find(\"option\").remove();                          // Find all child elements with tag name \"option\" and remove them (just to prevent duplicate options when button is pressed again).\r\n");
      out.write("\t\t\t\t        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.\r\n");
      out.write("\t\t\t\t            $(\"<option>\").val(key).text(value).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.\r\n");
      out.write("\t\t\t\t        });\r\n");
      out.write("\t\t\t\t    });  \t\t \r\n");
      out.write("\t \t\t\t }\r\n");
      out.write("\t\t});\r\n");
      out.write("// \t  $(document).on(\"click\", '#by_year', function() {// When HTML DOM \"click\" event is invoked on element with ID \"somebutton\", execute the following function...\r\n");
      out.write("// \t\t alert('dgfhsdfkkjdfh');\r\n");
      out.write("// \t\t/*  if  ($(this).attr(\"value\")==\"by_year\")\r\n");
      out.write("// \t\t\t {\r\n");
      out.write("//                alert('HERE!!'); \t\t\t \r\n");
      out.write("// \t\t\t } */\r\n");
      out.write("// \t     <!--$.get(\"someservlet\", function(responseText) {   // Execute Ajax GET request on URL of \"someservlet\" and execute the following function with Ajax response text...\r\n");
      out.write("// \t        alert(responseText);           // Locate HTML DOM element with ID \"somediv\" and set its text content with the response text.\r\n");
      out.write("// \t     });-->\r\n");
      out.write("// \t }); \r\n");
      out.write(" </script>\r\n");
      out.write("    \r\n");
      out.write("</body>");
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
