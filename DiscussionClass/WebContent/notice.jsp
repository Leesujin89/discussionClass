<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>    
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>	
<%
if(userID==null){
	session.setAttribute("messageType", "오류메시지");
	session.setAttribute("messageContent", "현재로그인이 되어 있지 않습니다.");
	response.sendRedirect("login.jsp");
	return;
}
if (!userID.equals("admin")){
	session.setAttribute("messageType", "오류메시지");
	session.setAttribute("messageContent", "글쓰기 권한이 없습니다.");
	response.sendRedirect("noticeList.jsp");
	return;
}
%>
<div class="container">
	<form method="POST" action="./NoticeServlet" enctype="multipart/form-data">
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="4"><h3>공지사항 글 쓰기</h3></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:110px;"><h5>관리자</h5></td>
					<td>
						<h5><%=userID %></h5>
						<input class="form-control" type="hidden" maxlength="20" name="userID" value="<%=userID %>" >
					</td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>제목</h5></td>
					<td colspan="3"><input class="form-control" type="text" maxlength="50" name="noticeTitle" placeholder="글 제목을 입력하세요"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>내용</h5></td>
					<td colspan="3"><textarea class="form-control" rows="10" name="noticeContent" maxlength="2048" placeholder="내용을 입력하세요"></textarea></td>
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
					<td colspan="5"><input type="submit" class="btn btn-primary pull-right" value="등록"></td>
				</tr>			
			</tbody>
		</table>
	</form>
</div>
</body>
</html>