<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 26.02.2017
  Time: 11:58
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
        <h3>Client's list:</h3></p>
        <br>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Password</th>
                <th>Full Name</th>
                <th>Admin Flag</th>
                <th>Action</th>
            </tr>

            <c:forEach var="i" begin="0" end="${clientList.size()-1}">
                <tr>
                    <td align="center"><c:out value="${clientList[i].clientID}"></c:out></td>
                    <td align="center"><c:out value="${clientList[i].clientLogin}"></c:out></td>
                    <td align="center"><c:out value="${clientList[i].clientPass}"></c:out></td>
                    <td align="center"><c:out value="${clientList[i].clientFullName}"></c:out></td>
                    <td align="center"><c:out value="${clientList[i].adminFlag}"></c:out></td>

                    <td>
                        <form method="post" action="/Servlet">
                            <input type="hidden" value="${clientList[i].clientID}" name="clientID"/>
                            <input type="hidden" value="openClientDetailsPage" name="actionType"/>
                            <input type="submit" value="Details" name="chooseButton" align="center"/>
                        </form>

                        <form method="post" action="/Servlet">
                            <input type="hidden" value="${clientList[i].clientID}" name="clientID"/>
                            <input type="hidden" value="${clientList[i].clientFullName}" name="clientFullName"/>
                            <input type="hidden" value="openDeleteAccPage" name="actionType"/>
                            <input type="submit" value="Delete " name="chooseButton" align="center"/>
                        </form>
                    </td>

                </tr>
            </c:forEach>
        </table>
        <br>
        <p><form action="/Servlet" method="post">
        <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
        <input type="submit" align="center" value="Back" name="homeButton"/>
    </form></p>
    </blockquote>
</blockquote>
</body>
</html>
