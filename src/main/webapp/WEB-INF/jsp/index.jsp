<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cookie</title>
<meta charset="utf-8">
</head>
<body>

	<h1>
		<c:if test="${not empty login_username}">
			欢迎${login_username}访问本站，<a href='/logout'>登出</a>
		</c:if>
		<c:if test="${empty login_username}">
			您尚未登录，<a href='/login'>点我登录</a>
		</c:if>
	</h1>

	<ul>
		<li><a href="/login" target="_blank">登录</a></li>
		<li><a href="/cookie/display-form.jsp" target="_blank">显示提交表单</a></li>
		<li><a href="/cookie/js-operate.jsp" target="_blank">javascript操作Cookie</a></li>
		<li><a href="/cookie/jquery-operate.jsp" target="_blank">jQuery操作Cookie</a></li>
	</ul>

	<div>攻击者访问：http://localhost/;jsessionid=<%=session.getId() %></div>

</body>
</html>