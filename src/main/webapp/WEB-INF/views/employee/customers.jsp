<html>
<head>
    <title>Customers</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/employee-navigation.jsp"%>

    <c:if test="${message != null}">
        <div class="alert-danger">${message}</div>
    </c:if>

    <form class="form-inline my-2 my-lg-0" method="post">
        <input class="form-control mr-sm-2" type="number" name="contractNumber" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>

    <div class="container py-2">
        <div class="container py-2">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Balance</th>
                    <th scope="col">Active</th>
                    <%--<th scope="col">Actions</th>--%>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${customers}" var="customer">
                        <tr>
                            <td><c:out value="${customer.id}"/> </td>
                            <td><c:out value="${customer.firstName}"/> </td>
                            <td><c:out value="${customer.lastName}"/> </td>
                            <td><c:out value="${customer.user.email}"/> </td>
                            <td><c:out value="${customer.balance}"/> </td>
                            <td><c:out value="${customer.user.active}"/> </td>
                            <td>
                                <a class="button btn-primary btn-sm" role="button"
                                   href="${pageContext.request.contextPath}/employee/customers/edit/${customer.id}">Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>
