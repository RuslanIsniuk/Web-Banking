
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
        <br>
        <form method="post" action="/Servlet">
            <input type="hidden" value="logOut" name="actionType"/>
            <input type="submit" value="Logout" name="chooseButton" align="center"/>
        </form>
    </blockquote>
</blockquote>
</body>
</html>
