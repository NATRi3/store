<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/guest/home.jsp">
            <img src="${pageContext.request.contextPath}/images/page/logo.png" width="30" height="30" class="d-inline-block align-top" alt="logo">
            <span><fmt:message key="header.shopname" bundle="${text}"/></span>
        </a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <form name="redirectHomeForm" method="GET" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="redirect_to_home">
                    <a class="nav-link" href="javascript:document.redirectHomeForm.submit()">
                        <fmt:message key="header.home" bundle="${text}"/>
                    </a>
                </form>
            </li>
            <li class="nav-item">
                <form name="redirectShopForm" method="GET" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="redirect_to_shop">
                    <a class="nav-link" href="javascript:document.redirectShopForm.submit()">
                        <fmt:message key="header.shop" bundle="${text}"/>
                    </a>
                </form>
            </li>
            <li class="nav-item">
                <form name="redirectAboutForm" method="GET" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="redirect_to_about">
                    <a class="nav-link" href="javascript:document.redirectAboutForm.submit()">
                        <fmt:message key="header.about" bundle="${text}"/>
                    </a>
                </form>
            </li>
            <li class="nav-item">
                <form name="submitForm" method="GET" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="change_locale">
                    <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                    <input type="hidden" name="newLocale" value="<fmt:message key="language.value" bundle="${text}"/>">
                    <A class="nav-link" href="javascript:document.submitForm.submit()">
                        <fmt:message key="language.submit" bundle="${text}"/>
                    </A>
                </form>
            </li>
            <c:if test="${sessionScope.currentUser.id!=-1}">
                <li class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" id="navDropDownLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="imageServlet?command=user&image_name=${sessionScope.currentUser.imageName}" alt="${sessionScope.currentUser.imageName}">
                        Profile
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navDropDownLink">
                        <a class="dropdown-item" href="#"><fmt:message key="header.account" bundle="${text}"/></a>
                        <a class="dropdown-item" href="#"><fmt:message key="header.logout" bundle="${text}"/></a>
                    </div>
                </li>
            </c:if>
            </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="<fmt:message key="header.search_product" bundle="${text}"/>" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
                <fmt:message key="header.search" bundle="${text}"/>
            </button>
        </form>
    </div>
</nav>
