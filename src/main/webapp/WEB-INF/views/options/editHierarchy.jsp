<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit option</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/employee-navigation.jsp" %>

<sec:authorize access="hasRole('EMPLOYEE')">

    <c:if test="${error != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>${error}</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <div class="container">
        <div class="row">
            <div class="col-8 col-md-auto mx-auto">
                <form:form method="post" action="/options/${option.id}/editHierarchy" modelAttribute="option">
                    <h2 class="mb-0 text-center">Dependencies for option</h2><br/>
                    <h4 class="mb-0 text-center">"${option}"</h4>
                    <br/><br/>
                        <form:hidden path="id" readonly="true"/>
                        <form:hidden path="name" readonly="true"/>
                        <form:hidden path="price" readonly="true"/>
                        <form:hidden path="connectionAvailable"/>
                    <div class="form-group">
                        <label class="font-weight-bold">Parent option</label>
                        <c:set var="parentName" value="${option.parentOption == null ? 'NONE' : option.parentOption.name}"/>
                        <form:select cssClass="form-control font-weight-bold" path="parentOption">
                            <form:option value="${null}">NONE</form:option>
                            <form:option value="${option.parentOption}">
                                <c:out value="${parentName}"/>
                            </form:option>
                            <c:forEach items="${options}" var="opt">
                                <form:option value="${opt.id}">${opt.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <br/>
                    <h4 class="mb-0 text-center">Children options:</h4>
                    <br />
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr><th>Name</th><th>Child</th></tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${options}" var="option" varStatus="counter">
                                <tr>
                                    <td class="font-weight-bold">${option.name}</td>
                                    <td><form:checkbox path="childOptions" value="${option}"/></td>
                                </tr>
                            </c:forEach>
                                <%--<form:checkboxes path="childOptions" items="${options}" itemValue="id" itemLabel="name"--%>
                                                 <%--cssClass="check-box" delimiter="<br/>"/>--%>
                        </tbody>
                    </table>
                    <form:button class="btn btn-primary float-right" name="Modify">Save</form:button>
                    <input type="reset" class="btn btn-danger" value="Reset"/>
                </form:form>
                <a class="btn btn-danger" href=".">Cancel</a>
            </div>
        </div>
    </div>
</sec:authorize>
</body>
</html>
