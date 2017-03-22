<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test JSTL</title>
</head>
<body>
<br>
<br>
<blockquote>
    <blockquote>
        <h3>Login BankDemo Page</h3>

        Please enter your login and password:
        <form method="post" action="/Servlet">
            <p>Username: <input type="text" name="username"/></p>
            <p>Password: <input type="password" name="password"/><br/></p>
            <input type="hidden" value="CheckLoginData" name="actionType"/>
            <input type="submit" value="Login in"/>
        </form>

        <p>
        <h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
    </blockquote>
</blockquote>


</body>
</html>