<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %> 
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>   
<!DOCTYPE html>
<html>
<head>
</head>
<script>
function checkPWD(){
	var userPWD1= $('#userPWD1').val();
	var userPWD2= $('#userPWD2').val();
	if(userPWD1 !=userPWD2){
		$('#checkPWDMSG').html('비밀번호가 서로 일치하지 않습니다.');
	}
	else{
		$('#checkPWDMSG').html('');
	}
}
</script>
<body>	
<%
	if(userID==null){
		session.setAttribute("messageType", "오류메시지");
		session.setAttribute("messageContent", "현재로그인이 되어 있지 않습니다.");
		response.sendRedirect("login.jsp");
		return;
	}
	UserDTO user=new UserDTO();
	user=new UserDAO().getUser(userID);
%>
<div class="container">
	<form method="post" action="./UserUpdateServlet">
		<table class="table table-boardered table-hover" style="text-align:center; boder: 1px solid #14b8b5;">
			<thead>
				<tr>
					<th colspan="3"><h1>회원정보 수정</h1></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:110px;"><h5>아이디</h5></td>
					<td><h5><%=user.getUserID()%></h5></td>
					<td><input  type="hidden" id="userID" name="userID" value="<%=user.getUserID()%>"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>비밀번호</h5></td>
					<td colspan="2"><input onkeyup="checkPWD();" class="form-control" type="password" id="userPWD1" name="userPWD1" maxlength="20" placeholder="비밀번호를 입력하세요"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>비밀번호 확인</h5></td>
					<td colspan="2"><input onkeyup="checkPWD();" class="form-control" type="password" id="userPWD2" name="userPWD2" maxlength="20" placeholder="비밀번호를 다시 입력하세요"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>이름</h5></td>
					<td colspan="2"><input class="form-control" type="text" id="userName" name="userName" value="<%=user.getUserName()%>" placeholder="이름을 입력하세요"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>이메일</h5></td>
					<td colspan="2"><input class="form-control" type="email" id="userEmail" name="userEmail" placeholder="메일을 입력하세요" value="<%=user.getUserEmail()%>"></td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>그룹번호</h5></td>
					<td colspan="2">
						<div class="form-group" style="text-align:center; margin:0 auto;">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary <%if (user.getGroupNumber()==1) out.print("active");%>">
									<input type="radio" name="groupNumber"  autocomplete="off" value="1" <%if(user.getGroupNumber()==1) out.print("checked"); %>>1
								</label>
								<label class="btn btn-primary <%if (user.getGroupNumber()==2) out.print("active");%>">
									<input type="radio" name="groupNumber" autocomplete="off" value="2" <%if(user.getGroupNumber()==2) out.print("checked"); %>>2
								</label>
								<label class="btn btn-primary <%if (user.getGroupNumber()==3) out.print("active");%>">
									<input type="radio" name="groupNumber" autocomplete="off" value="3"<%if(user.getGroupNumber()==3) out.print("checked"); %>>3
								</label>
								<label class="btn btn-primary <%if (user.getGroupNumber()==4) out.print("active");%>">
									<input type="radio" name="groupNumber" autocomplete="off" value="4" <%if(user.getGroupNumber()==4) out.print("checked"); %>>4
								</label>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="text-align:left;" colspan="3"><h5 style="color:red;" id="checkPWDMSG"></h5><input class="btn btn-primary pull-right" type="submit" value="수정"></td>
				</tr>
				
<%
	int userScore=user.getUserScore();
	int groupScore=user.getGroupSocre();
%>
				<tr>				
					<td colspan="3"><h2>현재 나의 점수</h2></td>
				</tr>
				<tr>
					<td style="width:110px;">개인 점수</td>
					<td  colspan="2"><%=userScore%></td>
				</tr>
				<tr>
					<td style="width:110px;">그룹 점수</td>
					<td  colspan="2"><%=groupScore %></td>
				</tr>
				<tr>
					<td style="width:110px; border-top:2px solid;"><h4>총 점</h4></td>
					<td style="border-top:2px solid;" colspan="2"><h4 style="color:red;"><%=userScore+groupScore %></h4></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!-- 아이디 중복체크 시 모달 창 -->
	<div class="modal fade" id="checkIDModal" tabindex="-1" role="dialog" aria-hidden="true"> <!-- dialog는 알림창 -->
	 	<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content panel-info" id="checkIDType"> <!-- 이부분의 class속성이 바뀜 -->
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
								확인메시지
						</h4>
					</div>
					<div class="modal-body" id="checkIDMessage"> 
					<!-- 여기에 있는 요소의 내용이 바뀜 -->	
					</div>
						<div class="modal-footer">
							<button class="btn btn-primary" type="button" data-dismiss="modal">확인</button>
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>