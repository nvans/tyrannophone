<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Options</title>
    <%@include file="../../templates/header.jsp"%>
</head>
<body>
<%@include file="../../templates/navigation.jsp"%>
<%@include file="../../templates/employee-navigation.jsp"%>

<table class="table table-striped table-hover col-6">
    <thead>
    <th>Id</th>
    <th>Name</th>
    <th>Price</th>
    <th>Connection available</th>
    </thead>
    <tbody>
    <c:forEach items="${options}" var="option">
        <tr>
            <td>${option.id}</td>
            <td>${option.name}</td>
            <td>${option.price}</td>
            <td>${option.connectionAvailable}</td>
            <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/employee/options/editHierarchy/${option.id}">Edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
