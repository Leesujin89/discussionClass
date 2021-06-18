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
		
		//��� ������ ���� �Ѿ���� ���� �� ����ó�� 
		if (userID.equals("")|| userID==null || userPWD1.equals("")||userPWD1==null|| userPWD2.equals("")|| userPWD2==null || userName.equals("")|| userName ==null||
		    userEmail.equals("")|| userEmail ==null || groupNumber.equals("")|| groupNumber==null) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","��� ������ �Է��ϼ���");
			response.sendRedirect("join.jsp");
			return;
		}
		//��й�ȣ�� ���� ��ġ���� ���� ��� 
		if (!userPWD1.equals(userPWD2)) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","��й�ȣ�� ���� ��ġ���� �ʽ��ϴ�.");
			response.sendRedirect("join.jsp");
			return;
		}
		int result =new UserDAO().join(userID, userPWD1, userName, userEmail, groupNumber, ""); //ȸ������ �޼ҵ� ���� 
		if (result == 1) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","ȸ�������� �Ǿ����ϴ�.");
			response.sendRedirect("main.jsp");
			return;
		}
		else {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","�̹� �����ϴ� ȸ���Դϴ�.");
			response.sendRedirect("login.jsp");
			return;
		}
		
	}

}
