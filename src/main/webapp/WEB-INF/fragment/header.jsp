<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/guest/home.jsp">
            <img src="${pageContext.request.contextPath}/images/page/logo.png" width="30" height="30" class="d-inline-block align-top" alt="logo">
            <span><fmt:message key="header.shopname" bundle="${text}"/></span>
        </a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/guest/home.jsp">
                    <fmt:message key="header.home" bundle="${text}"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/guest/shop.jsp">
                    <fmt:message key="header.shop" bundle="${text}"/>
                </a>
            </li>
            <li class="nav-item">
                <A class="nav-link" href="#">
                    <fmt:message key="header.about" bundle="${text}"/>
                </A>
            </li>
            <c:choose>
                <c:when test="${!sessionScope.currentUser.role.toString().equals('GUEST')}">
                    <li class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" id="navDropDownLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img class="img-thumbnail" width="30" height="30" id="accountImg" alt="${sessionScope.currentUser.imageName}"
                                 src="${sessionScope.currentUser.imageName}"/>
                                ${sessionScope.currentUser.name}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navDropDownLink">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/user/account.jsp">
                                <fmt:message key="header.account" bundle="${text}"/>
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=logout">
                                <fmt:message key="header.logout" bundle="${text}"/>
                            </a>
                        </div>
                    </li>
                    <c:choose>
                        <c:when test="${sessionScope.currentUser.role.toString().equals('MANAGER')}">
                            <li>
                                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/storage/storage-panel.jsp">
                                    <fmt:message key="header.storage_panel" bundle="${text}"/>
                                </a>
                            </li>
                        </c:when>
                        <c:when test="${sessionScope.currentUser.role.toString().equals('ADMIN')}">
                            <li>
                                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/admin/admin-panel.jsp">
                                    <fmt:message key="header.admin_panel" bundle="${text}"/>
                                </a>
                            </li>
                        </c:when>
                        <c:when test="${sessionScope.currentUser.role.toString().equals('CLIENT')}">
                            <li class="nav-item">
                                <a id="CART" href="${pageContext.request.contextPath}/jsp/user/cart-page.jsp" class="nav-link">
                                    <fmt:message key="header.view_cart" bundle="${text}"/>
                                    <span id="cartAmount">${sessionScope.cart.totalAmount}</span>
                                </a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:when test="${sessionScope.currentUser.role.toString().equals('GUEST')}">
                    <li class="nav-item" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/guest/login.jsp">
                            <fmt:message key="header.login" bundle="${text}"/>
                        </a>
                    </li>
                </c:when>
            </c:choose>

        </ul>
        <form class="navbar-nav" name="submitForm" method="GET" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="change_locale">
            <input type="hidden" name="newLocale" value="<fmt:message key="language.value" bundle="${text}"/>">
            <A class="nav-link" href="javascript:document.submitForm.submit()">
                <fmt:message key="language.submit" bundle="${text}"/>
            </A>
        </form>
        <div class="form-inline my-2 my-lg-0">
            <input id="search" class="form-control mr-sm-2" type="search" placeholder="<fmt:message key="header.search_product" bundle="${text}"/>" aria-label="Search">
        </div>
    </div>
</nav>
<ul class="search_result" id="resultSearch">

</ul>
<style>
    .search_result{
        top: 55px;
        position: fixed;
        left: 1150px;
        z-index: 10;
        background: #FFF;
        border: 1px #ccc solid;
        width: 400px;
        border-radius: 4px;
        max-height:300px;
    }
    .search_result li{
        list-style: none;
        padding: 5px 10px;
        margin: 0 0 0 -40px;
        color: #0896D3;
        border-bottom: 1px #ccc solid;
        cursor: pointer;
        transition:0.3s;
    }
    .search_result li:hover{
        background: rgba(0,0,0,0.13);
    }
</style>
<script>
    $(document).ready(function() {
        $('#search').on('keyup', function(){
            var search = $(this).val();
            if ((search != '') && (search.length > 1)){
                $.ajax({
                    url: "${pageContext.request.contextPath}/async?command=search_product",
                    type: 'POST',
                    data: {'search': search},
                    success: function(result){
                        document.getElementById("resultSearch").innerHTML="";
                        $.each(result, function (idx,product) {
                            var newTBDiv = document.createElement("li");
                            newTBDiv.innerHTML =
                                "<a href='${pageContext.request.contextPath}/controller?command=redirect_to_single_product&id_product="+product.id+"'>"
                                +product.name+
                                "</a>";
                            document.getElementById("resultSearch").appendChild(newTBDiv);
                        })
                    }
                });
            } else {
                document.getElementById("resultSearch").innerHTML="";
            }
        });

        $(document).on('click', function(e){
            if (!$(e.target).closest('.search_box').length){
                $result.html('');
                $result.fadeOut(100);
            }
        });
    });
</script>
