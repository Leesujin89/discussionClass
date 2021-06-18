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
String subject=request.getParameter("subject");
%>
<script>
	function getKeyword(){
		var subject = '<%=subject%>';
		$.ajax({
			type:'POST',
			url:'./GetKeywordServlet',
			data:{
				subject:encodeURIComponent(subject)
			},
			success:function(data){
				if(data=="")return;
				
				var parsed=JSON.parse(data);
				var result=parsed.result;
				
				for (var i=0; i < result.length; i++){
					if(result[i][0].value=='<%=userID%>'){
						result[i][1].value ='나';
					}
					showKeywordList(result[i][0].value, result[i][1].value, result[i][2].value)
				}
				$('#button').css('display', 'none');
			}
		});
	}
	function showKeywordList(userID, userName, keyword){
		$('#showKeyword').append('<div class="col-xs-3" style="border:1px solid red; text-align:center;">'+
				userID+'['+userName+']<br>'+
				'<h3>'+keyword+'</h3>'+
				'</div>'		
		)
	}
</script>
</head>
<body>	

<div class="container">
		<table class="table table-boardered table-hover" style="text-align:center; boder: 1px solid #14b8b5;">
			<thead>
				<tr>
					<th colspan="4" id="button"><%if(userID.equals("admin")) {%><button class="btn btn-primary btn-lg" onclick="getKeyword();">키워드 보기</button><%} else{
					%><h1>친구들이 'keyword'를 전송할 때 까지 기다려주세요</h1><%} %></th>
				</tr>
			</thead>
		</table>
</div>	
<div class="row" id="showKeyword">
	
</div>
	


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