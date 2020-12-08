package com.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.controller.AdminDBConnection;


@WebServlet("/loginCheck")
public class loginCheck extends HttpServlet {

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String userid=req.getParameter("loginId");
		String password=req.getParameter("loginPassword");
		AdminDBConnection connect = new AdminDBConnection();
		ArrayList user= new ArrayList(); 
		HttpSession session;
		if(userid.equals("Admin") && password.equals("Admin")) {
			res.sendRedirect("AdminPage.jsp");
		}
		else {
			try {
				user=connect.checkUser(userid);
				session = req.getSession();
				session.setAttribute("rid", user.get(0));
				session.setAttribute("readerName", user.get(1));
				res.sendRedirect("UserPage.jsp");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
