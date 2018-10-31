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
                    <td onclick="showContract(${contract.contractNumber})">
                        <b><c:out value="${contract.contractNumber}"/></b>
                    </td>
                    <td onclick="showContract(${contract.contractNumber})">
                        <c:out value="${contract.plan.planName}"/>
                    </td>
                    <td onclick="showContract(${contract.contractNumber})">
                        <c:out value="${contract.plan.monthlyPrice}"/>
                    </td>
                    <td class="text-left" onclick="showContract(${contract.contractNumber})">
                        <span class="badge  ${contract.active ? 'badge-success' : 'badge-danger'}">
                                ${contract.active ? 'Active' : 'Inactive'}
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
        <nav>
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/contracts?page=${page - 1 > 0 ? page - 1 : 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                        Prev
                    </a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/contracts?page=${page + 1 <= lastPage ? page + 1 : lastPage}" aria-label="Next">
                        Next
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>

    <%@include file="templates/show-contract-modal.jsp"%>

</body>
</html>
