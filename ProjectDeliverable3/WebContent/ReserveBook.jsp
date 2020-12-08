<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.PrintWriter"%>
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
	<%=session.getAttribute("rid") %>
	<div>
		<center>
			<table>
				<thead>
					<tr>
						<th>Document ID</th>
						<th>Book Name</th>
						<th>Copy Number</th>
						<th>Copy Position</th>
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
					value=co.reserveBookDetails(readerId);
					%><tr><%
					for(Map<String, Object> me:value) {
						%><form><%
						for(Map.Entry<String,Object> entry : me.entrySet()){
							System.out.println(entry);
						%>
						
							<td><%=entry.getValue()%>&nbsp;&nbsp;<input type="hidden" name="<%=entry.getKey() %>" value="<%=entry.getValue()%>"></td>
						<%
						}
						%>	<td><input type="submit" value="borrow" onclick="form.action='checkBorrowStatus'"></td>
						<td><input type="hidden" name="rid" value="<%=session.getAttribute("rid")%>"></td>
						<td><input type="hidden" name="url" value="fromReserve"></td>
						</form>
						</tr><%
					}
					
					%>
				</tr>
				</tbody>
			</table>
			<%
				boolean k = (request.getParameter("reserve")!=null);
				if(k){
					%>
					<p style="color:red;">Reservaction ahs been cancel. You need pickup book before 6PM</p>
					<%
				}			
			%>
		</center>
	</div>
</body>
</html>