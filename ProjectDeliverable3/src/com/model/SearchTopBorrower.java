package com.model;
import java.util.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;


@WebServlet("/SearchTopBorrower")
public class SearchTopBorrower extends HttpServlet {

	public int numberForList;
	public String branchName;
	List<Map> result;
	AdminDBConnection connect= new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		numberForList=Integer.parseInt(request.getParameter("numberForList"));
		branchName=request.getParameter("selectBranch");
			try {
				System.out.println("HI");
				result=connect.searchTopMostBorrower(numberForList, branchName);
				for(Map m:result) {
					System.out.println("=====");
					System.out.println(m);
					System.out.println("=====");
				}
				System.out.println("Out");
				request.setAttribute("resultSet", result);
				request.getRequestDispatcher("/AdminSearchTopBorrower.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}

