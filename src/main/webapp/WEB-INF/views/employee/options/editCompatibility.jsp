<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit option</title>
    <%@include file="../../templates/header.jsp"%>
</head>
<body>
<%@include file="../../templates/navigation.jsp"%>
<%@include file="../../templates/employee-navigation.jsp"%>

<form:form method="post" action="/employee/options/editCompatibility" modelAttribute="option">
    <table>
        <tr>
            <td>Option id</td>
            <td><form:input path="id" readonly="true"/></td>
        </tr>
        <tr>
            <td>Option name</td>
            <td><form:input path="name" readonly="true"/></td>
        </tr>
        <tr>
            <td>Option price</td>
            <td><form:input path="price" readonly="true"/></td>
        </tr>
        <tr>
            <td>Available</td>
            <td><form:input path="connectionAvailable" readonly="true"/></td>
        </tr>
        <tr>
            <td><br/><br/></td>
        </tr>
        <tr>
            <td>Incompatible options:</td>
            <td>
                <form:select path="incompatibleOptions" multiple="true">
                    <form:option value="">NONE</form:option>
                    <form:options items="${options}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>

    </table>
    <form:button name="Save">Save option</form:button>
</form:form>

</body>
</html>
