<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 04.03.2017
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Operation process</title>
</head>
<body>
<blockquote><blockquote>
    <p><h3>Operation status</h3></p>
<p><h4>Result: <font color="#228b22"><c:out value="${statusMessage}"></c:out></font</h4></p>
<blockquote>
    <form action="/Servlet" method="post">
        <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
        <input type="submit" align="center" value="Return to homepage" name="homeButton"/>
    </form>
</blockquote></blockquote></blockquote>
</body>
</html>
