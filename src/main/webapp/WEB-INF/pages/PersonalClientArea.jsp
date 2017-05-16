<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Web-banking Personal Area</title>
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
    <h2>Hello, <c:out value="${clientName}"></c:out>.</h2>
    <p>List of flow accounts:</p>
    <br>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Card ID</th>
            <th>Valid Date</th>
            <th>Card Status</th>
            <th>Account</th>
            <th>Balance</th>
            <th>Account Status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="0" end="${accountList.size()-1}">
            <tr>
                <td><c:out value="${i+1}"></c:out></td>
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
                        <input type="submit" value="Choose" name="chooseButton" class="btn btn-primary btn-md"/>
                    </form>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
