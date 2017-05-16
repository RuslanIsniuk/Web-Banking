<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Spring MVC form Validation</title>
</head>

<body>
<h2>Enter below details to login</h2>

<form:form method="POST" modelAttribute="loginInputForm" action="doLogin">
    <table>

        <tr>
            <td><form:label path="userName">Enter your E-mail:</form:label> </td>
            <td><form:input path="userName" /></td>
            <td><form:errors path="userName" cssStyle="color: #ff0000;" /></td>
        </tr>

        <tr>
            <td>Enter a password:</td>
            <td><form:password path="userPassword"  showPassword="true"/></td>
            <td><form:errors path="userPassword" cssStyle="color: #ff0000;"/></td>
        </tr>

        <tr>
                ${loginInputForm.errorMessage}
        </tr>

        <tr>
            <td><input type="submit" name="submit" value="Click here to Login"></td>
        </tr>
    </table>
</form:form>

</body>
</html>