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


@WebServlet("/CalculateFine")
public class CalculateFine extends HttpServlet {

	public String startDate,endDate;
	List<Map> result;
	AdminDBConnection connect = new AdminDBConnection();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		startDate=request.getParameter("firstDate");
		endDate=request.getParameter("endDate");
		System.out.println("sD"+startDate+"<====>"+" "+endDate);
		try {
			result=connect.calculateFineInPeriod(startDate, endDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("resultSet", result);
		request.getRequestDispatcher("/CalculateFineInLibrary.jsp").forward(request, response);
	}

}
