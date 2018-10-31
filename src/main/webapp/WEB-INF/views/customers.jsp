<html>
<head>
    <title>Customers</title>
    <%@include file="templates/header.jsp"%>
</head>
<body>
    <%@include file="templates/navigation.jsp"%>
    <%@include file="templates/employee-navigation.jsp"%>

    <sec:authorize access="hasRole('EMPLOYEE')">

        <c:if test="${message != null}">
            <div class="alert-danger">${message}</div>
        </c:if>

            <div class="container py-2">
                <form class="form-inline my-2 float-right my-lg-0" method="post">
                    <input class="form-control mr-sm-2" type="number" name="contractNumber" placeholder="Contract number" aria-label="Search">
                    <button class="btn btn-primary my-2 my-sm-0" type="submit">Search</button>
                </form>
                <br/><br/>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <%--<th scope="col">Id</th>--%>
                        <th scope="col">Email</th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Balance</th>
                        <th scope="col">Status</th>
                        <%--<th scope="col">Actions</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${customers}" var="customer">
                            <tr>
                                <%--<td><b><c:out value="${customer.id}"/></b></td>--%>
                                <td><b><c:out value="${customer.user.email}"/></b></td>
                                <td>
                                    <c:out value="${customer.firstName}"/> <c:out value="${customer.lastName}"/>
                                </td>
                                <td><c:out value="${customer.balance}"/> </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${customer.user.active}">
                                            <span class="badge badge-success">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Blocked</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a class="button btn-primary btn-sm" role="button"
                                       href="${pageContext.request.contextPath}/customers/${customer.id}">Details</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}/customers?page=${page - 1 > 0 ? page - 1 : 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                                Prev
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}/customers?page=${page + 1 <= lastPage ? page + 1 : lastPage}" aria-label="Next">
                                Next
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
    </sec:authorize>
</body>
</html>
