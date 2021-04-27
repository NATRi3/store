<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="cus" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}" scope="session"/>
<fmt:setBundle basename="property.text" var="text"/>
<fmt:setBundle basename="property.error" var="error"/>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginform.css" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/component/jquery/jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/component/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/component/jquery.validate.min.js" type="text/javascript"></script>
</head>
<body>
<%@ include file="/WEB-INF/fragment/header.jsp" %>
<cus:setCurrentPage/>
<div>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="box">
                        <form method="post" action="${pageContext.request.contextPath}/controller" id="loginForm">
                            <input type="hidden" name="ctoken" value="${sessionScope.stoken}">
                            <h1><fmt:message key="login.login" bundle="${text}"/></h1>
                            <p class ="text-muted"> <fmt:message bundle="${text}" key="login.title"/></p>
                            <c:if test="${requestScope.error_message!=null}">
                                <c:choose>
                                    <c:when test="${requestScope.error_message.contains('successful')}">
                                        <div class="alert alert-success" role="alert">
                                            <fmt:message key="${requestScope.error_message}" bundle="${error}"/>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-danger" role="alert">
                                            <fmt:message key="${requestScope.error_message}" bundle="${error}"/>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <input type="hidden" name="command" value="login"/>
                            <input maxlength="45" type="text" name="email" required pattern="^([A-Za-z0-9_-]+\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                                    placeholder="<fmt:message bundle="${text}" key="login.email"/>">
                            <input maxlength="45" type="password" name="password"
                                    placeholder="<fmt:message bundle="${text}" key="login.password"/>">
                            <a class="forgot text-muted" href="${pageContext.request.contextPath}/jsp/guest/forgot-password.jsp">
                                <fmt:message key="login.forgot_password" bundle="${text}"/>
                            </a>
                            <input type="submit" id="submitLogin" value=<fmt:message bundle="${text}" key="login.submit"/>>
                        </form>
                        <form action="${pageContext.request.contextPath}/jsp/guest/registration.jsp">
                            <input type="submit" value="<fmt:message bundle="${text}" key="login.toregistration"/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $('form').submit(function(){
            $(this).find('input[type=submit]').prop('disabled', true);
        })
    });
</script>
</body>
</html>
