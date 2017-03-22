<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Block account</title>
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

<div class="container col-md-offset-3 col-md-6">
    <h3>Do You really want to block card № <c:out value="${cardIDStr}"></c:out>?</h3>
    <div class="alert alert-warning">
        <strong>CAUTION!</strong> Pressing "Confirm" You also block your account № <c:out value="${accountID}"></c:out>.
        You will not have access to it balance.
    </div>

    <table class="col-md-offset-2 col-md-8">
        <tbody>
        <tr>
            <td>
                <form action="/Servlet" method="post">
                    <input type="hidden" value="${cardID}" name="cardID"/>
                    <input type="hidden" value="confirmBlockAccount" name="actionType"/>
                    <input type="submit" value="Confirm" class="btn btn-primary"/>
                </form>
            </td>

            <td align="right">
                <form action="/Servlet" method="post">
                    <input type="hidden" value="returnToPerArea" name="actionType"/>
                    <input type="hidden" value="${cardID}" name="cardID"/>
                    <input type="submit" value="Return to homepage" class="btn btn-primary"/>
                </form>
            </td>
        </tr>
        </tbody>
    </table>

    <p>
    <h3><font color="#006400"><c:out value="${infoMessage}"></c:out></font></h3></p>
</div>
</body>
</html>
