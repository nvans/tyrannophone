<html>
<head>
    <%@include file="../templates/header.jsp" %>
    <title>Title</title>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/employee-navigation.jsp" %>

<sec:authorize access="hasRole('EMPLOYEE')">
    <div class="container py-4">

        <div class="row">

            <div class="col-md-auto mx-auto">

                <h2 class="mb-0 text-center">Customer details</h2><br/>

                <form:form method="post" action="${pageContext.request.contextPath}/customers/edit"
                           modelAttribute="customer">

                    <form:hidden path="customerId"/>

                    <%--EMAIL--%>
                    <div class="form-group row">
                        <label for="inputEmail" class="col-sm-4 col-form-label font-weight-bold">Email</label>
                        <div class="col-sm-8">
                            <form:input path="email" id="inputEmail" cssClass="form-control"/>
                        </div>
                    </div>

                    <%-- First Name--%>
                    <div class="form-group row">
                        <label for="inputFirstName" class="col-sm-4 col-form-label font-weight-bold">First Name</label>
                        <div class="col-sm-8">
                            <form:input path="firstName" cssClass="form-control" id="inputFirstName"/>
                        </div>
                    </div>

                    <%-- Last Name--%>
                    <div class="form-group row">
                        <label for="inputLastName" class="col-sm-4 col-form-label font-weight-bold">Last Name</label>
                        <div class="col-sm-8">
                            <form:input path="lastName" cssClass="form-control" id="inputLastName"/>
                        </div>
                    </div>

                    <%-- Passport --%>
                    <div class="form-group row">
                        <label for="inputPassport" class="col-sm-4 col-form-label font-weight-bold">Passport</label>
                        <div class="col-sm-8">
                            <form:input path="passport" cssClass="form-control" id="inputPassport"/>
                        </div>
                    </div>

                    <%-- Address --%>
                    <div class="form-group row">
                        <label for="inputAddress" class="col-sm-4 col-form-label font-weight-bold">Address</label>
                        <div class="col-sm-8">
                            <form:input path="address" class="form-control" id="inputAddress"/>
                        </div>
                    </div>

                    <%--<div class="form-group row">--%>
                        <%--<div class="col-sm-12 offset-sm-8">--%>
                            <%--<input type="reset" class="btn btn-secondary" value="Reset"/>--%>
                            <%--<input type="submit" class="btn btn-primary" value="Submit"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </form:form>
                <button class="btn btn-danger float-right" data-target="#block-modal" data-toggle="modal">Block account</button>
                <br/>
                <br/>
                <br/>
                <div class="form-group row">
                    <c:if test="${customer.active}">

                        <%-- Modal form block details --%>
                        <div class="modal fade" id="block-modal">
                            <div class="modal-dialog modal-dialog-centered modal-sm">
                                <div class="modal-content">
                                    <!-- header -->
                                    <div class="modal-header">
                                        <h3 class="modal-title">Block details</h3>
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    </div>
                                    <!-- body -->
                                    <div class="modal-header">
                                        <form role="form" method="post" id="block-form"
                                              action="${pageContext.request.contextPath}/customers/block/${customer.customerId}">
                                            <div class="form-group">
                                                <label for="reason">Block reason</label>
                                                <textarea class="form-control" name="reason" id="reason"
                                                          rows="3"></textarea>
                                            </div>
                                        </form>
                                    </div>
                                    <!-- footer -->
                                    <div class="modal-footer">
                                        <button name="submit" type="submit" class="btn btn-danger btn-block"
                                                form="block-form">Block
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                            $('.modal').on('shown.bs.modal', function () {
                                $(this).find('#reason').focus();
                            });
                        </script>
                    </c:if>

                    <c:if test="${!customer.active}">
                        <form role="form" method="post" id="unblock-form"
                              action="${pageContext.request.contextPath}/customers/unblock/${customer.customerId}">
                            <button type="submit" class="btn btn-danger">Unblock</button>
                        </form>
                    </c:if>
                </div>




            <%-- Contracts --%>
        <div class="form-group row">
            <div class="col-sm">
                <h3 class="mb-0 text-center">Contracts</h3><br/>
                <c:forEach items="${customer.contracts}" var="contract">
                <span class="list-group-item text-center" style="font-size: larger">
                    <a class="font-weight-bold" href="${pageContext.request.contextPath}/contracts/${contract}">${contract}</a>
                </span>
                </c:forEach>
                <span class="list-group-item text-center">
                <a class="btn btn-primary" role="button" href="/contracts/add/${customer.customerId}">Add contract</a>
            </span>
            </div>
        </div>
            </div>
        </div>
    </div>
</sec:authorize>
</body>
</html>
