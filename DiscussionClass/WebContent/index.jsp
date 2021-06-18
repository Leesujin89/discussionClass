<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="score.ScoreDTO, score.ScoreDAO" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=viewport content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css"> 
<link rel="stylesheet" href="css/custom.css">
<title>토론수업도구</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<%
	String userID = null;
	if(session.getAttribute("userID")!=null){
		userID=(String)session.getAttribute("userID");
	}
%>
<script>
	function getUserScore(){
		var toID='<%=userID%>';
		$.ajax({
			type:'POST',
			url:'./GetUserScoreServlet',
			data:{
				toID:encodeURIComponent(toID)
			},
			success:function(data){
				if(data=="")return;
				
				var parsed=JSON.parse(data);
				var result=parsed.result;
				
				for(var i=0; i<result.length; i++){
					showScore(result[i][0].value);
				}
			}
		});
	}
	function showScore(fromID){
		console.log('보내는사람'+fromID);
		$('#showModal').append('<div class="modal-header panel-heading">'+
				'<button type="button" class="close" data-dismiss="modal">'	+
				'<span aria-hidden="true">&times</span>'+
				'<span class="sr-only">Close</span>'+
				'</button>'+
				'<h4 class="modal-title"> 칭찬합니다!&lt;'+fromID+'&gt;님이 칭찬하였습니다. </h4>'+
				'</div>'+
				'<div class="modal-body">'+
				'<img id="scoreImage" src="images/goodjob2.jpg">'+
				'<audio src="audio/와우.wav" autoplay>'+
				'</div>'+
				'<div class="modal-footer">'+
				'<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>'+
				'</div>'
               )

       	$('#scoreModal').modal("show");
         
	}
	
	function getInfinitedUserScore(){
		setInterval(function(){
			getUserScore();
		},5000);
	}
</script>
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>	
			<a class="navbar-brand" href="main.jsp">토론수업도구</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="roomList.jsp">토론방</a></li>
				<li><a href="keyword.jsp">키워드방</a></li>
				<li><a href="score.jsp">점수주기</a></li>
				<li><a href="noticeList.jsp">공지사항</a></li>
			</ul>	
			<%
				if (userID ==null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">들어가기 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>	
			</ul>
			<% 	
			}
				else {
			%>
			 <ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">관리 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="userProfile.jsp">내 프로필 관리</a></li>
						<li><a href="userUpdate.jsp">회원정보수정</a></li>
						<li><a href="logout.jsp">로그아웃</a></li>
					</ul>
				</li>	
			</ul>
			<% 		
				}
			%>
			
		</div>
	</nav>
<%
	String messageType=null;
	String messageContent=null;
	
	if (session.getAttribute("messageType") !=null){
		messageType = (String)session.getAttribute("messageType");
	}
	if (session.getAttribute("messageContent")!=null){
		messageContent= (String) session.getAttribute("messageContent");
	}
	if (messageContent !=null ){
%>
	<!-- 모달창 -->
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="veritical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <%if (messageType.equals("오류메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h2 class="modal-title">
							<%=messageType %>
						</h2>
					</div>
					<div class="modal-body">
						<%=messageContent %>
					</div>
					<div class="modal-footer">
						<button class="btn btn-primary" type="button" data-dismiss="modal">확인</button>
					</div>
				</div>	
			</div>
		</div>
	</div>
	<!-- score모달창 -->
	<div class="modal fade" id="scoreModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content panel-info" id="showModal">
					
				</div>
			</div>
		</div>
	</div>
	<!-- keyword subject  모달창 -->
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
	<script>
		$('#messageModal').modal("show");
	</script>
<% 		
	session.removeAttribute("messageType");
	session.removeAttribute("messageContent");
	}
%>	
<script>
$(document).ready(function(){
	getUserScore();
	getInfinitedUserScore();
});
</script>
</body>
</html>