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
	<form method="POST" action="./NoticeUpdateServlet" enctype="multipart/form-data">
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="4"><h3>공지사항 수정 양식</h3></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:110px;"><h5>관리자</h5></td>
					<td>
						<h5><%=notice.getUserID() %></h5>
						<input class="form-control" type="hidden" maxlength="20" name="userID" value="<%=userID %>" >
						<input class="form-control" type="hidden" maxlength="20" name="noticeID" value="<%=noticeID %>" >
					</td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>제목</h5></td>
					<td colspan="3"><input class="form-control" type="text" maxlength="50" name="noticeTitle" placeholder="글 제목을 입력하세요" value="<%=notice.getNoticeTitle()%>"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>내용</h5></td>
					<td colspan="3"><textarea class="form-control" rows="10" name="noticeContent" maxlength="2048" placeholder="내용을 입력하세요"><%=notice.getNoticeContent()%></textarea></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>파일 업로드</h5></td>
					<td colspan="3">
						<input type="file" name="noticeFile" class="file">
						<div class="input-group col-xs-12">
								<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
								<input type="text" class="form-control input-lg" disabled placeholder="파일을 업로드 하세요">
								<span class="input-group-btn">
									<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search"></i>파일찾기</button>
								</span>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="5"><input type="submit" class="btn btn-primary pull-right" value="수정"></td>
				</tr>			
			</tbody>
		</table>
	</form>
</div>
</body>
</html>