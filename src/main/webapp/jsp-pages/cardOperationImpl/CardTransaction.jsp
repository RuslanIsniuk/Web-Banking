<%--
  Created by IntelliJ IDEA.
  User: Руслан
  Date: 23.01.2017
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Pay on delivery</title>
</head>
<body>
<blockquote><blockquote>
<p><h2>Card № <c:out value="${cardIDStr}"></c:out></h2></p>

<p><h4>Operations</h4></p>
<p>===============================</p>

<form method="post" action="/Servlet">
    <p><h4><input type="radio" value="openTranToAnoCardPage" name="actionType"/> Trow money to another card</h4></p>
    <p><h4><input type="radio" value="openCommPaymentPage" name="actionType"/> Communal payment</h4></p>
    <p><h4><input type="radio" value="openMobilePaymentPage" name="actionType"/> Replenish the mobile account</h4></p>
    <p><h4><input type="radio" value="openInternetPaymentPage" name="actionType"/> Internet services payment</h4></p>
    <p><h4><input type="radio" value="openTVPaymentPage" name="actionType"/> TV services payment</h4></p>

    <input type="hidden" value="/cardOperationImpl/CardTransaction.jsp" name="pageLocation"/>
    <input type="hidden" value="${cardID}" name="cardID"/>
    <input type="submit" value="Submit" name="onButton"/>
</form>

<p><form action="/Servlet" method="post">
    <input type="hidden" value="openCardOperationMenu" name="actionType"/>
    <input type="hidden" value="${cardID}" name="cardID"/>
    <input type="submit" align="center" value="Back" name="homeButton"/>
</form></p>
    <br>
    <p><h3><font color="#dc143c"><c:out value="${errorMessage}"></c:out></font></h3></p>
</blockquote></blockquote>
</body>
</html>
