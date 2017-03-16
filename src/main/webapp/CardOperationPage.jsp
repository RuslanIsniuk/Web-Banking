<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 15.01.2017
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>BankDemo card menu</title>
</head>
<body>
<blockquote>
    <blockquote>
        <p>
        <h2>Card № <c:out value="${cardIDStr}"></c:out></h2></p>


        <p><h4>Operations</h4></p>
        <p>===============================</p>
        <table border="1">
            <tr>
                <td>Pay on delivery</td>
                <td>
                    <form method="post" action="/Servlet">
                        <input type="submit" value="Choose" align="center"/>
                        <input type="hidden" value="${cardID}" name="cardID"/>
                        <input type="hidden" value="openCardTransactionPage" name="actionType"/></form>
                </td>
            </tr>

            <tr>
                <td>Block account</td>
                <td>
                    <form method="post" action="/Servlet">
                        <input type="submit" value="Choose" align="center"/>
                        <input type="hidden" value="${cardID}" name="cardID"/>
                        <input type="hidden" value="openBlockAccPage" name="actionType"/></form>
                </td>
            </tr>

        </table>
        <br>
        <form action="/Servlet" method="post">
            <input type="hidden" value="returnToPerArea" name="actionType"/>
            <input type="hidden" value="${cardID}" name="cardID"/>
            <input type="submit" align="center" value="Back" name="homeButton"/>
        </form>
    </blockquote>
</blockquote>
</body>
</html>
