<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>    
<!DOCTYPE html>
<html>
<head>
</head>
<body>	
<div class="container">
	<form method="post" action="./LoginServlet">
		<table class="table table-boardered table-hover" style="text-align:center; boder: 1px solid #14b8b5;">
			<thead>
				<tr>
					<th colspan="2"><h1>로그인</h1></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:110px;"><h5>아이디</h5></td>
					<td><input class="form-control" type="text" id="userID" name="userID" maxlength="20" placeholder="아이디를 입력하세요">
				</tr>
				<tr>
					<td style="width:110px;"><h5>비밀번호</h5></td>
					<td><input class="form-control" type="password" id="userPWD" name="userPWD" maxlength="20" placeholder="비밀번호를 입력하세요"></td>
				</tr>
				<tr>
					<td colspan="2"><input class="btn btn-primary pull-right" type="submit" value="로그인"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>