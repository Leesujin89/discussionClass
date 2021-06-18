package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session=request.getSession();
		String userID=request.getParameter("userID");
		
		if (!userID.equals((String)session.getAttribute("userID"))) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","접근 할 수 없습니다 다시 로그인 해 주세요");
			response.sendRedirect("login.jsp");
			return;
		}
		
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
			response.sendRedirect("userUpdate.jsp");
			return;
		}
		//비밀번호가 서로 일치하지 않을 경우 
		if (!userPWD1.equals(userPWD2)) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","비밀번호가 서로 일치하지 않습니다.");
			response.sendRedirect("userUpdate.jsp");
			return;
		}
		int result =new UserDAO().userUpdate(userID, userPWD1, userName, userEmail, groupNumber);
		if (result == 1) {
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent","회원정보 수정이 완료 되었습니다");
			response.sendRedirect("main.jsp");
			return;
		}
		else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","데이터베이스 오류가 발생하였습니다.");
			response.sendRedirect("userUpdate.jsp");
			return;
		}
		
	}

}
