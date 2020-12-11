package com.model;
import java.util.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

/**
 * Servlet implementation class checkBorrowStatus
 */
@WebServlet("/checkBorrowStatus")
public class checkBorrowStatus extends HttpServlet {
		
	public int rid,dcid,copyNumber,branchId;
	public String bookTitle,copyPosition,publisherName,branchName,branchLocation,url;
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
		url=request.getParameter("url");
		SimpleDateFormat dataFormat = new SimpleDateFormat("HH");
		Date d = new Date();
		String time=dataFormat.format(d);
		System.out.println("====>"+Integer.parseInt(time));
		if(Integer.parseInt(time)>=18) {
				try {
					System.out.println("=======>rid="+rid+"dcid"+dcid+"copy"+copyNumber+"branch"+branchId+"<========");
					connect.cancelReservation(rid, dcid, copyNumber, branchId);
					response.sendRedirect("ReserveBook.jsp?reserve=fail");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		else
		{
			try {
				if(url != null) {
					if(connect.searchBorrowBook(rid, dcid, copyNumber, branchId)) {
						request.setAttribute("dcid",dcid );
						request.setAttribute("bookTitle",bookTitle );
						request.setAttribute("copyNumber",copyNumber );
						request.setAttribute("copyPosition",copyPosition );
						request.setAttribute("publisherName",publisherName );
						request.setAttribute("branchId",branchId );
						request.setAttribute("branchName",branchName );
						request.setAttribute("branchLocation",branchLocation);
						request.getRequestDispatcher("/BorrowConfirmation.jsp").forward(request, response);
					}
					else {
						response.sendRedirect("SearchPage.jsp?borrow=fail");
					}
				}
				else {
					if(connect.searchReserveAndBorrowBook(rid, dcid, copyNumber, branchId)) {
						request.setAttribute("dcid",dcid );
						request.setAttribute("bookTitle",bookTitle );
						request.setAttribute("copyNumber",copyNumber );
						request.setAttribute("copyPosition",copyPosition );
						request.setAttribute("publisherName",publisherName );
						request.setAttribute("branchId",branchId );
						request.setAttribute("branchName",branchName );
						request.setAttribute("branchLocation",branchLocation);
						request.getRequestDispatcher("/BorrowConfirmation.jsp").forward(request, response);
					}
					else {
						response.sendRedirect("SearchPage.jsp?borrow=fail");
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


