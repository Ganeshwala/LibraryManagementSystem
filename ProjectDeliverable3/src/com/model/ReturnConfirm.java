package com.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

@WebServlet("/ReturnConfirm")
public class ReturnConfirm extends HttpServlet {
	
	public int dcid,copyNumber,branchId,borrowId,rid,diffDays;
	public double fineAmount;
	public String borrowDate,borrowTime,returnDate,returnTime,branchName,bookTitle;
	AdminDBConnection connect = new AdminDBConnection();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			dcid=Integer.parseInt(request.getParameter("Book Id"));
			copyNumber=Integer.parseInt(request.getParameter("CopyNumber"));
			branchId=Integer.parseInt(request.getParameter("BranchId"));
			branchName=request.getParameter("Branch Name");
			bookTitle=request.getParameter("BookTitle");
			borrowId=Integer.parseInt(request.getParameter("TranscationID"));
			rid=Integer.parseInt(request.getParameter("rid"));
			System.out.println("Hello");
			borrowDate=request.getParameter("Borrow Date");
			borrowTime=request.getParameter("Borrow Time");
			returnDate=request.getParameter("returnDate");
			returnTime=request.getParameter("returnTime");
			System.out.println(returnDate+" "+returnTime);
			System.out.println("Hi");
			String format ="yyyy-MM-dd hh:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
		
			Date date1=sdf.parse(borrowDate +" "+ borrowTime);
			Date date2 = sdf.parse(returnDate +" "+returnTime);
			System.out.println("Date1"+date1+" "+date2);
			DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
			
			long diff = date2.getTime() - date1.getTime();
			 
			diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			System.out.println("difference between days: " + diffDays);
			
			if(diffDays>=21) {
				diffDays=diffDays-20;
				fineAmount=diffDays*0.20;
			}
			System.out.println("finalAMount==========>"+fineAmount);
			if(connect.returnBook(dcid,copyNumber,branchId,rid,fineAmount,borrowId,returnDate,returnTime)) {
				request.setAttribute("dcid", dcid);
				request.setAttribute("copyNumber",copyNumber);
				request.setAttribute("BranchId",branchId );
				request.setAttribute("BookTitle",bookTitle);
				request.setAttribute("branchName",branchName );
				request.setAttribute("ReaderId", rid);
				request.setAttribute("Fine Amount",fineAmount);
				request.getRequestDispatcher("/ReturnSuccess.jsp").forward(request, response);
			}
			else {
				response.sendRedirect("ReturnBook.jsp");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
