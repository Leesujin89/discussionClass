package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String cUserID= request.getParameter("userID"); //����ڰ� ���� fromID�� parameter���� �ʱ�ȭ 
		String roomID=request.getParameter("roomID");
		String chatContent=request.getParameter("chatContent");
		
		
		if(cUserID ==null || cUserID.equals("")|| roomID == null || roomID.equals("")|| chatContent==null || chatContent.equals("")) {
			
			//����ó�� 
			response.getWriter().write("0"); //����ڿ��� 0 ��ȯ
		}
		else {
			
			// ����ڷκ��� ���� ���� UTF-8�� ���ڵ���
			cUserID=URLDecoder.decode(cUserID, "UTF-8"); 
			roomID=URLDecoder.decode(roomID, "UTF-8");
			chatContent=URLDecoder.decode(chatContent, "UTF-8");
			
			//�ٸ� ����� ���� ���� �� ó�� ���� �ʵ��� 
			HttpSession session=request.getSession();
			if(!URLDecoder.decode(cUserID, "UTF-8").equals((String)session.getAttribute("userID"))) {
				//���� session���� �Ѿ�� userID���� Ʋ���� ���� ��� �� return 
				response.getWriter().write("");
				return;
			}
			//�޼ҵ� ���� �� ��� ���� ����ڿ��� ��ȯ 
			response.getWriter().write(new ChatDAO().chatSubmit(cUserID, roomID, chatContent)+"");
		}
	}

}
