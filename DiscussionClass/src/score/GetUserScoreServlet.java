package score;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetUserScoreServlet")
public class GetUserScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String toID=request.getParameter("toID");
	
		toID=URLDecoder.decode(toID,"UTF-8");
		
		HttpSession session =request.getSession();
		if(!toID.equals((String)session.getAttribute("userID"))) {
			response.getWriter().write("");
			return;
		}
		else {
			response.getWriter().write(getScore(toID));
			
		}
	}
	public String getScore(String toID) {
		int available;
		StringBuffer result=new StringBuffer("");
		result.append("{\"result\" : [");
		ScoreDAO scoreDAO=new ScoreDAO();
		ArrayList<ScoreDTO>scoreList=scoreDAO.getScore(toID);
		
		if(scoreList.size()==0)return"";
		
		for(int i=0; i<scoreList.size(); i++) {
			result.append("[{\"value\" : \""+ scoreList.get(i).getFromID()+"\"},");
			result.append("{\"value\" : \""+ scoreList.get(i).getToID()+"\"}]");
			if( i !=scoreList.size()-1) result.append(",");
		}
		result.append("]}");
		available=scoreDAO.scoreUpdate(toID);
		return result.toString();
	}

}
