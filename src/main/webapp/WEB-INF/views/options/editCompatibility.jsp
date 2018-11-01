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
<div class="container">
    <div class="row">
        <div class="col-8">
            <form:form method="post" action="${pageContext.request.contextPath}/options/${option.id}/editCompatibility" modelAttribute="option">
                <form:hidden path="id" readonly="true"/>
                <form:hidden path="name" readonly="true"/>
                <form:hidden path="price" readonly="true"/>
                <form:hidden path="connectionAvailable" readonly="true"/>
                <br/>
                <div class="form-group">
                    <h4 class="mb-0 text-center">Incompatible options for option "${option.name}"</h4>
                    <br/>
                    <table class="table table-responsive-md">
                        <thead class="bg-light text-center">
                        <tr>
                            <th>Name</th>
                            <th>Incompatibility</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${options}" var="option">
                            <tr>
                                <td class="font-weight-normal">${option.name}</td>
                                <td class="text-center">
                                    <form:checkbox path="incompatibleOptions" value="${option}"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                    <%--</table>--%>
                <div class="form-group float-right">
                    <input type="reset" value="Reset" class="btn btn-danger"/>
                    <form:button name="Save" class="btn btn-primary">Save</form:button>
                </div>
            </div>
        </form:form>

    </div>
    <a class="btn btn-danger" href=".">Cancel</a>
</div>
</sec:authorize>
</body>
</html>
