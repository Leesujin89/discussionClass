package room;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ProgressSubmitServlet")
public class ProgressSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String roomID=request.getParameter("roomID");
		String progress=request.getParameter("progress");
		roomID=URLDecoder.decode(roomID, "UTF-8");
		progress=URLDecoder.decode(progress, "UTF-8");
		
		if(roomID.equals("")|| roomID==null || progress.equals("")|| progress==null) {
			response.getWriter().write("0");
		}
		else {
			response.getWriter().write(new RoomDAO().progressSubmit(roomID, progress)+"");
		}
	}

}
