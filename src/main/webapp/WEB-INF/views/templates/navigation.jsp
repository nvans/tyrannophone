<nav class="navbar navbar-expand-md navbar-light">

    <a class="navbar-brand" href="#">Tyrannophone</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarContent">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarContent">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="#">Page 1</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Page 2</a></li>
            <li class="nav-item">
                <sec:authorize access="isAnonymous()">
                    <a class = "nav-link" data-toggle="modal" data-target="#login-modal">Sign in</a>
                    <%--<a class = "nav-link" href="${pageContext.request.contextPath}/login">Sign in</a>--%>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <a class = "nav-link" href="${pageContext.request.contextPath}/logout">Sign Out</a>
                </sec:authorize>
            </li>
        </ul>
    </div>
</nav>

<%--<%@include file="login.jsp"%>--%>
<c:import url="WEB-INF/views/templates/login.jsp" />