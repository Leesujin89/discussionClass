package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatDAO {
	DataSource dataSource;
	
	public ChatDAO() {
		try {
			InitialContext initContext=new InitialContext();
			Context envContext=(Context) initContext.lookup("java:/comp/env");
			dataSource=(DataSource) envContext.lookup("jdbc/DiscussionClass");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//채팅 전송 메소드 
	public int chatSubmit(String userID, String roomID, String chatContent) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO chat SELECT IFNULL((SELECT MAX(chatID) +1 FROM chat), 1), ?, ?, ?, NOW(), 0";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setInt(2, Integer.parseInt(roomID));
			pstmt.setString(3, chatContent);
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
	
	//채팅 리스트 메소드 (roomID)기준으로 
	public ArrayList getChatList(String roomID, String chatID) {
		ArrayList<ChatDTO> chatList=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT chat.chatID, chat.userID, chat.roomID, chat.chatContent, chat.chatTime, user.userProfile FROM chat JOIN user ON chat.userID = user.userID \r\n"
				+ "WHERE chat.roomID = ? AND chatID > ?;";
		
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(roomID));
			pstmt.setInt(2, Integer.parseInt(chatID));
			rs=pstmt.executeQuery();
			chatList=new ArrayList <ChatDTO>();
			while(rs.next()) {
				ChatDTO chat=new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setUserID(rs.getString("userID"));
				chat.setRoomID(rs.getInt("roomID"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatTime(rs.getString("chatTime"));
				chat.setMemberProfile(rs.getString("userProfile"));
				chatList.add(chat);
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
	return chatList;
	}
}
