<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 26.02.2017
  Time: 19:53
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
        <blockquote>
            <p>
            <h2>Creating new client profile:</h2></p>
            <br>
            <form action="/Servlet" method="post">
                <p>
                <h3>Client Info</h3></p>
                <p>Client login: <input type="text" size="18" maxlength="25" name="clientLogin"></p>
                <p><font color="#a9a9a9">Max length: 25 symbols. Next characters allowed:
                    "a...z","A...Z","_","0...9".</font></p>
                <p></p>
                <p>Client password: <input type="text" size="18" maxlength="25" name="clientPass"></p></p>
                <p><font color="#a9a9a9">Max length: 25 symbols. Next characters allowed:
                    "a...z","A...Z","_","0...9".</font></p>
                <p></p>
                <p>Client full name: <input type="text" size="30" maxlength="45" name="clientFullName"></p></p></p>
                <p><font color="#a9a9a9">Max length: 45 symbols. Next characters allowed:
                    "a...z","A...Z","_","0...9".</font></p>
                <p>Admin rights: </p>
                <p><input type="radio" value="yes" name="adminFlag"/> YES
                    <input type="radio" value="no" name="adminFlag"/> NO</p>
                <p><h3><font color="#dc143c"><c:out value="${errorMessageClient}"></c:out></font></h3></p>
                <br>
                <p>
                <h3>Account Info</h3></p>
                <p>Account ID: <input type="text" size="5" maxlength="6" name="accountID"></p>
                <p><font color="#a9a9a9">Max length: 6 symbols.First symbol is not 0. Next characters allowed:
                    "0...9".</font></p>
                <p></p>
                <p>Balance: <input type="text" size="7" maxlength="8" name="accountBalance"></p>
                <p><font color="#a9a9a9">Enter amount in next formats: 00000.00 or 00000</font></p>
                <p><font color="#a9a9a9">Permissible amount of payment: 50000.</font></p>
                <p></p>
                <p>Account status: </p>
                <p><input type="radio" value="active" name="accStatusFlag"/> Active
                    <input type="radio" value="block" name="accStatusFlag"/>Block</p>
                <p><h3><font color="#dc143c"><c:out value="${errorMessageAccount}"></c:out></font></h3></p>
                <br>
                <p>
                <h3>CreditCard Info</h3></p>
                <p>Card ID: <input type="text" size="3" maxlength="4" name="cardIDPart1">
                    <input type="text" size="3" maxlength="4" name="cardIDPart2">
                    <input type="text" size="3" maxlength="4" name="cardIDPart3">
                    <input type="text" size="3" maxlength="4" name="cardIDPart4">
                <p></p>
                <p>Card PIN: <input type="text" size="4" maxlength="4" name="cardPIN"></p>
                <p><font color="#a9a9a9">Max length: 4 symbols. Next characters allowed: "0...9".</font></p>
                <p></p>
                <p>Card status: </p>
                <p><input type="radio" value="active" name="cardStatusFlag"/> Active
                    <input type="radio" value="block" name="cardStatusFlag"/>Block</p>
                <p>Card valid date: YYYY <input type="text" size="2" maxlength="4" name="yearDate">
                    MM <input type="text" size="2" maxlength="2" name="monthDate">
                    DD <input type="text" size="2" maxlength="2" name="dayDate"></p>
                <p><h3><font color="#dc143c"><c:out value="${errorMessageCard}"></c:out></font></h3></p>
                <br>
                <input type="hidden" value="createNewClientConfirm" name="actionType"/>
                <blockquote><input type="submit" value="confirm" name="SubButton"></blockquote>
            </form>

            <p><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
        </blockquote>
    </blockquote>
</blockquote>
</body>
</html>
