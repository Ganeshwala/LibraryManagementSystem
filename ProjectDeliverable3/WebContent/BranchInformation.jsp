<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page language="java" import="com.controller.*" %>
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
			<table>
				<thead>
					<tr>
						<th>Branch Id</th>
						<th>Branch Name</th>
						<th>Branch Location</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<Map> value;
						AdminDBConnection co = new AdminDBConnection();
						value=co.branchInformation();
						%><tr><%
						for(Map<String, Object> me:value) {
							for(Map.Entry<String,Object> entry : me.entrySet()){
							%>
							
								<td><%=entry.getValue()%></td>
							<%
							}
							%>
							</tr><%
						}
					%>
				</tbody>
			</table>
		</center>
	</div>
</body>
</html>