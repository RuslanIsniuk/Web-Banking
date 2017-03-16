<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 05.03.2017
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Internet Payment</title>
</head>
<body>

<form action="/Servlet" method="post">
    <p><h4>From card: <c:out value="${cardIDStr}"></c:out></h4></p>

    <p>Enter internet account (10 numbers):</p>
    <p><input type="text" size="3" maxlength="5" name="internetIDPart1">
        <input type="text" size="3" maxlength="5" name="internetIDPart2"></p>

    <p>Enter the sum: <input type="text" name="amount"/></p>
    <p><font color="#a9a9a9">Enter amount in next formats: 00000.00 or 00000</font></p>
    <p><font color="#a9a9a9">Permissible amount of payment: 50000.</font></p>

    <p>For communal services</p>
    <input type="hidden" value="${cardID}" name="cardID">
    <input type="hidden" value="internetPaymentConfirm" name="actionType"/>
    <input type="submit" value="Accept">
</form>

<p><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>

</body>
</html>
