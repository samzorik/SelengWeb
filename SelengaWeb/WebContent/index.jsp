<%@page import="Entities.EntityTest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
teststat
<a href="index.html"> ссылка<br> </a>
<a href="Cookies.jsp"> ссылка2 </a>
<%@ page import="Entities.*"%>>
<% EntityTest.main(new String[0]);
%>
<%=EntityTest.output %>
</body>
</html>