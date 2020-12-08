<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header><%@include file="AdminMenu.jsp" %></header>
	<br/>
	<div>
		<center>
			<form action="AddDocumentType">
				Document Type:<select name="documentType">
					<option value="book">Book</option>
					<option value="journal">Journal</option>
					<option value="chair">Proceedings</option>
				</select>
				<input type="submit" value="Add">
			</form>
		</center>
	</div>
</body>
</html>