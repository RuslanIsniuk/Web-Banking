<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 23.01.2017
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Card Transaction</title>
</head>
<body>
<blockquote><blockquote>
<form action="/Servlet" method="post">
    <p><h4>From card: <c:out value="${cardIDStr}"></c:out></h4></p>
    <input type="hidden" value="${cardID}" name="cardID">
    <input type="hidden" value="transfToAnotherCardConfirm" name="actionType"/>
    <p>Enter the sum: <input type="text" size="7" maxlength="7" name="amount"/></p>
    <p><font color="#a9a9a9">Enter amount in next formats: 00000.00 or 00000</font></p>
    <p><font color="#a9a9a9">Permissible amount of payment: 50000.</font></p>

    <p>To card:
        <input type="text" size="3" maxlength="4" name="cardIDForTransferPart1">
        <input type="text" size="3" maxlength="4" name="cardIDForTransferPart2">
        <input type="text" size="3" maxlength="4" name="cardIDForTransferPart3">
        <input type="text" size="3" maxlength="4" name="cardIDForTransferPart4"></p>

    <input type="submit" value="Accept">
</form>

<p><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
</blockquote></blockquote>
</body>
</html>
