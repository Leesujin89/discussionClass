package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;

@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID=request.getParameter("userID");
		String roomID=request.getParameter("roomID");
		String lastChatID=request.getParameter("chatID");
		if(roomID.equals("")|| roomID == null || lastChatID.equals("")|| lastChatID==null) {
			response.getWriter().write(""); //공백 반환
			return;
		}
		else {
			int onUser=new UserDAO().onUser(userID);
			response.getWriter().write(getList(URLDecoder.decode(roomID, "UTF-8"), URLDecoder.decode(lastChatID,"UTF-8")));
		}

}
	public String getList(String roomID, String chatID) {
		StringBuffer result=new StringBuffer("");
		result.append("{\"result\" : [");
		ChatDAO chatDAO =new ChatDAO();
		ArrayList<ChatDTO>chatList=chatDAO.getChatList(roomID, chatID);
		
		if(chatList.size()==0) return"";
		
		for (int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\" : \"" + chatList.get(i).getUserID()+"\"},");
			result.append("{\"value\" : \"" + chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\" : \"" + chatList.get(i).getChatTime()+"\"},");
			result.append("{\"value\" : \"" + chatList.get(i).getMemberProfile()+"\"}]");
			if( i != chatList.size()-1) result.append(",");
		}
		result.append("], \"last\" :\"" + chatList.get(chatList.size()-1).getChatID()+"\"}");
		return result.toString();
	 }
}