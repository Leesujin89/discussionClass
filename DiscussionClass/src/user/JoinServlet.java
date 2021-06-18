package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/JoinServlet")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID=request.getParameter("userID");
		String userPWD1=request.getParameter("userPWD1");
		String userPWD2=request.getParameter("userPWD2");
		String userName=request.getParameter("userName");
		String userEmail=request.getParameter("userEmail");
		String groupNumber=request.getParameter("groupNumber");
		
		//모든 변수의 값이 넘어오지 않을 때 예외처리 
		if (userID.equals("")|| userID==null || userPWD1.equals("")||userPWD1==null|| userPWD2.equals("")|| userPWD2==null || userName.equals("")|| userName ==null||
		    userEmail.equals("")|| userEmail ==null || groupNumber.equals("")|| groupNumber==null) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","모든 내용을 입력하세요");
			response.sendRedirect("join.jsp");
			return;
		}
		//비밀번호가 서로 일치하지 않을 경우 
		if (!userPWD1.equals(userPWD2)) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","비밀번호가 서로 일치하지 않습니다.");
			response.sendRedirect("join.jsp");
			return;
		}
		int result =new UserDAO().join(userID, userPWD1, userName, userEmail, groupNumber, ""); //회원가입 메소드 실행 
		if (result == 1) {
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent","회원가입이 되었습니다.");
			response.sendRedirect("main.jsp");
			return;
		}
		else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","이미 존재하는 회원입니다.");
			response.sendRedirect("login.jsp");
			return;
		}
		
	}

}
