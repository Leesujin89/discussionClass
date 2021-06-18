package user;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetMemberListServlet")
public class GetMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String roomID=request.getParameter("roomID");
		if(roomID.equals("")|| roomID==null) {
			response.getWriter().write("");
			return;
		}
		else {
			response.getWriter().write(getMember(URLDecoder.decode(roomID, "UTF-8")));
		}
	}
	public String getMember(String roomID) {
		StringBuffer result=new StringBuffer("");
		result.append("{\"result\" : [");
		UserDAO userDAO=new UserDAO();
		ArrayList<UserDTO> memberList=userDAO.nowMemberList(roomID);
		
		if (memberList.size()==0) return "";
		
		for(int i=0; i<memberList.size(); i++) {
			result.append("[{\"value\" : \""+memberList.get(i).getUserID()+"\"},");
			result.append("{\"value\" : \"" +memberList.get(i).getUserName()+"\"},");
			result.append("{\"value\" : \""+memberList.get(i).getUserProfile()+"\"},");
			result.append("{\"value\" : \""+memberList.get(i).getNow()+"\"}]");
			if( i !=memberList.size()-1) result.append(",");
		}
		result.append("]}");
		return result.toString();
	}
}