package com.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	public String readerName,readerType,readerAddress;
	public int readerId;
	public double readerPhone;
	AdminDBConnection connection = new AdminDBConnection();
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		readerId=Integer.parseInt(req.getParameter("readerId"));
		readerName=req.getParameter("readerName");
		readerType=req.getParameter("readerType");
		readerAddress=req.getParameter("readerAddress");
		readerPhone=Double.parseDouble(req.getParameter("readerPhone"));
		try {
			//connection.getConnection();
			if(connection.addUser(readerId, readerName, readerType, readerAddress, readerPhone)) {
				System.out.println("from If");
				res.sendRedirect("AddUser.jsp?result=done");
			}
			else {
				System.out.println("From else");
				res.sendRedirect("AddUser.jsp?result=fail");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
