<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 26.02.2017
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web-banking Admin Personal Area</title>
</head>
<body>
<blockquote>
    <blockquote>
        <p>
        <h2>Hello, <c:out value="${clientName}"></c:out> .</h2></p>
        <br>
        <p><h4>Operations</h4></p>
        <p>===============================</p>
        <table border="1">
            <tr>
                <td>Create new client's account</td>
                <td>
                    <form method="post" action="/Servlet">
                        <input type="submit" value="Choose" align="center"/>
                        <input type="hidden" value="${cardID}" name="cardID"/>
                        <input type="hidden" value="openFormForCreatingNewAccPage" name="actionType"/></form>
                </td>
            </tr>

            <tr>
                <td>View client's list</td>
                <td>
                    <form method="post" action="/Servlet">
                        <input type="submit" value="Choose" align="center"/>
                        <input type="hidden" value="openAllClientsAccPage" name="actionType"/></form>
                </td>
            </tr>

            <tr>
                <td>Unblock account/card</td>
                <td>
                    <form method="post" action="/Servlet">
                        <input type="submit" value="Choose" align="center"/>
                        <input type="hidden" value="openBlockedAccountsPage" name="actionType"/></form>
                </td>
            </tr>

        </table>
    </blockquote>
</blockquote>
</body>
</html>
