<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Client's info</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-custom">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Web-Banking Demo</a>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <li>
                <form method="post" action="/Servlet">
                    <input type="hidden" value="logOut" name="actionType"/>
                    <button type="submit" class="btn btn-default navbar-btn">
                        <span class="glyphicon glyphicon-log-out"></span> Log out
                    </button>
                </form>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <h3>Client <c:out value="${client.clientFullName}"></c:out></h3>
    <p><h4>Login: <c:out value="${client.clientLogin}"></c:out></h4></p>
    <p><h4>ID: <c:out value="${client.clientID}"></c:out></h4></p>
    <br>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Card ID</th>
            <th>Valid Date</th>
            <th>Account ID</th>
            <th>Balance</th>
            <th>Status</th>
            <th>Date of creation</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="0" end="${accountList.size()-1}">
            <tr>
                <td><c:out value="${cardListStr[i]}"></c:out></td>
                <td><c:out value="${cardList[i].cardValidDate}"></c:out></td>
                <td><c:out value="${accountList[i].accountID}"></c:out></td>
                <td><c:out value="${accountList[i].accountBalance}"></c:out></td>
                <td><c:out value="${accountList[i].accountStatus}"></c:out></td>
                <td><c:out value="${accountList[i].accountDateOpen}"></c:out></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <p>
    <form action="/Servlet" method="post">
        <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
        <input type="submit" align="center" value="Back" class="btn btn-primary"/>
    </form>
    </p>
</div>

</body>
</html>