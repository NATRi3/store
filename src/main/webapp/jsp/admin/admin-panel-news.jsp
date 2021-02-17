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
                                <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#AddNewsModal'>
                                    <fmt:message key='admin.add_news' bundle='${text}'/>
                                </button>
                                <div class='modal fade' id='AddNewsModal' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
                                    <div class='modal-dialog' role='document'>
                                        <div class='modal-content'>
                                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                                <div class='modal-header'>
                                                    <h5 class='modal-title'><fmt:message key='admin.menu_add_news' bundle='${text}'/></h5>
                                                </div>
                                                <div class='modal-body'>
                                                    <input type='hidden' name='command' value='add_news'>
                                                    <input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>
                                                    <label><fmt:message key='admin.title' bundle='${text}'/></label>
                                                    <input data-toggle="tooltip" title="<fmt:message key="toggle.news_title" bundle="${text}"/>" class='form-control' type='text' name='news_title' placeholder="" required/>
                                                    <label><fmt:message key='admin.info' bundle='${text}'/></label>
                                                    <input data-toggle="tooltip" title="<fmt:message key="toggle.news_info" bundle="${text}"/>" class='form-control' type='text' name='news_info' placeholder="" required/>
                                                </div>
                                                    <button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>
                                                    <input type='submit' value='Save changes' class='btn btn-primary'/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <label>
                                    <fmt:message key='admin.sorting_type' bundle='${text}'/>
                                    <select onchange="changeSorting(this.value)" name="typeSort" size=1>
                                        <option value="title"><fmt:message key='sort.title' bundle='${text}'/> </option>
                                        <option value="title DESC"><fmt:message key='sort.title_desc' bundle='${text}'/></option>
                                        <option value="date DESC"><fmt:message key='sort.date_desc' bundle='${text}'/></option>
                                        <option value="date"><fmt:message key='sort.date' bundle='${text}'/></option>
                                    </select>
                                </label>
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key='admin.image' bundle='${text}'/></th>
                                            <th><fmt:message key='admin.title' bundle='${text}'/></th>
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
    let amounts;
    let sorting = 'title';
    function changeSorting(sort){
        sorting = sort;
        getListNews(0);
    }
    function getListNews(begin){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_news&type_sort="+sorting+
                "&begin_pagination="+begin,
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("table");
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
                $.each(res, function (idx,news){
                    var newTBDiv = document.createElement("tr");
                    newTBDiv.setAttribute("class","odd gradeX");
                    newTBDiv.setAttribute("id","news"+news.idNews);
                    newTBDiv.innerHTML =
                        "<td>"+
                        "<div class='profile-img'>"+
                        "<img class='img-thumbnail' width='100' height='100' id='accountImg' alt=''"+
                        "src='"+news.imageName+"'/>"+
                        "<form name='uploadForm"+news.idNews+"'"+
                        "action='${pageContext.request.contextPath}/controller?command=change_news_image&id_news="+news.idNews+"&ctoken=${sessionScope.stoken}'"+
                        "method='post' enctype='multipart/form-data'>"+
                        "<input class='btn btn-primary' type='file' name='file' onchange='document.uploadForm"+news.idNews+".submit()'/>"+
                        "</form>"+
                        "</div>"+
                        "<td>"+news.title+"</td>"+
                        "<td>"+news.info+"</td>"+
                        "<td>"+news.date+"</td>"+
                        "<td class='center' id='delete"+news.idNews+"'>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#statusModal"+news.idNews+"'>"+
                        "<fmt:message key='admin.change_status' bundle='${text}'/>"+
                        "</button>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#changeModal"+news.idNews+"'>"+
                        "<fmt:message key='admin.change' bundle='${text}'/>"+
                        "</button>"+
                        "</td>"+
                        "<div class='modal fade' id='changeModal"+news.idNews+"' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>"+
                        "</div>"+
                        "<div class='modal fade' id='statusModal"+news.idNews+"' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>"+
                        "</div>";
                    contentID.appendChild(newTBDiv);
                    var newForm = document.createElement('form');
                    newForm.setAttribute("name","productForm"+news.idNews+"");
                    newForm.setAttribute("action","${pageContext.request.contextPath}/controller")
                    newForm.setAttribute("method","POST");
                    newForm.innerHTML =
                        "<div class='modal-dialog' role='document'>"+
                        "<div class='modal-content' id='forForm"+news.idNews+"'>"+
                        "<div class='modal-header'>"+
                        "<h5 class='modal-title' id='exampleModalLabel'><fmt:message key='admin.change' bundle='${text}'/>"+news.title+"</h5>"+
                        "</div>"+
                        "<div class='modal-body'>"+
                        "<input type='hidden' name='command' value='change_news'>"+
                        "<input type='hidden' name='id_news' value='"+news.idNews+"'>"+
                        "<label>"+news.title+"</label><br>"+
                        "<input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>"+
                        "<label><fmt:message key='admin.title' bundle='${text}'/></label><input class='form-control' aria-label='TITLE' type='text' name='news_title' value='"+news.title+"'/>"+
                        "<label><fmt:message key='admin.info' bundle='${text}'/></label><input class='form-control' aria-label='INFO' type='text' name='news_info' value='"+news.info+"'/>"+
                        "</div>"+
                        "<button type='button' class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.close' bundle='${text}'/></button>"+
                        "<input type='submit' value='<fmt:message key='button.save_changes' bundle='${text}'/>' class='btn btn-primary'/>"+
                        "</div>"+
                        "</div>"+
                        "</div>";
                    document.getElementById("changeModal"+news.idNews).appendChild(newForm);
                    var newFormStatus = document.createElement('form');
                    newFormStatus.setAttribute("name","productForm"+news.idNews+"");
                    newFormStatus.setAttribute("action","${pageContext.request.contextPath}/controller")
                    newFormStatus.setAttribute("method","POST");
                    newFormStatus.innerHTML =
                            "<div class='modal-dialog' role='document'>"+
                            "<div class='modal-content' >"+
                            "<div class='modal-header'>"+
                            "<h5 class='modal-title' id='exampleModalLabel'><fmt:message key='admin.change_status' bundle='${text}'/> "+news.title+"</h5>"+
                            "</div>"+
                            "<div class='modal-body'>"+
                            "<input type='hidden' name='command' value='delete_news'>"+
                            "<input type='hidden' name='ctoken' value='${sessionScope.stoken}'>"+
                            "<input type='hidden' name='id_news' value='"+news.idNews+"'>"+
                            "<button class='btn btn-secondary' data-dismiss='modal'><fmt:message key='button.cancel' bundle='${text}'/></button>"+
                            "<input class='btn btn-primary' type='submit' name='delete' value='<fmt:message key='button.delete' bundle='${text}'/>'>"+
                            "</div>"+
                            "</div>";
                    document.getElementById("statusModal"+news.idNews).appendChild(newFormStatus);
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