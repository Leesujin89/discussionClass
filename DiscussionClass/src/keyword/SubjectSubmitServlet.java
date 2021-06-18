package keyword;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubjectSubmitServlet")
public class SubjectSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String subject=request.getParameter("subject");
		String userID=request.getParameter("userID");
		subject=URLDecoder.decode(subject,"UTF-8");
		userID=URLDecoder.decode(userID,"UTF-8");
		
		HttpSession session= request.getSession();
		if (!userID.equals((String)session.getAttribute("userID"))) {
			response.getWriter().write("");
			return;
		}
		if(subject.equals("")|| subject==null) {
			response.getWriter().write("-1");
		}
		else {
			response.getWriter().write(new KeywordDAO().subjectSubmit(userID, subject)+"");
		}
	}

}
