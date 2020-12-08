package com.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;


@WebServlet("/mostBorrowedBook")
public class mostBorrowedBook extends HttpServlet {
		
	public int numberForList;
	public String branchName;
	List<Map> result;
	AdminDBConnection connect= new AdminDBConnection();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		numberForList=Integer.parseInt(request.getParameter("numberForList"));
		branchName=request.getParameter("selectBranch");
		if(branchName.equals("Not Select")) {
			try {
				result=connect.mostBorrowBookLibrary(numberForList);
				for(Map m:result) {
					System.out.println("=====");
					System.out.println(m);
					System.out.println("=====");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				result=connect.mostBorrowBookBranch(numberForList, branchName);
				for(Map m:result) {
					System.out.println("=====");
					System.out.println(m);
					System.out.println("=====");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("resultSet", result);
		request.getRequestDispatcher("/BorrowInformation.jsp").forward(request, response);
		
	}
}
