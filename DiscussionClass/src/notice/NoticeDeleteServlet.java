package notice;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/NoticeDeleteServlet")
public class NoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//session������ userID���� ���� 
		HttpSession session=request.getSession();
		String userID=(String)session.getAttribute("userID");
		
		String noticeID=request.getParameter("noticeID");
		//boardID�� ����ó�� 
		if(noticeID.equals("")|| noticeID==null) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent", "������ �� �����ϴ�."); //�����޽����� session���� ����� 
			response.sendRedirect("noticeList.jsp");
			return;
		}
		//�����ͺ��̽����� �ϳ��� �Խñ��� ���� �����ش��ϴ� �Խù��� 
		NoticeDAO noticeDAO=new NoticeDAO();
		NoticeDTO notice=noticeDAO.getNotice(noticeID);
		if(!userID.equals(notice.getUserID())) {
			//����α����� ����ڿ� �ش�Խù��� �ۼ��ڿ� ��ġ���� ������ ���� ��� 
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent", "������ �� �����ϴ�."); //�����޽����� session���� ����� 
			response.sendRedirect("noticeList.jsp");
			return;
		}
		String savePath =request.getRealPath("/upload").replaceAll("\\\\", "/"); //���� ������ ��ο��� upload�� ���� �ȿ� ������ ���ε� �� �� �ֵ��� ���� 
		String prev=noticeDAO.getNotice(noticeID).getNoticeRealFile();
		int result = noticeDAO.noticeDelete(noticeID); //���� �޼ҵ� ���� 
		if(result ==-1) {
			//���� ���н� 
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent", "������ �� �����ϴ�."); //�����޽����� session���� ����� 
			response.sendRedirect("noticeList.jsp");
			return;
		}
		else {
			//���������� ���� �ߴٸ� ������ ������ ���ε� �ߴٸ� �� ������ ���� 
			File prevFile=new File(savePath+"/"+prev);
			if(prevFile.exists()) {
				//���� ���� ������ �����Ѵٸ� ������ 
				prevFile.delete();
			}
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent", "�Խñ��� �����Ǿ����ϴ�."); //�����޽����� session���� ����� 
			response.sendRedirect("noticeList.jsp");
		}
	}
}
