package keyword;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetKeywordServlet")
public class GetKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String subject = request.getParameter("subject");
		subject=URLDecoder.decode(subject, "UTF-8");
		if(subject.equals("")||subject ==null) {
			response.getWriter().write("");
			return;
		}
		else {
			response.getWriter().write(getKeywordList(subject));
		}
	}
	public String getKeywordList(String subject) {
		StringBuffer result=new StringBuffer("");
		result.append("{\"result\" : [");
		KeywordDAO keywordDAO = new KeywordDAO();
		ArrayList<KeywordDTO>keywordList=keywordDAO.getKeyword(subject);
		
		if (keywordList.size()==0) return "";
		
		for (int i=0; i < keywordList.size(); i++) {
			result.append("[{\"value\" : \""+keywordList.get(i).getUserID()+"\"},");
			result.append("{\"value\" : \""+keywordList.get(i).getUserName()+"\"},");
			result.append("{\"value\" : \""+keywordList.get(i).getKeyword()+"\"}]");
			if(i != keywordList.size()-1) result.append(",");
		}
		result.append("]}");
		return result.toString();
	}
}
