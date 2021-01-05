package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class AdminDBConnection {
	Connection connect;
	Statement statement,statement1,statement2,statement3;
	ResultSet result,result1,result2,result3;
	public ArrayList user;
	public List<Map> list = new ArrayList<Map>();
	private String connectionPath="jdbc:mysql://localhost:3306/library";
	private String hostName="Ganeshwc",password="";
	public String readerId,readerName;
	private String query,query1,queryAddUser,queryAddPublisher,queryAddPerson,queryAddDocument,queryAddBook,queryAddAuthor,queryAddBranch,queryAddCopy,queryAddReservation,queryDeleteBorrowBook,queryAddVolume,queryAddVolumeIssue,queryAddVolumeGuest;
	private String queryForTopNReaderList;
	private int branchId;
	public void getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(connectionPath, hostName, password);
		System.out.println("Connection Succesfull");
	}
	
	public ArrayList checkUser(String userid) throws Exception {
		getConnection();
		query="select RID,readerName from reader where RID='"+userid+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		while(result.next()) {
			readerId=String.valueOf(result.getInt("RID"));
			readerName=result.getString("readerName");
			user = new ArrayList();
			user.add(readerId);
			user.add(readerName);
		}
		
		return user;
	}
	
	public boolean addUser(int readerId,String readerName,String readerType,String readerAddress,double readerPhone) throws Exception {
		getConnection();
		queryAddUser="insert into reader values('"+readerId+"','"+readerName+"','"+readerType+"','"+readerAddress+"','"+readerPhone+"')";
		statement = connect.createStatement();
		if(statement.execute(queryAddUser)) {
			statement.close();
			return false;
		}else {
			statement.close();
			return true;
		}
	}
	
	public boolean addBook(int DCID,String title,double ISBN,String authorName,int authorId,String publisherName,int publisherId,String publisherAddress,String publisherDate,String branchName,int copyNumber,String documentPosition) throws Exception {
		getConnection();
		query="select libraryBranchId from branch where libraryName='"+branchName+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(result.next()) {
			branchId=result.getInt("libraryBranchId");
		}
		
		query="select publisherId from publisher where publisherId='"+publisherId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddPublisher="insert into publisher values('"+publisherId+"','"+publisherName+"','"+publisherAddress+"')";
			statement.executeUpdate(queryAddPublisher);
		}
		
		query="select personId from person where personId='"+authorId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddPerson="insert into person values('"+authorId+"','"+authorName+"')";
			statement.executeUpdate(queryAddPerson);
		}
		
		query="select documentId from document where documentId='"+DCID+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddDocument="insert into document values('"+DCID+"','"+title+"','"+publisherDate+"','"+publisherId+"')";
			statement.executeUpdate(queryAddDocument);
		}
		
		query="select documentId from book where documentId='"+DCID+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddBook="insert into book values('"+DCID+"','"+ISBN+"')";
			statement.executeUpdate(queryAddBook);
		}
		
		query="select authorId from author where documentId='"+DCID+"' and authorId='"+authorId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddAuthor="insert into author values('"+DCID+"','"+authorId+"')";
			statement.executeUpdate(queryAddAuthor);
		}
		
		query="select documentId from copy where documentId='"+DCID+"' and copyNumber='"+copyNumber+"' and libraryBranchId='"+branchId+"' and copyPosition='"+documentPosition+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddCopy="insert into copy values('"+DCID+"','"+copyNumber+"','"+branchId+"','"+documentPosition+"')";
			statement.executeUpdate(queryAddCopy);
		}
		else {
			return false;
		}
		
		return true;
	}
	
	public boolean addJournal(int DCID,String title,int volumeNumber,double volumeIssueNumber,String volumeScope,String authorName,int authorId,int guestId,String publisherName,int publisherId,String publisherAddress,String publisherDate,String branchName,int copyNumber,String documentPosition) throws Exception {
		getConnection();
		query="select libraryBranchId from branch where libraryName='"+branchName+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(result.next()) {
			branchId=result.getInt("libraryBranchId");
		}
		
		query="select publisherId from publisher where publisherId='"+publisherId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddPublisher="insert into publisher values('"+publisherId+"','"+publisherName+"','"+publisherAddress+"')";
			statement.executeUpdate(queryAddPublisher);
		}
		
		query="select personId from person where personId='"+authorId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddPerson="insert into person values('"+authorId+"','"+authorName+"')";
			statement.executeUpdate(queryAddPerson);
		}
		
		query="select documentId from document where documentId='"+DCID+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddDocument="insert into document values('"+DCID+"','"+title+"','"+publisherDate+"','"+publisherId+"')";
			statement.executeUpdate(queryAddDocument);
		}
		
		query="select documentId from journal_volume where documentId='"+DCID+"' and volumeNumber='"+volumeNumber+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddVolume="insert into journal_volume values('"+DCID+"','"+volumeNumber+"','"+authorId+"')";
			statement.execute(queryAddVolume);
		}
		
		query="select documentId from journal_issue where documentId='"+DCID+"' and journal_issueNumber='"+volumeIssueNumber+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddVolumeIssue="insert into journal_issue values('"+volumeIssueNumber+"','"+DCID+"','"+volumeScope+"')";
			statement.execute(queryAddVolumeIssue);
		}
		
		query="select guesteditorDoc from guesteditor where guesteditorDoc='"+DCID+"' and guesteditorIssueNumber='"+volumeIssueNumber+"' and guesteditorId='"+guestId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddVolumeGuest="insert into guesteditor values('"+guestId+"','"+DCID+"','"+volumeIssueNumber+"')";
			statement.execute(queryAddVolumeGuest);
		}
		
		query="select documentId from copy where documentId='"+DCID+"' and copyNumber='"+copyNumber+"' and libraryBranchId='"+branchId+"' and copyPosition='"+documentPosition+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddCopy="insert into copy values('"+DCID+"','"+copyNumber+"','"+branchId+"','"+documentPosition+"')";
			statement.executeUpdate(queryAddCopy);
		}
		
		else {
			return false;
		}
		return true;
	}
	
	public boolean addChairConference(int DCID,String title,String authorName,int authorId,String conferenceLocation,String conferenceDate,int conferenceEditorId,String publisherName,int publisherId,String publisherAddress,String publisherDate,String branchName,int copyNumber,String documentPosition) throws Exception {
		getConnection();
		query="select libraryBranchId from branch where libraryName='"+branchName+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(result.next()) {
			branchId=result.getInt("libraryBranchId");
		}
		
		query="select publisherId from publisher where publisherId='"+publisherId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddPublisher="insert into publisher values('"+publisherId+"','"+publisherName+"','"+publisherAddress+"')";
			statement.executeUpdate(queryAddPublisher);
		}
		
		query="select personId from person where personId='"+authorId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddPerson="insert into person values('"+authorId+"','"+authorName+"')";
			statement.executeUpdate(queryAddPerson);
		}
		
		query="select documentId from document where documentId='"+DCID+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddDocument="insert into document values('"+DCID+"','"+title+"','"+publisherDate+"','"+publisherId+"')";
			statement.executeUpdate(queryAddDocument);
		}
		
		query="select documentId from proceeding where documentId='"+DCID+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddBook="insert into proceeding values('"+DCID+"','"+conferenceDate+"','"+conferenceLocation+"','"+conferenceEditorId+"')";
			statement.executeUpdate(queryAddBook);
		}
		
		query="select documentId from chairs where documentId='"+DCID+"' and chairsId='"+authorId+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddAuthor="insert into chairs values('"+authorId+"','"+DCID+"')";
			statement.executeUpdate(queryAddAuthor);
		}
		
		query="select documentId from copy where documentId='"+DCID+"' and copyNumber='"+copyNumber+"' and libraryBranchId='"+branchId+"' and copyPosition='"+documentPosition+"'";
		statement=connect.createStatement();
		result=statement.executeQuery(query);
		if(!result.next()) {
			queryAddCopy="insert into copy values('"+DCID+"','"+copyNumber+"','"+branchId+"','"+documentPosition+"')";
			statement.executeUpdate(queryAddCopy);
		}
		else {
			return false;
		}
		
		return true;
	}
	
	public List<Map> display() throws Exception {
		getConnection();
		query="select * from copy";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		list.clear();
		while(result.next()){
			int dcid= result.getInt("documentId");
			int copyNum=result.getInt("copyNumber");
			int branchId=result.getInt("libraryBranchId");
			String copyPosition=result.getString("copyPosition");
			Map map= new LinkedHashMap<String, Object>();
			map.put("documentId",dcid);
			map.put("copyNumber", copyNum);
			map.put("branchId",branchId);
			map.put("copyPosition",copyPosition);
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByAll() throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		
		
		while(result.next()) {
			
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			
			list.add(map);

		}
		
		
		return list;
	}
	
	public List<Map> searchBookById(int bookId) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentId like '"+bookId+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition = result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	
	public List<Map> searchBookByTitle(String Title) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentTitle like '"+Title+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByPublisher(String publishername) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND p.publisherName like '"+publishername+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByBranchLocation(String Location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND b.libraryName like '"+Location+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByIdAndBookTitle(int bookId,String title) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentId like '"+bookId+"%' AND d.documentTitle like '"+title+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);

			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByIdAndPublisher(int bookId,String publisher) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentId like '"+bookId+"%' AND p.publisherName like '"+publisher+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByTitleAndPublisher(String Title,String publisher) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentTitle like '"+Title+"%' AND p.publisherName like '"+publisher+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByIdAndBranch(int bookId,String location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentId like '"+bookId+"%' AND b.libraryName like '"+location+"%' ";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber =result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByTitleAndBranch(String Title,String location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentTitle like '"+Title+"' AND b.libraryName like '"+location+"'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByPublisherAndBranch(String publishername, String location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND p.publisherName like '"+publishername+"%' AND b.libraryName like '"+location+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByIdAndBookTitleAndBranch(int bookId,String title,String location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentId like '"+bookId+"%' AND d.documentTitle like '"+title+"%' AND b.libraryName like '"+location+"%' ";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition = result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByIdAndPublisherAndBranch(int bookId,String publisher,String location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentId like '"+bookId+"%' AND p.publisherName like '"+publisher+"%' AND b.libraryName like '"+location+"%' ";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchBookByTitleAndPublisherAndBranch(String Title,String publisher,String location) throws Exception{
		getConnection();
		query="select d.documentId,d.documentTitle,c.copyNumber,c.copyPosition,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation from document as d,copy as c,publisher as p,branch as b where d.documentId=c.documentId AND c.libraryBranchId=b.libraryBranchId AND d.documentPublisherId=p.publisherId AND d.documentTitle like '"+Title+"%' AND p.publisherName like '"+publisher+"%' AND b.libraryName like '"+location+"%'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		System.out.println("number of row"+result);
		list.clear();
		while(result.next()) {
			int dcid= result.getInt("documentId");
			String bookTitle=result.getString("documentTitle");
			String copyNumber=result.getString("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map= new LinkedHashMap<String, Object>();
			map.put("dcid", dcid);
			map.put("bookTitle",bookTitle);
			map.put("copyNumber", copyNumber);
			map.put("copyPosition", copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",branchId);
			map.put("branchName", branchName);
			map.put("branchLocation",branchLocation);
			
			query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1=connect.createStatement();
			result1=statement1.executeQuery(query);
			if(result1.last()) {
				if(result1.getString("borrowReturnDate")!=null) {
					query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
					statement3=connect.createStatement();
					result3=statement3.executeQuery(query1);
					if(result3.next()) {
						map.put("Reserved Status","Not Available");
					}
					else {
						map.put("Reserved Status","Available");
					}
				}
				else {
				map.put("Reserved Status","Not Available");
				}
			}
			else {
				query1="select documentId from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement3=connect.createStatement();
				result3=statement3.executeQuery(query1);
				if(result3.next()) {
					map.put("Reserved Status","Not Available");
				}
				else {
					map.put("Reserved Status","Available");
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public boolean searchReserveAndBorrowBook(int rid,int dcid,int copyNumber,int branchId) throws Exception {
		System.out.println("rid"+rid+"dcid"+dcid+"copyNumber"+copyNumber+"branchId"+branchId);
		getConnection();
		query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		if(result.last()) {
			if(result.getString("borrowReturnDate")!=null) {
				query="select * from reserves  where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
				statement1 = connect.createStatement();
				result1=statement1.executeQuery(query); 
				if(result1.next()) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else {
			query="select * from reserves  where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
			statement1 = connect.createStatement();
			result1=statement1.executeQuery(query); 
			if(result1.next()) {
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	public boolean searchBorrowBook(int rid,int dcid,int copyNumber,int branchId) throws Exception {
		System.out.println("rid"+rid+"dcid"+dcid+"copyNumber"+copyNumber+"branchId"+branchId);
		getConnection();
		query="select borrowReturnDate from borrows,borrowtransaction where borrowsId=borrowTransactionNumber and readerId='"+rid+"' and documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"'";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		if(result.last()) {
			if(result.getString("borrowReturnDate") != null) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
				return true;
		}
	}
	
	
	public boolean insertReserveBook(int rid,int dcid,int copyNumber,int branchId,String todayDate,String todayTime) throws Exception {
		System.out.println(rid+"<====>"+dcid+"<====>"+copyNumber+"<=====>"+branchId+"<===>");
		getConnection();
		query="select count(*) as totalRecord from reserves where readerID='"+rid+"' ";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		
		while(result.next()) {
			int totalReserve=0;
			System.out.println(result.getInt("totalRecord"));
			if(result.getInt("totalRecord") > 9) {
				return false;
			}
			
		}
		query="insert into reservation (reservationDate,reservationTime) values('"+todayDate+"','"+todayTime+"')";
		if(statement.execute(query)) {
			return false;
		}
		else {
			query="select reservationNumber from reservation where reservationTime='"+todayTime+"'";
			result1=statement.executeQuery(query);
			int reserveNum=0;
			while(result1.next()) {
				reserveNum=result1.getInt("reservationNumber");
			}
			query="insert into reserves values('"+reserveNum+"','"+dcid+"','"+copyNumber+"','"+branchId+"','"+rid+"')";
			if(statement.execute(query)) {
				return false;
			}
			else {
				return true;
			}
		}
		
	}
	
	public boolean cancelReservation(int rid,int dcid,int copyNumber,int branchId) throws Exception {
		getConnection();
		query = "delete from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"' and readerID='"+rid+"'";
		statement=connect.createStatement();
		int rowCount= statement.executeUpdate(query);
		if(rowCount==0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean insertBorrowBook(int rid,int dcid,int copyNumber,int branchId,String todayDate,String todayTime) throws Exception {
		System.out.println(rid+"<====>"+dcid+"<====>"+copyNumber+"<=====>"+branchId+"<===>");
		getConnection();
		query="select count(*) as totalRecord from borrows,borrowtransaction where borrowTransactionNumber=borrowsId and readerID='"+rid+"' and borrowReturnDate IS NULL";
		statement = connect.createStatement();
		result=statement.executeQuery(query);
		
		while(result.next()) {
			int totalReserve=0;
			System.out.println("<*******=======******>"+result.getInt("totalRecord"));
			totalReserve=result.getInt("totalRecord");
			if(totalReserve > 9) {
				return false;
			}
			
		}
		query="select * from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"' and readerID='"+rid+"'";
		result=statement.executeQuery(query); 
		
		if(result.next()) {
			System.out.println("inside Whlie");
			query = "delete from reserves where documentId='"+dcid+"' and copyNumber='"+copyNumber+"' and libraryId='"+branchId+"' and readerID='"+rid+"'";
			statement.executeUpdate(query);
		}
		System.out.println("out side while");
		query="insert into borrowtransaction (borrowTransactionDate,borrowTransactionTime) values('"+todayDate+"','"+todayTime+"')";
		if(statement.execute(query)) {
			return false;
		}
		else {
			query="select borrowTransactionNumber from borrowtransaction where borrowTransactionTime='"+todayTime+"'";
			result1=statement.executeQuery(query);
			int borrowNum=0;
			while(result1.next()) {
				borrowNum=result1.getInt("borrowTransactionNumber");
			}
			query="insert into borrows values('"+borrowNum+"','"+dcid+"','"+copyNumber+"','"+branchId+"','"+rid+"')";
			if(statement.execute(query)) {
				return false;
			}
			else {
				return true;
			}
		}
		
	}
	
	public List<Map> reserveBookDetails(int rid) throws Exception{
		getConnection();
		String query1="select d.documentId,d.documentTitle,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation,c.copyPosition,c.copyNumber,r.reservesId from document as d,copy as c,reserves as r,branch as b,publisher as p where d.documentId=c.documentId and d.documentPublisherId=p.publisherId AND c.copyNumber=r.copyNumber  And c.libraryBranchId=r.libraryId And c.documentId=r.documentId and c.libraryBranchId=b.libraryBranchId and r.readerID='"+rid+"' ";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		while(result.next()) {
			int documentId=result.getInt("documentId");
			String documentTitle=result.getString("documentTitle");
			int copyNumber=result.getInt("copyNumber");
			String copyPosition=result.getString("copyPosition");
			String publisherName=result.getString("publisherName");
			int libraryId=result.getInt("libraryBranchId");
			String libraryName=result.getString("libraryName");
			String libraryLocation=result.getString("libraryLocation");
			
			Map map = new LinkedHashMap<String, Object>();
			map.put("dcid",documentId);
			map.put("bookTitle",documentTitle);
			map.put("copyNumber",copyNumber);
			map.put("copyPosition",copyPosition);
			map.put("publisherName",publisherName);
			map.put("branchId",libraryId);
			map.put("branchName",libraryName);
			map.put("branchLocation",libraryLocation);
			list.add(map);
		}
		return list;
	}
	
	public List<Map> borrowBookDetails(int rid) throws Exception{
		getConnection();
		String query1="select d.documentId,d.documentTitle,p.publisherId,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation,c.copyPosition,c.copyNumber,bor.borrowsId from document as d,copy as c,borrows as bor,branch as b,publisher as p,borrowtransaction as borTran where d.documentId=c.documentId and d.documentPublisherId=p.publisherId AND c.copyNumber=bor.copyNumber  And c.libraryBranchId=bor.libraryId And c.documentId=bor.documentId and c.libraryBranchId=b.libraryBranchId and bor.borrowsId=borTran.borrowTransactionNumber and bor.readerID='"+rid+"' and borTran.borrowReturnDate IS NULL ";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		while(result.next()) {
			int documentId=result.getInt("documentId");
			String documentTitle=result.getString("documentTitle");
			int copyNumber=result.getInt("copyNumber");
			String copyPosition=result.getString("copyPosition");
			int publisherId=result.getInt("publisherId");
			String publisherName=result.getString("publisherName");
			int libraryId=result.getInt("libraryBranchId");
			String libraryName=result.getString("libraryName");
			String libraryLocation=result.getString("libraryLocation");
			
			Map map = new LinkedHashMap<String, Object>();
			map.put("dcid",documentId);
			map.put("bookTitle",documentTitle);
			map.put("copyNumber",copyNumber);
			map.put("copyPosition",copyPosition);
			map.put("publisherId",publisherId);
			map.put("publisherName",publisherName);
			map.put("branchId",libraryId);
			map.put("branchName",libraryName);
			map.put("branchLocation",libraryLocation);
			list.add(map);
		}
		return list;
	}
	
	public List<Map> returnBookDetails(int rid,int dcid,int copyNum,int branchId,int publisherID) throws Exception{
		getConnection();
		String query1="select d.documentId,d.documentTitle,p.publisherName,b.libraryBranchId,b.libraryName,b.libraryLocation,bor.copyNumber,bor.borrowsId,borTrans.borrowTransactionTime,borTrans.borrowTransactionDate from document as d,branch as b,publisher as p,borrows as bor,borrowtransaction as borTrans where bor.documentId=d.documentId and bor.libraryId=b.libraryBranchId and bor.borrowsId=borTrans.borrowTransactionNumber and bor.documentId='"+dcid+"' and bor.libraryId='"+branchId+"' and bor.copyNumber='"+copyNum+"' and readerId='"+rid+"' and publisherId='"+publisherID+"' and borTrans.borrowReturnDate IS NULL";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		Map map = new LinkedHashMap<String, Object>();
		map.clear();
		while(result.next()) {
			int documentId=result.getInt("documentId");
			String documentTitle=result.getString("documentTitle");
			int copyNumber=result.getInt("copyNumber");
			String publisherName=result.getString("publisherName");
			int libraryId=result.getInt("libraryBranchId");
			String libraryName=result.getString("libraryName");
			String libraryLocation=result.getString("libraryLocation");
			String borrowDate=result.getString("borrowTransactionDate");
			String borrowTime=result.getString("borrowTransactionTime");
			int borrowId=result.getInt("borrowsId");
			
			map.put("Book Id",documentId);
			map.put("BookTitle",documentTitle);
			map.put("CopyNumber",copyNumber);
			map.put("PublisherName",publisherName);
			map.put("BranchId",libraryId);
			map.put("Branch Name",libraryName);
			map.put("Branch Location",libraryLocation);
			map.put("TranscationID",borrowId);
			map.put("Borrow Date",borrowDate);
			map.put("Borrow Time", borrowTime);
			
			list.add(map);
		}
		
		return list;
	}
	
	public boolean returnBook(int dcid,int copyNumber,int branchId,int rid,double fineAmount,int borrowId,String returnDate,String returnTime) throws Exception {
		getConnection();
		query="Update borrowtransaction set borrowReturnDate='"+returnDate+"',borrowReturnTime='"+returnTime+"',fineAmount='"+fineAmount+"' where borrowTransactionNumber='"+borrowId+"'";
		statement=connect.createStatement();
		if(statement.execute(query)) {
			return false;
		}
		else {
			System.out.println("Record Updated !!!!");
			return true;
		}
		
	}
	
	public List<Map> branchInformation() throws Exception{
		getConnection();
		statement=connect.createStatement();
		query="select * from branch";
		result=statement.executeQuery(query);
		list.clear();
		while(result.next()) {
			int branchId=result.getInt("libraryBranchId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			
			Map map = new LinkedHashMap<String,Object>();
			map.put("branchId",branchId);
			map.put("branchName",branchName);
			map.put("branchLocation",branchLocation);
			list.add(map);
		}
		return list;
	}
	
	public List<Map> searchTopMostBorrower(int numberForList,String branchName) throws Exception{
		getConnection();
		System.out.println("NumberForList======>"+numberForList+"<========>"+branchName);
		if(!(branchName.equals("Not Select"))) {
			query="select libraryBranchId from branch where libraryName='"+branchName+"'";
			statement=connect.createStatement();
			result=statement.executeQuery(query);
			if(result.next()) {
				branchId=result.getInt("libraryBranchId");
			}
			
			queryForTopNReaderList="select RID,readerName,count(documentId) as totalBorrowed from borrows,reader,branch where readerId=RID and libraryBranchId=libraryId and libraryId='"+branchId+"' group by readerId order by totalBorrowed DESC limit "+numberForList+"";
			statement=connect.createStatement();
			result=statement.executeQuery(queryForTopNReaderList);
			list.clear();
			while(result.next()) {
				
				int readerId=result.getInt("RID");
				String readerName=result.getString("readerName");
				int totalborrowed=result.getInt("totalBorrowed");
				
				Map map = new LinkedHashMap<String, Object>();
				map.put("readerId",readerId);
				map.put("readerName",readerName);
				map.put("totalBorrowed",totalborrowed);
				
				list.add(map);
			}
			System.out.println("List data"+list);
			return list;
		}
		else {
			queryForTopNReaderList="select readerId,readerName,count(b.documentId) as totalborrowed from borrows as b,reader where readerId=RID group by b.readerId order by count(b.documentId) desc limit "+numberForList+"";
			statement=connect.createStatement();
			result=statement.executeQuery(queryForTopNReaderList);
			list.clear();
			while(result.next()) {
				
				int readerId=result.getInt("readerId");
				String readerName=result.getString("readerName");
				int totalborrowed=result.getInt("totalBorrowed");
				
				Map map = new LinkedHashMap<String, Object>();
				map.put("readerId",readerId);
				map.put("readerName",readerName);
				map.put("totalBorrowed",totalborrowed);
				
				list.add(map);
			}
			System.out.println("List data"+list);
			return list;
		}
		
	}
	
	public List<Map> mostBorrowBookBranch(int mostNumber,String branchName) throws Exception{
		getConnection();
		String query1="select documentTitle,b.documentId,count(b.documentId) as mostBorrow from borrows as b,document as d  where b.documentId=d.documentId and b.libraryId in( select libraryBranchId from branch where libraryName='"+branchName+"') group by b.documentId order by count(b.documentId) desc  limit "+mostNumber+"";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		
		while(result.next()) {
			int documentId=result.getInt("documentId");
			String documentTitle=result.getString("documentTitle");
			int mostBorrowed=result.getInt("mostBorrow");
			Map map = new LinkedHashMap<String, Object>();
			map.put("Book Id",documentId);
			map.put("BookTitle",documentTitle);
			map.put("Borrowed", mostBorrowed);
			
			list.add(map);
		}
		
		return list;
	}
	
	public List<Map> mostBorrowBookLibrary(int mostNumber) throws Exception{
		getConnection();
		String query1="select *,count(b.documentId) as mostBorrow from borrows as b ,document as d where b.documentId=d.documentId group by b.documentId order by count(b.documentId) desc limit "+mostNumber+"";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		
		while(result.next()) {
			int documentId=result.getInt("documentId");
			String documentTitle=result.getString("documentTitle");
			int mostBorrowed=result.getInt("mostBorrow");
			Map map = new LinkedHashMap<String, Object>();
			map.put("Book Id",documentId);
			map.put("BookTitle",documentTitle);
			map.put("Borrowed", mostBorrowed);
			
			list.add(map);
		}
		return list;
	}
	
	public List<Map> mostPopularBookOfYearInLibrary(double year) throws Exception{
		getConnection();
		String query1="select b.documentId,d.documentTitle,count(b.documentId) as mostBorrowYear from borrows as b,document as d where b.documentId=d.documentId and b.borrowsId in (select borrowTransactionNumber from borrowTransaction where year(borrowTransactionDate)='"+year+"') group by b.documentId order by mostBorrowYear desc limit 10";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		
		while(result.next()) {
			int documentId=result.getInt("documentId");
			String documentTitle=result.getString("documentTitle");
			int mostBorrowed=result.getInt("mostBorrowYear");
			
			Map map = new LinkedHashMap<String, Object>();
			map.put("Book Id",documentId);
			map.put("BookTitle",documentTitle);
			map.put("Borrowed", mostBorrowed);
			
			list.add(map);
		}
		return list;
	}
	
	public List<Map> calculateFineInPeriod(String startDate,String endDate) throws Exception{
		getConnection();
		String query1="select *,avg(fineAmount) from borrows,borrowtransaction,branch where libraryId=libraryBranchId and borrowTransactionNumber=borrowsId and libraryId in(select libraryId from borrows,borrowtransaction where borrowTransactionNumber=borrowsId and borrowTransactionDate between '"+startDate+"' and '"+endDate+"') group by libraryId;";
		statement=connect.createStatement();
		result=statement.executeQuery(query1);
		list.clear();
		while(result.next()) {
			int branchId=result.getInt("libraryId");
			String branchName=result.getString("libraryName");
			String branchLocation=result.getString("libraryLocation");
			float fine=result.getFloat("avg(fineAmount)");
			System.out.println("fine======>"+result.getDouble("avg(fineAmount)"));
			Map map = new LinkedHashMap<String, Object>();
			
			map.put("branchId",branchId);
			map.put("branchName",branchName);
			map.put("branchLocation",branchLocation);
			map.put("fine",fine);
			
			list.add(map);
		}
		return list;
	}
	
}
       
        
