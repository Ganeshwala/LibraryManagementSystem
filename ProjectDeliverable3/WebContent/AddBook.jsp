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
			<form action="AddBook">
				<table>
					<tr>
						<td>Document ID(DCID):</td>
						<td><input type="text" name="DCID" autofocus="autofocus"></td>
					</tr>
					<tr>
						<td>Book Title:</td>
						<td><input type="text" name="bookTitle"></td>
					</tr>
					<tr>
						<td>Book ISBN:</td>
						<td><input type="number" name="bookIsbn"></td>
					</tr>
					<tr>
						<td>Author Name:</td>
						<td><input type="text" name="authorName"></td>
					</tr>
					<tr>
						<td>Author Id:</td>
						<td><input type="text" name="authorId"></td>
					</tr>
					<tr>
						<td>Publisher Name:</td>
						<td><input type="text" name="publisherName"></td>
					</tr>
					<tr>
						<td>Publisher Id:</td>
						<td><input type="text" name="publisherId"></td>
					</tr>
					<tr>
						<td>Publisher Address:</td>
						<td><input type="text" name="publisherAddress"></td>
					</tr>
					<tr>
						<td>Publication Date:</td>
						<td><input type="date" name="publicationDate"></td>
					</tr>
					<tr>
						<td>Branch Name:</td>
						<td>
							<select name="branchName">
								<option value="Parsippany">Parsippany</option>
								<option value="JerseyCity">Jersey City</option>
								<option value="Five Corne">Five Corne</option>
								<option value="Lafayette">Lafayette</option>
								<option value="City Library">City Library</option>
								<option value="Public Library">Public Library</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Copy Number:</td>
						<td><input type="text" name="copyNumber"></td>
					</tr>
					<tr>
						<td>Position of document:</td>
						<td><input type="text" name="documentPostionInBranch"></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="Add Document">
						</td>
					</tr>
				</table>
			</form>
			<div>
				<%
					boolean i = (request.getParameter("Result") !=null);
					if(i){
						if(request.getParameter("Result").equals("success")){
							%><p style="color:green;">Document Added Successfully</p><%
						}
						else if(request.getParameter("Result").equals("fail")){
							%><p style="color:red;">Error whlie adding Document</p><%
						}
					}
				%>
			</div>
		</center>
	</div>
</body>
</html>