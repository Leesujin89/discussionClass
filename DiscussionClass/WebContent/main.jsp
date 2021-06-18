<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index.jsp" %>   
<%@ page import="java.util.ArrayList, room.RoomDAO, room.RoomDTO, notice.NoticeDAO, notice.NoticeDTO" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>	

<%
	ArrayList<RoomDTO>mainRoomList=new RoomDAO().mainRoomList();
	ArrayList<NoticeDTO>mainNoticeList=new NoticeDAO().mainNoticeList();
%>
<div class="container">
	<div id="carousel-example-generic" class="carousel slide">
            <ol class="carousel-indicators">
              <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
              <li data-target="#carousel-example-generic" data-slide-to="1"></li>
              <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>
             <div class="carousel-inner">
                <div class="item active">
                   <img src="images/discussion3.png" alt="First slide">
                </div>
                <div class="item">
                   <img src="images/discussion2.png" alt="Second slide">               
                </div>
                <div class="item">
                   <img src="images/discussion1.png" alt="Four slide">                 
                </div>
             </div>
              <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
              </a>
              <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right "></span>
              </a>
          </div>
</div>	
<hr>
<div class="row">
	<div class="col-md-6" style="padding-left:150px;">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">공지사항 <a href="noticeList.jsp"><span class="glyphicon glyphicon-list pull-right"></span></a></h1>
				<span ></span>
			</div>
			<div class="panel-body">
				<div class="media">
					<div class="media-left">
						<table class="table table-hover" style="width:500px;">
							<%
							for(int i=0; i < mainNoticeList.size(); i++){
							%>
							<tr>
								<td><a href="noticeShow.jsp?noticeID=<%=mainNoticeList.get(i).getNoticeID()%>"><%=mainNoticeList.get(i).getNoticeTitle() %></a></td>
								<td><%=mainNoticeList.get(i).getNoticeDate()%></td>
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
	<div class="col-md-6" style="padding-right:150px;">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">토론방 <a href="roomList.jsp"><span class="glyphicon glyphicon-list pull-right"></span></a></h1>
			</div>
			<div class="panel-body">
				<div class="media">
					<div class="media-left">
						<table class="table table-hover" style="width:500px;">
							<%
							for(int i=0; i < mainRoomList.size(); i++){
							%>
							<tr>
								<td><a href="roomShow.jsp?roomID=<%=mainRoomList.get(i).getRoomID()%>&roomGroupNumber=<%=mainRoomList.get(i).getRoomGroupNumber()%>&roomUserID=<%=mainRoomList.get(i).getRoomUserID()%>"><%=mainRoomList.get(i).getRoomTitle() %></a></td>
								<td><%=mainRoomList.get(i).getRoomDate() %></td>
								<td><%=mainRoomList.get(i).getProgress() %>% 완료</td>
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
</div>
<hr>
<footer>
	<div class="container">
		<br>
		<div class="row">
			<div class="col-md-5" style="padding-left:30px;"><h4 style="text-align:center;">내비게이션</h4>
				<div class="list-group">
					<a href="#" class="list-group-item">토론방</a>
					<a href="#" class="list-group-item">키워드방</a>
					<a href="#" class="list-group-item">친구에게 점수 주기</a>
				</div>
			</div>
			<div class="col-md-2" style="text-align: center;">
				<h5>copyright &copy; 2021</h5><h5>SuJin(이수진)</h5>
			</div>
			<div class="col-md-5" style="padding-right:30px;"><h4 style="text-align:center;">SNS</h4>
				<div class="list-group">
					<a href="#" class="list-group-item">이메일</a>
					<a href="#" class="list-group-item">블로그</a>
					<a href="#" class="list-group-item">인스타그램</a>
				</div>
			</div>
		</div>
	</div>
</footer>
<script>
 $('.carousel').carousel({
	 interval:2000
 })
</script>
</body>
</html>