<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}" scope="session"/>
<fmt:setBundle basename="property.text" var="text"/>
<html>
<head>
    <title>Single-Product</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginform.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" type="text/css">
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
<c:if test="${requestScope.product==null}" >
    <jsp:forward page="/jsp/error/error404.jsp"/>
</c:if>
<div class="container">
    <div class="row">
        <%@ include file="/WEB-INF/fragment/header.jsp" %>
        <main style="background-color: #FFFFFF">
            <div class="container">
                <section class="my-5">
                    <div class="row">
                        <div class="col-md-5 mb-4 mb-md-0">
                            <div class="view zoom z-depth-2 rounded">
                                <img class="img-fluid w-100" src="${pageContext.request.contextPath}/async?command=get_image&image_name=${requestScope.product.imageName}" alt="Sample">
                            </div>
                        </div>
                        <div class="col-md-7">

                            <h5>${requestScope.product.name}</h5>
                            <p class="mb-2 text-muted text-uppercase small">(brand)</p>
                            <p><span class="mr-1"><strong>$${requestScope.product.price}</strong></span></p>
                            <p class="pt-1">${requestScope.product.info}</p>
                            <div class="table-responsive">
                                <table class="table table-sm table-borderless mb-0">
                                    <tbody>
                                    <tr>
                                        <th class="pl-0 w-25" scope="row"><strong><fmt:message key="admin.rating" bundle="${text}"/></strong></th>
                                        <td>${requestScope.product.rating}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="table-responsive mb-2">
                                <table class="table table-sm table-borderless">
                                    <tbody>
                                    <tr>
                                        <td class="pl-0">
                                            <div class="def-number-input number-input safari_only mb-0">
                                                <button onclick="this.parentNode.querySelector('input[type=number]').stepDown()" class="minus">
                                                    <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span> <fmt:message key="button.down" bundle="${text}"/>
                                                </button>
                                                <input class="quantity" min="0" name="quantity" value="1" type="number">
                                                <button onclick="this.parentNode.querySelector('input[type=number]').stepUp()" class="plus">
                                                    <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span> <fmt:message key="button.up" bundle="${text}"/>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <button type="button" class="btn btn-light btn-md mr-1 mb-2 waves-effect waves-light">
                                <fmt:message key="button.add_to_cart" bundle="${text}"/>
                            </button>
                        </div>
                    </div>
                        <div class="col-md-6 col-xs-12 col-md-offset-3">
                            <div class="panel">
                                <div class="panel-heading">
                                    <h3 class="panel-title">//FEEDBACK</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="form-group">
                                        <textarea class="form-control" placeholder="Enter here for tweet..." rows="3"></textarea>
                                    </div>
                                    <a href="javascript:void(0)" class="btn btn-info btn-sm pull-right waves-effect waves-light">
                                        Tweet
                                    </a>
                                    <div class="clearfix"></div>
                                    <hr class="margin-bottom-10">
                                    <ul id="lastComment" class="list-group list-group-dividered list-group-full">

                                    </ul>
                                </div>
                            </div>
                        </div>
                    <div class="col-md-12 ">
                        <h3 class="text-center mt-4 pt-5">Recommended products</h3>
                        <div class="row mt-5 mb-4" id="recommended">

                        </div>
                    </div>
                </section>
            </div>
        </main>
    </div>
</div>
<script>
    $(document).ready(function () {
        $(function () {
            $("#mdb-lightbox-ui").load("mdb-addons/mdb-lightbox-ui.html");
        });
    });
    $(document).ready ( function(){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_product_random&amount_product=3",
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("recommended");
                    $.each(res, function (idx,product){
                        var newTBDiv = document.createElement("div");
                        newTBDiv.setAttribute("class","col-md-6 col-lg-3 mb-4");
                        newTBDiv.innerHTML =
                            "<div>"+
                            "<div class='view zoom z-depth-2 rounded'>"+
                            "<img class='img-fluid w-100' src='${pageContext.request.contextPath}/async?command=get_image&image_name="+product.imageName+"' alt='Sample'>"+
                            "<a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+product.id+"'>"+
                            "<div class='mask waves-effect waves-light'></div>"+
                            "</a>"+
                            "</div>"+
                            "<div class='text-center pt-4'>"+
                            "<h5 class='mb-0'>"+product.name+"</h5>"+
                            "<h6 class='mb-3'>$"+product.price+"</h6>"+
                            "<button type='button' class='btn btn-primary btn-sm mr-1 waves-effect waves-light'>"+
                                "<fmt:message key='shop.add_to_cart' bundle='${text}'/></button>"+
                            "<a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+product.id+"' type='button' class='btn btn-light btn-sm waves-effect waves-light'><i class='fas fa-info-circle pr-2'></i>Details</a>"+
                            "</div>"+
                            "</div>";
                        contentID.appendChild(newTBDiv);
                    });
            },
            statusCode:{
                500: function (){
                    window.location.href = "${pageContext.request.contextPath}/jsp/error/error500.jsp";
                },
                404: function (){
                    window.location.href = "${pageContext.request.contextPath}/jsp/error/error404.jsp";
                }
            }
        });
    });
    $(document).ready ( function g(){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_product_feedback&id_product="+${requestScope.product.id},
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("lastComment");
                if(res.length===0){
                    var newTBDiv = document.createElement("li");
                    newTBDiv.innerHTML =
                        "<div class='media'>"+
                        "<div class='media-body'>"+
                        "No comments be first!"+
                        "</div>"+
                        "</div>";
                    contentID.appendChild(newTBDiv);
                } else {
                    $.each(res, function (idx,feedback){
                        var newTBDiv = document.createElement("li");
                        newTBDiv.innerHTML =
                            "<div class='media'>"+
                                "<div class='media-left'>"+
                                "<a class='avatar avatar-online' href='javascript:void(0)'>"+
                                    "<img src='${pageContext.request.contextPath}/async?command=get_image&image_name="+feedback.user.imageName+"' alt=''>"+
                                "</a>"+
                                "</div>"+
                                "<div class='media-body'>"+
                                "<small class='text-muted pull-right'>"+feedback.date+"</small>"+
                                "<h4 class='media-heading'>"+feedback.user.name+"("+feedback.evaluation+"/10)"+"</h4>"+
                                "<div>"+feedback.feedback+"</div>"+
                                "</div>"+
                            "</div>";
                        contentID.appendChild(newTBDiv);
                    });
                }
            }
        })
    })
</script>
</body>
<style>
    .card{
        display: flex;
    }
    .avatar {
        position: relative;
        display: inline-block;
        width: 40px;
        white-space: nowrap;
        border-radius: 1000px;
        vertical-align: bottom

    }

    .avatar i {
        position: absolute;
        right: 0;
        bottom: 0;
        width: 10px;
        height: 10px;
        border: 2px solid #fff;
        border-radius: 100%
    }

    .avatar img {
        width: 100%;
        max-width: 100%;
        height: auto;
        border: 0 none;
        border-radius: 1000px;
        box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
    }

    .avatar-online i {
        background-color: #4caf50
    }

    .avatar-off i {
        background-color: #616161
    }

    .avatar-busy i {
        background-color: #ff9800
    }

    .avatar-away i {
        background-color: #f44336
    }

    .avatar-100 {
        width: 100px
    }

    .avatar-100 i {
        height: 20px;
        width: 20px
    }

    .avatar-lg {
        width: 50px
    }

    .avatar-lg i {
        height: 12px;
        width: 12px
    }

    .avatar-sm {
        width: 30px
    }

    .avatar-sm i {
        height: 8px;
        width: 8px
    }

    .avatar-xs {
        width: 20px
    }

    .avatar-xs i {
        height: 7px;
        width: 7px
    }

    .list-group-item {
        position: relative;
        display: block;
        padding: 10px 15px;
        margin-bottom: -1px;
        background-color: #fff;
        border: 1px solid transparent;
    }
</style>
</html>
