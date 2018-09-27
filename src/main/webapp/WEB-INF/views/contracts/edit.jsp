<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Edit Contract</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<%@include file="../templates/navigation.jsp"%>
<%@include file="../templates/customer-navigation.jsp"%>
<%@include file="../templates/employee-navigation.jsp"%>

    <c:if test="${error != null}">
        <div class="alert-danger">${error}</div>
    </c:if>

    <c:if test="${!contract.active}">
        <div class="jumbotron">
            <h1 class="display-3">Contract is blocked!</h1>
            <p class="lead">
                The contract '${contract.contractNumber}' was blocked on ${contract.blockDetails.blockTs.toLocalDate()}
                <c:if test="${contract.blockDetails.blockedBefore != null}"> before ${contract.blockDetails.blockedBefore.toLocalDate()}.</c:if>
            </p>
            <p class="lead">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <c:choose>
                        <c:when test="${contract.blockDetails.blockedByUser.id != contract.customer.id}">
                            <a class="btn btn-primary btn-lg" href="#" role="button">Contact us</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/contracts/unblock/${contract.contractNumber}" role="button">Unblock</a>
                        </c:otherwise>
                    </c:choose>
                </sec:authorize>
                <sec:authorize access="hasRole('EMPLOYEE')">
                    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/contracts/unblock/${contract.contractNumber}" role="button">Unblock</a>
                </sec:authorize>
            </p>
        </div>
    </c:if>

    <c:if test="${contract.active}">
    <div class="container py-2">
        <form:form modelAttribute="contract" method="post" action="/contracts/edit">

            <div class="form-group row">
                <label for="number" class="col-sm-2 col-form-label">Number</label>
                <div class="col-sm-10">
                    <form:input path="contractNumber" id="number" cssClass="form-control col-sm-4" readonly="true"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="planChoose" class="col-sm-2 col-form-label">Plan</label>
                <div class="col-sm-10">
                    <form:select cssClass="custom-select col-sm-4" id="planChoose" path="plan">
                        <form:options items="${plans}"/>
                    </form:select>
                </div>
            </div>

            <form:hidden path="active"/>

            <div class="form-group row">
                <label for="options" class="col-sm-2 col-form-label">Options</label>
                <div class="col-sm-10">
                    <form:select cssClass="custom-select col-sm-4" name="options" id="options" path="options">
                        <form:option value="">-Without options-</form:option>
                        <form:options items="${currentPlanOpts}"/>
                    </form:select>
                </div>
            </div>

            <form:button name="Submit">Submit</form:button>
        </form:form>
    </div>
    <c:if test="${contract.active}">
        <a class = "btn btn-primary btn-lg" data-toggle="modal" data-target="#block-modal" role="button">Block</a>
    </c:if>
    <c:if test="${!contract.active}">
        <a class="btn btn-primary" role="button" href="/contracts/unblock/${contract.contractNumber}">Unblock</a>
    </c:if>

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
                    <form role="form" method="post" id="block-form" action="${pageContext.request.contextPath}/contracts/block">
                        <div class="form-group">
                            <input type="hidden" name="contractNumber" value="${contract.contractNumber}">
                            <label for="reason">Block reason</label>
                            <textarea class="form-control" name="reason" id="reason" rows="3"></textarea>
                        </div>
                    </form>
                </div>
                <!-- footer -->
                <div class="modal-footer">
                    <button name="submit" type="submit" class="btn btn-primary btn-block" form="block-form">Block</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        $('.modal').on('shown.bs.modal', function() {
            $(this).find('#reason').focus();
        });

        $("#planChoose").change(function () {

            console.log($(this).val());

            $.ajax({
                url : '${pageContext.request.contextPath}/contracts/super',
                type: 'post',
                data: {
                    'planName': $(this).val()
                },
                success: function (result) {
                    result = result.replace('[', '').replace(']', '');
                    result = result.split(',');

                    var options = '<option value="">Without options</option>';
                    for(var i = 0; i < result.length; i++) {
                        console.log(result[i]);
                        options += '<option>'+result[i]+'</option>';
                    }
                    $('#options').html(options);
                }
            });
        });
    </script>
    </c:if>
</body>
</html>


