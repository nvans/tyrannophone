<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create new option</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/employee-navigation.jsp" %>

<sec:authorize access="hasRole('EMPLOYEE')">
    <div class="container py-4">
        <div class="row">
            <div class="col-md-auto mx-auto">
                <h4 class="mb-0 text-center">Create option</h4>
                <br/>
                <form:form method="post" action="/options/add" modelAttribute="option">
                    <table class="table table-sm">
                        <div class="form-group col-6">
                        <tr>
                            <td>Option name</td>
                            <td><form:input path="name"/></td>
                            <td><form:errors path="name" cssClass="alert-danger"/></td>
                        </tr>
                        </div>
                        <tr>
                            <td>Option price</td>
                            <td><form:input type="number" path="price"/></td>
                            <td><form:errors path="price" cssClass="alert-danger"/></td>
                        </tr>
                        <tr>
                            <td>Make active?</td>
                            <td><form:checkbox path="connectionAvailable"/></td>
                        </tr>
                    </table>
                    <form:button class="btn btn-primary float-right" name="Add">Add option</form:button>
                </form:form>
            </div>
        </div>
    </div>
</sec:authorize>
</body>
</html>
