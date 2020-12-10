<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.io.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header><%@include file="UserMenu.jsp" %></header>
	<div>
		<center>
			<form action="borrowConfirmation">
				<table>
					<tr><input type="hidden" name="rid" value="<%=session.getAttribute("rid")%>"></tr>
					<tr>
						<td>User Name</td>
						<td><%=session.getAttribute("readerName")%></td>
						<td><input type="hidden"  value="<%=session.getAttribute("readerName")%>"></td>
					</tr>
					<tr>
						<td>Book Id</td>
						<td><%=request.getAttribute("dcid") %></td>
						<td><input type="hidden" name="dcid" value="<%=request.getAttribute("dcid") %>"></td>
					</tr>
					<tr>
						<td>Book Name</td>
						<td><%=request.getAttribute("bookTitle") %></td>
						<td><input type="hidden" name="bookTitle" value="<%=request.getAttribute("bookTitle") %>"></td>
					</tr>
					<tr>
						<td>Book Copy Number </td>
						<td><%=request.getAttribute("copyNumber") %></td>
						<td><input type="hidden" name="copyNumber" value="<%=request.getAttribute("copyNumber") %>"></td>
					</tr>
					<tr>
						<td>Book Position</td>
						<td><%=request.getAttribute("copyPosition") %></td>
						<td><input type="hidden" name="copyPosition" value="<%=request.getAttribute("copyPosition") %>"></td>
					</tr>
					<tr>
						<td>Publisher Name</td>
						<td><%=request.getAttribute("publisherName") %></td>
						<td><input type="hidden" name="publisherName" value="<%=request.getAttribute("publisherName") %>"></td>
					</tr>
					<tr>
						<td>Branch Id:</td>
						<td><%=request.getAttribute("branchId") %></td>
						<td><input type="hidden" name="branchId" value="<%=request.getAttribute("branchId") %>"></td>
					</tr>
					<tr>
						<td>Branch Name:</td>
						<td><%=request.getAttribute("branchName") %></td>
						<td><input type="hidden" name="branchName" value="<%=request.getAttribute("branchName") %>"></td>
					</tr>
					<tr>
						<td>Branch Location</td>
						<td><%=request.getAttribute("branchLocation") %></td>
						<td><input type="hidden" name="branchLocation" value="<%=request.getAttribute("branchLocation") %>"></td>
					</tr>
					<tr>
						<td>Borrow Date</td>
						<td>
						<%
							SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
							Date mydate= new Date();
							String date=formatter.format(mydate);
						%>
						<%=date.toString() %>
						</td>
						<td><input type="hidden" name="todayDate" value="<%=date.toString() %>"></td>
					</tr>
					<tr>
						<td>Borrowing Time</td>
						<td>
						<%
							SimpleDateFormat timeFormatter= new SimpleDateFormat("hh:mm:ss");
							Date mytime= new Date();
							String time=timeFormatter.format(mytime);
						%>
						<%=time.toString() %>
						</td>
						<td><input type="hidden" name="todayTime" value="<%=time.toString() %>"></td>
					</tr>
					<tr>
						<td colspan="3"><input type="submit" value="Confirm"></td>
					</tr>
				</table>
			</form>
		</center>
	</div>
</body>
</html>