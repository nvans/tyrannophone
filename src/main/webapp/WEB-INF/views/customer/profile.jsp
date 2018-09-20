<html>
<head>
    <%@include file="../templates/header.jsp"%>
    <title>Title</title>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/customer-navigation.jsp"%>

    <table class="table table-striped table-hover">
        <tbody>
            <c:forEach items="${details}" var="pair">
                <tr>
                    <td><c:out value="${pair.key}"/></td>
                    <td><c:out value="${pair.value}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
