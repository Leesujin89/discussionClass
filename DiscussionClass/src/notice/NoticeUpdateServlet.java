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

import user.UserDAO;


@WebServlet("/NoticeUpdateServlet")
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		MultipartRequest multi=null;
		int fileMaxSize=10*1024*1024;
		String savePath=request.getRealPath("/upload").replaceAll("\\\\", "/");
		try {
			multi=new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		}
		catch(Exception e) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","����ũ��� 10MB�� ���� �� �����ϴ�.");
			response.sendRedirect("noticeUpdate.jsp");
			return;
		}
		
		String userID=multi.getParameter("userID");
		if(!userID.equals("admin")) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","������ �� �����ϴ�. �ٽ� �α��� �� �ּ���");
			response.sendRedirect("login.jsp");
			return;
		}
		String noticeID=multi.getParameter("noticeID");
		if(noticeID.equals("")||noticeID==null) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","������ �� �����ϴ�.");
			response.sendRedirect("noticeUpdate.jsp");
			return;
		}
		String noticeTitle=multi.getParameter("noticeTitle");
		String noticeContent=multi.getParameter("noticeContent");
		
		if(noticeTitle.equals("")||noticeTitle==null || noticeContent.equals("")|| noticeContent==null) {
			request.getSession().setAttribute("messageType", "�����޽���");
			request.getSession().setAttribute("messageContent","��� �׸��� �Է� �� �ּ��� ");
			response.sendRedirect("noticeUpdate.jsp");
			return;
		}
		String fileName="";
		File file=multi.getFile("noticeFile");
		if(file != null) {
			String ext=file.getName().substring(file.getName().lastIndexOf(".")+1); //Ȯ���� Ȯ��
			if(ext.equals("jpg")|| ext.equals("png") || ext.equals("gif")) {
				String prev=new UserDAO().getUser(userID).getUserProfile(); //���� user�� userProfile �� 
				File prevFile= new File(savePath+"/"+prev); //����userProfile ���� ���� ��� 
				if(prevFile.exists()) {
					prevFile.delete(); //������ ������ �����Ѵٸ� ����
				}
				fileName=file.getName();
			}
			else {
				if(file.exists()) { //���� ����ڷκ��� ���� ������ �ִٸ� ���� �� 
					file.delete();
				}
				request.getSession().setAttribute("messageType", "�����޽���");
				request.getSession().setAttribute("messageContent","�̹����� ���ε� �����մϴ�");
				response.sendRedirect("noticeUpdate.jsp");
				return;
			}
		}
		new NoticeDAO().noticeUpdate(noticeID, userID, noticeTitle, noticeContent, fileName, fileName);
		request.getSession().setAttribute("messageType", "�����޽���");
		request.getSession().setAttribute("messageContent","���������� �����Ǿ����ϴ�.");
		response.sendRedirect("noticeList.jsp");
		return;
	}

}