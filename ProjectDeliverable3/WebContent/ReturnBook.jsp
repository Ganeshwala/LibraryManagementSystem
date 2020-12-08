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
	<header><%@include file="UserMenu.jsp" %></header>
	<br/>
	<br/>
	<div>
		<center>
			<table>
				<thead>
					<tr>
						<th>Book ID</th>
						<th>Book Name</th>
						<th>Copy Number</th>
						<th>Copy Position</th>
						<th>Publisher Id</th>
						<th>Publisher Name</th>
						<th>Branch Id</th>
						<th>Branch Name</th>
						<th>Branch Location</th>
						<th>Borrow</th>
					</tr>
				</thead>
				<tbody>
				
				<tr>
					<%
					List<Map> value;
					AdminDBConnection co = new AdminDBConnection();
					String rid=session.getAttribute("rid").toString();
					int readerId = Integer.parseInt(rid);
					value=co.borrowBookDetails(readerId);
					%><tr><%
					for(Map<String, Object> me:value) {
						%><form><%
						for(Map.Entry<String,Object> entry : me.entrySet()){
							System.out.println(entry);
						%>
						
							<td><%=entry.getValue()%>&nbsp;&nbsp;<input type="hidden" name="<%=entry.getKey() %>" value="<%=entry.getValue()%>"></td>
						<%
						}
						%>	<td><input type="submit" value="Return" onclick="form.action='ReturnConfirmation.jsp'"></td>
						</form>
						</tr><%
					}
					
					%>
				</tr>
				</tbody>
			</table>
		</center>
	</div>
</body>
</html>