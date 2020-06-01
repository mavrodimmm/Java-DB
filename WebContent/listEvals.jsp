<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Evaluations</title>
<link type="text/css" rel="stylesheet" href="css/a1.css">
</head>
<body>
	<h1>Evaluation Tracker</h1>
	<br>
	<a href="index.jsp">Add an Evaluation</a>
	<br>
	<table>
		<tr>
			<th>Course</th>
			<th>Evaluation</th>
			<th>Due Date</th>
			<th>Submitted?</th>
		</tr>
		<c:forEach var="eval" items="${sessionScope.evals}">
			<tr>
				<td>
					<p><c:out value="${eval.course.code}"/></p>
					<p><c:out value="${eval.course.title}"/></p>
				</td>
				<td><c:out value="${eval.evalName}"/></td>
				<td><c:out value="${eval.dueDate}"/></td>
				<td><c:out value="${eval.submitted}"/></td>
			</tr>
		</c:forEach>
	</table>
	
</body>
</html>