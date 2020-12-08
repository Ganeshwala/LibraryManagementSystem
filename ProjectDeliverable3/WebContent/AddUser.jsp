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
	<div>
		<center>
		<form action="AddUser">
			<table>
				<tr>
					<td>Reader Name:</td>
					<td><input type="text" name="readerName" placeholder="Enter Reader Name" autofocus></td>
				</tr>
				<tr>
					<td>Reader Id:</td>
					<td><input type="text" name="readerId" placeholder="Enter Reader Id"></td>
				</tr>
				<tr>
					<td>Reader Type:</td>
					<td><input type="text" name="readerType" placeholder="Enter Reader Type"></td>
				</tr>
				<tr>
					<td>Reader Address:</td>
					<td><textArea name="readerAddress" placeholder="Enter Reader Address"></textArea></td>
				</tr>
				<tr>
					<td>Reader Phone:</td>
					<td><input type="text" name="readerPhone" placeholder="Enter Reader Phone"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Add User"></td>
				</tr>
			</table>
		</form>
		<div>
			<%
				boolean result= (request.getParameter("result") != null);
				if(result){
					if(request.getParameter("result").equals("done")){
						%><p style="color:green;">Sucessfully Added</p><%	
					}
					else{
						%><p style="color:red;">Please Try Again</p><%
					} 
				}
			%>
		</div>
		</center>
	</div>
</body>
</html>
