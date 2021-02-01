<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 29.01.2021
  Time: 03:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}" scope="session"/>
<fmt:setBundle basename="property.text" var="text"/>
<fmt:setBundle basename="property.error" var="error"/>
<html>
  <head>
    <title>Account</title>
      <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/account.css" type="text/css"/>
      <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
      <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginform.css" type="text/css"/>
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
    <div>
        <div class="container emp-profile">
          <div class="row">
              <div class="col-md-4">
                  <div class="profile-img">
                      <img id="accountImg" src="imageServlet?command=user&image_name=${sessionScope.currentUser.imageName}" alt="${sessionScope.currentUser.imageName}"/>
                      <div class="file btn btn-lg btn-primary">
                          <fmt:message key="account.change_img" bundle="${text}"/>
                          <form id="uploadForm" action="${pageContext.request.contextPath}/async?command=upload_image" method="post" enctype="multipart/form-data">
                              <input type="file" name="file" onchange="document.getElementById('uploadForm').submit()"/>
                          </form>
                      </div>
                  </div>
              </div>
              <div class="col-md-6">
                  <div class="profile-head">
                      <h5>
                          ${sessionScope.currentUser.name}
                      </h5>
                      <h6>
                          ${sessionScope.currentUser.role}
                      </h6>
                      <ul class="nav nav-tabs" id="myTab" role="tablist">
                          <li class="nav-item">
                              <a class="nav-link active" id="home-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="home" aria-selected="true">
                                  <fmt:message key="account.about" bundle="${text}"/>
                              </a>
                          </li>
                          <li class="nav-item">
                              <a class="nav-link" id="profile-tab" data-toggle="tab" href="#wallet" role="tab" aria-controls="profile" aria-selected="false">
                                  <fmt:message key="account.wallet" bundle="${text}"/>
                              </a>
                          </li>
                      </ul>
                  </div>
              </div>
              <form method="get" action="${pageContext.request.contextPath}/controller" class="col-md-2">
                  <input type="hidden" name="command" value="logout">
                  <input type="submit" class="profile-edit-btn" value="<fmt:message key="header.logout" bundle="${text}"/>"/>
              </form>
          </div><br>
          <div class="row">
              <div class="col-md-4">
                  <div class="profile-work">
                      <form method="post" action="${pageContext.request.contextPath}/controller">
                          <input type="hidden" name="command" value="get_orders"/>
                          <input class="btn-light" type="submit" value="<fmt:message key="account.orders" bundle="${text}"/>">
                      </form>
                      <form method="post" action="${pageContext.request.contextPath}/controller">
                          <input type="hidden" name="command" value="get_orders"/>
                          <input class="btn-light" type="submit" value="<fmt:message key="account.change_pass" bundle="${text}"/>">
                      </form>
                  </div>
              </div>
              <div class="col-md-8">
                  <div class="tab-content profile-tab" id="myTabContent">
                      <div class="tab-pane fade show active" id="profile" role="tabpanel" aria-labelledby="home-tab">
                          <div class="row">
                              <div class="col-md-6">
                                  <label><fmt:message key="account.name" bundle="${text}"/> </label>
                              </div>
                              <div class="col-md-6">
                                  <p>${sessionScope.currentUser.name}</p>
                              </div>
                          </div>
                          <div class="row">
                              <div class="col-md-6">
                                  <label><fmt:message key="account.email" bundle="${text}"/></label>
                              </div>
                              <div class="col-md-6">
                                  <p>${sessionScope.currentUser.email}</p>
                              </div>
                          </div>
                      </div>
                      <div class="tab-pane fade" id="wallet" role="tabpanel" aria-labelledby="profile-tab">
                          <div class="row">
                              <div class="col-md-6">
                                  <label><fmt:message key="account.wallet"/></label>
                              </div>
                              <div class="col-md-6">
                                  <p>${sessionScope.currentUser.name}</p>
                              </div>
                          </div>
                          <div class="row">
                              <div class="col-md-6">
                                  <label><fmt:message key="account.balance"/></label>
                              </div>
                              <div class="col-md-6">
                                  <p>${sessionScope.currentUser}</p>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>
    </div>
  </body>
</html>