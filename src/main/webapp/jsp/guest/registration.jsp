<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="cus" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}" scope="session"/>
<fmt:setBundle basename="property.text" var="text"/>
<fmt:setBundle basename="property.error" var="error"/>
<html>
<head>
    <title>Registration</title>
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
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="ctoken" value="${sessionScope.stoken}">
                            <h1><fmt:message key="header.registration" bundle="${text}"/></h1>
                            <c:choose>
                            <c:when test="${requestScope.error_message==null}">
                                <p class ="text-muted"> <fmt:message bundle="${text}" key="registration.title"/></p>
                            </c:when>
                            <c:when test="${requestScope.error_message!=null}">
                                <p class ="has-error"><fmt:message key="${requestScope.error_message}" bundle="${error}"/> </p>
                            </c:when>
                            </c:choose>
                            <input type="hidden" name="command" value="registration"/>
                            <input maxlength="45" type="text" name="name" required
                                   value="${requestScope.name}"
                                   placeholder="<fmt:message bundle="${text}" key="registration.name"/>">
                            <input maxlength="45" data-toggle="tooltip" title="<fmt:message key="toggle.email" bundle="${text}"/>"
                                   value="${requestScope.email}"
                                   type="text" name="email" required pattern="^([A-Za-z0-9_-]+\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                                   placeholder="<fmt:message bundle="${text}" key="registration.email"/>">
                            <input maxlength="45" data-toggle="tooltip" title="<fmt:message key="toggle.password" bundle="${text}"/>"
                                   value="${requestScope.password}"
                                   type="password" name="password"
                                   placeholder="<fmt:message bundle="${text}" key="registration.password"/>" required>
                            <input maxlength="45" type="password" name="repeat_password"
                                   value="${requestScope.repeat_password}"
                                   placeholder="<fmt:message bundle="${text}" key="registration.repeat"/>"/>
                            <input type="submit" value="<fmt:message bundle="${text}" key="registration.submitregistration"/>" required>
                        </form>
                        <form action="${pageContext.request.contextPath}/jsp/guest/login.jsp">
                            <input type="submit" value="<fmt:message bundle="${text}" key="registration.login"/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<style>
    input + .tooltip > .tooltip-inner {
        border-right: 5px solid black;
        background-color: rgba(0,0,0,0.13);
        color: #FFFFFF;
        padding: 15px;
        font-size: 20px;
    }
</style>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
</html>
