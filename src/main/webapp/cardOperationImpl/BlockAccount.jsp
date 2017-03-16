<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 15.01.2017
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Block account</title>
</head>
<body>
    <h4>Do You really want to block card №  <c:out value="${cardIDStr}"></c:out>?</h4>

    <p><h4><font color="#8b0000">CAUTION!</font> Pressing "Confirm" You also block your account № <c:out value="${accountID}"></c:out>. You will not have access to it balance.</h4></p>

    <form action="/Servlet" method="post">
        <input type="submit" value="Confirm" name="ConfirmButton" align="left"/>
        <input type="hidden" value="${cardID}" name="cardID"/>
        <input type="hidden" value="confirmBlockAccount" name="actionType"/>
    </form>

    <p><h3><font color="#006400"><c:out value="${infoMessage}"></c:out></font></h3></p>

    <form action="/Servlet" method="post">
        <input type="hidden" value="returnToPerArea" name="actionType"/>
        <input type="hidden" value="${cardID}" name="cardID"/>
        <input type="submit" value="Return to homepage" name="homeButton"/>
    </form>


</body>
</html>
