package notice;

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


@WebServlet("/NoticeServlet")
public class NoticeServlet extends HttpServlet {
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
		 String userID = multi.getParameter("userID");
		 HttpSession session=request.getSession();
		 if(!userID.equals((String) session.getAttribute("userID"))) {
			 //session�� �ִ� userID ���� ���� �Ѿ�� userID ���� ���� �ٸ� ��� ���� ����Ͽ� index�������� �̵��� �� �ֵ��� �� 
			 	request.getSession().setAttribute("messageType", "�����޽���"); //messagerType�̶�� session�� ����
				request.getSession().setAttribute("messageContent", "������ �� �����ϴ�."); //messageContent session �����Ͽ� �޽��� ������ session���� ����
				response.sendRedirect("noticeList.jsp"); //����� �̵�
				return; 
		 }
		 String noticeTitle=multi.getParameter("noticeTitle");
		 String noticeContent=multi.getParameter("noticeContent");
		 
		 if(noticeTitle.equals("")||noticeTitle==null|| noticeContent.equals("")|| noticeContent==null) {
			 request.getSession().setAttribute("messageType", "�����޽���"); //messagerType�̶�� session�� ����
			 request.getSession().setAttribute("messageContent", "������ ��� ä���ּ���."); //messageContent session �����Ͽ� �޽��� ������ session���� ����
			 response.sendRedirect("notice.jsp"); //����� �̵�
			 return;
		 }
		 String noticeFile="";
		 String noticeRealFile="";
		 File file=multi.getFile("noticeFile"); //boardFile�̶�� �̸��� ���� ������ ���� ���� ��ü�� ó���� �� �� �ֵ��� �� 
		 if(file !=null) {
			 noticeFile=multi.getOriginalFileName("noticeFile");
			 noticeRealFile=file.getName();
		 }
		 NoticeDAO noticeDAO=new NoticeDAO();
		 noticeDAO.notice(userID, noticeTitle, noticeContent, noticeFile, noticeRealFile);
		 	session.setAttribute("messageType", "�����޽���");
			session.setAttribute("messageContent", "���� ��� �Ǿ����ϴ�");
			response.sendRedirect("noticeList.jsp");
			return;
	}

}
