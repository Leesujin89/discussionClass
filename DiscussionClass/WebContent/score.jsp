<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>    
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<%@ page import="score.ScoreDTO" %>
<%@ page import="score.ScoreDAO" %>
<%@ page import="java.util.ArrayList" %>

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
	int getScore=new ScoreDAO().userScoreTotal(userID).getUserScore();
	
	ArrayList<UserDTO> memberList1= new UserDAO().getMemberList("1");
	ArrayList<UserDTO> memberList2= new UserDAO().getMemberList("2");
	ArrayList<UserDTO> memberList3= new UserDAO().getMemberList("3");
	ArrayList<UserDTO> memberList4= new UserDAO().getMemberList("4");
%>
<script>
	function autoClosingAlert(selector, delay){
		var alert=$(selector).alert();
		alert.show();
		window.setTimeout(function(){alert.hide()}, delay);
	}
	function userScoreSubmit(toID){
		var fromID='<%=userID%>';
		var toID=toID;
		$.ajax({
			type:'POST',
			url:'./UserScoreSubmit',
			data:{
				fromID:encodeURIComponent(fromID),
				toID:encodeURIComponent(toID)
			},
			success:function(result){
				if(result ==1){
					autoClosingAlert("#successMSG", 2000);
				}
				else if (result == 0){
					autoClosingAlert("#dangerMSG", 2000);
				}
				else {
					autoClosingAlert("#warningMSG", 2000);
				}
			}
		});
	}
	
</script>
<div class="container">
	<p><h4>현재 나의 점수는 <strong style="color:red;"><%=getScore %>점</strong>입니다.</h4></p>
</div>
<div class="container">
	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
						(그룹Number 1)
					</a>
				</h4>
			</div>
			<div id="collapseOne" class="panel-collapse collapse in">
				<div class="panel-body">
					<table>
					<%
						if(memberList1.size()==0){
					%>
						<tr>
							<td colspan="2"><h3>현재 존재하는 멤버가 없습니다.</h3></td>
						</tr>
					<% 		
						}
						for(int i=0; i < memberList1.size(); i++){
									
					%>
						<tr>
							<td><h2><%=memberList1.get(i).getUserName() %>(<%=memberList1.get(i).getUserID() %>)</h2></td>
							<td><button class="btn btn-defaule btn-lg"<%if (userID.equals(memberList1.get(i).getUserID())) out.println("disabled"); %> onclick="userScoreSubmit('<%=memberList1.get(i).getUserID() %>');">점수주기</button></td>
						</tr>
					<% 
						}
					%>
						
					</table>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
						(그룹Number 2)
					</a>
				</h4>
			</div>
			<div id="collapseTwo" class="panel-collapse collapse in">
				<div class="panel-body">
					<table>
					<%
						if(memberList2.size()==0){
					%>
						<tr>
							<td colspan="2"><h3>현재 존재하는 멤버가 없습니다.</h3></td>
						</tr>
					<% 		
						}
						for(int i=0; i < memberList2.size(); i++){
				
					%>
						<tr>
							<td><h2><%=memberList2.get(i).getUserName() %>(<%=memberList2.get(i).getUserID() %>)</h2></td>
							<td><button class="btn btn-defaule btn-lg"<%if (userID.equals(memberList2.get(i).getUserID())) out.println("disabled"); %> onclick="userScoreSubmit('<%=memberList2.get(i).getUserID() %>');">점수주기</button></td>
						</tr>
					<% 
						}
					%>
						
					</table>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
						(그룹Number 3)
					</a>
				</h4>
			</div>
			<div id="collapseThree" class="panel-collapse collapse in">
				<div class="panel-body">
					<table>
					<%
						if(memberList3.size()==0){
					%>
						<tr>
							<td colspan="2"><h3>현재 존재하는 멤버가 없습니다.</h3></td>
						</tr>
					<% 		
						}
						for(int i=0; i < memberList3.size(); i++){
				
					%>
						<tr>
							<td><h2><%=memberList3.get(i).getUserName() %>(<%=memberList3.get(i).getUserID() %>)</h2></td>
							<td><button class="btn btn-defaule btn-lg" <%if (userID.equals(memberList3.get(i).getUserID())) out.println("disabled"); %> onclick="userScoreSubmit('<%=memberList3.get(i).getUserID() %>');">점수주기</button></td>
						</tr>
					<% 
						}
					%>
						
					</table>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
						(그룹Number 4)
					</a>
				</h4>
			</div>
			<div id="collapseFour" class="panel-collapse collapse in">
				<div class="panel-body">
										<table>
					<%
						if(memberList4.size()==0){
					%>
						<tr>
							<td colspan="2"><h3>현재 존재하는 멤버가 없습니다.</h3></td>
						</tr>
					<% 		
						}
						for(int i=0; i < memberList4.size(); i++){
				
					%>
						<tr>
							<td><h2><%=memberList4.get(i).getUserName() %>(<%=memberList4.get(i).getUserID() %>)</h2></td>
							<td><button class="btn btn-defaule btn-lg"<%if (userID.equals(memberList4.get(i).getUserID())) out.println("disabled"); %> onclick="userScoreSubmit('<%=memberList4.get(i).getUserID() %>');">점수주기</button></td>
						</tr>
					<% 
						}
					%>
						
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="alert alert-success" id="successMSG" style="display:none;">
	<strong>상대방에게 점수를 주었습니다.</strong>
</div>
<div class="alert alert-danger" id="dangerMSG" style="display:none;">
	<strong>점수를 줄 상대방을 다시 눌려주세요</strong>
</div>
<div class="alert alert-warning" id="warningMSG" style="display:none;">
	<strong>자신에게 점수를 줄 수 없습니다.</strong>
</div>
</body>
</html>