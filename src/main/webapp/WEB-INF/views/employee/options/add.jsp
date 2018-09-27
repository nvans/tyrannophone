<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new option</title>
    <%@include file="../../templates/header.jsp"%>
</head>
<body>
    <%@include file="../../templates/navigation.jsp"%>
    <%@include file="../../templates/employee-navigation.jsp"%>

    <form:form method="post" action="/employee/options/add" modelAttribute="option">
        <table>
            <tr>
                <td>Option name</td>
                <td><form:input path="name" /></td>
                <td><form:errors path="name" cssClass="alert-danger"/> </td>
            </tr>
            <tr>
                <td>Option price</td>
                <td><form:input path="price" /></td>
                <td><form:errors path="price" cssClass="alert-danger"/></td>
            </tr>
            <tr>
                <td>Make active?</td>
                <td><form:checkbox path="connectionAvailable" /></td>
            </tr>
        </table>
        <form:button name="Add">Add option</form:button>
    </form:form>
</body>
</html>
