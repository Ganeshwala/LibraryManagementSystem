<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header>
		<%@include file="UserMenu.jsp" %>
	</header>
	<div>
		<%=session.getAttribute("readerName") %>
	</div>
</body>
</html>