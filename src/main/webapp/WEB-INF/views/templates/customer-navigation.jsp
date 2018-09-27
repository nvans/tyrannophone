<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<sec:authorize access="hasRole('CUSTOMER')">
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">

    <a class="navbar-brand" href="/profile"><sec:authentication property="principal.username" /></a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarCustomer">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCustomer">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/contracts">Contracts</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/profile">Edit profile</a></li>
            <li class="nav-item nav-link disabled">Balance</li>
        </ul>
    </div>
</nav>
</sec:authorize>
<%--<%@include file="login.jsp"%>--%>