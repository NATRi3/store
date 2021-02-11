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
        <div id="page-wrapper">
            <div class="container-fluid">
                <div style="background-color: #FFFFFF" class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Products</h1>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-user.jsp"><h1 class="page-header">Users</h1> </a>
                        <a href="${pageContext.request.contextPath}/jsp/admin/admin-panel-news.jsp"><h1 class="page-header">News</h1> </a>
                    </div>
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                DataTables Advanced Tables
                                <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#ADDPRODUCTMODAL'>
                                    ADD PRODUCT
                                </button>
                                <div class='modal fade' id='ADDPRODUCTMODAL' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
                                    <div class='modal-dialog' role='document'>
                                        <div class='modal-content'>
                                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                                <div class='modal-header'>
                                                    <h5 class='modal-title'>Add product menu</h5>
                                                </div>
                                                <div class='modal-body'>
                                                    <input type='hidden' name='command' value='add_product'>
                                                    <input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>
                                                    <label>NAME</label>
                                                    <input class='form-control' type='text' name='name_product' placeholder="" required/>
                                                    <label>INFO</label>
                                                    <input class='form-control' type='text' name='info_product' placeholder="" required/>
                                                    <label>PRICE</label>
                                                    <input class='form-control' type='text' name='price_product' placeholder="" required pattern="\d*+(\.\d{2})?"/>
                                                    <label>Collection</label>
                                                    <select name="id_collection" id="selectorCollection">

                                                    </select>
                                                </div>
                                                <button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>
                                                <input type='submit' value='Save changes' class='btn btn-primary'/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <label>
                                    <select onchange="changeSorting(this.value)" name="typeSort" size=1>
                                        <option value="name">name</option>
                                        <option value="name DESC">name desc</option>
                                        <option value="price DESC">price desc</option>
                                        <option value="price">price</option>
                                    </select>
                                </label>
                                <label>
                                    <select onchange="changeStatus(this.value)" name="typeStatus" size=1>
                                        <option value="ACTIVE">active</option>
                                        <option value="BLOCKED">blocked</option>
                                        <option value="NONACTIVE">nonactive</option>
                                    </select>
                                </label>
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>NAME</th>
                                            <th>INFO</th>
                                            <th>PRICE</th>
                                            <th>RATING</th>
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
            error(error){
                alert(error);
            }
        });
    });
    let amounts = 10;
    let sorting = 'name';
    function changeSorting(sort){
        sorting = sort;
        getListProduct(0,0);
    }
    let status = 'ACTIVE';
    function changeStatus(typeStatus){
        status=typeStatus;
        getListProduct(0,0);
    }
    function getListProduct(begin,collection){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?" +
                "command=get_list_product_by_collection&id_collection="+collection+
                "&type_sort="+sorting+"&begin_pagination="+begin+"&type_status="+status,
            type: 'GET',
            dataType: 'json',
            success: function (res){
                var contentID = document.getElementById("table");
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
                    var newTBDiv = document.createElement("tr");
                    newTBDiv.setAttribute("class","odd gradeX");
                    newTBDiv.setAttribute("id","product"+product.id);
                    newTBDiv.innerHTML =
                        "<td>"+
                        "<div class='profile-img'>"+
                        "<img class='img-thumbnail' width='100' height='100' id='accountImg' alt=''"+
                        "src='${pageContext.request.contextPath}/async?command=get_image&image_name="+product.imageName+"'/>"+
                        "<form name='uploadForm"+product.id+"'"+
                        "action='${pageContext.request.contextPath}/controller?command=change_product_image&id_product="+product.id+"&ctoken=${sessionScope.stoken}'"+
                        "method='post' enctype='multipart/form-data'>"+
                        "<input class='btn btn-primary' type='file' name='file' onchange='document.uploadForm"+product.id+".submit()'/>"+
                        "</form>"+
                        "</div>"+
                        "<td>"+product.name+"</td>"+
                        "<td>"+product.info+"</td>"+
                        "<td>"+product.price+"</td>"+
                        "<td class='center'>"+product.rating+"</td>"+
                        "<td class='center' id='delete"+product.id+"'>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#statusModal"+product.id+"'>"+
                        "Status"+
                        "</button>"+
                        "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#changeModal"+product.id+"'>"+
                        "Change"+
                        "</button>"+
                        "</td>"+
                        "<div class='modal fade' id='changeModal"+product.id+"' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>"+
                        "</div>"+
                        "<div class='modal fade' id='statusModal"+product.id+"' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>"+
                        "</div>";
                    contentID.appendChild(newTBDiv);
                    var newForm = document.createElement('form');
                    newForm.setAttribute("name","productForm"+product.id+"");
                    newForm.setAttribute("action","${pageContext.request.contextPath}/controller")
                    newForm.setAttribute("method","POST");
                    newForm.innerHTML =
                        "<div class='modal-dialog' role='document'>"+
                        "<div class='modal-content' id='forForm"+product.id+"'>"+
                        "<div class='modal-header'>"+
                        "<h5 class='modal-title' id='exampleModalLabel'>Change Product Menu</h5>"+
                        "</div>"+
                        "<div class='modal-body'>"+
                        "<input type='hidden' name='command' value='change_product'>"+
                        "<input type='hidden' name='id_product' value='"+product.id+"'>"+
                        "<label>"+product.name+"</label><br>"+
                        "<input type='hidden' name='ctoken' value='${sessionScope.stoken}'/>"+
                        "<label>INFO</label><input class='form-control' aria-label='INFO' type='text' name='info_product' value='"+product.info+"'/>"+
                        "<label>PRICE</label><input class='form-control' aria-label='PRICE' type='text' name='price_product' value='"+product.price+"'/>"+
                        "</div>"+
                        "<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>"+
                        "<input type='submit' value='Save changes' class='btn btn-primary'/>"+
                        "</div>"+
                        "</div>";
                    document.getElementById("changeModal"+product.id).appendChild(newForm);
                    var newFormStatus = document.createElement('div');
                    newFormStatus.setAttribute("class","modal-dialog");
                    newFormStatus.setAttribute("role","document");
                    if (product.status==='ACTIVE'){
                        newFormStatus.innerHTML =
                            "<div class='modal-content' >"+
                            "<div class='modal-header'>"+
                            "<h5 class='modal-title' id='exampleModalLabel'>Change Status "+product.name+"</h5>"+
                            "</div>"+
                            "<div class='modal-body'>"+
                            "<button class='btn btn-primary' data-dismiss='modal' onclick='nonactiveProduct("+product.id+")' >NONACTIVE</button>"+
                            "<button class='btn btn-primary' data-dismiss='modal' onclick='blockProduct("+product.id+")'>BLOCKED</button>"+
                            "</div>"+
                            "<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>"+
                            "</div>";
                    }else {
                        if(product.status==='BLOCKED'){
                            newFormStatus.innerHTML =
                                "<div class='modal-content' >"+
                                "<div class='modal-header'>"+
                                "<h5 class='modal-title' id='exampleModalLabel'>Change Status "+product.name+"</h5>"+
                                "</div>"+
                                "<div class='modal-body'>"+
                                "<button class='btn btn-primary' data-dismiss='modal' onclick='unblockProduct("+product.id+")'>UNBLOCK</button>"+
                                "</div>"+
                                "<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>"+
                                "</div>";
                        } else {
                            newFormStatus.innerHTML =
                                "<div class='modal-content' >"+
                                "<div class='modal-header'>"+
                                "<h5 class='modal-title' id='exampleModalLabel'>Change Status "+product.name+"</h5>"+
                                "</div>"+
                                "<div class='modal-body'>"+
                                "<button class='btn btn-primary' data-dismiss='modal' onclick='activeProduct("+product.id+")' >ACTIVE</button>"+
                                "<button class='btn btn-primary' data-dismiss='modal' onclick='blockProduct("+product.id+")'>BLOCKED</button>"+
                                "</div>"+
                                "<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>"+
                                "</div>";
                        }
                    }
                    document.getElementById("statusModal"+product.id).appendChild(newFormStatus);
                    amounts = begin;
                });
            },
            error(error) {
                alert(error);
            }
        });
    }
    function blockProduct(product){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?command=block_product&id_product="+product,
            type: 'GET',
            dataType: 'text',
            success: function (text){
                getListProduct(0,0);
            },
            error(error){
                alert(error);
            }
        })
    }
    function unblockProduct(product){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?command=unblock_product&id_product="+product,
            type: 'GET',
            dataType: 'text',
            success: function (text){
                getListProduct(0,0);
            },
            error(error){
                alert(error);
            }
        })
    }
    function nonactiveProduct(product){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?command=deactivate_product&id_product="+product,
            type: 'GET',
            dataType: 'text',
            success: function (text){
                getListProduct(0,0);
            },
            error(error){
                alert(error);
            }
        })
    }
    function activeProduct(product){
        $.ajax({
            url: "${pageContext.request.contextPath}/async?command=activate_product&id_product="+product,
            type: 'GET',
            dataType: 'text',
            success: function (text){
                getListProduct(0,0);
            },
            error(error){
                alert(error);
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