<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Invoice</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<%@include file="../templates/navigation.jsp"%>
<%@include file="../templates/customer-navigation.jsp"%>

    <div class="container">
        <div class="card">
            <div class="card-header font-weight-bold">
                Invoice
                <strong>${order.orderDate}</strong>
                <span class="float-right"> <strong>Status:</strong> ${order.orderStatus}</span>
            </div>
            <div class="card-body">
                <div class="row mb-4">
                    <div class="col-sm-6">
                        <h6 class="mb-3">From:</h6>
                        <div>
                            <strong>Tyrannophone</strong>
                        </div>
                        <div>Somestreet 8</div>
                        <div>Email: info@tyrannophone.com</div>
                        <div>Phone: +70000000000</div>
                    </div>

                    <div class="col-sm-6">
                        <h6 class="mb-3">To:</h6>
                        <div>
                            <strong>${order.customer.firstName} ${order.customer.lastName}</strong>
                        </div>
                        <div>${order.customer.address}</div>
                        <div>Email: ${order.customer.email}</div>
                        <div>Phone: +${order.contract}</div>
                    </div>
                </div>

                <div class="table-responsive-sm">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th class="text-center">#</th>
                            <th>Item</th>
                            <th>Description</th>
                            <th class="text-right">Cost</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${order.invoiceTriples}" var="row" varStatus="counter">
                                <tr class="font-weight-bold">
                                    <td>${counter.count}</td>
                                    <td>${row.val1}</td>
                                    <td>${row.val2}</td>
                                    <td class="text-right">$${row.val3}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="row">
                    <div class="col-lg-4 col-sm-5">

                    </div>

                    <div class="col-lg-4 col-sm-5 ml-auto">
                        <table class="table table-clear">
                            <tbody>
                            <tr>
                                <td class="left">
                                    <strong>Total</strong>
                                </td>
                                <td class="right">
                                    <strong>$${order.price}</strong>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                    </div>

                </div>

            </div>
        </div>
    </div>

</body>
</html>
