<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 23.02.2017
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payment System</title>
</head>
<body>
<br>
<br>
<p><h3 align="center"><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
<br>
<center>
<form action="/Servlet" method="post">
    <input type="hidden" value="returnToPerArea" name="actionType"/>
    <input type="hidden" value="${cardID}" name="cardID"/>
    <input type="submit" value="Return to homepage" name="homeButton"/>
</form></center>
</body>
</html>
