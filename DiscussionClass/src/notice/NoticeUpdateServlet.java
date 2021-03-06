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
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","파일크기는 10MB를 넘을 수 없습니다.");
			response.sendRedirect("noticeUpdate.jsp");
			return;
		}
		
		String userID=multi.getParameter("userID");
		if(!userID.equals("admin")) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","접근할 수 없습니다. 다시 로그인 해 주세요");
			response.sendRedirect("login.jsp");
			return;
		}
		String noticeID=multi.getParameter("noticeID");
		if(noticeID.equals("")||noticeID==null) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","접근할 수 없습니다.");
			response.sendRedirect("noticeUpdate.jsp");
			return;
		}
		String noticeTitle=multi.getParameter("noticeTitle");
		String noticeContent=multi.getParameter("noticeContent");
		
		if(noticeTitle.equals("")||noticeTitle==null || noticeContent.equals("")|| noticeContent==null) {
			request.getSession().setAttribute("messageType", "오류메시지");
			request.getSession().setAttribute("messageContent","모든 항목을 입력 해 주세요 ");
			response.sendRedirect("noticeUpdate.jsp");
			return;
		}
		String fileName="";
		File file=multi.getFile("noticeFile");
		if(file != null) {
			String ext=file.getName().substring(file.getName().lastIndexOf(".")+1); //확장자 확인
			if(ext.equals("jpg")|| ext.equals("png") || ext.equals("gif")) {
				String prev=new UserDAO().getUser(userID).getUserProfile(); //현재 user의 userProfile 값 
				File prevFile= new File(savePath+"/"+prev); //이전userProfile 값을 넣은 경로 
				if(prevFile.exists()) {
					prevFile.delete(); //이전의 파일이 존재한다면 지움
				}
				fileName=file.getName();
			}
			else {
				if(file.exists()) { //만약 사용자로부터 받은 파일이 있다면 지운 후 
					file.delete();
				}
				request.getSession().setAttribute("messageType", "오류메시지");
				request.getSession().setAttribute("messageContent","이미지만 업로드 가능합니다");
				response.sendRedirect("noticeUpdate.jsp");
				return;
			}
		}
		new NoticeDAO().noticeUpdate(noticeID, userID, noticeTitle, noticeContent, fileName, fileName);
		request.getSession().setAttribute("messageType", "성공메시지");
		request.getSession().setAttribute("messageContent","공지사항이 수정되었습니다.");
		response.sendRedirect("noticeList.jsp");
		return;
	}

}
