<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<%
		session.invalidate(); //세션 무효화
	%>
<script>
	location.href="main.jsp"; //페이지 이동 
</script>	
</body>
</html>