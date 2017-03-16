<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 05.03.2017
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Client's info</title>
</head>
<body>
<blockquote>
    <blockquote>
        <p>
        <h3>Client <c:out value="${client.clientFullName}"></c:out> </h3></p>
        <p><h4>Login: <c:out value="${client.clientLogin}"></c:out></h4></p>
        <p><h4>ID: <c:out value="${client.clientID}"></c:out></h4></p>
        <br>
        <table border="1">
            <tr>
                <th>Card ID</th>
                <th>Valid Date</th>
                <th>Account ID</th>
                <th>Balance</th>
                <th>Status</th>
                <th>Date of creation</th>
            </tr>

            <c:forEach var="i" begin="0" end="${accountList.size()-1}">
                <tr>
                    <td align="center"><c:out value="${cardListStr[i]}"></c:out></td>
                    <td align="center"><c:out value="${cardList[i].cardValidDate}"></c:out></td>
                    <td align="center"><c:out value="${accountList[i].accountID}"></c:out></td>
                    <td align="center"><c:out value="${accountList[i].accountBalance}"></c:out></td>
                    <td align="center"><c:out value="${accountList[i].accountStatus}"></c:out></td>
                    <td align="center"><c:out value="${accountList[i].accountDateOpen}"></c:out></td>
                </tr>
            </c:forEach>
        </table>
        <p><blockquote>
        <form action="/Servlet" method="post">
        <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
        <input type="submit" align="center" value="Back" name="homeButton"/>
        </form>
        </blockquote></p>
    </blockquote>
</blockquote>
</body>
</html>