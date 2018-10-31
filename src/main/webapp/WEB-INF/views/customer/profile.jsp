<html>
<head>
    <%@include file="../templates/header.jsp" %>
    <title>Title</title>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/customer-navigation.jsp" %>

<sec:authorize access="hasRole('CUSTOMER')">
    <div class="container py-4">
        <div class="row">
            <div class="col-md-auto mx-auto">
                <h2 class="mb-0 text-center">Profile</h2><br/>
                <form:form modelAttribute="customer">
                    <form:hidden path="id" id="inputId" cssClass="form-control" readonly="true"/>
                    <%--EMAIL--%>
                    <div class="form-group row">
                        <label for="inputEmail" class="col-sm-4 col-form-label font-weight-bold">Email</label>
                        <div class="col-sm-8">
                            <form:input path="user.email" id="inputEmail" cssClass="form-control"/>
                        </div>
                    </div>

                    <%-- First Name--%>
                    <div class="form-group row">
                        <label for="inputFirstName" class="col-sm-4 col-form-label font-weight-bold">First Name</label>
                        <div class="col-sm-8">
                            <form:input path="firstName" cssClass="form-control" id="inputFirstName"/>
                        </div>
                    </div>

                    <%-- Last Name--%>
                    <div class="form-group row">
                        <label for="inputLastName" class="col-sm-4 col-form-label font-weight-bold">Last Name</label>
                        <div class="col-sm-8">
                            <form:input path="lastName" cssClass="form-control" id="inputLastName"/>
                        </div>
                    </div>

                    <%-- Passport --%>
                    <div class="form-group row">
                        <label for="inputPassport" class="col-sm-4 col-form-label font-weight-bold">Passport</label>
                        <div class="col-sm-8">
                            <form:input path="passport" cssClass="form-control" id="inputPassport"/>
                        </div>
                    </div>

                    <%-- Address --%>
                    <div class="form-group row">
                        <label for="inputAddress" class="col-sm-4 col-form-label font-weight-bold">Address</label>
                        <div class="col-sm-8">
                            <form:input path="address" class="form-control" id="inputAddress"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-lg-12">
                                <%--<input type="reset" class="btn btn-secondary col-sm-2" value="Reset"/>--%>
                                <%--<input type="submit" class="btn btn-success col-sm-2" value="Edit"/>--%>
                            <button type="button" class="btn btn-primary float-right">Edit details</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</sec:authorize>
</body>
</html>
