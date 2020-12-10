<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="JsScript/jquery.js"></script>
</head>
<body>
	<header><%@include file="UserMenu.jsp" %></header>
	<br/>
	<div>
		<center>
			<form action="SearchBook">
				<lable>Select Branch</lable>
				<select name="selectBranch">
					<option value="Not Select">Select Library Name</option>
					<option value="Parsippany">Parsippany</option>
					<option value="JerseyCity">Jersey City</option>
					<option value="Five Corne">Five Corne</option>
					<option value="Lafayette">Lafayette</option>
					<option value="City Library">City Library</option>
					<option value="Public Library">Public Library</option>
				</select>&nbsp;
				Search Id:<input type="text" name="bookId" placeholder="Enter Book Id" autofocus> &nbsp;
				Search Title:<input type="text" name="bookTitle" placeholder="Enter Book Title"> &nbsp;
				Search Publisher:<input type="text" name="bookPublisher" placeholder="Enter Book Publisher"> &nbsp;
				<input type="submit" value="Search">
			</form>
		</center>
	</div>
	<br/><br/>
	<div>
		<center>
		
			<%
				boolean i = (request.getAttribute("searchResult")!=null);
				System.out.print(i);
				List<Map> list = (List)request.getAttribute("searchResult");
				if(i){
					
					%>
				
					<table>
						<tr>
							<th>Doucment Id</th>
							<th>Book Name</th>
							<th>copy Number</th>
							<th>copy Position</th>
							<th>Publisher Name</th>
							<th>Branch ID</th>
							<th>Branch Name</th>
							<th>Branch Location</th>
							<th>Book Status</th>
							<th>Reserve</th>
							<th>Borrow</th>
						</tr>
						<tr>
						<% 
							for(Map<String, Object> me:list) {
								%><form><%
								for(Map.Entry<String,Object> entry : me.entrySet()){
								%>
									<td><%=entry.getValue() %>&nbsp;&nbsp;<input type="hidden" name="<%=entry.getKey() %>" value="<%=entry.getValue()%>"></td>
									
								<% 
									}
								%>
								<td><input type="submit" value="Reserve" onclick="form.action='checkReservationStatus'"></td>
								<td><input type="submit" value="Borrow" onclick="form.action='checkBorrowStatus'"></td>
								<td><input type="hidden" name="rid" value="<%=session.getAttribute("rid")%>"></td>
						</form>		
						</tr>
						<% 
							}
						%>
					</table>
				
					<% 
				}
			%>
			<%
				boolean k = (request.getParameter("reserve")!=null);
				if(k){
					%>
					<p style="color:red;">This Book already Reserved or Borrowed or if it is available then you have reached your limit.</p>
					<%
				}			
			%>
			<%
				boolean j = (request.getParameter("borrow")!=null);
				if(j){
					%>
					<p style="color:red;">This Book already Reserved or Borrowed or if it is available then you have reached your limit.</p>
					<%
				}			
			%>
		</center>
	</div>
</body>
</html>