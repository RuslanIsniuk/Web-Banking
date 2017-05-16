<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Test JSTL</title>
</head>
<body>
<br>
<br>

<div class="container col-md-offset-4 col-md-4">
    <div class="well">
        <h2 class="col-md-offset-1">Login BankDemo Page</h2>
        <br>
        <form:form class="form-horizontal" method="POST" modelAttribute="loginForm" action="/LoginForm">
            <div class="form-group">
                <form:label path="userName" class="control-label col-md-3" for="login">Login:</form:label>
                <div class="col-md-6">
                    <form:input  path="userName" class="form-control"  id="login" placeholder="Enter login"/>
                </div>
                <form:errors path="userName" cssClass="error" />
            </div>

            <div class="form-group">
                <form:label path="userPassword" class="control-label col-md-3" for="pwd">Password:</form:label>
                <div class="col-md-6">
                    <form:input type="password" class="form-control" path="userPassword" id="pwd" placeholder="Enter password"/>
                </div>
                <form:errors path="userPassword" cssClass="error" />
            </div>
            <br>
            <div class="form-group">
                <div class="col-md-offset-5 col-md-2">
                    <%--<input type="hidden" value="CheckLoginData" name="actionType"/>--%>
                    <input type="submit" value="Submit" class="btn btn-primary"/>
                </div>
            </div>
        </form:form>
        <p>
        <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3>
        <form:errors path="errorMessage" cssClass="error"/>
        </p>
    </div>
</div>

</body>
</html>