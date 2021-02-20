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
    <title>ADMINPANEL</title>
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
    <c:if test="${requestScope.error_message!=null}">
        <c:choose>
            <c:when test="${requestScope.error_message.contains('successful')}">
                <div class="messages" style="position: fixed; top: 80px; right: 15px; width: 250px; z-index: 100;">
                    <div id="my-alert-success" class="alert alert-success alert-dismissible fade show" role="alert">
                        <br>
                        <fmt:message key="${requestScope.error_message}" bundle="${error}"/>
                        <br>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="messages" style="position: fixed; top: 80px; right: 15px; width: 250px; z-index: 100;">
                    <div id="my-alert-error" class="alert alert-danger alert-dismissible fade show" role="alert">
                        <br>
                        <fmt:message key="${requestScope.error_message}" bundle="${error}"/>
                        <br>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="row">
        <%@ include file="/WEB-INF/fragment/header.jsp" %>
        <div id="page-wrapper">
            <div class="container-fluid">
                <div style="background-color: #FFFFFF" class="row">
                    <div class="col-lg-12">
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel.jsp"><h1 class="page-header"><fmt:message key='admin.products' bundle='${text}'/></h1></a>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-user.jsp"><h1 class="page-header"><fmt:message key='admin.users' bundle='${text}'/></h1> </a>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-news.jsp"><h1 class="page-header"><fmt:message key='admin.news' bundle='${text}'/></h1> </a>
                        <h1 class="page-header"><fmt:message key='admin.collections' bundle='${text}'/></h1>
                    </div>
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#ADDPRODUCTMODAL'>
                                    <fmt:message key='admin.add_product' bundle='${text}'/>
                                </button>
                                <div class='modal fade' id='ADDPRODUCTMODAL' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='false'>
                                    <div class='modal-dialog' role='document'>
                                        <div class='modal-content'>
                                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                                <div class='modal-header'>
                                                    <h5 class='modal-title'><fmt:message key='admin.menu_add_product' bundle='${text}'/></h5>
                                                </div>
                                                <div class='modal-body'>
                                                    <input type='hidden' name='command' value='add_collection'>
                                                    <input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>
                                                    <label><fmt:message key='admin.name' bundle='${text}'/></label>
                                                    <input class='form-control' type='text' name='name_collection' value="${requestScope.name_collection}" required/>
                                                    <label><fmt:message key='admin.info' bundle='${text}'/></label>
                                                    <input class='form-control' type='text' name='info_collection' value="${requestScope.info_collection}" required/>
                                                </div>
                                                <button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>
                                                <input type='submit' value='<fmt:message key='button.save_changes' bundle='${text}'/>' class='btn btn-primary'/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <label>
                                    <select onchange="changeStatus(this.value)" name="typeStatus" size=1>
                                        <option value="ACTIVE"><fmt:message key='sort.active' bundle='${text}'/></option>
                                        <option value="BLOCKED"><fmt:message key='sort.blocked' bundle='${text}'/></option>
                                    </select>
                                </label>
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key='admin.name' bundle='${text}'/></th>
                                            <th><fmt:message key='admin.info' bundle='${text}'/></th>
                                            <th><fmt:message key='admin.date' bundle='${text}'/></th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody id="table">

                                        </tbody>
                                    </table>
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
    let status = 'ACTIVE';
    function changeStatus(typeStatus){
        status=typeStatus;
        getListCollection();
    }
    function getListCollection(){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_collection&type_status="+status,
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("table");
                contentID.innerHTML = "";
                $.each(res, function (idx,collection){
                    var newTBDiv = document.createElement("tr");
                    newTBDiv.setAttribute("class","odd gradeX");
                    newTBDiv.setAttribute("id","product"+collection.id);
                    newTBDiv.innerHTML =
                        "<td>"+collection.name+"</td>"+
                        "<td>"+collection.info+"</td>"+
                        "<td class='center'>"+collection.date+"</td>"+
                        "<td class='center' id='delete"+collection.id+"'>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#statusModal"+collection.id+"'>"+
                        "<fmt:message key='admin.change_status' bundle='${text}'/>"+
                        "</button>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#changeModal"+collection.id+"'>"+
                        "<fmt:message key='admin.change' bundle='${text}'/>"+
                        "</button>"+
                        "</td>"+
                        "<div class='modal fade' id='changeModal"+collection.id+"' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>"+
                        "</div>"+
                        "<div class='modal fade' id='statusModal"+collection.id+"' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>"+
                        "</div>";
                    contentID.appendChild(newTBDiv);
                    var newForm = document.createElement('form');
                    newForm.setAttribute("name","productForm"+collection.id+"");
                    newForm.setAttribute("action","${pageContext.request.contextPath}/controller")
                    newForm.setAttribute("method","POST");
                    newForm.innerHTML =
                        "<div class='modal-dialog' role='document'>"+
                        "<div class='modal-content' id='forForm"+collection.id+"'>"+
                        "<div class='modal-header'>"+
                        "<h5 class='modal-title' id='exampleModalLabel'><fmt:message key='admin.menu_change_product' bundle='${text}'/></h5>"+
                        "</div>"+
                        "<div class='modal-body'>"+
                        "<input type='hidden' name='command' value='change_product'>"+
                        "<input type='hidden' name='id_product' value='"+collection.id+"'>"+
                        "<label>"+collection.name+"</label><br>"+
                        "<input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>"+
                        "<label><fmt:message key='admin.info' bundle='${text}'/></label><input data-toggle='tooltip' title='<fmt:message key='toggle.products_info' bundle='${text}'/>' class='form-control' aria-label='INFO' type='text' name='info_product' value='"+collection.info+"'/>"+
                        "<label><fmt:message key='admin.price' bundle='${text}'/></label><input data-toggle='tooltip' title='<fmt:message key='toggle.products_price' bundle='${text}'/>' class='form-control' aria-label='PRICE' type='text' name='price_product' value='"+collection.price+"'/>"+
                        "</div>"+
                        "<button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>"+
                        "<input type='submit' value='<fmt:message key='button.save_changes' bundle='${text}'/>' class='btn btn-primary'/>"+
                        "</div>"+
                        "</div>";
                    document.getElementById("changeModal"+collection.id).appendChild(newForm);
                    var newFormStatus = document.createElement('div');
                    newFormStatus.setAttribute("class","modal-dialog");
                    newFormStatus.setAttribute("role","document");
                    if (collection.status==='ACTIVE'){
                        newFormStatus.innerHTML =
                            "<div class='modal-content' >"+
                            "<div class='modal-header'>"+
                            "<h5 class='modal-title' id='exampleModalLabel'><fmt:message key='admin.change_status' bundle='${text}'/> "+collection.name+"</h5>"+
                            "</div>"+
                            "<div class='modal-body'>"+
                            "<button class='btn btn-primary' data-dismiss='modal' onclick='nonactiveProduct("+collection.id+")' ><fmt:message key='admin.nonactive' bundle='${text}'/></button>"+
                            "<button class='btn btn-primary' data-dismiss='modal' onclick='blockProduct("+collection.id+")'><fmt:message key='admin.block' bundle='${text}'/></button>"+
                            "</div>"+
                            "<button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>"+
                            "</div>";
                    }else {
                        if(collection.status==='BLOCKED'){
                            newFormStatus.innerHTML =
                                "<div class='modal-content' >"+
                                "<div class='modal-header'>"+
                                "<h5 class='modal-title' id='exampleModalLabel'><fmt:message key='admin.change_status' bundle='${text}'/> "+collection.name+"</h5>"+
                                "</div>"+
                                "<div class='modal-body'>"+
                                "<button class='btn btn-primary' data-dismiss='modal' onclick='unblockProduct("+collection.id+")'><fmt:message key='admin.unblock' bundle='${text}'/></button>"+
                                "</div>"+
                                "<button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>"+
                                "</div>";
                        } else {
                            newFormStatus.innerHTML =
                                "<div class='modal-content' >"+
                                "<div class='modal-header'>"+
                                "<h5 class='modal-title' id='exampleModalLabel'>Change Status "+collection.name+"</h5>"+
                                "</div>"+
                                "<div class='modal-body'>"+
                                "<button class='btn btn-primary' data-dismiss='modal' onclick='activeProduct("+collection.id+")' ><fmt:message key='admin.active' bundle='${text}'/></button>"+
                                "<button class='btn btn-primary' data-dismiss='modal' onclick='blockProduct("+collection.id+")'><fmt:message key='admin.block' bundle='${text}'/></button>"+
                                "</div>"+
                                "<button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>"+
                                "</div>";
                        }
                    }
                    document.getElementById("statusModal"+collection.id).appendChild(newFormStatus);
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
    function blockCollection(product){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'POST',
            data: "command=block_collection&id_collection="+product,
            statusCode:{
                200: function (){
                    getListCollection(0,0);
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
    function unblockCollection(product){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'POST',
            data: "command=unblock_collection&id_collection="+product,
            statusCode:{
                200: function (){
                    getListCollection(0,0);
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
    $(document).ready ( function(){
        getListCollection();
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