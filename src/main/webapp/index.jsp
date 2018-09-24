<%--<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<html>
<head>
    <%@include file="WEB-INF/views/templates/header.jsp" %>
    <title>Tyrannophone Home</title>
</head>
<body>
    <div><%@include file="WEB-INF/views/templates/navigation.jsp" %></div>
    <div><%@include file="WEB-INF/views/templates/customer-navigation.jsp"%></div>
    <div><%@include file="WEB-INF/views/templates/employee-navigation.jsp"%></div>

    <c:if test="${param.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Incorrect login or password!</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
</body>
</html>
