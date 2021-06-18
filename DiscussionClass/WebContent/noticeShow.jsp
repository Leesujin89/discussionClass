<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>    
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<%@ page import="notice.NoticeDTO" %>
<%@ page import="notice.NoticeDAO" %>
<!DOCTYPE html>
<html>
<head>
<%
	
	String noticeID=request.getParameter("noticeID");
	if(userID==null){
		session.setAttribute("messageType", "오류메시지");
		session.setAttribute("messageContent", "현재로그인이 되어 있지 않습니다.");
		response.sendRedirect("login.jsp");
		return;
	}
	
	if(noticeID==null || noticeID.equals("")){
		session.setAttribute("messageType", "오류메시지");
		session.setAttribute("messageContent", "접근할 수 없습니다.");
		response.sendRedirect("noticeList.jsp");
		return;
	}
	
	
	NoticeDTO notice= new NoticeDAO().getNotice(noticeID);
	String noticeFile=notice.getNoticeRealFile();
	if(noticeFile.equals("")){
		noticeFile="http://localhost:8080/DiscussionClass/images/noImage.png";
	}
	else{
		noticeFile="http://localhost:8080/DiscussionClass/upload/"+noticeFile;
	}
%>


</head>
<body>	
	<div class="container">
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>제목</h5></td>
					<td colspan="3"><h5><%=notice.getNoticeTitle()%></h5></td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>작성자</h5></td>
					<td colspan="3"><h5><%=notice.getUserID() %></h5></td>
				
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>작성날짜</h5></td>
					<td colspan="3"><h5><%=notice.getNoticeDate() %></h5></td>
				</tr>
				<tr>
					<td style="vertical-align: middle; min-height: 150px; background-color:#fafafa; color:#000000; width:80px;"><h5>글 내용</h5></td>
					<td colspan="3" style="text-align:left;"><h5><%=notice.getNoticeContent()%></h5></td>
				</tr>
				<tr>
					<td style="vertical-align: middle; min-height: 150px; background-color:#fafafa; color:#000000; width:80px;"><h5>첨부된 이미지</h5></td>
					<td colspan="3" style="text-align:center;"><img src="<%=noticeFile %>" width="300px" height="200px"> </td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>첨부파일</h5></td>
					<td colspan="3"><h5><a href="boardDownLoad.jsp?boardID=>"><%=notice.getNoticeFile()%></a></h5></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" style="text-align:right;">
						<a href="noticeList.jsp" class="btn btn-primary">목록</a>
				<%
					if(userID.equals("admin")){
				%>
					<a href="noticeUpdate.jsp?noticeID=<%=notice.getNoticeID()%>" class="btn btn-default">수정</a>
					<a href="NoticeDeleteServlet?noticeID=<%=notice.getNoticeID()%>" class="btn btn-danger" onclick="return confirm('정말로 삭제하시겠습니까?')">삭제</a>
				<% 		
					}
				%>
					</td>
				</tr>	
			</tbody>
		</table>
	</div>
	
</body>
</html>