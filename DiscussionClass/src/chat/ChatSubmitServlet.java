package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String cUserID= request.getParameter("userID"); //사용자가 보낸 fromID의 parameter값을 초기화 
		String roomID=request.getParameter("roomID");
		String chatContent=request.getParameter("chatContent");
		
		
		if(cUserID ==null || cUserID.equals("")|| roomID == null || roomID.equals("")|| chatContent==null || chatContent.equals("")) {
			
			//예외처리 
			response.getWriter().write("0"); //사용자에게 0 반환
		}
		else {
			
			// 사용자로부터 받은 값을 UTF-8로 디코딩함
			cUserID=URLDecoder.decode(cUserID, "UTF-8"); 
			roomID=URLDecoder.decode(roomID, "UTF-8");
			chatContent=URLDecoder.decode(chatContent, "UTF-8");
			
			//다른 사람이 내가 보낸 것 처럼 하지 않도록 
			HttpSession session=request.getSession();
			if(!URLDecoder.decode(cUserID, "UTF-8").equals((String)session.getAttribute("userID"))) {
				//만약 session값과 넘어온 userID값이 틀리면 공백 출력 후 return 
				response.getWriter().write("");
				return;
			}
			//메소드 실행 한 결과 값을 사용자에게 반환 
			response.getWriter().write(new ChatDAO().chatSubmit(cUserID, roomID, chatContent)+"");
		}
	}

}
