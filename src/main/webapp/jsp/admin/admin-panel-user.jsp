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
                        <h1 class="page-header"><fmt:message key='admin.users' bundle='${text}'/></h1>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-news.jsp"><h1 class="page-header"><fmt:message key='admin.news' bundle='${text}'/></h1> </a>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-collection.jsp"><h1 class="page-header"><fmt:message key='admin.collections' bundle='${text}'/></h1></a>
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
                                                    <input type='hidden' name='command' value='add_product'>
                                                    <input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>
                                                    <label><fmt:message key='admin.name' bundle='${text}'/></label>
                                                    <input class='form-control' type='text' name='name_product' value="${requestScope.name_product}" required/>
                                                    <label><fmt:message key='admin.info' bundle='${text}'/></label>
                                                    <input class='form-control' type='text' name='info_product' value="${requestScope.info_product}" required/>
                                                    <label><fmt:message key='admin.price' bundle='${text}'/></label>
                                                    <input class='form-control' type='text' name='price_product' value="${requestScope.price_product}" required pattern="\d*+(\.\d{2})?"/>
                                                    <label><fmt:message key='admin.collection' bundle='${text}'/></label>
                                                    <select name="id_collection" id="selectorCollection">

                                                    </select>
                                                </div>
                                                <button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>
                                                <input type='submit' value='<fmt:message key='button.save_changes' bundle='${text}'/>' class='btn btn-primary'/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <label>
                                    <select onchange="changeSorting(this.value)" name="typeSort" size=1>
                                        <option value="CLIENT"><fmt:message key='sort.client' bundle='${text}'/></option>
                                        <option value="MANAGER"><fmt:message key='sort.manager' bundle='${text}'/></option>
                                    </select>
                                </label>
                                <label>
                                    <select onchange="changeStatus(this.value)" name="typeStatus" size=1>
                                        <option value="ACTIVE"><fmt:message key='sort.active' bundle='${text}'/></option>
                                        <option value="BLOCKED"><fmt:message key='sort.blocked' bundle='${text}'/></option>
                                        <option value="NONACTIVE"><fmt:message key='sort.nonactive' bundle='${text}'/></option>
                                    </select>
                                </label>
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key='admin.id' bundle='${text}'/></th>
                                            <th><fmt:message key='admin.image' bundle='${text}'/></th>
                                            <th><fmt:message key='admin.email' bundle='${text}'/></th>
                                            <th><fmt:message key='admin.name' bundle='${text}'/></th>
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
    $(document).ready ( function(){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_collection",
            type: 'GET',
            dataType: 'json',
            success: function(res) {
                var contentID = document.getElementById("selectorCollection");
                $.each(res, function (idx,collection){
                    var newTBDiv = document.createElement("option");
                    newTBDiv.setAttribute("name","id_collection");
                    newTBDiv.setAttribute("size",1);
                    newTBDiv.setAttribute("value",""+collection.idCollection);
                    newTBDiv.innerHTML = collection.name;
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
    let role = 'CLIENT';
    function changeRole(newRole){
        role =newRole;
        getListProduct(0,0);
    }
    let status = 'ACTIVE';
    function changeStatus(typeStatus){
        status=typeStatus;
        getListProduct(0,0);
    }
    function getListProduct(begin){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_users_by_role_status"+
                "&type_role="+role+"&begin_pagination="+begin+"&type_status="+status,
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("table");
                contentID.innerHTML = "";
                if(begin>=10) {
                    var previous = document.createElement("button");
                    previous.setAttribute("class", "btn-primary");
                    previous.setAttribute("onclick", "getListProduct(" + (begin-10) + ","+collection+")")
                    previous.innerHTML = "PREVIOS";
                    contentID.appendChild(previous);
                }
                if(res.length===10) {
                    var next = document.createElement("button");
                    next.setAttribute("class", "btn-primary");
                    next.setAttribute("onclick", "getListProduct(" + (begin + 10) + "," + collection + ")");
                    next.innerHTML = "NEXT";
                    contentID.appendChild(next);
                }
                $.each(res, function (idx,user){
                    var newTBDiv = document.createElement("tr");
                    newTBDiv.setAttribute("class","odd gradeX");
                    newTBDiv.setAttribute("id","user"+user.id);
                    newTBDiv.innerHTML =
                        "<td>"+user.id+"</td>"+
                        "<td>"+
                        "<div class='profile-img'>"+
                        "<img class='img-thumbnail' width='100' height='100' id='accountImg' alt=''"+
                        "src='"+user.imageName+"'/>"+
                        "</div>"+
                        "</td>"+
                        "<td>"+user.email+"</td>"+
                        "<td>"+user.name+"</td>"+
                        "<td class='center' id='blocked"+user.id+"'>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#statusModal"+user.id+"'>"+
                        "<fmt:message key='admin.change_status' bundle='${text}'/>"+
                        "</button>"+
                        "</td id = 'userBtnBlock"+user.id+"'>"+
                        "</div>";
                    contentID.appendChild(newTBDiv);
                    if (user.status==='ACTIVE'){
                        var newFormStatus = document.createElement('button');
                        newFormStatus.setAttribute("class","btn btn-primary");
                        newFormStatus.setAttribute("onclick","blockProduct("+user.id+")");
                        newFormStatus.innerHTML = "<fmt:message key='admin.block' bundle='${text}'/>";
                        document.getElementById("userBtnBlock"+user.id).appendChild(newFormStatus);
                    }
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
    function blockUser(userId){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'GET',
            data: "command=block_user&id_user="+userId,
            statusCode:{
                200: function(){
                    getListProduct(0,0);
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
    function unblockUser(userId){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'POST',
            data: "command=unblock_user&id_user="+userId,
            statusCode: {
                200: function (){
                    getListProduct(0,0);
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
    function deleteUser(userId){
        $.ajax({
            url: "${pageContext.request.contextPath}/async",
            type: 'POST',
            data: "command=deactivate_product&id_product="+userId,
            statusCode:{
                200: function (){
                    getListProduct(0,0);
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
        getListProduct(0,0);
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