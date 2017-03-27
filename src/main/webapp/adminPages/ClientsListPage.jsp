<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web-banking Admin Personal Area</title>
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
    <h3>Client's list:</h3>
    <br>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Password</th>
            <th>Full Name</th>
            <th>Admin Flag</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="0" end="${clientList.size()-1}">
            <tr>
                <td><c:out value="${clientList[i].clientID}"></c:out></td>
                <td><c:out value="${clientList[i].clientLogin}"></c:out></td>
                <td><c:out value="${clientList[i].clientPass}"></c:out></td>
                <td><c:out value="${clientList[i].clientFullName}"></c:out></td>
                <td><c:out value="${clientList[i].adminFlag}"></c:out></td>

                <td>
                    <form method="post" action="/Servlet">
                        <input type="hidden" value="${clientList[i].clientID}" name="clientID"/>
                        <input type="hidden" value="openClientDetailsPage" name="actionType"/>
                        <input type="submit" value="Details" name="chooseButton" class="btn btn-primary btn-md"/>
                    </form>

                    <form method="post" action="/Servlet">
                        <input type="hidden" value="${clientList[i].clientID}" name="clientID"/>
                        <input type="hidden" value="${clientList[i].clientFullName}" name="clientFullName"/>
                        <input type="hidden" value="openDeleteAccPage" name="actionType"/>
                        <input type="submit" value="Delete " name="chooseButton" class="btn btn-primary btn-md"/>
                    </form>
                </td>

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
