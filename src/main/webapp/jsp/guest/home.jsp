     <%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="cus" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}" scope="session"/>
<fmt:setBundle basename="property.text" var="text"/>
<fmt:setBundle basename="property.error" var="error"/>
<html>
<head>
    <title>Home</title>
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
<cus:message/>
<cus:setCurrentPage/>
<div>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner" role="listbox">
            <div class="carousel-item active" style="background-image: url('${pageContext.request.contextPath}/images/slide-bar1.jpg')">
                <div class="carousel-caption d-none d-md-block">
                </div>
            </div>
            <div class="carousel-item" style="background-image: url('${pageContext.request.contextPath}/images/slide-bar2.jpg')">
                <div class="carousel-caption d-none d-md-block">
                    <h3></h3>
                    <p></p>
                </div>
            </div>
            <div class="carousel-item" style="background-image: url('${pageContext.request.contextPath}/images/slide-bar3.jpg')">
                <div class="carousel-caption d-none d-md-block">
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</div>
<div class="container">
    <h1 class="my-4"><fmt:message key="home.social" bundle="${text}"/> </h1>
    <div class="row">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <div class="box-part text-center">
                <i class="fa fa-instagram fa-3x" aria-hidden="true"></i>
                <div class="title">
                    <h4>Instagram</h4>
                </div>
                <div class="text">
                    <span><fmt:message key="home.instagram" bundle="${text}"/></span>
                </div>
                <a style="text-decoration:none; color: #0062cc;" href="#"><fmt:message key="home.learn_more" bundle="${text}"/></a>
            </div>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <div class="box-part text-center">
                <i class="fa fa-twitter fa-3x" aria-hidden="true"></i>
                <div class="title">
                    <h4>Twitter</h4>
                </div>
                <div class="text">
                    <span><fmt:message key="home.twitter" bundle="${text}"/></span>
                </div>
                <a style="text-decoration:none; color: #0062cc;" href="#"><fmt:message key="home.learn_more" bundle="${text}"/></a>
            </div>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <div class="box-part text-center">
                <i class="fa fa-facebook fa-3x" aria-hidden="true"></i>
                <div class="title">
                    <h4>Facebook</h4>
                </div>
                <div class="text">
                    <span><fmt:message key="home.facebook" bundle="${text}"/></span>
                </div>
                <a style="text-decoration:none; color: #0062cc;" href="#"><fmt:message key="home.learn_more" bundle="${text}"/></a>
            </div>
        </div>
    </div>
    <h2><fmt:message key="home.news" bundle="${text}"/></h2>
    <div id="NEWS" class="row">
        <script>
            $(document).ready ( function(){
                $.ajax({
                    url: "${pageContext.request.contextPath}/async?command=get_fresh_news&news_amount=6",
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                        $.each(res, function (idx,news){
                            var contentID = document.getElementById("NEWS");
                            var newTBDiv = document.createElement("div");
                            newTBDiv.setAttribute("class","col-lg-4 col-sm-6 portfolio-item");
                            newTBDiv.innerHTML =
                                "<div class='card h-100'>"+
                                "<img class='card-img-top' src='"+news.imageName+"' alt=''>" +
                                "<div class='card-body'>"+
                                    "<h4 class='card-title'>"+
                                        "<a>"+news.title+"</a>"+
                                    "</h4>"+
                                    "<p class='card-text'>"+
                                        news.info+
                                    "</p>"+
                                "</div>"+
                                "</div>";
                            contentID.appendChild(newTBDiv);
                        });
                    },
                    statusCode:{
                        402: function (){
                            window.location.href = "${pageContext.request.contextPath}/jsp/user/account.jsp";
                        },
                        500: function (){
                            window.location.href = "${pageContext.request.contextPath}/jsp/error/error500.jsp";
                        },
                        403: function (){
                            window.location.href = "${pageContext.request.contextPath}/jsp/guest/login.jsp";
                        }
                    }
                });
            });
        </script>
    </div>

    <div class="row">
        <div class="col-lg-6">
            <h2><fmt:message key="home.about_us" bundle="${text}"/></h2>
            <p><fmt:message key="home.about" bundle="${text}"/></p>
        </div>
        <div class="col-lg-6">
            <img class="img-fluid rounded" src="${pageContext.request.contextPath}/images/page/shop.jpg" alt="">
        </div>
    </div>

    <hr>
</div>
</body>
</html>
