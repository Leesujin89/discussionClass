package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID=request.getParameter("userID");
		String userPWD=request.getParameter("userPWD");
		
		//모든 변수의 값이 넘어오지 않을 때 예외처리 
		if (userID.equals("")|| userID==null || userPWD.equals("")||userPWD==null) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","모든 내용을 입력하세요");
			response.sendRedirect("login.jsp");
			return;
		}
		
		int result =new UserDAO().login(userID, userPWD); 
		if (result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent","환영합니다.");
			response.sendRedirect("main.jsp");
			return;
		}
		else if (result == 2){
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","비밀번호를 확인 해 주세요");
			response.sendRedirect("login.jsp");
			return;
		}
		else if (result == 0){
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","해당 아이디가 존재하지 않습니다.");
			response.sendRedirect("login.jsp");
			return;
		}
		else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","데이터베이스 오류");
			response.sendRedirect("login.jsp");
			return;
		}
	}

}
