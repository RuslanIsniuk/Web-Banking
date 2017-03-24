<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Operation process</title>
    <meta charset="UTF-8">
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

<div class="container col-md-offset-4 col-md-4">
    <div class="alert alert-success">
        <div class="row col-md-offset-1">
            Result: <strong><font color="#228b22"><c:out value="${statusMessage}"></c:out></strong>
        </div>

        <div class="row col-md-offset-1">
            <strong> <c:out value="${clientFullName}"></c:out></strong>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-offset-4 col-md-3">
            <form action="/Servlet" method="post">
                <input type="hidden" value="returnToPerAdminAreaPage" name="actionType"/>
                <input type="submit" align="center" value="Return to homepage" class="btn btn-primary"/>
            </form>
        </div>
    </div>
</div>
</div>

</body>
</html>
