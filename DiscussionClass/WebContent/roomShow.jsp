<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>    
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<%@ page import="room.RoomDTO" %>
<%@ page import="room.RoomDAO" %>
<!DOCTYPE html>
<html>
<head>
<%
	RoomDAO roomDAO=new RoomDAO();
	String userGroupNumber=new UserDAO().getUser(userID).getGroupNumber()+"";//현재접속자의 그룹넘버 
	
	String roomUserID=request.getParameter("roomUserID");
	String roomGroupNumber=request.getParameter("roomGroupNumber");
	String roomID=request.getParameter("roomID");
	
	
	if(userID==null){
		session.setAttribute("messageType", "오류메시지");
		session.setAttribute("messageContent", "현재로그인이 되어 있지 않습니다.");
		response.sendRedirect("login.jsp");
		return;
	}
	if(!userGroupNumber.equals(roomGroupNumber)){
		session.setAttribute("messageType", "오류메시지");
		session.setAttribute("messageContent", "내 그룹이 아닌 방에는 접속할 수 없습니다.");
		response.sendRedirect("roomList.jsp");
		return;
	}
	if(roomID==null || roomID.equals("")){
		session.setAttribute("messageType", "오류메시지");
		session.setAttribute("messageContent", "접근할 수 없습니다.");
		response.sendRedirect("roomList.jsp");
		return;
	}
	int on=new UserDAO().onUser(userID);
	
	RoomDTO room= roomDAO.getRoom(roomID);
	int progress=roomDAO.getRoom(roomID).getProgress();
%>

<script>
	//메시지 보낼 때 alert 띄운 후 자동으로 꺼질 수 있는 이벤트 
	function autoClosingAlert(selector, delay){
		var alert=$(selector).alert();
		alert.show();
		window.setTimeout(function(){alert.hide()}, delay);
	}
	//채팅 전송 이벤트 
	function chatSubmit(){
		var userID = '<%= userID %>';
		var roomID = '<%=roomID%>';
		var chatContent = $('#chatContent').val();
		$.ajax({
			type:'POST',
			url:'./ChatSubmitServlet',
			data:{
				userID:encodeURIComponent(userID),
				roomID:encodeURIComponent(roomID),
				chatContent:encodeURIComponent(chatContent)
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
		$('#chatContent').val('');
	}
	
	//채팅 리스트 이벤트 
	var lastChatID=0;
	function getChatList(chatID){
		var roomID= '<%=roomID%>';
		var userID='<%=userID%>';
		$.ajax({
			type:"POST",
			url:"./ChatListServlet",
			data:{
				userID:encodeURIComponent(userID),
				roomID:encodeURIComponent(roomID),
				chatID:encodeURIComponent(chatID)
			},
			success:function(data){
				if(data=="") return;
				
				var parsed =JSON.parse(data);
				var result=parsed.result;
				
				for(var i=0; i<result.length; i++){
					
					if(result[i][3].value=="" || result[i][3].value=="0"){
						result[i][3].value="http://localhost:8080/DiscussionClass/images/icon.png";
					}
					else{
						result[i][3].value="http://localhost:8080/DiscussionClass/upload/"+result[i][3].value;
					}
					showChat(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value);
				}
				lastChatID=Number(parsed.last);
			}
		});
	}
	function showChat(chatName, chatContent, chatTime, memberProfile){
		if(chatName=='<%=userID%>'){
			$('#chatList').append('<ul class="chat">'+
					'<li class="right clearfix">'+
					'<span class="chat-img pull-right">'+
					'<img src="'+memberProfile+'">'+
					'</span>'+
					'<div class="chat-body clearfix">'+
					'<div class="header">'+
					'<strong class="primary-font">나</strong>'+
					'<small class="pull-right text-muted"><i class="fa fa-clock-o"></i>'+chatTime+'</small>'+
					'</div>'+
					'<h5>'+ chatContent +'</h5>'+
					'</div>'+
					'</li>'+
					'</ul>'
					)
		}
		else{
			$('#chatList').append('<ul class="chat">'+
					'<li class="left clearfix">'+
					'<img src="'+memberProfile+'">'+
					'</span>'+
					'<div class="chat-body clearfix">'+
					'<div class="header">'+
					'<strong class="primary-font">'+chatName+'</strong>'+
					'<small class="pull-right text-muted"><i class="fa fa-clock-o"></i>'+chatTime+'</small>'+
					'</div>'+
					'<h5>'+ chatContent +'</h5>'+
					'</div>'+
					'</li>'+
					'</ul>'
					)
		}
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}
	function getInfinitedChat(){
		setInterval(function(){
			getChatList(lastChatID);
		},3000);
	}
	function getMemberList(){
		var roomID='<%=roomID%>';
		$.ajax({
			type:'POST',
			url:'./GetMemberListServlet',
			data:{
				roomID:encodeURIComponent(roomID)
			},
			success:function(data){
				if(data=="") return;
				var parsed =JSON.parse(data);
				var result=parsed.result;
				
				for(var i=0; i < result.length; i++){
					if(result[i][2].value=="" || result[i][2].value=="0"){
						result[i][2].value="http://localhost:8080/DiscussionClass/images/icon.png";
					}
					else{
						result[i][2].value="http://localhost:8080/DiscussionClass/upload/"+result[i][2].value;
					}
					if(result[i][0].value=='<%=userID%>'){
						result[i][1].value='나';
					}

					showMemberList(result[i][0].value,result[i][1].value,result[i][2].value,result[i][3].value);
				}
			}
		});
	}
	function showMemberList(m_userID, m_userName, m_userProfile, now){
		
		if(now==1){
			$('#memberList').append('<li class="active bounceInDown">'+
					'<img src="'+m_userProfile+'" alt="" class="img-circle" style="width:15px; height:15px;">'+
					'<div class="friend-name">'+
					'<strong>'+m_userName+'('+m_userID+')</strong>'+
					'<span class="glyphicon glyphicon-check" style="color:red;"></span>'+					
					'<button class="btn btn-defalt btn-sm" style="margin-left:20px;" onclick="userScoreSubmit(\''+m_userID+'\');">점수주기</button>'+
					'</div>'+
					'</li>'
					)
		}
		else{
			$('#memberList').append('<li class="active bounceInDown">'+
					'<img src="'+m_userProfile+'" alt="" class="img-circle" style="width:15px; height:15px;">'+
					'<div class="friend-name">'+
					'<strong>'+m_userName+'('+m_userID+')</strong>'+
					'<span class="glyphicon glyphicon-check"></span>'+
					'<button class="btn btn-defalt btn-sm" style="margin-left:20px;" onclick="userScoreSubmit(\''+m_userID+'\');">점수주기</button>'+
					'</div>'+
					'</li>'
					)
			
		}
	}
	function getInfinitedMember(){
		setInterval(function(){
			getMemberList();
		},7000);
	}
	function progress(parameter){
		var roomID='<%=roomID%>';
		console.log(parameter);
		$.ajax({
			type:'POST',
			url:'./ProgressSubmitServlet',
			data:{
				roomID:encodeURIComponent(roomID),
				progress:encodeURIComponent(parameter)
			},
			success:function(result){
				if(parameter=='25'){
					$('#progress25').css('width','25%');
				}
				else if(parameter=='50'){
					$('#progress25').css('width','25%');
					$('#progress50').css('width','25%');
				}
				else if(parameter =='75'){
					$('#progress25').css('width','25%');
					$('#progress50').css('width','25%');
					$('#progress75').css('width','25%');
				}
				else if(parameter =='100'){
					$('#progress25').css('width','25%');
					$('#progress50').css('width','25%');
					$('#progress75').css('width','25%');
					$('#progress100').css('width','25%');
				}
			}
		})
	}
	function getProgress(progress){
		if(progress=='25'){
			$('#progress25').css('width','25%');
		}
		else if(progress=='50'){
			$('#progress25').css('width','25%');
			$('#progress50').css('width','25%');
		}
		else if(progress =='75'){
			$('#progress25').css('width','25%');
			$('#progress50').css('width','25%');
			$('#progress75').css('width','25%');
		}
		else if(progress =='100'){
			$('#progress25').css('width','25%');
			$('#progress50').css('width','25%');
			$('#progress75').css('width','25%');
			$('#progress100').css('width','25%');
		}
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
</head>
<body>	
	<div class="container">
		<table class="table table-bordered table-hover" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="4"><h4>토론하기</h4></th>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>제목</h5></td>
					<td colspan="3"><h5><%=room.getRoomTitle()%></h5></td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>작성자</h5></td>
					<td><h5><%=room.getRoomUserID() %></h5></td>
					<td style="background-color:#fafafa; color:#000000; width:110px;"><h5>작성자 그룹넘버</h5></td>
					<td><h5><%=userGroupNumber %></h5></td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>작성날짜</h5></td>
					<td colspan="3"><h5><%=room.getRoomDate() %></h5></td>
				</tr>
				<tr>
					<td style="vertical-align: middle; min-height: 150px; background-color:#fafafa; color:#000000; width:80px;"><h5>글 내용</h5></td>
					<td colspan="3" style="text-align:left;"><h5><%=room.getRoomContent()%></h5></td>
				</tr>
				<tr>
					<td style="vertical-align: middle; min-height: 150px; background-color:#fafafa; color:#000000; width:80px;"><h5>첨부된 이미지</h5></td>
					<td colspan="3" style="text-align:center;"><img src="<%=roomDAO.getRoomFile(roomID)%>" width="300px" height="200px"> </td>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color:#000000; width:80px;"><h5>첨부파일</h5></td>
					<td colspan="3"><h5><a href="boardDownLoad.jsp?boardID=>"><%=room.getRoomFile() %></a></h5></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" style="text-align:right;">
						<a href="roomList.jsp" class="btn btn-primary" onclick="offUser();">목록</a>
				<%
					if(userID.equals(roomUserID)){
				%>
					<a href="roomUpdate.jsp?roomID=<%=room.getRoomID()%>" class="btn btn-default">수정</a>
					<a href="RoomDeleteServlet?roomID=<%=room.getRoomID()%>" class="btn btn-danger" onclick="return confirm('정말로 삭제하시겠습니까?')">삭제</a>
				<% 		
					}
				%>
					</td>
				</tr>	
			</tbody>
		</table>
	</div>
	<!-- 진행률 -->
	<div class="progress progress-striped active">
		<div class="progress-bar progress-bar-info" id="progress25">
			<span class="sr-only">15% Complete(info)</span>
		</div>
		<div class="progress-bar progress-bar-success" id="progress50">
			<span class="sr-only">15% Complete(success)</span>
		</div>
		<div class="progress-bar progress-bar-warning" id="progress75">
			<span class="sr-only">15% Complete(warning)</span>
		</div>
		<div class="progress-bar progress-bar-danger" id="progress100">
			<span class="sr-only">15% Complete(danger)</span>
		</div>
	</div>
	<%
		if(userID.equals(roomUserID)){
	%>
	<div class="container">
 		<table style="width:100%; text-align:center;">
 			<tr>
 				<td><button class="btn btn-info" onclick="progress('25');">25%</button></td>
 				<td><button class="btn btn-success"  onclick="progress('50');">50%</button></td>
 				<td><button class="btn btn-warning" onclick="progress('75');" >75%</button></td>
 				<td><button class="btn btn-danger"  onclick="progress('100');">100%</button></td>
 			</tr>
 		</table>
 	</div>
	<% 		
		}
	%>
	 
	<div class="container bootstrap snippets bootdey">
    <div class="row">
		<div class="col-md-4 bg-white ">
            <div class=" row border-bottom padding-sm" style="height: 40px;">
            	<h4>현재접속자</h4> 
            </div>
            <!-- member list -->
	            <ul class="friend-list" id="memberList">
	                           
	            </ul>
		</div>
        
        <!--=========================================================-->
        <!-- selected chat -->
    	<div class="col-md-8 bg-white ">
            <div class="chat-message" id="chatList" style="overflow-y: auto; width: auto; height:300px;">
                
            </div>
            <!-- 전송폼 -->
            <div class="chat-box bg-white">
            	<div class="input-group">
            		<input class="form-control border no-shadow no-rounded" placeholder="메시지를 입력하세요" id="chatContent">
            		<span class="input-group-btn">
            			<button class="btn btn-success no-rounded" type="button" onclick="chatSubmit();">전송</button>
            		</span>
            	</div><!-- /input-group -->	
            </div>            
		</div>        
	</div>
</div>
<div class="alert alert-success" id="successMSG" style="display:none;">
	<strong>메시지를 전송 했습니다.</strong>
</div>
<div class="alert alert-danger" id="dangerMSG" style="display:none;">
	<strong>내용을 입력 해 주세요</strong>
</div>
<div class="alert alert-warning" id="warningMSG" style="display:none;">
	<strong>데이터베이스 오류가 발생 했습니다.</strong>
</div>
<script>
	$(document).ready(function(){
		getChatList('0');
		getInfinitedChat();
		getMemberList();
		getProgress('<%=progress%>');
	});
</script>
</body>
</html>