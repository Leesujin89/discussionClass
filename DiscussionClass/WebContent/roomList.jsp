<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>    
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<%@ page import="room.RoomDTO" %>
<%@ page import="room.RoomDAO" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	int userGroupNumber=user.getGroupNumber();
	
	ArrayList<RoomDTO> roomList= new RoomDAO().getRoomList();
	request.setAttribute("list", roomList);
%>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today"/>

<div class="container">
	<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
		<thead>
			<tr>
				<th colspan="4"><h3>토론방</h3></th>
			</tr>
			<tr>
				   <th style="background-color: #fafafa; color:#000000; width:70px; text-align:center;"><h5>번호</h5></th>
					<!-- fafa는 파란색 000000은 검은색 width는 70px로 고정 -->
					<th style="background-color: #fafafa; color:#000000; text-align:center;"><h5>제목</h5></th>
					<th style="background-color: #fafafa; color:#000000; text-align:center;"><h5>작성자</h5></th>
				 	<th style="background-color: #fafafa; color:#000000; width:100px; text-align:center;"><h5>작성날짜</h5></th>
			</tr>
		</thead>	
		<tbody>
	<c:forEach var="roomList" items="${list}">
			<tr>
				<td>${roomList.roomID }</td>
				<td>	
					
					<a href="roomShow.jsp?roomID=${roomList.roomID }&roomGroupNumber=${roomList.roomGroupNumber}&roomUserID=${roomList.roomUserID }">${roomList.roomTitle }</a>
					<c:if test="${roomList.roomDate >= today}"><span class="label label-info">new</span></c:if>				
				</td>
				<td>${roomList.roomUserID }</td>
				<td>${roomList.roomDate}</td>
			</tr>
	
			</c:forEach>
			<tr>
				<td colspan="4"><a class="btn btn-primary pull-right" href="room.jsp?userID=<%=userID%>&roomGroupNumber=<%=userGroupNumber%>">방 만들기</a></td>
			</tr>
		</tbody>
	</table>
</div>
</body>

</html>