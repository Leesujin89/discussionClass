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
		
		//��� ������ ���� �Ѿ���� ���� �� ����ó�� 
		if (userID.equals("")|| userID==null || userPWD.equals("")||userPWD==null) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","��� ������ �Է��ϼ���");
			response.sendRedirect("login.jsp");
			return;
		}
		
		int result =new UserDAO().login(userID, userPWD); 
		if (result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","ȯ���մϴ�.");
			response.sendRedirect("main.jsp");
			return;
		}
		else if (result == 2){
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","��й�ȣ�� Ȯ�� �� �ּ���");
			response.sendRedirect("login.jsp");
			return;
		}
		else if (result == 0){
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","�ش� ���̵� �������� �ʽ��ϴ�.");
			response.sendRedirect("login.jsp");
			return;
		}
		else {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","�����ͺ��̽� ����");
			response.sendRedirect("login.jsp");
			return;
		}
	}

}
