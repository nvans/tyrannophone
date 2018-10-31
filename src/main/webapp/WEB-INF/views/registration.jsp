<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="templates/header.jsp" %>
    <title>Registration</title>
</head>
<body>
<div>
    <%@include file="templates/navigation.jsp" %>
</div>

<div class="container py-3">
    <div class="row">
        <div class="col-md-6 mx-auto">
            <div class="card card-body">
                <h3 class="text-center mb-4">Sign-up</h3>
                <form:form method="post" modelAttribute="registration"
                           action="${pageContext.request.contextPath}/registration">
                    <div class="form-group">
                        <%--<div>Email:</div>--%>
                        <form:input id="email" path="email" cssClass="form-control" placeholder="E-Mail address"/>
                        <div><form:errors path="email" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:input id="email" path="confirmEmail" cssClass="form-control" placeholder="Confirm E-mail address"/>
                        <div><form:errors path="confirmEmail" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:input path="firstName" cssClass="form-control" placeholder="First name"/>
                        <div><form:errors path="firstName" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:input path="lastName" cssClass="form-control" placeholder="Last name"/>
                        <div><form:errors path="lastName" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:input path="passport" cssClass="form-control" placeholder="Passport id"/>
                        <div><form:errors path="passport" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:input path="address" cssClass="form-control" placeholder="Address"/>
                        <div><form:errors path="address" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:password path="password" cssClass="form-control" placeholder="Password"/>
                        <div><form:errors path="password" cssClass="text-danger"/></div>
                    </div>
                    <div class="form-group">
                        <form:password path="confirmPassword" cssClass="form-control" placeholder="Confirm password"/>
                        <div><form:errors path="confirmPassword" cssClass="text-danger"/></div>
                    </div>
                    <br/>
                    <form:button class="btn btn-md btn-primary btn-block">Register</form:button>
                </form:form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
