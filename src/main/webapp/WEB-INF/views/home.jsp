<html>
<head>
    <%@include file="templates/header.jsp" %>
    <title>Tyrannophone Home</title>
</head>
<body>
<div><%@include file="templates/navigation.jsp" %></div>
<div><%@include file="templates/customer-navigation.jsp"%></div>
<div><%@include file="templates/employee-navigation.jsp"%></div>

<c:if test="${param.error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>${SPRING_SECURITY_LAST_EXCEPTION.message}</strong>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>

<div class="card-deck">
    <c:forEach items="${plans}" var="plan">
        <c:if test="${plan.connectionAvailable}">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">${plan.planName}</h4>
                    <p class="card-text">
                        <ul class="list-group">
                            <c:forEach items="${plan.availableOptions}" var="option">
                                <li class="list-group-item">${option}</li>
                            </c:forEach>
                        </ul>
                    </p>
                    Starts at T${plan.connectionPrice}
                </div>
                <div class="card-footer">
                    <a class="card-link" href="#">See details</a>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>

</body>
</html>
