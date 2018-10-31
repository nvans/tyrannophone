<%--
  Created by IntelliJ IDEA.
  User: withb
  Date: 27.10.2018
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
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
            <div class="card-header">
                Invoice
                <strong>${order.orderDate}</strong>
                <span class="float-right"> <strong>Status:</strong> Completed</span>
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
                            <strong>${customer.firstName} ${customer.lastName}</strong>
                        </div>
                        <div>${customer.address}</div>
                        <div>Email: ${customer.user.email}</div>
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
                        <tr>
                            <td class="text-center">1</td>
                            <td class="text-left font-weight-bold">Plan</td>
                            <td class="text-left">"${order.plan.planName}"</td>
                            <td class="text-right">$${order.plan.connectionPrice}</td>
                        </tr>
                        <c:forEach items="${order.options}" var="option" varStatus="i">
                        <tr>
                            <td class="text-center">${i.count + 1}</td>
                            <td class="text-left font-weight-bold">Option</td>
                            <td class="text-left">${option.name}</td>
                            <td class="text-right">$${option.price}</td>
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
