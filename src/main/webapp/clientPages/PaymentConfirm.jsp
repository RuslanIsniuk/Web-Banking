<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payment System</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%--<blockquote>--%>
    <%--<blockquote>--%>
        <%--<p>--%>
        <%--<h3>--%>
            <%--<blockquote>Transaction status</blockquote>--%>
        <%--</h3>--%>
        <%--</p>--%>

        <%--<p><h4>From: <c:out value="${cardIDStr}"></c:out></h4></p>--%>
        <%--<p><h4>To: <c:out value="${destinationID}"></c:out></h4></p>--%>
        <%--<p><h4>Description: <c:out value="${infoMessage}"></c:out></h4></p>--%>

        <%--<p></p>--%>
        <%--<p><h4>Result: <font color="#228b22"><c:out value="${statusMessage}"></c:out></font</h4></p>--%>
        <%--<blockquote>--%>
            <%--<form action="/Servlet" method="post">--%>
                <%--<input type="hidden" value="returnToPerArea" name="actionType"/>--%>
                <%--<input type="hidden" value="${cardID}" name="cardID"/>--%>
                <%--<input type="submit" align="center" value="Return to homepage" name="homeButton"/>--%>
            <%--</form>--%>

            <%--<form action="/Servlet" method="post">--%>
                <%--<input type="hidden" value="openCardOperationMenu" name="actionType"/>--%>
                <%--<input type="hidden" value="${cardID}" name="cardID"/>--%>
                <%--<input type="submit" align="center" value="Make other operations" name="homeButton"/>--%>
            <%--</form>--%>
        <%--</blockquote>--%>
    <%--</blockquote>--%>
<%--</blockquote>--%>

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

<div class="container col-md-offset-3 col-md-6">
    <div class="alert alert-success">

        <div class="col-md-offset-5">
            <h4>Transaction status</h4>
        </div>

        <div class="row">
            <div class="col-md-2">
                <h4>From:</h4>
            </div>
            <div class="col-md-8">
                <h4><c:out value="${cardIDStr}"></c:out></h4>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2">
                <h4>To:</h4>
            </div>
            <div class="col-md-8">
                <h4><c:out value="${destinationID}"></c:out></h4>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2">
                <h4>Description:</h4>
            </div>
            <div class="col-md-10">
                <h4> <c:out value="${infoMessage}"></c:out></h4>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2">
                <h4>Result:</h4>
            </div>
            <div class="col-md-10">
                <h4> <strong><font color="#228b22"><c:out value="${statusMessage}"></c:out></font></strong></h4>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-1 col-md-3">
            <form action="/Servlet" method="post">
                <input type="hidden" value="returnToPerArea" name="actionType"/>
                <input type="hidden" value="${cardID}" name="cardID"/>
                <input type="submit" align="center" value="Return to homepage" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col-md-offset-4 col-md-3">
            <form action="/Servlet" method="post">
                <input type="hidden" value="openCardOperationMenu" name="actionType"/>
                <input type="hidden" value="${cardID}" name="cardID"/>
                <input type="submit" align="center" value="Make other operations" class="btn btn-primary"/>
            </form>
        </div>
    </div>
</div>

</body>
</html>
