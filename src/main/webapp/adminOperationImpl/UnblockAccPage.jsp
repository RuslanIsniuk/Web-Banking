<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 04.03.2017
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Unblock Account</title>
</head>
<body>
<blockquote><blockquote>
    <p><h3>Unblock account <c:out value="${accountID}"></c:out> ?</h3></p>
    <p><h4>Unblock account with credit card №  <c:out value="${cardIDStr}"></c:out> ?</h4></p>
    <blockquote>
        <form action="/Servlet" method="post">
            <input type="hidden" value="unblockClientAccount" name="actionType"/>
            <input type="hidden" value="${cardID}" name="cardID"/>
            <input type="submit" align="center" value="Confirm" name="homeButton"/>
        </form>

        <form action="/Servlet" method="post">
            <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
            <input type="submit" align="center" value="Back" name="homeButton"/>
        </form>
    </blockquote>
</blockquote></blockquote>
</body>
</html>
