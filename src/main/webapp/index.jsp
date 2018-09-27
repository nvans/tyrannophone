<%--<html>--%>
<%--<head>--%>
    <%--<%@include file="WEB-INF/views/templates/header.jsp" %>--%>
    <%--<title>Tyrannophone Home</title>--%>
<%--</head>--%>
<%--<body>--%>
    <%--<div><%@include file="WEB-INF/views/templates/navigation.jsp" %></div>--%>
    <%--<div><%@include file="WEB-INF/views/templates/customer-navigation.jsp"%></div>--%>
    <%--<div><%@include file="WEB-INF/views/templates/employee-navigation.jsp"%></div>--%>

    <%--<c:if test="${param.error}">--%>
        <%--<div class="alert alert-danger alert-dismissible fade show" role="alert">--%>
            <%--<strong>${SPRING_SECURITY_LAST_EXCEPTION.message}</strong>--%>
            <%--<button type="button" class="close" data-dismiss="alert" aria-label="Close">--%>
                <%--<span aria-hidden="true">&times;</span>--%>
            <%--</button>--%>
        <%--</div>--%>
    <%--</c:if>--%>

    <%--<c:forEach items="${plans}" var="plan">--%>
        <%--<div class="card">--%>
            <%--<div class="card-body">--%>
                <%--<h4 class="card-title"></h4>--%>
                <%--<p class="card-text">--%>
                    <%--Some quick example text to build on the card title--%>
                    <%--and make up the bulk of the card's content.--%>
                <%--</p>--%>
                <%--<a href="#!" class="btn btn-primary">Go somewhere</a>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</c:forEach>--%>

<%--</body>--%>
<%--</html>--%>
<jsp:forward page="/home"/>