<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Internet Payment</title>
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
    <div class="well">
        <form class="form-horizontal" action="/Servlet" method="post">
            <div class="form-group">
                <label class="control-label col-md-3" for="cardFrom">From card: </label>
                <label class="control-label col-md-4" id="cardFrom"> <c:out value="${cardIDStr}"></c:out></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3" for="sum">Enter the sum:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" maxlength="8" id="sum" name="amount"/>
                </div>
            </div>

            <div class="form-group" >
                <label class="control-label col-md-8"><font color="#a9a9a9">Enter amount in next formats: 00000.00 or 00000</font></label>

            </div>

            <div class="form-group">
                <label class="control-label col-md-8"><font color="#a9a9a9">Permissible amount of payment: 50000.</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-4">Enter internet account (10 numbers):</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="5" name="internetIDPart1">
                </div>

                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="5" name="internetIDPart2">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3" for="des">Destination: </label>
                <label class="control-label col-md-4" id="des">For internet services.</label>
            </div>

            <div class="form-group">
                <p><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
            </div>

            <br>
            <div class="form-group">
                <div class="col-md-offset-4 col-md-4">
                    <input type="hidden" value="${cardID}" name="cardID">
                    <input type="hidden" value="internetPaymentConfirm" name="actionType"/>
                    <p><input type="submit" value="Accept" class="btn btn-primary btn-block"></p>
                </div>
            </div>
        </form>
    </div>
</div>

<%--<form action="/Servlet" method="post">--%>
    <%--<p><h4>From card: <c:out value="${cardIDStr}"></c:out></h4></p>--%>

    <%--<p>Enter internet account (10 numbers):</p>--%>
    <%--<p><input type="text" size="3" maxlength="5" name="internetIDPart1">--%>
        <%--<input type="text" size="3" maxlength="5" name="internetIDPart2"></p>--%>

    <%--<p>Enter the sum: <input type="text" name="amount"/></p>--%>
    <%--<p><font color="#a9a9a9">Enter amount in next formats: 00000.00 or 00000</font></p>--%>
    <%--<p><font color="#a9a9a9">Permissible amount of payment: 50000.</font></p>--%>

    <%--<p>For communal services</p>--%>
    <%--<input type="hidden" value="${cardID}" name="cardID">--%>
    <%--<input type="hidden" value="internetPaymentConfirm" name="actionType"/>--%>
    <%--<input type="submit" value="Accept">--%>
<%--</form>--%>

<%--<p><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>--%>

</body>
</html>
