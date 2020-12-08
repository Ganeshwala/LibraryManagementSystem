<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
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
			<form action="PopularBookYear">
				<label>Enter Year:</label>
				<input type="number" name="popularBookOfYear">
				<input type="submit" value="Search">
			</form>
		</center>
	</div>
	<div>
		<center>
			<%
				boolean i = (request.getAttribute("resultSet")!=null);
				List<Map> list = (List)request.getAttribute("resultSet");
				if(i){
					%>
						<table>
							<tr>
								<th>Document Id</th>
								<th>Document Title</th>
								<th>Most Borrowed(count)</th>
							</tr>
							
								<tr><%
								for(Map<String, Object> me:list){
									for(Map.Entry<String,Object> entry : me.entrySet()){
										%><td><%=entry.getValue() %>&nbsp;&nbsp;<input type="hidden" name="<%=entry.getKey() %>" value="<%=entry.getValue()%>"></td><%
									}
									%></tr><%
								}
						%></table><%
				}
			%>
		</center>
	</div>
</body>
</html>