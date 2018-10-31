<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit option</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/employee-navigation.jsp" %>

<sec:authorize access="hasRole('EMPLOYEE')">
    <div class="container py-2">
        <div class="row">
            <div class="col-6">
                <h4 class="mb-0 text-center">Edit option</h4>
                <br/>
                <form:form method="post" action="/options/add" modelAttribute="option">
                    <table class="table table-sm">
                        <tr>
                            <td>Option name</td>
                            <td><form:input path="name" readonly="true"/></td>
                            <td><form:errors path="name" cssClass="alert-danger"/></td>
                        </tr>
                        <tr>
                            <td>Option price</td>
                            <td><form:input type="number" path="price"/></td>
                            <td><form:errors path="price" cssClass="alert-danger"/></td>
                        </tr>
                        <tr>
                            <td>Available to connect</td>
                            <td><form:checkbox path="connectionAvailable"/></td>
                        </tr>
                    </table>
                    <form:button class="btn btn-primary float-right" name="Add">Submit</form:button>
                </form:form>
                <a class="btn btn-sm btn-primary" href="/options/${option.id}/editHierarchy">Edit hierarchy</a>
                <a class="btn btn-sm btn-primary" href="/options/${option.id}/editCompatibility">Edit compatibility</a>
            </div>
        </div>
    </div>
</sec:authorize>
</body>
</html>
