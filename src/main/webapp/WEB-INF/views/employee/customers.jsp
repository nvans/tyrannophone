<html>
<head>
    <title>Customers</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/employee-navigation.jsp"%>

    <div class="row">
        <div class="col-3"></div>
        <div class="col-6">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Email</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Passport</th>
                    <th scope="col">Address</th>
                    <%--<th scope="col">Actions</th>--%>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${customers}" var="customer">
                        <tr>
                            <td><c:out value="${customer.id}"/> </td>
                            <td><c:out value="${customer.firstName}"/> </td>
                            <td><c:out value="${customer.lastName}"/> </td>
                            <td><c:out value="${customer.email}"/> </td>
                            <td><c:out value="${customer.balance}"/> </td>
                            <td><c:out value="${customer.active}"/> </td>
                            <td>
                            <td>
                                <a class="button btn-primary btn-sm" role="button"
                                   href="${pageContext.request.contextPath}/employee/customers/edit/${customer.id}">Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-3"></div>
    </div>

</body>
</html>
