<html>
<head>
    <%@include file="../templates/header.jsp"%>
    <title>Title</title>
</head>
<body>
<%@include file="../templates/navigation.jsp"%>
<%@include file="../templates/customer-navigation.jsp"%>

<sec:authorize access="hasRole('CUSTOMER')">
    <div class="container py-2">

        <form:form method="post" action="${pageContext.request.contextPath}/customers/edit" modelAttribute="customer">
            <%-- ID --%>
            <div class="form-group row">
                <label for="inputId" class="col-sm-2 col-form-label">Id</label>
                <div class="col-sm-10">
                    <form:input path="id" id="inputId" cssClass="form-control" readonly="true"/>
                </div>
            </div>

            <%--EMAIL--%>
            <div class="form-group row">
                <label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-10">
                    <form:input path="user.email" id="inputEmail" cssClass="form-control" />
                </div>
            </div>

            <%-- First Name--%>
            <div class="form-group row">
                <label for="inputFirstName" class="col-sm-2 col-form-label">First Name</label>
                <div class="col-sm-10">
                    <form:input path="firstName" cssClass="form-control" id="inputFirstName"/>
                </div>
            </div>

            <%-- Last Name--%>
            <div class="form-group row">
                <label for="inputLastName" class="col-sm-2 col-form-label">Last Name</label>
                <div class="col-sm-10">
                    <form:input path="lastName"  cssClass="form-control" id="inputLastName" />
                </div>
            </div>

            <%-- Passport --%>
            <div class="form-group row">
                <label for="inputPassport" class="col-sm-2 col-form-label">Passport</label>
                <div class="col-sm-10">
                    <form:input path="passport" cssClass="form-control" id="inputPassport"/>
                </div>
            </div>

            <%-- Address --%>
            <div class="form-group row">
                <label for="inputAddress" class="col-sm-2 col-form-label">Address</label>
                <div class="col-sm-10">
                    <form:input path="address" class="form-control" id="inputAddress"/>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-10 offset-sm-8">
                    <input type="reset" class="btn btn-secondary col-sm-2"/>
                    <input type="submit" class="btn btn-success col-sm-2"/>
                </div>
            </div>
        </form:form>

        <%-- Contracts --%>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Contracts</label>
            <div class="col-sm-10">
                <c:forEach items="${customer.contracts}" var="contract">
                <span class="list-group-item">
                    <a href="${pageContext.request.contextPath}/contracts/${contract.contractNumber}">
                            ${contract.contractNumber}
                    </a>
                </span>
                </c:forEach>
            </div>
        </div>
    </div>
</sec:authorize>
</body>
</html>
