package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	DataSource dataSource;
	
	public UserDAO() {
		try {
			InitialContext initContext=new InitialContext();
			Context envContext=(Context) initContext.lookup("java:/comp/env");
			dataSource=(DataSource) envContext.lookup("jdbc/DiscussionClass");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//ȸ������ �޼ҵ� 
	public int join(String userID, String userPWD, String userName, String userEmail, String groupNumber, String userProfile) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, 0, 0, 0)";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPWD);
			pstmt.setString(3, userName);
			pstmt.setString(4, userEmail);
			pstmt.setInt(5, Integer.parseInt(groupNumber));
			pstmt.setString(6, userProfile);
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
	
	//�ߺ�Ȯ�� �޼ҵ� 
	public int checkUserID(String userID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT userID FROM user WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()|| userID.equals("")) {
				return 0; //���� �ְų� userID���� ����� �ִٸ� 0�� ��ȯ�Ͽ� �̹� �����ϴ� ȸ������ �˷��� 
			}
			else return 1; 
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
		return -1;
	}
	
	//�α��� �޼ҵ�
	public int login(String userID, String userPWD) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM user WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("userPWD").equals(userPWD)) {
					return 1;
				}
				else return 2; //��й�ȣ�� ��ġ���� ������ 2��ȯ 
			}
			else return 0; //�ش� userID�� �������� ���� 
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
		return -1;
	}
	
	//ȸ�� ���� �ҷ����� �޼ҵ�
	public UserDTO getUser(String userID) {
		UserDTO user=new UserDTO();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM user WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				user.setUserID(rs.getString("userID"));
				user.setUserPWD(rs.getString("userPWD"));
				user.setUserName(rs.getString("userName"));
				user.setUserEmail(rs.getString("userEmail"));
				user.setUserProfile(rs.getString("userProfile"));
				user.setGroupNumber(rs.getInt("groupNumber"));
				user.setUserScore(rs.getInt("userScore"));
				user.setGroupSocre(rs.getInt("groupScore"));
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
		return user;
	}
	//ȸ������ ���� �޼ҵ� 
	public int userUpdate(String userID, String userPWD, String userName, String userEmail, String groupNumber) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="UPDATE user SET userPWD = ?, userName = ?, userEmail = ?, groupNumber = ? WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userPWD);
			pstmt.setString(2, userName);
			pstmt.setString(3, userEmail);
			pstmt.setInt(4, Integer.parseInt(groupNumber));
			pstmt.setString(5, userID);
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
	
	//������ ���ε� �޼ҵ�
	public int userProfileUpdate(String userID, String userProfile) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="UPDATE user SET userProfile = ? WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userProfile);
			pstmt.setString(2, userID);
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
	
	//ȸ���� ������ ���� �ҷ����� �޼ҵ�
	public String getProfile(String userID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT userProfile FROM user WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("userProfile").equals("")) {
					return "http://localhost:8080/DiscussionClass/images/icon.png";
				}
				return "http://localhost:8080/DiscussionClass/upload/"+rs.getString("userProfile");
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
		return "http://localhost:8080/DiscussionClass/images/icon.png";
	}
	//���������� ����Ʈ �޼ҵ� 
	public ArrayList<UserDTO> nowMemberList(String roomID){
		ArrayList<UserDTO> memberList=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String SQL="SELECT user.userID, user.userName, user.userProfile, user.now FROM user JOIN room ON user.groupNumber = room.roomGroupNumber WHERE room.roomID = ? AND user.userID NOT LIKE 'admin'";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(roomID));
			rs=pstmt.executeQuery();
			memberList=new ArrayList<UserDTO>();
			while(rs.next()) {
				UserDTO user=new UserDTO();
				user.setUserID(rs.getString("userID"));
				user.setUserName(rs.getString("userName"));
				user.setUserProfile(rs.getString("userProfile"));
				user.setNow(rs.getInt("now"));
				memberList.add(user);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return memberList;
	}
	public int onUser(String userID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="UPDATE user SET now=1 WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
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
	public int offUser(String userID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="UPDATE user SET now=0 WHERE userID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
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
	
	//�׷�ѹ� ���� ��� ����Ʈ ��� �޼ҵ� 
	public ArrayList<UserDTO> getMemberList(String groupNumber){
		ArrayList<UserDTO> memberList=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String SQL="SELECT * FROM user WHERE groupNumber = ? AND userID NOT LIKE 'admin'";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(groupNumber));
			rs=pstmt.executeQuery();
			memberList=new ArrayList<UserDTO>();
			while(rs.next()) {
				UserDTO user=new UserDTO();
				user.setUserID(rs.getString("userID"));
				user.setUserName(rs.getString("userName"));
				user.setUserProfile(rs.getString("userProfile"));
				memberList.add(user);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return memberList;
	}
	
}
