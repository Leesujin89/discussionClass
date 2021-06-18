package room;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import user.UserDAO;

@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		MultipartRequest multi=null; //���� ���ε忡 �ʿ��� ���� ���� 
		int fileMaxSize= 10*1024*1024; //������ �ִ�ũ�� ���� 
		String savePath =request.getRealPath("/upload").replaceAll("\\\\", "/"); //���� ������ ��ο��� upload�� ���� �ȿ� ������ ���ε� �� �� �ֵ��� ���� 
		 try {
			 multi=new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy()); 
			 //����ڷκ��� ��û���� ���������� ��ο� ���� �ִ�ũ��, ���ڵ� Ÿ�Ե��� ���� �ϰ� defaultFileRenamepolicy�� �ߺ� �̸� �̳� ��Ÿ �������� ó���� �� �ֵ��� �� 
		 }
		 catch(Exception e) {
				request.getSession().setAttribute("messageType", "�����޽���"); //messagerType�̶�� session�� ����
				request.getSession().setAttribute("messageContent", "����ũ��� 10MB�� ���� �� �����ϴ�."); //messageContent session �����Ͽ� �޽��� ������ session���� ����
				response.sendRedirect("room.jsp"); //����� �̵�
				return; 
		 }
		 //���� ���ε尡 ������ ��� 
		 String roomUserID = multi.getParameter("roomUserID");
		 HttpSession session=request.getSession();
		 if(!roomUserID.equals((String) session.getAttribute("userID"))) {
			 //session�� �ִ� userID ���� ���� �Ѿ�� userID ���� ���� �ٸ� ��� ���� ����Ͽ� index�������� �̵��� �� �ֵ��� �� 
			 	request.getSession().setAttribute("messageType", "�����޽���"); //messagerType�̶�� session�� ����
				request.getSession().setAttribute("messageContent", "������ �� �����ϴ�."); //messageContent session �����Ͽ� �޽��� ������ session���� ����
				response.sendRedirect("main.jsp"); //����� �̵�
				return; 
		 }
		 //����� ���� ���� ���� �� 
		 String roomGroupNumber=multi.getParameter("roomGroupNumber");
		 String roomTitle=multi.getParameter("roomTitle");
		 String roomContent=multi.getParameter("roomContent");
		 //����ó��
		 if(roomTitle==null || roomTitle.equals("")|| roomContent==null || roomContent.equals("")) {
			 request.getSession().setAttribute("messageType", "�����޽���"); //messagerType�̶�� session�� ����
				request.getSession().setAttribute("messageContent", "������ ��� ä���ּ���."); //messageContent session �����Ͽ� �޽��� ������ session���� ����
				response.sendRedirect("room.jsp"); //����� �̵�
				return;
		 }
		 String roomFile="";
		 String roomRealFile="";
		 File file=multi.getFile("roomFile"); //boardFile�̶�� �̸��� ���� ������ ���� ���� ��ü�� ó���� �� �� �ֵ��� �� 
		 if(file !=null) {
			 roomFile=multi.getOriginalFileName("roomFile");
			 roomRealFile=file.getName();
		 }
		 RoomDAO roomDAO=new RoomDAO();
		 roomDAO.room(roomUserID, roomGroupNumber, roomTitle, roomContent, roomFile, roomRealFile);
		 //���Ͼ��ε尡 �Ϸ�Ǹ� �����޽����� ��� �� �ֵ��� �� 
			session.setAttribute("messageType", "�����޽���");
			session.setAttribute("messageContent", "���������� ���� ��������ϴ�.");
			response.sendRedirect("roomList.jsp");
			return;
	}

}
