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
<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginform.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/products.css" type="text/css">
    <script src="${pageContext.request.contextPath}/js/products-page.js" type="text/javascript"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/component/jquery/jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/component/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/component/jquery.validate.min.js" type="text/javascript"></script>
</head>
<body>
<div>
<%@ include file="/WEB-INF/fragment/header.jsp" %>
<div>
    <div class="container">
        <div id="content">

        </div>
        <script>
            $(document).ready ( function(){

            });
        </script>
    </div>
</div>
</div>
<script>
    var amounts = 10;
    var sorting = 'name';
    function changeSorting(sort){
        sorting = sort;
    }
    function getListProduct(begin,collection){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_shop_list_product&type_sort="+sorting+"&begin_pagination="+begin,
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("content");
                contentID.innerHTML = "";
                if(amounts>=20) {
                    var previous = document.createElement("button");
                    previous.setAttribute("class", "btn-primary");
                    previous.setAttribute("onclick", "getListProduct(" + (amounts-res.length*2) + ","+collection+")")
                    previous.innerHTML = "PREVIOS";
                    contentID.appendChild(previous);
                }
                if(res.length===10) {
                    var next = document.createElement("button");
                    next.setAttribute("class", "btn-primary");
                    next.setAttribute("onclick", "getListProduct(" + (amounts + res.length) + "," + collection + "))");
                    next.innerHTML = "NEXT";
                    contentID.appendChild(next);
                }
                $.each(res, function (idx,product){
                    var newTBDiv = document.createElement("div");
                    newTBDiv.setAttribute("class","col-lg-4 col-md-6 mb-4");
                    newTBDiv.innerHTML =
                        "<div class='card h-100'>" +
                        "<a href='#'><img class='card-img-top' src='${pageContext.request.contextPath}/async?command=get_product_image&image_name="+product.imageName+"' alt=''></a>" +
                        "<div class='card-body'>" +
                        "<h4 class='card-title'>" +
                        "<a href='#'>" + product.name + "</a>" +
                        "</h4>" +
                        "<h5>" + product.price + "$</h5>" +
                        "<p class='card-text'>" + product.info + "</p>" +
                        "</div>" +
                        "<div class='card-footer'>" +
                        "<small class='text-muted'>" + product.status + "</small>" +
                        "</div>" +
                        "</div>";
                    contentID.appendChild(newTBDiv);
                    amounts = begin;
                });
            }
        });
    }
</script>
</body>
</html>
