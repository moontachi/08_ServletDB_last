<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ex02_surveyList.jsp</title>
</head>
<body>
<h1>Ex02_surveyList.jsp</h1>
<h2>레코드 갯수 : ${fn:length(lists) }</h2>
<%
	boolean flag = false;
	request.setAttribute("flag", flag);
%>
	<table border="1">
		<tr>
			<td>번호</td>
			<td>이름</td>
			<td>소속 회사</td>
			<td>이메일 주소</td>
			<td>과정 만족도</td>
			<td>관심 분야</td>
			<td>정보 발송 방법</td>
			<td>정보 발송 동의</td>
			<td>삭제</td>
			<td>수정</td>
		</tr>
		<c:forEach var="i" items="${lists }">
			<tr>
				<td>${i.no }</td>
				<td>${i.name }</td>
				<td>${i.company }</td>
				<td>${i.email }</td>
				<td>${i.satisfaction }</td>
				<td>${i.part }</td>
				<td>${i.howto }</td>
				<td>
					<c:if test="${i.agree == 1 }">
						동의함
					</c:if>
					<c:if test="${i.agree == 0 }">
						동의안함
					</c:if>
				</td>
				<td><a href="delete.sv?no=${i.no}">삭제</a></td>
				<td><a href="updateForm.sv?no=${i.no}">수정</a></td>
			</tr>
		
		</c:forEach>
	
	</table>
	<a href="Ex02_surveyInputForm.jsp">등록</a>
	
</body>
</html>