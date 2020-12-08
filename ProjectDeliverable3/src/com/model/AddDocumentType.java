package com.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddDocument
 */
@WebServlet("/AddDocumentType")
public class AddDocumentType extends HttpServlet {
	public String documentType;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		documentType=request.getParameter("documentType");
		if(documentType.equals("book")) {
			response.sendRedirect("AddBook.jsp");
		}
		else if(documentType.equals("journal")) {
			response.sendRedirect("AddJournal.jsp");
		}
		else if(documentType.equals("chair")) {
			response.sendRedirect("AddChair.jsp");
		}
	}

	
	

}
