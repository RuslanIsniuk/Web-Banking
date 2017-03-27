<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Web-banking Admin Personal Area</title>
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
    <div class="row col-md-offset-1">
        <p>
        <h2>Creating new client profile:</h2></p>
    </div>

    <form class="form-horizontal" action="/Servlet" method="post">
        <div class="well">
            <h4>Client Info</h4>

            <div class="form-group">
                <label class="control-label col-md-3">Client login:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" maxlength="25" name="clientLogin" placeholder="Enter login">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-offset-1"><font color="#a9a9a9">Max length: 25 symbols. Next
                    characters allowed:
                    "a...z","A...Z","_","0...9".</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Client password:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" maxlength="25" name="clientPass"
                           placeholder="Enter password">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-offset-1"><font color="#a9a9a9">Max length: 25 symbols. Next
                    characters allowed:
                    "a...z","A...Z","_","0...9".</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Client full name:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" maxlength="45" name="clientFullName"
                           placeholder="Enter name">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-offset-1"><font color="#a9a9a9">Max length: 45 symbols. Next
                    characters allowed:
                    "a...z","A...Z","_","0...9".</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-8"><font color="#dc143c"><c:out
                        value="${errorMessageClient}"></c:out></font></label>
            </div>
        </div>

        <div class="well">
            <h4>Account Info</h4>

            <div class="form-group">
                <label class="control-label col-md-3" for="acc_id">Account ID:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="acc_id" maxlength="6" name="accountID"
                           placeholder="000000">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-offset-1"><font color="#a9a9a9">Max length: 6 symbols.First symbol is
                    not 0. Next characters allowed: "0...9".</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3" for="bal">Balance:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="bal" maxlength="8" name="accountBalance"
                           placeholder="00000.00">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-8"><font color="#a9a9a9">Enter amount in next formats: 00000.00 or
                    00000</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-8"><font color="#a9a9a9">Permissible amount of payment: 50000.</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Account status:</label>
                <div class="col-sm-4">
                    <input type="radio" value="active" name="accStatusFlag"/> Active
                    <input type="radio" value="block" name="accStatusFlag"/>Block
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-8"><font color="#dc143c"><c:out
                        value="${errorMessageAccount}"></c:out></font></label>
            </div>
        </div>

        <div class="well">
            <h4>CreditCard Info</h4>

            <div class="form-group">
                <label class="control-label col-md-3">Card ID:</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="4" name="cardIDPart1" placeholder="0000"/>
                </div>

                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="4" name="cardIDPart2" placeholder="0000"/>
                </div>

                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="4" name="cardIDPart3" placeholder="0000"/>
                </div>

                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="4" name="cardIDPart4" placeholder="0000"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3" for="pin">Card PIN:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="pin" maxlength="4" name="cardPIN" placeholder="0000">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-offset-1"><font color="#a9a9a9">Max length: 4 symbols. Next
                    characters allowed: "0...9".</font></label>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Card status:</label>
                <div class="col-sm-4">
                    <input type="radio" value="active" name="cardStatusFlag"/> Active
                    <input type="radio" value="block" name="cardStatusFlag"/>Block
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Card valid date:</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" maxlength="4" name="yearDate" placeholder="YYYY">
                </div>

                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="2" name="monthDate" placeholder="MM">
                </div>

                <div class="col-md-2">
                    <input type="text" class="form-control" maxlength="2" name="dayDate" placeholder="DD">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-8"><font color="#dc143c"><c:out
                        value="${errorMessageCard}"></c:out></font></label>
            </div>
        </div>

        <div class="row col-md-offset-4 col-md-4">
            <input type="hidden" value="createNewClientConfirm" name="actionType"/>
            <input type="submit" value="Submit" name="SubButton" class="btn btn-primary btn-block">
        </div>
        <br>
        <br>
        <div class="form-group">
            <label class="control-label col-md-8"><font color="#dc143c"><c:out
                    value="${errorMessage}"></c:out></font></label>
        </div>
    </form>
</div>

</body>
</html>
