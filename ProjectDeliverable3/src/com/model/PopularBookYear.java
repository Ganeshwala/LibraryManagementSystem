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

@WebServlet("/PopularBookYear")
public class PopularBookYear extends HttpServlet {


	public int givenYear;
	List<Map> result;
	AdminDBConnection connect= new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		givenYear=Integer.parseInt(request.getParameter("popularBookOfYear"));
		
		try {
			result=connect.mostPopularBookOfYearInLibrary(givenYear);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("resultSet", result);
		request.getRequestDispatcher("/MostPopularBookOfYear.jsp").forward(request, response);
	}

}
