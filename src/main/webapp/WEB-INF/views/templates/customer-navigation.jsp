<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<sec:authorize access="hasRole('CUSTOMER')">
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">

    <%--<a class="navbar-brand" href="/profile"><sec:authentication property="principal.username" /></a>--%>
    <a class="navbar-brand text-light" href="/profile">${customerInfo.name}</a>
    <a class="navbar-brand text-light" href="#">${customerInfo.balance}</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarCustomer">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCustomer">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/contracts">Contracts</a></li>
        </ul>
        <ul class="navbar-nav ml-auto">

            <li class="nav-item"><a class="navbar-brand <c:if test="${!cart.cartEmpty}">text-warning"</c:if> href="${pageContext.request.contextPath}/cart">Cart</a></li>
        </ul>
    </div>
</nav>
</sec:authorize>
<%--<%@include file="login.jsp"%>--%>