<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 04.03.2017
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Delete Account</title>
</head>
<body>
<blockquote><blockquote>
    <p><h3>Delete all client`s information ?</h3></p>
    <p><h4>Client: <c:out value="${clientFullName}"></c:out> </h4></p>
    <blockquote>
        <form action="/Servlet" method="post">
            <input type="hidden" value="${clientID}" name="clientID"/>
            <input type="hidden" value="deleteClientAccount" name="actionType"/>
            <input type="submit" align="center" value="Confirm" />
        </form>

        <form action="/Servlet" method="post">
            <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
            <input type="submit" align="center" value="Back" name="homeButton"/>
        </form>
    </blockquote>
</blockquote></blockquote>
</body>
</html>