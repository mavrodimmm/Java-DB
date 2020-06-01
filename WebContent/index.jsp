<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Evaluation</title>
<link type="text/css" rel="stylesheet" href="css/a1.css">
</head>
<body>
	<sql:setDataSource var="Data" driver="com.mysql.cj.jdbc.Driver" 
		url="${initParam.dbUrl}${initParam.dbName}" 
		user="${initParam.user}" password="${initParam.password}"/>
	
	<sql:query var="results" dataSource="${Data}" 
		sql="Select * from courses;"  />
	
	<h1>Add New Evaluation</h1>
	<br>
	<a href="ListEvaluations">List all Evaluations</a>
	<br>
	
	<c:if test="${!empty pageContext.exception.message}">
		<div class="errors">${pageContext.exception.message}</div>
	</c:if>
	
	<form action="AddEvaluation">
		<label>Course:</label>
		<select name="course">
			<c:forEach var="row" items="${results.rows}">
				<option value="${row.code}" 
				${sessionScope.courseCode == row.code ? 'selected' : ''}>
					<c:out value="${row.code}"/>:
					<c:out value="${row.title}"/>
				</option>
			</c:forEach> 
		</select>
		<br>
		<label>Evaluation Name:</label>
		<input type="text" name="evaluationName" value="${sessionScope.evaluationName}"><br>
		<label>Due Date (yyyy/mm/dd): Year:</label>
		<input type="text" name="year" value="${sessionScope.year}">
		<label>Month:</label>
		<select name="month">
			<c:forEach var="mCounter" begin="1" end="12">
				<option ${sessionScope.month == mCounter ? 'selected' : ''}>
					<c:out value="${mCounter}"/>
				</option>
			</c:forEach>
		</select>
		
		<label>Day:</label>
		<select name="day">
			<c:forEach var="dCounter" begin="1" end="31">
				<option ${sessionScope.day == dCounter ? 'selected' : ''}>
					<c:out value="${dCounter}"/>
				</option>
			</c:forEach>
		</select>
		
		<fieldset>
			<legend>Submitted</legend>
			<input type="radio" name="submit" 
			${sessionScope.submit == 'true' ? 'checked' : ''} value="true">
			<label>Submitted</label>
			<input type="radio" name="submit" 
			${sessionScope.submit == 'false' ? 'checked' : ''} value="false">
			<label>Not Submitted</label>
		</fieldset>
		
		<input type="submit" value="Add"> 
		<input type="reset" value="Clear">
		
	</form>
</body>
</html>