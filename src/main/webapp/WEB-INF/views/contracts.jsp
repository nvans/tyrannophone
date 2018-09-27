<html>
<head>
    <%@include file="templates/header.jsp"%>
</head>
<body>
<%@include file="templates/navigation.jsp"%>
<%@include file="templates/customer-navigation.jsp"%>
<%@include file="templates/employee-navigation.jsp"%>

    <div class="container py-2">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">Number</th>
                <th scope="col">Plan</th>
                <th scope="col">Cost per month</th>
                <th scope="col">Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${contracts}" var="contract">
                <tr>
                    <td><b><c:out value="${contract.contractNumber}"/></b></td>
                    <td><c:out value="${contract.plan.planName}"/> </td>
                    <td><c:out value="${contract.plan.monthlyPrice}"/> </td>
                    <td>
                        <span class="badge  ${contract.active ? 'badge-success' : 'badge-danger'}">
                                ${contract.active ? 'Active' : 'Blocked'}
                        </span>
                    </td>
                    <td>
                        <a class="button btn-primary btn-sm" role="button"
                           href="${pageContext.request.contextPath}/contracts/${contract.contractNumber}">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
