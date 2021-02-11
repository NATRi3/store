<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 22:27
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
    <title>Shop</title>
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
<div class="container">

    <div class="row">
        <%@ include file="/WEB-INF/fragment/header.jsp" %>
        <br>
        <div style="padding-top: 50px" class="col-lg-3">
            <div id="collectionList" class="list-group">
                <button class="list-group-item" onclick="getListProduct(0,0)">
                    Показать все
                </button>
            </div>
        </div>
        <div  style="padding-top: 50px"  class="col-lg-9">
            <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                        <img class="d-block img-fluid" src="${pageContext.request.contextPath}/images/page/summer_collection.jpg" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="${pageContext.request.contextPath}/images/page/winter_collection.jpg" alt="Second slide">
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
            <div class="pagination" id="pagination">

            </div>
            <div class="row" id="content">

            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(document).ready ( function(){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_collection",
            type: 'GET',
            dataType: 'json',
            success: function(res) {
                var contentID = document.getElementById("collectionList");
                $.each(res, function (idx,collection){
                    var newTBDiv = document.createElement("button");
                    newTBDiv.setAttribute("class","list-group-item");
                    newTBDiv.setAttribute("onclick","getListProduct(0,"+collection.idCollection+")");
                    newTBDiv.innerHTML = collection.name+"("+collection.date+")";
                    contentID.appendChild(newTBDiv);
                });
            }
        });
    });
    let sorting = 'name';
    function changeSorting(sort){
        sorting = sort;
        getListProduct(0,0);
    }
    let status = 'ACTIVE';
    function getListProduct(begin,collection){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'POST',
            dataType: 'json',
            data:"command=get_list_product_by_collection"+
                        "&type_sort="+sorting+
                        "&id_collection="+collection+
                        "&begin_pagination="+begin+
                        "&type_status="+status,
            success: function (res){
                var contentID = document.getElementById("content");
                var pagination = document.getElementById("pagination");
                pagination.innerHTML = "";
                contentID.innerHTML = "";
                if(begin>=12) {
                    var previous = document.createElement("button");
                    previous.setAttribute("class", "btn-primary");
                    previous.setAttribute("onclick", "getListProduct(" + (begin-12) + ","+collection+")")
                    previous.innerHTML = "PREVIOS";
                    pagination.appendChild(previous);
                }
                if(res.length===12) {
                    var next = document.createElement("button");
                    next.setAttribute("class", "btn-primary");
                    next.setAttribute("onclick", "getListProduct(" + (begin + 12) + "," + collection + ")");
                    next.innerHTML = "NEXT";
                    pagination.appendChild(next);
                }
                $.each(res, function (idx,product){
                    var newTBDiv = document.createElement("div");
                    newTBDiv.setAttribute("class","col-lg-4 col-md-6 mb-4");
                    newTBDiv.innerHTML =
                        "<div class='card h-100'>" +
                        "<a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+product.id+"'>"+
                        "<img class='card-img-top' src='${pageContext.request.contextPath}/async?command=get_image&image_name="+product.imageName+"' alt=''></a>" +
                        "<div class='card-body'>" +
                        "<h4 class='card-title'>" +
                        "<a href='#'>" + product.name + "</a>" +
                        "</h4>" +
                        "<h5>" + product.price + "$</h5>" +
                        "<p class='card-text'><fmt:message key="admin.rating" bundle="${text}"/> : " + product.rating+ "</p>"+
                        "</div>" +
                        "<div class='card-footer'>" +
                        "<small class='text-muted'>"+
                        "<button class='btn-primary' onclick='addToCart(" + product.id + ")'>" +
                        "<fmt:message key='shop.add_to_cart' bundle='${text}'/>"+
                        "</button>"+
                        "</small>" +
                        "</div>" +
                        "</div>";
                    contentID.appendChild(newTBDiv);
                });
            }
        });
    }
    function addToCart(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=add_product_to_cart&id_product="+id+"",
            type: 'GET',
            dataType: 'text',
            success: function (){
                var CART = document.getElementById("CART");
                CART.innerHTML = "<fmt:message key='header.view_cart' bundle='${text}'/>"+
                    "<span>${sessionScope.cart.getTotalAmount()}</span>";
            },
            statusCode:{
                403: function (){
                    window.location.href = "${pageContext.request.contextPath}/jsp/user/account.jsp";
                },
                601: function (){
                    window.location.href = "${pageContext.request.contextPath}/jsp/user/cart-page.jsp";
                }
            }

        })
    }
    $(document).ready ( function(){
        getListProduct(0,0);
    });
</script>
</html>