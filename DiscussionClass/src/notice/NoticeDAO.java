package notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class NoticeDAO {

DataSource dataSource;
	
	public NoticeDAO() {
		try {
			InitialContext initContext=new InitialContext();
			Context envContext=(Context) initContext.lookup("java:/comp/env");
			dataSource=(DataSource) envContext.lookup("jdbc/DiscussionClass");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//�� ��� �޼ҵ� 
	public int notice(String userID, String noticeTitle, String noticeContent, String noticeFile, String noticeRealFile) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO notice SELECT IFNULL((SELECT MAX(noticeID)+1 FROM notice), 1), ?, ?, ?, NOW(), ?, ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, noticeTitle);
			pstmt.setString(3, noticeContent);
			pstmt.setString(4, noticeFile);
			pstmt.setString(5, noticeRealFile);
			return pstmt.executeUpdate(); //insert �� ��ŭ ��ȯ 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (pstmt!=null) pstmt.close();
				if (conn !=null) conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //�����ͺ��̽� ���� �� -1��ȯ 
	}
	//�� ����Ʈ �ҷ����� 
	public ArrayList<NoticeDTO> getNoticeList(){
		ArrayList<NoticeDTO> noticeList=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM notice ORDER BY noticeID DESC";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			noticeList=new ArrayList<NoticeDTO>();
			while(rs.next()) {
				NoticeDTO notice=new NoticeDTO();
				notice.setNoticeID(rs.getInt("noticeID"));
				notice.setUserID(rs.getString("userID"));
				notice.setNoticeTitle(rs.getString("noticeTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				notice.setNoticeContent(rs.getString("noticeContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				notice.setNoticeDate(rs.getString("noticeDate").substring(0, 11));
				notice.setNoticeFile(rs.getString("noticeFile"));
				notice.setNoticeRealFile(rs.getString("noticeRealFile"));
				noticeList.add(notice);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null)  conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return noticeList;
	}
	//�ϳ��� ������ �ҷ����� 
	public NoticeDTO getNotice(String noticeID) {
		NoticeDTO notice=new NoticeDTO();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM notice WHERE noticeID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(noticeID));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				notice.setNoticeID(rs.getInt("noticeID"));
				notice.setUserID(rs.getString("userID"));
				notice.setNoticeTitle(rs.getString("noticeTitle"));
				notice.setNoticeContent(rs.getString("noticeContent"));
				notice.setNoticeDate(rs.getString("noticeDate"));
				notice.setNoticeFile(rs.getString("noticeFile"));
				notice.setNoticeRealFile(rs.getString("noticeRealFile"));
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
		return notice;
	}
	//�������� ����� �޼ҵ� 
	public int noticeDelete(String noticeID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="DELETE FROM notice WHERE noticeID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(noticeID));
			return pstmt.executeUpdate(); //delete �� ��ŭ ��ȯ 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (pstmt!=null) pstmt.close();
				if (conn !=null) conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //�����ͺ��̽� ���� �� -1��ȯ 
	}
	//�������� ���� �޼ҵ� 
	public int noticeUpdate(String noticeID, String userID, String noticeTitle, String noticeContent, String noticeFile, String noticeRealFile) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="UPDATE notice SET userID = ?, noticeTitle = ?, noticeContent = ?, noticeFile = ?, noticeRealFile = ? WHERE noticeID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, noticeTitle);
			pstmt.setString(3, noticeContent);
			pstmt.setString(4, noticeFile);
			pstmt.setString(5, noticeRealFile);
			pstmt.setInt(6, Integer.parseInt(noticeID));
			return pstmt.executeUpdate(); //update �� ��ŭ ��ȯ 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (pstmt!=null) pstmt.close();
				if (conn !=null) conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //�����ͺ��̽� ���� �� -1��ȯ 
	}
	//�ֱ� �Խñ� 5���� �ҷ����� �޼ҵ� 
	public ArrayList<NoticeDTO> mainNoticeList(){
		ArrayList<NoticeDTO> noticeList=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT noticeID, noticeTitle, noticeDate FROM notice ORDER BY noticeID DESC LIMIT 5";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			noticeList=new ArrayList<NoticeDTO>();
			while(rs.next()) {
				NoticeDTO notice=new NoticeDTO();
				notice.setNoticeID(rs.getInt("noticeID"));
				notice.setNoticeTitle(rs.getString("noticeTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				notice.setNoticeDate(rs.getString("noticeDate").substring(0, 11));
				noticeList.add(notice);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(conn !=null)  conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return noticeList;
	}
	
}
