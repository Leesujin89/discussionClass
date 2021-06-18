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
		
		MultipartRequest multi=null; //사진 업로드에 필요한 변수 선언 
		int fileMaxSize= 10*1024*1024; //사진의 최대크기 설정 
		String savePath =request.getRealPath("/upload").replaceAll("\\\\", "/"); //실제 서버의 경로에서 upload의 폴더 안에 파일이 업로드 될 수 있도록 설정 
		 try {
			 multi=new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy()); 
			 //사용자로부터 요청받은 사진파일을 경로와 사진 최대크기, 인코딩 타입등을 설정 하고 defaultFileRenamepolicy는 중복 이름 이나 기타 오류들을 처리할 수 있도록 함 
		 }
		 catch(Exception e) {
				request.getSession().setAttribute("messageType", "오류메시지"); //messagerType이라는 session을 생성
				request.getSession().setAttribute("messageContent", "파일크기는 10MB를 넘을 수 없습니다."); //messageContent session 생성하여 메시지 내용을 session값에 넣음
				response.sendRedirect("room.jsp"); //여기로 이동
				return; 
		 }
		 //파일 업로드가 성공한 경우 
		 String roomUserID = multi.getParameter("roomUserID");
		 HttpSession session=request.getSession();
		 if(!roomUserID.equals((String) session.getAttribute("userID"))) {
			 //session에 있는 userID 값과 현재 넘어온 userID 값이 서로 다를 경우 오류 출력하여 index페이지로 이동할 수 있도록 함 
			 	request.getSession().setAttribute("messageType", "오류메시지"); //messagerType이라는 session을 생성
				request.getSession().setAttribute("messageContent", "접근할 수 없습니다."); //messageContent session 생성하여 메시지 내용을 session값에 넣음
				response.sendRedirect("main.jsp"); //여기로 이동
				return; 
		 }
		 //제목과 글의 값을 받은 후 
		 String roomGroupNumber=multi.getParameter("roomGroupNumber");
		 String roomTitle=multi.getParameter("roomTitle");
		 String roomContent=multi.getParameter("roomContent");
		 //예외처리
		 if(roomTitle==null || roomTitle.equals("")|| roomContent==null || roomContent.equals("")) {
			 request.getSession().setAttribute("messageType", "오류메시지"); //messagerType이라는 session을 생성
				request.getSession().setAttribute("messageContent", "내용을 모두 채워주세요."); //messageContent session 생성하여 메시지 내용을 session값에 넣음
				response.sendRedirect("room.jsp"); //여기로 이동
				return;
		 }
		 String roomFile="";
		 String roomRealFile="";
		 File file=multi.getFile("roomFile"); //boardFile이라는 이름을 가진 파일을 들고와 파일 자체를 처리해 줄 수 있도록 함 
		 if(file !=null) {
			 roomFile=multi.getOriginalFileName("roomFile");
			 roomRealFile=file.getName();
		 }
		 RoomDAO roomDAO=new RoomDAO();
		 roomDAO.room(roomUserID, roomGroupNumber, roomTitle, roomContent, roomFile, roomRealFile);
		 //파일업로드가 완료되면 성공메시지를 띄울 수 있도록 함 
			session.setAttribute("messageType", "성공메시지");
			session.setAttribute("messageContent", "성공적으로 방을 만들었습니다.");
			response.sendRedirect("roomList.jsp");
			return;
	}

}
