package room;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RoomDeleteServlet")
public class RoomDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//session값으로 userID값을 받음 
		HttpSession session=request.getSession();
		String userID=(String)session.getAttribute("userID");
		
		String roomID=request.getParameter("roomID");
		//boardID의 예외처리 
		if(roomID.equals("")|| roomID==null) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다."); //오류메시지를 session으로 잡아줌 
			response.sendRedirect("roomList.jsp");
			return;
		}
		//데이터베이스에서 하나의 게시글을 들고옴 현재해당하다 게시물을 
		RoomDAO roomDAO=new RoomDAO();
		RoomDTO room=roomDAO.getRoom(roomID);
		if(!userID.equals(room.getRoomUserID())) {
			//현재로그인한 사용자와 해당게시물의 작성자와 일치하지 않으면 오류 출력 
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다."); //오류메시지를 session으로 잡아줌 
			response.sendRedirect("roomList.jsp");
			return;
		}
		String savePath =request.getRealPath("/upload").replaceAll("\\\\", "/"); //실제 서버의 경로에서 upload의 폴더 안에 파일이 업로드 될 수 있도록 설정 
		String prev=roomDAO.getRoom(roomID).getRoomRealFile();
		int result = roomDAO.roomDelete(roomID); //삭제 메소드 실행 
		if(result ==-1) {
			//삭제 실패시 
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다."); //오류메시지를 session으로 잡아줌 
			response.sendRedirect("roomList.jsp");
			return;
		}
		else {
			//성공적으로 삭제 했다면 기존에 파일을 업로드 했다면 그 파일을 지움 
			File prevFile=new File(savePath+"/"+prev);
			if(prevFile.exists()) {
				//만약 이전 파일이 존재한다면 지워라 
				prevFile.delete();
			}
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent", "게시글이 삭제되었습니다."); //오류메시지를 session으로 잡아줌 
			response.sendRedirect("roomList.jsp");
		}
	}
}
