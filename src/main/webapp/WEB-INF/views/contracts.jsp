<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@page isELIgnored="false" %>--%>

<html>
<head>
    <%@include file="templates/header.jsp"%>
</head>
<body>
    <%@include file="templates/navigation.jsp"%>
    <%@include file="templates/customer-navigation.jsp"%>
    <table class="table table-striped table-hover">
        <thead>
            <tr>
                <th scope="col">Number</th>
                <th scope="col">Plan</th>
                <th scope="col">Cost per month</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${contracts}" var="contract">
            <tr>
                <td><c:out value="${contract.contractNumber}"/> </td>
                <td><c:out value="${contract.plan.planName}"/> </td>
                <td><c:out value="${contract.plan.monthlyPrice}"/> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
