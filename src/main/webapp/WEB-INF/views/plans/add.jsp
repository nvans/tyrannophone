<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new plan</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/employee-navigation.jsp"%>

    <sec:authorize access="hasRole('EMPLOYEE')">
    <form:form method="post" action="/plans/add" modelAttribute="plan">
        <table>
            <tr>
                <td>Plan name</td>
                <td><form:input path="planName" /></td>
                <td><form:errors path="planName" cssClass="alert-danger"/> </td>
            </tr>
            <tr>
                <td>Connection price</td>
                <td><form:input path="connectionPrice" /></td>
                <td><form:errors path="connectionPrice" cssClass="alert-danger" /></td>
            </tr>
            <tr>
                <td>Monthly price</td>
                <td><form:input path="monthlyPrice" /></td>
                <td><form:errors path="monthlyPrice" cssClass="alert-danger" /></td>
            </tr>
                <td>Available options</td>
                <td>
                    <form:select path="availableOptions" multiple="true">
                        <form:options items="${options}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </td>
                <td><form:errors path="availableOptions" cssClass="alert-danger" /></td>
            <tr>
                <td>Make active?</td>
                <td><form:checkbox path="connectionAvailable" /></td>
            </tr>
        </table>
        <form:button name="Add">Add plan</form:button>
    </form:form>
    </sec:authorize>
</body>
</html>
