
<html>
<head>
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.InitialContext, javax.naming.Context" %>
</head>
<body>
<%
	InitialContext initCtx=new InitialContext();
	Context envContext =(Context) initCtx.lookup("java:/comp/env");
	DataSource ds=(DataSource)envContext.lookup("jdbc/DiscussionClass");
	Connection conn=ds.getConnection();
	Statement stmt=conn.createStatement();
	ResultSet rs=stmt.executeQuery("select version();");
	while(rs.next()){
		//결과 값이 있다면
		out.println("MYSQL version: "+ rs.getString("version()"));
		//rs에서 String으로 출력
	}
	rs.close();
	stmt.close();
	conn.close();
	initCtx.close();
%>
</body>
</html> 