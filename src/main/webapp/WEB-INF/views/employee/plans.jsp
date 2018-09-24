<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Plans</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/employee-navigation.jsp"%>

    <table class="table table-striped table-hover col-6">
        <thead>
            <th>Id</th>
            <th>Name</th>
            <th>Connection price</th>
            <th>Monthly price</th>
            <th>Connection available</th>
        </thead>
        <tbody>
        <c:forEach items="${plans}" var="plan">
            <tr>
                <td>${plan.id}</td>
                <td>${plan.planName}</td>
                <td>${plan.connectionPrice}</td>
                <td>${plan.monthlyPrice}</td>
                <td>${plan.connectionAvailable}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
