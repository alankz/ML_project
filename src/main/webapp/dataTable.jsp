<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Vist Data</title>
</head>
<body>
	<center>
		<c:choose>
		    <c:when test="${admin}">
		       <a href="/userManagement">User Management</a>
		    </c:when>
		</c:choose>
		<h2>Test Vist Data</h2>
		<table style="width: 500px">
			<colgroup>
				<col span="1" style="width: 33%;">
				<col span="1" style="width: 33%;">
				<col span="1" style="width: 33%;">
			</colgroup>
			<tr>
				<td>Date</td>
				<td>Website</td>
				<td>Visits</td>
			</tr>
			<c:forEach var="data" items="${dataList}">
				<tr>
					<td><c:out value="${data.id.date}" /></td>
					<td><c:out value="${data.id.website}" /></td>
					<td><c:out value="${data.visits}" /></td>
				</tr>
			</c:forEach>
		</table>
		<form name="dataForm" action="/home" method="post">
			<input name="queryDate" type="date" value="${queryDate}">
			<input type="submit">
		</form>
	</center>
</body>
</html>