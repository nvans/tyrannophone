<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Options</title>
    <%@include file="templates/header.jsp"%>
</head>
<body>
<%@include file="templates/navigation.jsp"%>
<%@include file="templates/employee-navigation.jsp"%>
<sec:authorize access="hasRole('EMPLOYEE')">
    <div class="container">
        <h2 class="mb-0 text-center">Options</h2><br/>
    <table class="table table-sm">
        <thead>
        <th>Name</th>
        <th>Price</th>
        <th>Availability</th>
        </thead>
        <tbody>
        <c:forEach items="${options}" var="option" varStatus="counter">
            <tr <c:if test="${option.parentOption == null}">class="bg-light"</c:if>>
                <td <c:if test="${option.parentOption == null}">class="font-weight-bold"</c:if>>${option.name}</td>
                <td>${option.price}</td>
                <td>
                    <c:choose>
                        <c:when test="${option.connectionAvailable}">
                            <span class="badge badge-success">Available</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">Unavailable</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/options/${option.id}">Edit</a>
                </td>
                <%--<td>--%>
                    <%--<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/options/${option.id}/editHierarchy">Dependencies</a>--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/options/${option.id}/editCompatibility">Compatibility</a>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</sec:authorize>
</body>
</html>
