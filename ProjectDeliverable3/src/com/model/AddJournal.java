package com.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

@WebServlet("/AddJournal")
public class AddJournal extends HttpServlet {
	
	public String bookName,publisherName,publisherAddress,publicationDate,authorName,branchName,branchLocation,documentPosition,volumeScope;
	public int DCID,authorId,guestId,publisherId,branchId,copyNumber,volumeNumber;
	public double volumeIssueNumber;
	AdminDBConnection connect= new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DCID=Integer.parseInt(request.getParameter("bookDcid"));
		bookName=request.getParameter("bookTitle");
		volumeNumber=Integer.parseInt(request.getParameter("volumnNumber"));
		volumeIssueNumber=Double.parseDouble(request.getParameter("volumnIssueNumber"));
		volumeScope=request.getParameter("volumnScope");
		authorName=request.getParameter("authorName");
		authorId=Integer.parseInt(request.getParameter("authorId"));
		guestId=Integer.parseInt(request.getParameter("guestId"));
		publisherName=request.getParameter("publisherName");
		publisherId=Integer.parseInt(request.getParameter("publisherId"));
		publisherAddress=request.getParameter("publisherAddress");
		publicationDate=request.getParameter("publicationDate");
		branchName=request.getParameter("branchName");
		copyNumber=Integer.parseInt(request.getParameter("copyNumber"));
		documentPosition=request.getParameter("documentPostionInBranch");
		try {
			if(connect.addJournal(DCID, bookName, volumeNumber, volumeIssueNumber, volumeScope, authorName, authorId,guestId, publisherName, publisherId, publisherAddress, publicationDate, branchName, copyNumber, documentPosition)) {
				response.sendRedirect("AddJournal.jsp?Result=success");
			}
			else {
				response.sendRedirect("AddJournal.jsp?Result=fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
