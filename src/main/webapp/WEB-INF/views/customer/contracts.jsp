<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@page isELIgnored="false" %>--%>

<html>
<head>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<%@include file="../templates/navigation.jsp"%>
<%@include file="../templates/customer-navigation.jsp"%>


    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th scope="col">Number</th>
            <th scope="col">Plan</th>
            <th scope="col">Cost per month</th>
            <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${contracts}" var="contract">
            <tr>
                <td><c:out value="${contract.contractNumber}"/> </td>
                <td><c:out value="${contract.plan.planName}"/> </td>
                <td><c:out value="${contract.plan.monthlyPrice}"/> </td>
                <td>
                        <span class="badge  ${contract.active ? 'badge-success' : 'badge-danger'}">
                                ${contract.active ? 'Active' : 'Blocked'}
                        </span>
                </td>
                <td>
                    <<a href="${pageContext.request.contextPath}/customer/contracts/${contract.contractNumber}">Edit</a>
                </td>
            </tr>

        </c:forEach>
        </tbody>


    </table>
</body>
</html>
