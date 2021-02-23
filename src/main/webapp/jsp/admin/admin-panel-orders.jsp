<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 22:27
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
    <cus:message/>
    <div class="row">
        <%@ include file="/WEB-INF/fragment/header.jsp" %>
        <br>
        <div id="page-wrapper">
            <div class="container-fluid">
                <div style="background-color: #FFFFFF" class="row">
                    <div class="col-lg-12">
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel.jsp"><h1 class="page-header"><fmt:message key='admin.products' bundle='${text}'/></h1> </a>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-user.jsp"><h1 class="page-header"><fmt:message key='admin.users' bundle='${text}'/></h1> </a>
                        <h1 class="page-header"><fmt:message key='admin.news' bundle='${text}'/></h1>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-collection.jsp"><h1 class="page-header"><fmt:message key='admin.collections' bundle='${text}'/></h1> </a>
                    </div>
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <label>
                                    <fmt:message key='admin.sorting_type' bundle='${text}'/>
                                    <select onchange="changeSorting(this.value)" name="typeSort" size=1>
                                        <option value="price"><fmt:message key='sort.price' bundle='${text}'/> </option>
                                        <option value="price DESC"><fmt:message key='sort.price_desc' bundle='${text}'/></option>
                                        <option value="date DESC"><fmt:message key='sort.date_desc' bundle='${text}'/></option>
                                        <option value="date"><fmt:message key='sort.date' bundle='${text}'/></option>
                                    </select>
                                </label>
                                <label>
                                    <select onchange="changeStatus(this.value)" name="typeStatus" size=1>
                                        <option value="WAIT"><fmt:message key='sort.wait' bundle='${text}'/></option>
                                        <option value="COMPLETED"><fmt:message key='sort.complited' bundle='${text}'/></option>
                                        <option value="INPROGRESS"><fmt:message key='sort.inprodress' bundle='${text}'/></option>
                                        <option value="DISLINE"><fmt:message key='sort.disline' bundle='${text}'/></option>
                                    </select>
                                </label>
                            </div>
                            <div id="CONTENT">

                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    let amounts;
    let sorting = 'title';
    function changeSorting(sort){
        sorting = sort;
        getListNews(0);
    }
    let status = 'WAIT';
    function changeStatus(newStatus){
        status = newStatus;
        getListNews(0);
    }
    function getListNews(begin){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_orders&type_sort="+sorting+
                "&begin_pagination="+begin+"&type_status="+status,
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("CONTENT");
                contentID.innerHTML = "";
                if(amounts>=10) {
                    var previous = document.createElement("button");
                    previous.setAttribute("class", "btn-primary");
                    previous.setAttribute("onclick", "getListProduct(" + (amounts-res.length*2) + ","+collection+")")
                    previous.innerHTML = "<fmt:message key='button.previous' bundle='${text}'/>";
                    contentID.appendChild(previous);
                }
                if(res.length===10) {
                    var next = document.createElement("button");
                    next.setAttribute("class", "btn-primary");
                    next.setAttribute("onclick", "getListProduct(" + (amounts + res.length) + "," + collection + "))");
                    next.innerHTML = "<fmt:message key='button.next' bundle='${text}'/>";
                    contentID.appendChild(next);
                }
                $.each(res, function (idx,order){
                    var newTBDiv = document.createElement("tr");
                    newTBDiv.setAttribute("class","odd gradeX");
                    newTBDiv.setAttribute("id","news"+order.idNews);
                    newTBDiv.innerHTML =
                        "<div class='card-header' id='heading"+order.id+"'>"+
                        "<h5 class='mb-0'>"+
                        "<button class='btn btn-link' data-toggle='collapse"+order.id+"' data-target='#collapse"+order.id+"'  aria-expanded='true' aria-controls='collapse"+order.id+"'>"+
                        order.price + order.address + order.phone +
                        "</button>"+
                        "</h5>"+
                        "</div>"+
                        "<div id='collapse"+order.id+"' class='collapse show' aria-labelledby='heading"+order.id+"' data-parent='#accordion'>"+
                        "<div class='card-body'>"+
                        "<table class='table'>"+
                        "<thead>"+
                        "<tr>"+
                        "<th scope='col'></th>"+
                        "<th scope='col'>ProductName</th>"+
                        "<th scope='col'>ProductPrice</th>"+
                        "<th scope='col'>ProductAmount</th>"+
                        "</tr>"+
                        "</thead>"+
                        "<tbody id='order"+order.id+"Products'>"+

                        "</tbody>"+
                        "</table>"+
                        "</div>"+
                        "</div>";
                    contentID.appendChild(newTBDiv);
                    for(var i = 0; i<order.product.length;i++){
                        var divElement = document.createElement("tr");
                        divElement.innerHTML =
                            "<td scope='row'><img width='50' height='50' src='"+order.product[i].imageName+"' </td>"+
                            "<td><a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+order.product[i].id+"'>"+order.product[i].name+"</a></td>"+
                            "<td>"+order.product[i].price+"</td>"+
                            "<td>"+order.product[i].amount+"</td>";
                        document.getElementById("order"+order.id+"Products").appendChild(divElement);
                    }
                    amounts = begin;
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
    }
    $(document).ready ( function(){
        getListNews(0);
    });
</script>
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