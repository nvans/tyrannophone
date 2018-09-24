<html>
<head>
    <%@include file="../../templates/header.jsp"%>
    <title>Title</title>
</head>
<body>
<%@include file="../../templates/navigation.jsp"%>
<%@include file="../../templates/employee-navigation.jsp"%>
<div class="container py-2">
    <form>
        <%-- ID --%>
        <div class="form-group row">
            <label for="inputId" class="col-sm-2 col-form-label">Id</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputId" value="${customer.id}" readonly>
            </div>
        </div>

        <%--EMAIL--%>
        <div class="form-group row">
            <label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputEmail" value="${customer.email}">
            </div>
        </div>

        <%-- First Name--%>
        <div class="form-group row">
            <label for="inputFirstName" class="col-sm-2 col-form-label">First Name</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputFirstName" value="${customer.firstName}">
            </div>
        </div>

        <%-- Last Name--%>
        <div class="form-group row">
            <label for="inputLastName" class="col-sm-2 col-form-label">Last Name</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputLastName" value="${customer.lastName}">
            </div>
        </div>

        <%-- Passport --%>
        <div class="form-group row">
            <label for="inputPassport" class="col-sm-2 col-form-label">Passport</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputPassport" value="${customer.passport}">
            </div>
        </div>

        <%-- Address --%>
        <div class="form-group row">
            <label for="inputAddress" class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="inputAddress" value="${customer.address}">
            </div>
        </div>

        <%-- Contracts --%>
        <c:forEach items="${customer.contracts}" var="contract">
            <%--<div class="form-group row">--%>
                <%--<label class="col-sm-2 col-form-label">Last Name</label>--%>
                <%--<div class="col-sm-10">--%>
                    <%--<input type="email" class="form-control" value="${contract.contractNumber}">--%>
                <%--</div>--%>
            <%--</div>--%>
            <label class="col-sm-2 col-form-label">Contracts</label>
            <ul>
                <li>${contract.contractNumber}</li>
            </ul>
        </c:forEach>

            <div class="form-group row">
            <div class="col-sm-10 offset-sm-2">
                <button type="submit" class="btn btn-primary">Sign in</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
