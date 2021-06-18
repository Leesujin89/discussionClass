package room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RoomDAO {

DataSource dataSource;
	
	public RoomDAO() {
		try {
			InitialContext initContext=new InitialContext();
			Context envContext=(Context) initContext.lookup("java:/comp/env");
			dataSource=(DataSource) envContext.lookup("jdbc/DiscussionClass");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//�游��� �޼ҵ� 
	public int room(String roomUserID, String roomGroupNumber, String roomTitle, String roomContent, String roomFile, String roomRealFile) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO room SELECT ?, ?, IFNULL((SELECT MAX(roomID)+1 FROM ROOM), 1), ?, ?, NOW(), ?, ?, 0";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, roomUserID);
			pstmt.setInt(2,Integer.parseInt(roomGroupNumber));
			pstmt.setString(3, roomTitle);
			pstmt.setString(4, roomContent);
			pstmt.setString(5, roomFile);
			pstmt.setString(6, roomRealFile);
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
	
	//�ϳ��� room ������ �ҷ����� �޼ҵ�  
	public RoomDTO getRoom(String roomID) {
		RoomDTO room=new RoomDTO();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM room WHERE roomID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(roomID));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				room.setRoomUserID(rs.getString("roomUserID"));
				room.setRoomGroupNumber(rs.getInt("roomGroupNumber"));
				room.setRoomID(rs.getInt("roomID"));
				room.setRoomTitle(rs.getString("roomTitle"));
				room.setRoomContent(rs.getString("roomContent"));
				room.setRoomDate(rs.getString("roomDate"));
				room.setRoomFile(rs.getString("roomFile"));
				room.setRoomRealFile(rs.getString("roomRealFile"));
				room.setProgress(rs.getInt("progress"));
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
		return room;
	}
	
	//roomList�ҷ����� �޼ҵ� 
	public ArrayList<RoomDTO> getRoomList(){
		ArrayList<RoomDTO> roomList=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM room ORDER BY roomID DESC";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			roomList=new ArrayList<RoomDTO>();
			while(rs.next()) {
				RoomDTO room=new RoomDTO();
				room.setRoomUserID(rs.getString("roomUserID"));
				room.setRoomGroupNumber(rs.getInt("roomGroupNumber"));
				room.setRoomID(rs.getInt("roomID"));
				room.setRoomTitle(rs.getString("roomTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				room.setRoomContent(rs.getString("roomContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				room.setRoomDate(rs.getString("roomDate").substring(0, 11));
				room.setRoomFile(rs.getString("roomFile"));
				room.setRoomRealFile(rs.getString("roomRealFile"));
				room.setProgress(rs.getInt("progress"));
				roomList.add(room);
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
		return roomList;
	}
	
	//�� ���� �޼ҵ� 
	public int roomDelete(String roomID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="DELETE FROM room WHERE roomID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(roomID));
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
	
	public String getRoomFile(String roomID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT roomRealFile FROM room WHERE roomID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(roomID));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("roomRealFile").equals("")) {
					return "http://localhost:8080/DiscussionClass/images/noImage.png";
				}
				return "http://localhost:8080/DiscussionClass/upload/"+rs.getString("roomRealFile");
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
		return "http://localhost:8080/DiscussionClass/images/noImage.png";
	}
	//����� �ֱ� �޼ҵ�
		public int progressSubmit(String roomID, String progress) {
			Connection conn=null;
			PreparedStatement pstmt=null;
			String SQL="UPDATE room SET progress = ? WHERE roomID = ?";
			try {
				conn=dataSource.getConnection();
				pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, Integer.parseInt(progress));
				pstmt.setInt(2, Integer.parseInt(roomID));
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
		//�ֱ� �Խñ� 5���� �ҷ����� �޼ҵ� 
		public ArrayList<RoomDTO> mainRoomList(){
			ArrayList<RoomDTO> roomList=null;
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String SQL="SELECT * FROM room ORDER BY roomID DESC LIMIT 5";
			try {
				conn=dataSource.getConnection();
				pstmt=conn.prepareStatement(SQL);
				rs=pstmt.executeQuery();
				roomList=new ArrayList<RoomDTO>();
				while(rs.next()) {
					RoomDTO room=new RoomDTO();
					room.setRoomID(rs.getInt("roomID"));
					room.setRoomTitle(rs.getString("roomTitle").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					room.setRoomDate(rs.getString("roomDate").substring(0, 11));
					room.setProgress(rs.getInt("progress"));
					room.setRoomGroupNumber(rs.getInt("roomGroupNumber"));
					room.setRoomUserID(rs.getString("roomUserID"));
					roomList.add(room);
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
			return roomList;
		}
}
