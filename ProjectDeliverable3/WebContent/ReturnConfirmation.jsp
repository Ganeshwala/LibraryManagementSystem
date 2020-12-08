<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page language="java" import="com.controller.*" %>
<%@page import="java.util.*,java.io.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header><%@include file='UserMenu.jsp' %></header>
	<br/>
	<br/>
	<div>
		<center>
			
				<table>
					
						<%
					List<Map> value;
					AdminDBConnection co = new AdminDBConnection();
					String rid=session.getAttribute("rid").toString();
					int dcid = Integer.parseInt(request.getParameter("dcid"));
					int copyNumber=Integer.parseInt(request.getParameter("copyNumber"));
					int branchId=Integer.parseInt(request.getParameter("branchId"));
					int publisherId=Integer.parseInt(request.getParameter("publisherId"));
					int readerId = Integer.parseInt(rid);
					value=co.returnBookDetails(readerId, dcid, copyNumber, branchId,publisherId);
					%><tr><%
					for(Map<String, Object> me:value) {
						%><form action="ReturnConfirm"><%
						for(Map.Entry<String,Object> entry : me.entrySet()){
							System.out.println(entry);
						%>
							<tr>
							<td><%=entry.getKey() %>
							<td><%=entry.getValue()%>&nbsp;&nbsp;<input type="hidden" name="<%=entry.getKey() %>" value="<%=entry.getValue()%>"></td>
							</tr>
						<%
						}
						%>
						<tr>
						<td>Return Date</td>
						<td>
						<%
							SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
							Date mydate= new Date();
							String date=formatter.format(mydate);
						%>
						<%=date.toString() %>
						</td>
						<td><input type="hidden" name="returnDate" value="<%=date.toString() %>"></td>
					</tr>
					<tr>
						<td>ring Time</td>
						<td>
						<%
							SimpleDateFormat timeFormatter= new SimpleDateFormat("hh:mm:ss");
							Date mytime= new Date();
							String time=timeFormatter.format(mytime);
						%>
						<%=time.toString() %>
						</td>
						<td><input type="hidden" name="returnTime" value="<%=time.toString() %>"></td>
					</tr>
					<tr><input type="hidden" name="rid" value="<%=session.getAttribute("rid")%>"></tr>
						<tr><td><input type="submit" value="Confirm"></td></tr>
						</form>
						</tr><%
					}
					
					%>
				
				</table>
			
		</center>
	</div>
</body>
</html>
