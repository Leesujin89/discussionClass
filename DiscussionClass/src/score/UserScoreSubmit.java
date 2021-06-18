package score;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/UserScoreSubmit")
public class UserScoreSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String fromID=request.getParameter("fromID");
		String toID=request.getParameter("toID");

		if(fromID==null ||fromID.equals("")||toID==null || toID.equals("")) {
			response.getWriter().write("0");
		}
		else {
			fromID=URLDecoder.decode(fromID,"UTF-8");
			toID=URLDecoder.decode(toID,"UTF-8");
			HttpSession session=request.getSession();
			if(toID.equals((String)session.getAttribute("userID"))) {
				response.getWriter().write("-1");
				return;
			}
			response.getWriter().write(new ScoreDAO().userScoreSubmit(fromID, toID)+"");
			
		}
	}

}
