package com.model;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

@WebServlet("/AddBook")
public class AddBook extends HttpServlet{
	public String bookName,publisherName,publisherAddress,publicationDate,authorName,branchName,branchLocation,documentPosition;
	public int DCID,authorId,publisherId,branchId,copyNumber;
	public double bookIsbn;
	AdminDBConnection connect= new AdminDBConnection();
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DCID=Integer.parseInt(req.getParameter("DCID"));
		bookName=req.getParameter("bookTitle");
		bookIsbn=Double.parseDouble(req.getParameter("bookIsbn"));
		authorName=req.getParameter("authorName");
		authorId=Integer.parseInt(req.getParameter("authorId"));
		publisherName=req.getParameter("publisherName");
		publisherId=Integer.parseInt(req.getParameter("publisherId"));
		publisherAddress=req.getParameter("publisherAddress");
		publicationDate=req.getParameter("publicationDate");
		branchName=req.getParameter("branchName");
		copyNumber=Integer.parseInt(req.getParameter("copyNumber"));
		documentPosition=req.getParameter("documentPostionInBranch");
		try {
			if(connect.addBook(DCID, bookName, bookIsbn, authorName, authorId, publisherName, publisherId, publisherAddress, publicationDate, branchName,copyNumber, documentPosition)) {
				res.sendRedirect("AddBook.jsp?Result=success");
			}
			else {
				res.sendRedirect("AddBook.jsp?Result=fail");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
