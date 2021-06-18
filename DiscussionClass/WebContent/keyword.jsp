<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>  
<%@ page import="keyword.KeywordDTO" %> 
<%@ page import="keyword.KeywordDAO" %>   
<!DOCTYPE html>
<html>
<head>
<%
if(userID==null){
	session.setAttribute("messageType", "오류메시지");
	session.setAttribute("messageContent", "현재로그인이 되어 있지 않습니다.");
	response.sendRedirect("login.jsp");
	return;
}
%>
</head>
<script>
	function subjectSubmit(){
		var subject=$('#subject').val();
		var userID='<%=userID%>';
		$.ajax({
			type:'POST',
			url:'./SubjectSubmitServlet',
			data:{
				subject:encodeURIComponent(subject),
				userID:encodeURIComponent(userID)
			},
			success:function(result){
				if(result==1){
					$('#subjectSubmitMessage').html('주제가 등록되었습니다.')
					$('#subjectSubmitType').attr('class', 'modal-content panel-success');
				
				}
				else if (result==-1){
					$('#subjectSubmitMessage').html('주제를 입력해 주세요')
					$('#subjectSubmitType').attr('class', 'modal-content panel-warning');
				}
				else{
					$('#subjectSubmitMessage').html('다시 로그인해 주세요')
					$('#subjectSubmitType').attr('class', 'modal-content panel-danger');
				}
				$('#subjectSubmitModal').modal("show");
				location.href='keywordShow.jsp';
			}
		
		});
		$('#subject').val('');
		
	}
</script>
<body>	

<% 
KeywordDTO keyword=new KeywordDAO().getSubjet();
String subject=keyword.getSubject();
if(userID.equals("admin")){
%>
<div class="container">
		<table class="table table-boardered table-hover" style="text-align:center; boder: 1px solid #14b8b5;">
			<thead>
				<tr>
					<th colspan="4"><h1>키워드방</h1></th>
				</tr>
			</thead>
			<tbody>
			
				<tr>
					<td style="width:110px;"><h5>주제</h5></td>
					<td colspan="2"><input class="form-control" type="text" id="subject" name="subject" maxlength="100" placeholder="주제를 입력하세요" value="<%=subject%>">
				</tr>
				<tr>
					<td>
					<button class="btn btn-primary pull-right btn-lg" onclick="subjectSubmit();">입력하기</button>
					</td>
					<%
						if(!subject.equals("")|| subject !=null){
					%>
					<td colspan="2">
					<a class="btn btn-info pull-right btn-lg" href="keywordShow.jsp?subject=<%=subject %>">키워드 보기</a>
					</td>	
					<% 		
						}
					%>
					
				</tr>	
			</tbody>
		</table>
</div>	
<% 	
}else{
	
%>
<div class="container">
	<form method="post" action="./KeywordSubmitServlet">
		<table class="table table-boardered table-hover" style="text-align:center; boder: 1px solid #14b8b5;">
			<thead>
				<tr>
					<th colspan="2"><h1>키워드방</h1></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><h3>주제</h3></td>
					<td>
					<input class="form-control" type="text" id="subject" name="subject" maxlength="100" value="<%=subject %>" readonly>
					</td>
				</tr>
				<tr>
					<td style="width:110px;"><h5>키워드</h5></td>
					<td>
					<input class="form-control" type="text" id="keyword" name="keyword" maxlength="50" placeholder="주제를 보고 떠오르는 단어를 입력하세요">
					<input class="form-control" type="hidden" id="userID" name="userID" maxlength="20" value="<%=userID%>">
					</td>
				</tr>
				<tr>
					<td colspan="2"><input class="btn btn-primary pull-right" type="submit" value="보내기"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>	
<% 	
}
%>
<div class="modal fade" id="subjectSubmitModal" tabindex="-1" role="dialog" aria-hidden="true"> <!-- dialog는 알림창 -->
	 	<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content panel-info" id="subjectSubmitType"> <!-- 이부분의 class속성이 바뀜 -->
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
								확인메시지
						</h4>
					</div>
					<div class="modal-body" id="subjectSubmitMessage"> 
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