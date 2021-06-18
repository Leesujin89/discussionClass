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
	UserDTO user=new UserDAO().getUser(userID);
	
	String roomUserID=request.getParameter("userID");
	String roomGroupNumber=request.getParameter("roomGroupNumber");
%>
<div class="container">
	<form method="POST" action="./RoomServlet" enctype="multipart/form-data">
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="4"><h3>방 만들기</h3></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:110px;"><h5>아이디</h5></td>
					<td><h5><%=roomUserID %></h5>
					<input type="hidden" name="roomUserID" value="<%=roomUserID %>"></td>
					<td style="width:110px;"><h5>그룹번호</h5></td>
					<td><h5><%=roomGroupNumber %></h5>
					<input type="hidden" name="roomGroupNumber" value="<%=roomGroupNumber %>"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>방 제목</h5></td>
					<td colspan="3"><input class="form-control" type="text" maxlength="50" name="roomTitle" placeholder="글 제목을 입력하세요"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>내용</h5></td>
					<td colspan="3"><textarea class="form-control" rows="10" name="roomContent" maxlength="2048" placeholder="내용을 입력하세요"></textarea></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>파일 업로드</h5></td>
					<td colspan="3">
						<input type="file" name="roomFile" class="file">
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
					<td colspan="5"><input type="submit" class="btn btn-info pull-right" value="방 만들기"></td>
				</tr>			
			</tbody>
		</table>
	</form>
</div>
</body>
</html>