package com.model;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.AdminDBConnection;

@WebServlet("/SearchBook")
public class SearchBook extends HttpServlet {

	public String bookTitle,bookPublisher,selectBranch,id,direction;
	public int bookId;
	AdminDBConnection connect = new AdminDBConnection();
	public List<Map> searchResult;
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		
		selectBranch=req.getParameter("selectBranch");
		id=req.getParameter("bookId");
		bookTitle=req.getParameter("bookTitle");
		bookPublisher=req.getParameter("bookPublisher");
		
		System.out.println("id====>"+id);
		if(selectBranch.equals("Not Select")) {
			System.out.println("Inside If");
			if(id=="" && bookTitle=="" && bookPublisher=="" ) {
				
				try {
					searchResult=connect.searchBookByAll();
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(bookTitle=="" && bookPublisher == "") {	
				try {
					bookId=Integer.parseInt(id);
					searchResult=connect.searchBookById(bookId);
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(id=="" && bookPublisher == "") {
				try {
					searchResult=connect.searchBookByTitle(bookTitle);
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(id=="" && bookTitle == "") {
				try {
					searchResult=connect.searchBookByPublisher(bookPublisher);
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(bookPublisher=="") {
				try {
					bookId=Integer.parseInt(id);
					searchResult=connect.searchBookByIdAndBookTitle(bookId, bookTitle);
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(id=="") {
				try {
					searchResult=connect.searchBookByTitleAndPublisher(bookTitle, bookPublisher);
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(bookTitle=="") {
				try {
					bookId=Integer.parseInt(id);
					searchResult=connect.searchBookByIdAndPublisher(bookId, bookPublisher);
					for(Map m:searchResult) {
						System.out.println("=====");
						System.out.println(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		else{
			System.out.println("Inside Else");
				if(id=="" && bookTitle=="" && bookPublisher=="" ) {
				
					try {
						searchResult=connect.searchBookByBranchLocation(selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(bookTitle=="" && bookPublisher == "") {	
					try {
						bookId=Integer.parseInt(id);
						searchResult=connect.searchBookByIdAndBranch(bookId,selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(id=="" && bookPublisher == "") {
					try {
						searchResult=connect.searchBookByTitleAndBranch(bookTitle, selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(id=="" && bookTitle == "") {
					try {
						searchResult=connect.searchBookByPublisherAndBranch(bookPublisher, selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(bookPublisher=="") {
					try {
						bookId=Integer.parseInt(id);
						searchResult=connect.searchBookByIdAndBookTitleAndBranch(bookId, bookTitle, selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(id=="") {
					try {
						searchResult=connect.searchBookByTitleAndPublisherAndBranch(bookTitle, bookPublisher, selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(bookTitle=="") {
					try {
						bookId=Integer.parseInt(id);
						searchResult=connect.searchBookByIdAndPublisherAndBranch(bookId, bookPublisher, selectBranch);
						for(Map m:searchResult) {
							System.out.println("=====");
							System.out.println(m);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}	
		req.setAttribute("searchResult", searchResult);
		req.getRequestDispatcher("/SearchPage.jsp").forward(req, res);
		
	}
}
