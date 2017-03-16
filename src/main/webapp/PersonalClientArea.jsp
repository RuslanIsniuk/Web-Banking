<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 10.01.2017
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Web-banking Personal Area</title>
</head>
<body>
<blockquote>
    <blockquote>
        <p>
        <h2>Hello, <c:out value="${clientName}"></c:out> .</h2></p>

        <p><h4>List of flow accounts:</h4></p>

        <table border="1">
            <tr>
                <th>Card ID</th>
                <th>Valid Date</th>
                <th>Card Status</th>
                <th>Account</th>
                <th>Balance</th>
                <th>Account Status</th>
                <th></th>
            </tr>

            <c:forEach var="i" begin="0" end="${accountList.size()-1}">
                <tr>

                    <td><c:out value="${cardListStr[i]}"></c:out></td>
                    <td><c:out value="${cardList[i].cardValidDate}"></c:out></td>
                    <td><c:out value="${cardList[i].cardStatus}"></c:out></td>
                    <td><c:out value="${accountList[i].accountID}"></c:out></td>
                    <td><c:out value="${accountList[i].accountBalance}"></c:out></td>
                    <td><c:out value="${accountList[i].accountStatus}"></c:out></td>

                    <td>
                        <form method="post" action="/Servlet">
                            <input type="hidden" value="${cardList[i].cardID}" name="cardID"/>
                            <input type="hidden" value="openCardOperationMenu" name="actionType"/>
                            <input type="submit" value="Choose" name="chooseButton" align="center"/>
                        </form>
                    </td>

                </tr>
            </c:forEach>
        </table>
        <h3><a href="index.jsp">Logout</a></h3>
    </blockquote>
</blockquote>

</body>
</html>
