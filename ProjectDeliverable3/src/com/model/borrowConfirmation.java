package com.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

@WebServlet("/borrowConfirmation")
public class borrowConfirmation extends HttpServlet {
	
	public int dcid,copyNumber,branchId,rid;
	public String todayDate,todayTime;
	AdminDBConnection connect = new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rid=Integer.parseInt(request.getParameter("rid"));
		dcid=Integer.parseInt(request.getParameter("dcid"));
		copyNumber=Integer.parseInt(request.getParameter("copyNumber"));
		branchId=Integer.parseInt(request.getParameter("branchId"));
		todayDate=request.getParameter("todayDate");
		todayTime=request.getParameter("todayTime");
		
		
		try {
			if(connect.insertBorrowBook(rid, dcid, copyNumber, branchId, todayDate, todayTime)) {
				response.sendRedirect("ReturnBook.jsp");
			}
			else {
				response.sendRedirect("SearchPage.jsp?Result='fail'");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
