<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Student Page</title>
</head>
<body>

<form action="addStudentRequest.jsp" method = "post">
Add Student
<br/>
First name: <input type = "text" name = "fName">
<br/>
Last name: <input type="text" name = "lName">
<br/>
Gender: 
Male <input type = "radio" name = "gender" value = "male">
Female <input type = "radio" name = "gender" value = "female">
<br/>
Country:
<select name = "country">
	<option>China</option>
	<option>USA</option>
	<option>UK</option>
</select>
<br/>
Hobbies:
<input type = "checkbox" name ="hobbies" value ="Swimming">Swimming
<input type = "checkbox" name ="hobbies" value ="Basketball">Basketball
<input type = "checkbox" name ="hobbies" value ="Football">Football
<br/>
Email: <input type = "text" name = "sEmail">
<br/>
<input type = "submit" value = "Save">
</form>

</body>
</html>