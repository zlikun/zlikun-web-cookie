<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cookie</title>
<meta charset="utf-8">
</head>
<body>

	<h1>测试Cookie！</h1>

	<form action="/login" method="post">
		<label>姓名： <input name="username"></label><br>
		<label>邮箱： <input name="password" type="password"></label><br>
		<input type="submit" value="登录">
	</form>

</body>
</html>