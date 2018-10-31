<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Not found</title>
    <%@include file="templates/header.jsp"%>
</head>
<body>
<%@include file="templates/navigation.jsp" %>
<%@include file="templates/customer-navigation.jsp" %>
<%@include file="templates/employee-navigation.jsp" %>

<div class="jumbotron">
    <h1 class="display-3">Not found!</h1>
    <br/><br/><br/>
    <p class="lead">
        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/" role="button">Go home</a>
    </p>
</div>

</body>
</html>
