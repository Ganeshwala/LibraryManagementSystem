package com.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

/**
 * Servlet implementation class checkReservationStatus
 */
@WebServlet("/checkReservationStatus")
public class checkReservationStatus extends HttpServlet {
	
	public int rid,dcid,copyNumber,branchId;
	public String bookTitle,copyPosition,publisherName,branchName,branchLocation;
	AdminDBConnection connect = new AdminDBConnection();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rid=Integer.parseInt(request.getParameter("rid"));
		dcid=Integer.parseInt(request.getParameter("dcid"));
		bookTitle=request.getParameter("bookTitle");
		copyNumber=Integer.parseInt(request.getParameter("copyNumber"));
		copyPosition=request.getParameter("copyPosition");
		publisherName=request.getParameter("publisherName");
		branchId=Integer.parseInt(request.getParameter("branchId"));
		branchName=request.getParameter("branchName");
		branchLocation=request.getParameter("branchLocation");
		
		try {
			if(connect.searchReserveAndBorrowBook(rid, dcid, copyNumber, branchId)) {
				request.setAttribute("dcid",dcid );
				request.setAttribute("bookTitle",bookTitle );
				request.setAttribute("copyNumber",copyNumber );
				request.setAttribute("copyPosition",copyPosition );
				request.setAttribute("publisherName",publisherName );
				request.setAttribute("branchId",branchId );
				request.setAttribute("branchName",branchName );
				request.setAttribute("branchLocation",branchLocation);
				request.getRequestDispatcher("/ConfirmationReservation.jsp").forward(request, response);
			}
			else {
				response.sendRedirect("SearchPage.jsp?reserve=fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
