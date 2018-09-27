<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit plan</title>
    <%@include file="../templates/header.jsp"%>
    
    <script>
        document.onload(function () {

        })
    </script>
</head>
<body>
<%@include file="../templates/navigation.jsp"%>
<%@include file="../templates/employee-navigation.jsp"%>

<sec:authorize access="hasRole('EMPLOYEE')">
<form:form method="post" action="/plans/edit" modelAttribute="plan">
    <table>
        <tr>
            <td>Plan id</td>
            <td><form:input path="id" readonly="true"/></td>
        </tr>
        <tr>
            <td>Plan name</td>
            <td><form:input path="planName" readonly="true"/></td>
        </tr>
        <tr>
            <td>Connection price</td>
            <td><form:input path="connectionPrice" readonly="true"/></td>
        </tr>
        <tr>
            <td>Monthly price</td>
            <td><form:input path="monthlyPrice" readonly="true"/></td>
        </tr>
        <tr>
            <td>Available:</td>
            <td><form:checkbox path="connectionAvailable" /></td>
        </tr>
        <tr>
            <td><br/><br/></td>
        </tr>
        <tr>
            <td>Available options:</td>
            <td>
                <form:select path="availableOptions" multiple="true">
                    <form:options items="${options}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>

    </table>
    <form:button name="Modify">Modify plan</form:button>
</form:form>

<a id="deletePlan" class="btn btn-danger" href="/plans/delete/${plan.id}">Delete</a>
</sec:authorize>
</body>
</html>
