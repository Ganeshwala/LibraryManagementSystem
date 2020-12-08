package com.model;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;


@WebServlet("/AdminSearchDocumentStatus")
public class AdminSearchDocumentStatus extends HttpServlet {
	
	public int bookId;
	List<Map> result;
	AdminDBConnection connect= new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bookId=Integer.parseInt(request.getParameter("bookId"));
		
		try {
			result=connect.searchBookById(bookId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("resultSet", result);
		request.getRequestDispatcher("/AdminSearchDocument.jsp").forward(request, response);
	}

}
