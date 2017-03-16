<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 26.02.2017
  Time: 13:37
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
        <h3>Blocked credit card's list:</h3></p>
        <br>
        <table border="1">
            <tr>
                <th>Card ID</th>
                <th>Valid Date</th>
                <th>Status</th>
                <th>Account ID</th>
                <th>Account Status</th>
                <th>Client Name</th>
                <th>Action</th>
            </tr>

            <c:forEach var="i" begin="0" end="${sizeList-1}">
                <tr>
                    <td align="center"><c:out value="${cardListStr[i]}"></c:out></td>
                    <td align="center"><c:out value="${cardList[i].cardValidDate}"></c:out></td>
                    <td align="center"><c:out value="${cardList[i].cardStatus}"></c:out></td>
                    <td align="center"><c:out value="${accountList[i].accountID}"></c:out></td>
                    <td align="center"><c:out value="${accountList[i].accountStatus}"></c:out></td>
                    <td align="center"><c:out value="${clientList[i].clientFullName}"></c:out></td>
                    <td>
                        <form method="post" action="/Servlet">
                            <input type="hidden" value="${cardList[i].cardID}" name="cardID"/>
                            <input type="hidden" value="openUnblockAccPage" name="actionType"/>
                            <input type="submit" value="Unblock" name="chooseButton" align="center"/>
                        </form>
                    </td>

                </tr>
            </c:forEach>
        </table>
    </blockquote>
</blockquote>
</body>
</html>
