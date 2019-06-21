<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Form Display</title>
</head>
<body>

<%
	String fName = request.getParameter("fName");
	String lName = request.getParameter("lName");
	String sEmail = request.getParameter("sEmail");
	String sGender = request.getParameter("gender");
	String sCountry = request.getParameter("country");	
	String[] sHobiies = request.getParameterValues("hobbies");
%>
Name: <%=fName %> <%=lName %>
<br/>
Gender: <%=sGender %>
<br/>
Email: <%=sEmail %>
<br/>
Country: <%=sCountry %>
<br/>
Hobbies: <%for(String s :sHobiies) out.print(s + " "); %>


</body>
</html>