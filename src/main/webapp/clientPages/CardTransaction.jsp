<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pay on delivery</title>
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
    <h2>Card № <c:out value="${cardIDStr}"></c:out></h2>
    <div class="panel panel-default ">
        <div class="panel-heading">Operations</div>
        <div class="panel-body">
            <form method="post" action="/Servlet">
                <p><h4><input type="radio" value="openTranToAnoCardPage" name="actionType"/> Trow money to another card
            </h4></p>
                <p><h4><input type="radio" value="openCommPaymentPage" name="actionType"/> Communal payment</h4></p>
                <p><h4><input type="radio" value="openMobilePaymentPage" name="actionType"/> Replenish the mobile
                account</h4></p>
                <p><h4><input type="radio" value="openInternetPaymentPage" name="actionType"/> Internet services payment
            </h4></p>
                <p><h4><input type="radio" value="openTVPaymentPage" name="actionType"/> TV services payment</h4></p>

                <input type="hidden" value="/cardOperationImpl/CardTransaction.jsp" name="pageLocation"/>
                <input type="hidden" value="${cardID}" name="cardID"/>
                <input type="submit" value="Submit" class="btn btn-primary btn-block"/>
            </form>


        </div>
    </div>
    <br>
    <p>
    <form action="/Servlet" method="post">
        <input type="hidden" value="openCardOperationMenu" name="actionType"/>
        <input type="hidden" value="${cardID}" name="cardID"/>
        <input type="submit" align="center" value="Back" class="btn btn-primary"/>
    </form>
    </p>
    <p>
    <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
</div>
</body>
</html>
