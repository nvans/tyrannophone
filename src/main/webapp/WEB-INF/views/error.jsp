<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Oops</title>
    <%@include file="templates/header.jsp"%>
</head>
<body>
<%@include file="templates/navigation.jsp"%>
<%@include file="templates/employee-navigation.jsp"%>

<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <strong>${message}</strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

</body>
</html>
