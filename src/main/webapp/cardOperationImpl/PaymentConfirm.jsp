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
<blockquote><blockquote>
    <p><h3><blockquote>Transaction status</blockquote></h3></p>

    <p><h4>From: <c:out value="${cardIDStr}"></c:out></h4></p>
    <p><h4>To: <c:out value="${destinationID}"></c:out></h4></p>
    <p><h4>Description: <c:out value="${infoMessage}"></c:out></h4></p>

    <p></p>
    <p><h4>Result: <font color="#228b22"><c:out value="${statusMessage}"></c:out></font</h4></p>
<blockquote>
<form action="/Servlet" method="post">
    <input type="hidden" value="returnToPerArea" name="actionType"/>
    <input type="hidden" value="${cardID}" name="cardID"/>
    <input type="submit" align="center" value="Return to homepage" name="homeButton"/>
</form>

<form action="/Servlet" method="post">
    <input type="hidden" value="openCardOperationMenu" name="actionType"/>
    <input type="hidden" value="${cardID}" name="cardID"/>
    <input type="submit" align="center" value="Make other operations" name="homeButton"/>
</form>
</blockquote></blockquote></blockquote>
</body>
</html>
