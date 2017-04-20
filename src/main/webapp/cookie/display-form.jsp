<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cookie</title>
<meta charset="utf-8">
</head>
<body>

	<h1>测试Cookie！</h1>

	<form action="/display" method="post" target="_blank">
		<label>姓名： <input name="name"></label><br>
		<label>邮箱： <input name="email" type="email"></label><br>
		<label>性别： </label><label><input name="gender" type="radio" value="1"> 男</label> <label><input name="gender" type="radio" value="-1"> 女</label><br>
		<input type="submit" value="提交">
	</form>

</body>
</html>