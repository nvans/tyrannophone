<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
    <%@include file="../../templates/header.jsp"%>
</head>
<body>
    <%@include file="../../templates/navigation.jsp"%>
    <%@include file="../../templates/customer-navigation.jsp"%>


    <c:if test="${!contract.active}">
        <div class="jumbotron">
            <h1 class="display-3">Contract is blocked!</h1>
            <p class="lead">
                Your contract was blocked on ${contract.blockDetails.blockTs.toLocalDate()}
                <c:if test="${contract.blockDetails.blockedBefore != null}"> before ${contract.blockDetails.blockedBefore.toLocalDate()}.</c:if>
            </p>
            <p class="lead">
            <c:choose>
                <c:when test="${contract.readOnly}">
                    <a class="btn btn-primary btn-lg" href="#" role="button">Contact us</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/customer/contracts/unblock/${contract.contractNumber}" role="button">Unblock</a>
                </c:otherwise>
            </c:choose>
            </p>
        </div>
    </c:if>

    <c:if test="${contract.active}">
        <form>
            <label for="contract">Contract</label>
            +<input type="text" id="contract" value="${contract.contractNumber}" readonly/><br/>
            <label for="plan">Plan</label>
            <input type="text" id="plan" value="${contract.plan}"><br/>
        </form>


        <%--<a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/customer/contracts/block/${contract.contractNumber}" role="button">Block</a>--%>
        <a class = "btn btn-primary btn-lg" data-toggle="modal" data-target="#block-modal" role="button">Block</a>

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
                        <form role="form" method="post" id="block-form" action="${pageContext.request.contextPath}/customer/contracts/block">
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
        </script>
    </c:if>
</body>
</html>

