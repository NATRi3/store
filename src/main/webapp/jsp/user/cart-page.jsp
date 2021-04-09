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
    <title>Cart</title>
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
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js" type="text/javascript"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery.maskedinput@1.4.1/src/jquery.maskedinput.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
    <cus:message/>
        <%@ include file="/WEB-INF/fragment/header.jsp" %>
        <!--Section: Block Content-->
        <section>

            <!--Grid row-->
            <div class="row">

                <!--Grid column-->
                <div class="col-lg-8">

                    <!-- Card -->
                    <div class="card wish-list mb-3">
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${sessionScope.cart.products.size()==0}">
                                    <h5 class="mb-4">
                                        <fmt:message key="cart.cart_empty" bundle="${text}"/>
                                        <br>
                                        <a class="" href="${pageContext.request.contextPath}/jsp/guest/shop.jsp">
                                            <fmt:message key="cart.start_shop" bundle="${text}"/>
                                        </a>
                                    </h5>
                                </c:when>
                                <c:when test="${sessionScope.cart.products.size()>0}">
                                    <h5 class="mb-4">
                                        <fmt:message key="cart.cart" bundle="${text}"/>
                                        (<span>${sessionScope.cart.totalAmount}</span>
                                        <fmt:message key="cart.items" bundle="${text}"/>)
                                    </h5>
                                    <c:forEach items="${sessionScope.cart.products.keySet()}" var="product" >
                                        <hr class="mb-4">
                                        <div class="row mb-4">
                                        <div class="col-md-5 col-lg-3 col-xl-3">
                                            <div class="view zoom overlay z-depth-1 rounded mb-3 mb-md-0">
                                                <img class="img-fluid w-100"
                                                     src="${product.imageName}" alt="Sample">
                                            </div>
                                        </div>
                                            <div class="col-md-7 col-lg-9 col-xl-9">
                                            <div>
                                                <div class="d-flex justify-content-between">
                                                    <div>
                                                        <h5>${product.name}</h5>
                                                        <p class="mb-3 text-muted text-uppercase small">${product.info}</p>
                                                    </div>
                                                    <div>
                                                        <form action="${pageContext.request.contextPath}/controller" >
                                                        <div class="def-number-input number-input safari_only mb-0 w-100">
                                                            <input type="hidden" name="command" value="add_amount_product_to_cart">
                                                            <input type="hidden" name="id_product" value="${product.id}">
                                                            <input class="quantity" min="0" name="amount_product" value="${sessionScope.cart.products.get(product)}" type="number">
                                                            <button type="submit" class="btn-primary"><fmt:message key="button.save_changes" bundle="${text}"/></button>
                                                        </div>
                                                        </form>
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div>
                                                        <a href="${pageContext.request.contextPath}/controller?command=remove_product_from_cart&id_product=${product.id}"
                                                           type="button" class="card-link-secondary small text-uppercase mr-3"><i
                                                                class="fas fa-trash-alt mr-1"></i> <fmt:message key="button.delete" bundle="${text}"/></a>
                                                    </div>
                                                    <p class="mb-0"><span><strong>${product.price}$</strong></span></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    </c:forEach>
                                    <hr class="mb-4">

                                </div>
                            </div>


                        </div>
                        <!--Grid column-->

                        <!--Grid column-->
                        <div class="col-lg-4">

                            <!-- Card -->
                            <div class="card mb-3">
                                <div class="card-body">

                                    <h5 class="mb-3">The total amount of</h5>

                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                                            <div>
                                                <strong><fmt:message key="admin.price" bundle="${text}"/></strong>
                                            </div>
                                            <span><strong>$${sessionScope.cart.totalPrice}</strong></span>
                                        </li>
                                    </ul>

                                    <button type="button" class="btn btn-primary btn-block waves-effect waves-light" data-toggle="modal" data-target="#exampleModal">
                                        <fmt:message key="create.order" bundle="${text}"/>
                                    </button>
                                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <form action="${pageContext.request.contextPath}/controller" method="post">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="create.order" bundle="${text}"/></h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                </div>
                                                    <input type="hidden" name="command" value="create_order">
                                                    <input type="hidden" name="ctoken" value="${sessionScope.stoken}">
                                                    <label>
                                                        <fmt:message key="cart.address" bundle="${text}"/>
                                                        <input type="text" class="form-control" name="address" value="${requestScope.address}">
                                                    </label>
                                                    <label for="phone">
                                                        <fmt:message key="cart.phone" bundle="${text}"/>
                                                        <input type="tel" class="form-control" id="phone" name="phone" value="${requestScope.phone}" placeholder="+375 (99) 99 99 999">
                                                    </label>
                                                    <script src="${pageContext.request.contextPath}/js/jquery.maskedinput.zip"></script>
                                                    <script>
                                                        $(function(){
                                                            $("#phone").mask("+375(99)999-99-99");
                                                        });
                                                    </script>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="button.close" bundle="${text}"/></button>
                                                    <button type="submit" class="btn btn-primary"><fmt:message key="button.save" bundle="${text}"/></button>
                                                </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                    </c:when>
                </c:choose>
                </div>
            </div>
        </section>
</div>
</body>
<script type="text/javascript">
    function mask(inputName, mask, evt) {
        try {
            var text = document.getElementById(inputName);
            var value = text.value;
            try {
                var e = (evt.which) ? evt.which : event.keyCode;
                if ( e == 46 || e == 8 ) {
                    text.value = "";
                    return;
                }
            } catch (e1) {}
            var literalPattern=/[0\*]/;
            var numberPattern=/[0-9]/;
            var newValue = "";
            for (var vId = 0, mId = 0 ; mId < mask.length ; ) {
                if (mId >= value.length)
                break;
                if (mask[mId] == '0' && value[vId].match(numberPattern) == null) {
                    break;
                }
                while (mask[mId].match(literalPattern) == null) {
                    if (value[vId] == mask[mId])
                        break;
                    newValue += mask[mId++];
                }
                newValue += value[vId++];
                mId++;
            }
            text.value = newValue;
        } catch(e) {}
    }
</script>

</html>