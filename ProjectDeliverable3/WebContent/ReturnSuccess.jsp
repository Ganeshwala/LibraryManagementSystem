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
		<%@include file='UserMenu.jsp' %>
	</header>
	<div>
		<center>
			<p>
				Hi <%=request.getAttribute("ReaderId") %>,
				<br/>
				You seccussfully return the book <%=request.getAttribute("BookTitle") %> in Library branch <%=request.getAttribute("branchName") %>.
				<br/>
				Fine Amount:<%=request.getAttribute("Fine Amount") %>
				<br/>
				Thank You. 
				<form action="SearchPage.jsp">
					<input type="submit" value="Thank You">
				</form>
			</p>
		</center>
	</div>
</body>
</html>