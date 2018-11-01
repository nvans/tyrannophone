<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Plans</title>
    <%@include file="templates/header.jsp" %>
</head>
<body>
<%@include file="templates/navigation.jsp" %>
<%@include file="templates/employee-navigation.jsp" %>
<%@include file="templates/customer-navigation.jsp" %>


    <div class="container">
        <table class="table table-hover">
            <thead>
                <th>Name</th><th>Connection price</th><th>Monthly price</th><th>Connection available</th>
            </thead>
            <tbody>
            <c:forEach items="${plans}" var="plan">
                <tr>
                    <td class="font-weight-bold" onclick="showPlan(${plan.id})">${plan.planName}</td>
                    <td onclick="showPlan(${plan.id})">${plan.connectionPrice}</td>
                    <td onclick="showPlan(${plan.id})">${plan.monthlyPrice}</td>
                    <td onclick="showPlan(${plan.id})">${plan.connectionAvailable}</td>
                    <sec:authorize access="hasRole('EMPLOYEE')">
                    <td>
                        <a class="btn btn-primary" role="button" href="/plans/${plan.id}">Edit</a>
                    </td>
                    </sec:authorize>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="planModal">
        <div class="modal-dialog">
                <%--<div class="modal-content">--%>
                <%--<div class="modal-header">--%>
                <%--Plan preview--%>
                <%--</div>--%>
            <div class="modal-body">
                <div class="card" style="margin: 3%; width: 400px; background: azure">
                    <img class="card-img" src="${pageContext.request.contextPath}/resources/images/plan-bg.jpg">
                    <div class="card-header card-img-overlay">
                        <h2 id="planName" class="card-title text-light text-center">Plan name</h2>
                    </div>
                    <h5 id="planDescription" class="text-dark font-italic text-left mx-4">Description</h5>
                    <div class="card-body">
                        <h4 id="planPrice" class="text-center">Price$ / per month</h4><br/>
                        <div class="font-weight-bold">Connected options</div>
                        <table id="connectedOptions" class="table"></table>
                        <div class="font-weight-bold">Available options</div>
                        <table id="availableOptions" class="table"></table>
                        <button type="button" class="btn btn-danger float-right" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>


                <%--</div>--%>
        </div>
    </div>

    <script type="text/javascript">
        function showPlan(id) {

            $.ajax({
                url: '${pageContext.request.contextPath}/api/plans/' + id,
                type: 'get',
                success: function (plan) {
                    $("#planName").html(plan.name);
                    $("#planDescription").html(plan.description);
                    $("#planPrice").html(plan.monthlyPrice + '$ / per month');

                    const connectedOptions = plan.options.filter(p => p.connected);
                    const availableOptions = plan.options.filter(p => !p.connected);

                    addOptionsToTable(document.getElementById("connectedOptions"), connectedOptions);
                    addOptionsToTable(document.getElementById("availableOptions"), availableOptions);

                    $("#planModal").modal();
                }
            });

            function addOptionsToTable(table, options) {

                table.innerHTML = '';

                if (options.length === 0) {
                    let row = table.insertRow(0);
                    row.className = 'd-sm-table-row';
                    row.insertCell(0).innerHTML = 'No options';

                    return;
                }

                for (let i = 0; i < options.length; i++) {
                    let row = table.insertRow(i);
                    row.className = 'd-sm-table-row';

                    let nameCell = row.insertCell(0);
                    nameCell.innerHTML = options[i].name;

                    let priceCell = row.insertCell(1);
                    priceCell.innerHTML = options[i].price > 0 ? options[i].price : 'Free';
                }
            }
        }
    </script>
</body>
</html>
