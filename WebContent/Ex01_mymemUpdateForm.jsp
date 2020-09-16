<%@page import="myPkg.MymemBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ex01_mymemUpdateForm.jsp</title>
</head>
<body>
	<h1>Ex01_mymemUpdateForm.jsp</h1>
	<%
		MymemBean mb = (MymemBean)request.getAttribute("mb");
	%>
	
	<!--  
		내가쓰지 않아도  제일 작은 범위부터 해당 순서로 찾아나감.
		1. pageContext.getAttribute("mb");
		2. request.getAttribute("mb");
		3. session.getAttribute("mb");
		4. application.getAttribute("mb");
	-->
	<h3>수정 폼</h3>
	<!-- select.mem update.mem delete.mem -->
	<form action="update.mem" method="post" name="myform">
		<input type="hidden" name="id" value="${mb.id }">
		<table border="1">
		<%-- 
			<tr>
				<td>아이디</td>
				<td><input type="text" name="id" value="${mb.id }" disabled></td>
			</tr>
		--%>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" value="${mb.name }"></td>
			</tr>

			<tr>
				<td>비밀번호</td>
				<td><input type="text" name="password" value="${mb.password }"></td>
			</tr>

			<tr>
				<td colspan="2">
				
					<!-- <a href="#" onClick="insertData()">회원가입</a> --> <input
					type="submit" value="수정하기">
				</td>
			</tr>

		</table>

	</form>



</body>
</html>