package score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import user.UserDTO;

public class ScoreDAO {

DataSource dataSource;
	
	public ScoreDAO() {
		try {
			InitialContext initContext=new InitialContext();
			Context envContext=(Context) initContext.lookup("java:/comp/env");
			dataSource=(DataSource) envContext.lookup("jdbc/DiscussionClass");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//���� �ֱ� �޼ҵ� 
	public int userScoreSubmit(String fromID, String toID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="INSERT INTO score SELECT IFNULL((SELECT MAX(scoreID) +1 FROM score), 1), ?, ?, 10, 0, 1";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
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
	//���� �ջ� �޼ҵ�
	public ScoreDTO userScoreTotal(String toID) {
		ScoreDTO score=new ScoreDTO();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT SUM(userScore) AS userScore FROM score WHERE toID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, toID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				score.setUserScore(rs.getInt("userScore"));
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
		return score;
	}
	//���� �ޱ� �޼ҵ�
	public ArrayList getScore(String toID) {
		ArrayList<ScoreDTO> scoreList=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String SQL="SELECT * FROM score WHERE toID = ? AND available = 1";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, toID);
			rs=pstmt.executeQuery();
			scoreList=new ArrayList<ScoreDTO>();
			while(rs.next()) {
				ScoreDTO score=new ScoreDTO();
				score.setScoreID(rs.getInt("scoreID"));
				score.setFromID(rs.getString("fromID"));
				score.setToID(rs.getString("toID"));
				score.setUserScore(rs.getInt("userScore"));
				score.setGroupScore(rs.getInt("groupScore"));
				scoreList.add(score);
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
		return scoreList;
	}
	//available ��������Ʈ �޼ҵ� 
	public int scoreUpdate(String toID) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		String SQL="UPDATE score SET available= 0 WHERE toID = ?";
		try {
			conn=dataSource.getConnection();
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, toID);
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
	
}
