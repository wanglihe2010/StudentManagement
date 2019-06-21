<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ page import ="java.util.*,edu.ksu.www.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Student Tracker App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">

</head>



<body>
	<div id ="wrapper">
		<div id="header">
			<h2> FooBar University </h2>
		</div>
	</div>
	
	<div id="container">
		<div id ="content">
			<!-- add new button -->
			<input type = "button" value ="Add Student"
				onclick = "window.location.href='add-student-form.jsp'; return false;"
				class = "add-student-button"
			/>
			<br>
			
			<!-- search student form --> 
			<form action="StudentControllerServlet" method = "Get">
				<input type ="hidden" name = "command" value="SEARCH"/>
				Search Student: 
				<input type="text" name="SearchName" />
				<input type="submit" value ="Search" class="add-student-button" />
			</form>
			
			<!-- list student -->
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
					
				</tr>
				
				<c:forEach var ="tempStudent" items = "${STUDENTS_LIST }">
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="studentId" value ="${tempStudent.id }"/>
					</c:url>
					<c:url var="tempLink_delete" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"/>
						<c:param name="studentId" value ="${tempStudent.id }"/>
					</c:url>
				
					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td>
							<a href="${tempLink}">Update</a> 
							| 
							<a href="${tempLink_delete}"
							onclick ="if(!(confirm('Are you sure you want to delete this student'))) return false">
							Delete</a>
						</td>
					</tr>
				</c:forEach>
				
				
				
			</table>
		</div>
	</div>
</body>
</html>