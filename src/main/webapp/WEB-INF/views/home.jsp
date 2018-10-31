<html>
<head>
    <%@include file="templates/header.jsp" %>
    <title>Tyrannophone Home</title>
</head>
<body>
    <%@include file="templates/navigation.jsp" %>
    <%@include file="templates/customer-navigation.jsp" %>
    <%@include file="templates/employee-navigation.jsp" %>

<c:if test="${param.error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>${SPRING_SECURITY_LAST_EXCEPTION.message}</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>

<div class="container-fluid">
    <div class="row justify-content-center">
        <c:forEach items="${plans}" var="plan">
            <c:if test="${plan.connectionAvailable}">
                <div class="card" style="margin: 3%; width: 400px; background: azure ">
                    <img class="card-img" src="${pageContext.request.contextPath}/resources/images/plan-bg.jpg">
                    <div class="card-header card-img-overlay">
                            <%--<div class="card-img-overlay">--%>
                        <h2 class="card-title text-light text-center">${plan.planName}</h2>
                            <%--</div>--%>
                    </div>
                    <h5 class="text-dark font-italic text-left mx-4">${plan.description}</h5>
                    <div class="card-body">
                        <h4 class="text-center">${plan.monthlyPrice}$ / per month</h4><br/>
                            <%--<p class="card-text">--%>
                        <label>Connected options</label>
                        <ul class="card-text">
                            <c:forEach items="${plan.connectedOptions}" var="option">
                                <li>${option.name}</li>
                            </c:forEach>
                        </ul>
                            <%--</p>--%>

                    </div>

                    <div class="card-footer d-flex justify-content-center">
                        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                            <a class="btn btn-primary col-md-4 text-light"
                               href="${pageContext.request.contextPath}/cart/add/${plan.id}">Buy</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
                            <a class="btn btn-primary col-md-4 text-light" href="#">Buy</a>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <a class="btn btn-primary col-md-4 text-light" data-toggle="modal" data-target="#login-modal">Buy</a>
                        </sec:authorize>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>

</body>
</html>
