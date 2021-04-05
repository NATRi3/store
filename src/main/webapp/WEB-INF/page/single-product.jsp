<%--
  Created by IntelliJ IDEA.
  User: ssykh
  Date: 25.01.2021
  Time: 22:28
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
<cus:message/>
<div class="container">
    <div class="row">
        <%@ include file="/WEB-INF/fragment/header.jsp" %>
        <main style="background-color: #FFFFFF">
            <div class="container">
                <section class="my-5">
                    <div class="row">
                        <div class="col-md-5 mb-4 mb-md-0">
                            <div class="view zoom z-depth-2 rounded">
                                <img  src="${requestScope.product.imageName}" alt="Sample" height="400">
                            </div>
                        </div>
                        <div class="col-md-7">

                            <h5>${requestScope.product.name}</h5>
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
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                            <div class="table-responsive mb-2">
                                <table class="table table-sm table-borderless">
                                    <tbody>
                                    <tr>
                                        <td class="pl-0">
                                            <div class="def-number-input number-input safari_only mb-0">
                                                <input type="hidden" name="id_product" value="${requestScope.product.id}">
                                                <input type="hidden" name="command" value="add_amount_product_to_cart">
                                                <input type="hidden" name="ctoken" value="${sessionScope.stoken}">
                                                <input class="quantity" min="0" name="amount_product" value="1" type="number">
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <button type="submit" class="btn btn-light btn-md mr-1 mb-2 waves-effect waves-light">
                                <fmt:message key="button.add_to_cart" bundle="${text}"/>
                            </button>
                            </form>
                        </div>
                    </div>
                        <div class="col-md-6 col-xs-12 col-md-offset-3">
                            <div class="panel">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><fmt:message key="singe.feedback" bundle="${text}"/> </h3>
                                </div>
                                <div class="panel-body">
                                    <form action="${pageContext.request.contextPath}/controller" method="post">
                                        <div class="form-group">
                                            <input type="hidden" name="command" value="create_feedback">
                                            <input type="hidden" name="ctoken" value="${sessionScope.stoken}">
                                            <input type="hidden" name="id_product" value="${requestScope.product.id}">
                                            <textarea name="feedback" class="form-control" placeholder="<fmt:message key="single.text" bundle="${text}"/>" rows="3">${requestScope.feedback}</textarea>
                                        </div>
                                        <input type="submit" class="btn btn-info btn-sm pull-right waves-effect waves-light" value="Tweet">
                                        <div class="clearfix"><div class="form-group" id="rating-ability-wrapper">
                                            <label class="control-label" for="rating">
                                                <input type="hidden" id="selected_rating" name="evaluation" value="${requestScope.evaluation}" required="required">
                                            </label>
                                            <button type="button" class="btnrating btn btn-default btn-lg" data-attr="1" id="rating-star-1">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </button>
                                            <button type="button" class="btnrating btn btn-default btn-lg" data-attr="2" id="rating-star-2">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </button>
                                            <button type="button" class="btnrating btn btn-default btn-lg" data-attr="3" id="rating-star-3">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </button>
                                            <button type="button" class="btnrating btn btn-default btn-lg" data-attr="4" id="rating-star-4">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </button>
                                            <button type="button" class="btnrating btn btn-default btn-lg" data-attr="5" id="rating-star-5">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                        </div>
                                    </form>
                                    <hr class="margin-bottom-10">
                                    <ul id="lastComment" class="list-group list-group-dividered list-group-full">

                                    </ul>
                                </div>
                            </div>
                        </div>
                    <div class="col-md-12 ">
                        <h3 class="text-center mt-4 pt-5"><fmt:message key="single.recommended" bundle="${text}"/></h3>
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
                        newTBDiv.setAttribute("class","block");
                        newTBDiv.innerHTML =
                            "<div>"+
                            "<div style='height:80px' class='top'>"+
                            "<h5 class='mb-0'>"+product.name+"</h5>"+
                            "</div>"+
                            "<div class='middle'>"+
                            "<a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+product.id+"'>"+
                            "<img height='300' src='"+product.imageName+"' alt='Sample'>"+
                            "<div class='mask waves-effect waves-light'></div>"+
                            "</a>"+
                            "</div>"+
                            "<div class='bottom'>"+
                            "<h6 class='mb-3'>$"+product.price+"</h6>"+
                            "<button type='button' class='btn btn-primary btn-sm mr-1 waves-effect waves-light'>"+
                                "<fmt:message key='shop.add_to_cart' bundle='${text}'/></button>"+
                            "<a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+product.id+"' type='button' class='btn btn-light btn-sm waves-effect waves-light'>Details</a>"+
                            "</div>"+
                            "</div>";
                        contentID.appendChild(newTBDiv);
                    });
            },
            statusCode:{
                404: function (){
                    window.location.href = "${pageContext.request.contextPath}/jsp/error/error404.jsp";
                },
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
                        newTBDiv.setAttribute("id","feedbackRemove"+feedback.id);
                        newTBDiv.innerHTML =
                            "<div class='media'>"+
                                "<div class='media-left'>"+
                                "<a class='avatar avatar-online' href='javascript:void(0)'>"+
                                    "<img src='"+feedback.user.imageName+"' alt=''>"+
                                "</a>"+
                                "</div>"+
                                "<div class='media-body' id='feedback'"+feedback.id+">"+
                                "<small class='text-muted pull-right'>"+feedback.date+"</small>"+
                                "<h4 class='media-heading'>"+feedback.user.name+"("+feedback.evaluation+"/5)"+"</h4>"+
                                "<div>"+feedback.feedback+
                                "<cus:access access="ADMIN">"+
                                "<button onclick='deleteFeedback("+feedback.id+")' >DELETE</button>"+
                                "</cus:access>"+
                                "</div>"+
                                "</div>"+
                            "</div>";
                        contentID.appendChild(newTBDiv);
                        if(${sessionScope.currentUser.role.equals('ADMIN')}){
                            var newDelBtn = document.createElement("button");
                            newDelBtn.setAttribute("onclick","deleteFeedback("+feedback.id+")");
                            newDelBtn.setAttribute("class","btn-primary");
                            newTBDiv.innerHTML ="delete";
                            contentID.appendChild(newTBDiv);
                        }
                    });
                }
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
        })
    })
    function deleteFeedback(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'POST',
            data: "command=delete_feedback&id_feedback="+id,
            statusCode:{
                200: function (message){
                    document.getElementById("feedbackRemove"+id).remove();
                    viewMessage(message);
                },
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
        })
    }
    function viewMessage(text){
        var newMessage = document.createElement("div");
        newMessage.setAttribute("class","message");
        newMessage.setAttribute("style","position: fixed; top: 80px; right: 15px; width: 250px; z-index: 100;");
        if (text.includes('success')||text.includes('успешн')){
            newMessage.innerHTML =
                "<div id='my-alert-success' class='alert alert-success alert-dismissible fade show' role='alert'>" +
                "<br>" +
                text +
                "<br>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true''>×</span>" +
                "</button>" +
                "</div>";
        } else {
            newMessage.innerHTML =
                "<div id='my-alert-error' class='alert alert-danger alert-dismissible fade show' role='alert'>" +
                "<br>" +
                text +
                "<br>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true''>×</span>" +
                "</button>" +
                "</div>";
        }
        document.body.appendChild(newMessage);
    }
    jQuery(document).ready(function($){

        $(".btnrating").on('click',(function(e) {

            var previous_value = $("#selected_rating").val();

            var selected_value = $(this).attr("data-attr");
            $("#selected_rating").val(selected_value);

            $(".selected-rating").empty();
            $(".selected-rating").html(selected_value);

            for (i = 1; i <= selected_value; ++i) {
                $("#rating-star-"+i).toggleClass('btn-warning');
                $("#rating-star-"+i).toggleClass('btn-default');
            }

            for (ix = 1; ix <= previous_value; ++ix) {
                $("#rating-star-"+ix).toggleClass('btn-warning');
                $("#rating-star-"+ix).toggleClass('btn-default');
            }

        }));
    });
</script>
</body>
<style>
    .rating-header {
        margin-top: -10px;
        margin-bottom: 10px;
    }
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
    .top {
        border-bottom: 1px solid #e5e5e5;
        padding-bottom: 10px;
    }
    .top ul {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
    }
    .top a {
        color: #9e9e9e;
    }
    .top a:hover {
        color: #c7ccdb;
    }
    .converse {
        padding: 2px 10px;
        border-radius: 20px;
        text-transform: uppercase;
        font-size: 14px;
    }
    .middle {
        margin-bottom: 40px;
    }
    .middle img {
        width: 100%;
    }
    .bottom {
        text-align: center;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
        -webkit-box-flex: 1;
        -ms-flex-positive: 1;
        flex-grow: 1;
    }
    .heading {
        font-size: 17px;
        text-transform: uppercase;
        margin-bottom: 5px;
        letter-spacing: 0;
    }
    .info {
        font-size: 14px;
        color: #969696;
        margin-bottom: 10px;
    }
    .style {
        font-size: 16px;
        margin-bottom: 20px;
    }
    .old-price {
        color: #f00;
        text-decoration: line-through;
    }
    .block {
        margin: 20px;
        border-radius: 4px;
        width: 280px;
        min-height: 430px;
        background: #fff;
        padding: 23px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        box-shadow: 0 2px 55px rgba(0,0,0,0.1);
    }
</style>
</html>
