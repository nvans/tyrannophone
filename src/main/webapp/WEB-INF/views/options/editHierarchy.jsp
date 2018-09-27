<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit option</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/employee-navigation.jsp"%>

<sec:authorize access="hasRole('EMPLOYEE')">
    <form:form method="post" action="/options/editHierarchy" modelAttribute="option">
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
                <td>Available:</td>
                <td><form:checkbox path="connectionAvailable" /></td>
            </tr>
            <tr>
                <td>Parent option:</td>
                <td>
                    <c:set var="parentName" value="${option.parentOption == null ? 'NONE' : option.parentOption.name}"/>
                    <form:select path="parentOption">

                        <form:option value="${null}">NONE</form:option>
                        <form:option value="${option.parentOption}"><c:out value="${parentName}"/></form:option>

                        <c:forEach items="${options}" var="opt1">
                            <form:option value="${opt1.id}">${opt1.name}</form:option>
                        </c:forEach>

                    </form:select>
                </td>
            </tr>
            <tr>
                <td><br/><br/></td>
            </tr>
            <tr>
                <td>Children options:</td>
                <td>
                    <form:select path="childOptions" multiple="true">
                        <form:option value="">NONE</form:option>
                        <form:options items="${options}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </td>
            </tr>
            
        </table>
        <form:button name="Modify">Modify option</form:button>
    </form:form>
</sec:authorize>
</body>
</html>
