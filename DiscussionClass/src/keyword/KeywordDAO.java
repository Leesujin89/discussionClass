package keyword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import chat.ChatDTO;

public class KeywordDAO {
	DataSource dataSource;
	
	public KeywordDAO() {
		try {
			InitialContext initContext=new InitialContext();
			Context envContext=(Context) initContext.lookup("java:/comp/env");
			dataSource=(DataSource) envContext.lookup("jdbc/DiscussionClass");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//주제 넣기 메소드 
	public int subjectSubmit(String userID, String subject) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO keyword VALUES (?, '', ?, now())";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, subject);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	//주제 불러오기 메소드 
	public KeywordDTO getSubjet() {
		KeywordDTO keyword=new KeywordDTO();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT subject FROM keyword WHERE userID= 'admin' ORDER BY subjectTime DESC LIMIT 1";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				keyword.setSubject(rs.getString("subject"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return keyword;
	}
	//keyword 삽입 메소드 
	public int keywordSubmit(String userID, String keyword, String subject) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO keyword VALUES (?, ?, ? , now())";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, keyword);
			pstmt.setString(3, subject);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(pstmt !=null) pstmt.close();
				if(conn !=null) conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	//keyword 불러오는 메소드
	public ArrayList getKeyword(String subject) {
		ArrayList<KeywordDTO> keywordList=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT keyword.userID, user.userName, keyword.keyword FROM keyword JOIN user ON keyword.userID = user.userID WHERE keyword.subject = ? AND keyword.userID NOT LIKE 'admin'";
		
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, subject);
			rs=pstmt.executeQuery();
			keywordList=new ArrayList<KeywordDTO>();
			while(rs.next()) {
				KeywordDTO keyword=new KeywordDTO();
				keyword.setUserID(rs.getString("userID"));
				keyword.setUserName(rs.getString("userName"));
				keyword.setKeyword(rs.getString("keyword"));
				keywordList.add(keyword);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	return keywordList;
	}
}
