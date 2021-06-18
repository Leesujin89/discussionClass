package keyword;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/KeywordSubmitServlet")
public class KeywordSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID=request.getParameter("userID");
		String keyword=request.getParameter("keyword");
		String subject=request.getParameter("subject");
		
		HttpSession session= request.getSession();
		if(!userID.equals((String)session.getAttribute("userID"))) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","다시 로그인 해 주세요");
			response.sendRedirect("login.jsp");
			return;
		}
		if(userID.equals("")|| userID==null || keyword.equals("")||keyword==null || subject.equals("")|| subject==null) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","내용을 입력하세요");
			response.sendRedirect("keyword.jsp");
			return;
		}
		
		int result =new KeywordDAO().keywordSubmit(userID, keyword, subject);
		if(result ==1) {
			request.getSession().setAttribute("messageType", "성공메시지");
			request.getSession().setAttribute("messageContent","등록이 완료되었습니다.");
			response.sendRedirect("keywordShow.jsp");
			return;
		}
		else {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","데이터베이스오류.");
			response.sendRedirect("keyword.jsp");
			return;
		}
	}

}
