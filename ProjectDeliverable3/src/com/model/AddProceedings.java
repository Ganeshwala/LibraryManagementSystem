package com.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

/**
 * Servlet implementation class AddProceedings
 */
@WebServlet("/AddProceedings")
public class AddProceedings extends HttpServlet {

	public String bookName,publisherName,publisherAddress,publicationDate,authorName,branchName,branchLocation,documentPosition,conferenceLocation,conferenceDate;
	public int DCID,authorId,publisherId,copyNumber,editorId;
	AdminDBConnection connect = new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DCID=Integer.parseInt(request.getParameter("bookDcid"));
		bookName=request.getParameter("bookTitle");
		authorName=request.getParameter("authorName");
		authorId=Integer.parseInt(request.getParameter("authorId"));
		conferenceLocation=request.getParameter("conferenceLocation");
		conferenceDate=request.getParameter("conferenceDate");
		editorId=Integer.parseInt(request.getParameter("editorId"));
		publisherName=request.getParameter("publisherName");
		publisherId=Integer.parseInt(request.getParameter("publisherId"));
		publisherAddress=request.getParameter("publisherAddress");
		publicationDate=request.getParameter("publicationDate");
		branchName=request.getParameter("branchName");
		copyNumber=Integer.parseInt(request.getParameter("copyNumber"));
		documentPosition=request.getParameter("documentPostionInBranch");
		try {
			if(connect.addChairConference(DCID, bookName, authorName, authorId, conferenceLocation, conferenceDate, editorId, publisherName, publisherId, publisherAddress, publicationDate, branchName, copyNumber, documentPosition)) {
				response.sendRedirect("AddChair.jsp?Result=success");
			}
			else {
				response.sendRedirect("AddChair.jsp?Result=fail");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
