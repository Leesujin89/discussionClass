<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>  
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>  
<!DOCTYPE html>
<html>
<head>
<script>
	function back(){
		location.href="main.jsp";
	}
</script>
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
	String userProfile=new UserDAO().getProfile(userID);
%>
<div class="container">
	<form method="post" action="./ProfileUpdateServlet" enctype="multipart/form-data">
		<table class="table table-boardered table-hover" style="text-align:center; boder: 1px solid #14b8b5;">
			<thead>
				<tr>
					<th colspan="2"><h1>나의 프로필</h1></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:110px;"><h5>아이디</h5></td>
					<td><input type="hidden" id="userID" name="userID" maxlength="20" value="<%= user.getUserID()%>"><h3><%=user.getUserID() %></h3></td>
				</tr>
				<tr>
					<td colspan="2"><img src="<%=userProfile%>" class="media-object img-circle" style="max-width:300px; margin:0 auto;"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>사진업로드</h5></td>
					<td colspan="2">
						<input type="file" name="userProfile" class="file">
					</td>
				</tr>
				<tr>
					<td><button class="btn btn-default pull-left" type="button" onclick="back();">취소</button></td>
					<td><input class="btn btn-info pull-right" type="submit" value="프로필 업로드하기"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>