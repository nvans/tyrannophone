<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
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
                The contract '${contract.contractNumber}' was blocked!
            </p>
            <p class="lead">
                <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/contracts/unblock/${contract.contractNumber}" role="button">Activate</a>
            </p>
        </div>
    </c:if>

    <c:if test="${contract.active}">
        <div class="container py-4">
            <div class="row">
                <div class="col-md-auto mx-auto">
            <h2 class="mb-0 text-center">Contract</h2><br/>
            <form:form modelAttribute="contract" method="post" action="/contracts/edit">
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label font-weight-bold">Owner</label>
                    <div class="col-sm-8">
                        <label class="form-control font-weight-bold">
                                ${customer.firstName} ${customer.lastName}
                        </label>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="number" class="col-sm-4 col-form-label font-weight-bold">Number</label>
                    <div class="col-sm-8">
                        <label class="form-control font-weight-bold">${contract.contractNumber}</label>
                        <form:hidden path="contractNumber" id="number" cssClass="form-control font-weight-bold" readonly="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="planChoose" class="col-form-label col-sm-4 font-weight-bold">Plan</label>
                    <div class="col-sm-8">
                        <form:select cssClass="form-control" id="planChoose" path="plan">
                            <form:options items="${plans}"/>
                        </form:select>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="planPrice" class="col-sm-6 col-form-label font-weight-bold">Connection price</label>
                    <div class="col-sm-6">
                        <label class="form-control font-weight-bold" id="planPrice">${contract.plan.connectionPrice}</label>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="monthlyPrice" class="col-sm-6 col-form-label font-weight-bold">Monthly price</label>
                    <div class="col-sm-6">
                        <label class="form-control font-weight-bold" id="monthlyPrice">${contract.price}</label>
                    </div>
                </div>

                <form:hidden path="active"/>
                <div class="form-group">
                <h4 class="mb-0 text-center">Options</h4>
                </div>
                <div class="form-group row">
                    <div class="col-sm-auto">
                        <table id="options" class="table table-striped align-content-center">
                            <thead>
                                <th></th>
                                <th>Name</th>
                                <th>Connect</th>
                                <th>Price</th>
                            </thead>
                            <tbody>
                                <c:forEach items="${currentPlanOpts}" var="option">
                                    <c:set var="isOptRequired" value="${option.connected}"/>
                                    <tr>
                                        <td>
                                            <c:if test="${isOptRequired}">
                                                <img src="${pageContext.request.contextPath}/resources/images/lock.png" width="15px" height="15px"/>
                                            </c:if>
                                        </td>
                                        <td>${option.name}</td>
                                        <td><form:checkbox path="options" value="${option.name}" onclick="${isOptRequired ? 'return false;' : ''}"/></td>
                                        <td>${option.price}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <form:button class="btn btn-primary float-right" name="Submit">Submit</form:button>
            </form:form>
                </div>
            </div>
            <c:if test="${contract.active}">
                <button class="btn btn-danger" data-toggle="modal" data-target="#block-modal">Suspend contract</button>
            </c:if>
            <c:if test="${!contract.active}">
                <button class="btn btn-danger" href="/contracts/unblock/${contract.contractNumber}">Activate</button>
            </c:if>
        </div>


        <%-- Modal form block details --%>
        <div class="modal fade" id="block-modal">
            <div class="modal-dialog modal-dialog-centered modal-sm">
                <div class="modal-content">
                    <!-- header -->
                    <div class="modal-header">
                        <h3 class="modal-title">Details</h3>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <!-- body -->
                    <div class="modal-header">
                        <form role="form" method="post" id="block-form" action="${pageContext.request.contextPath}/contracts/block">
                            <div class="form-group">
                                <input type="hidden" name="contractNumber" value="${contract.contractNumber}">
                                <label for="reason">Suspense reason</label>
                                <textarea class="form-control" name="reason" id="reason" rows="3"></textarea>
                            </div>
                        </form>
                    </div>
                    <!-- footer -->
                    <div class="modal-footer">
                        <button name="submit" type="submit" class="btn btn-danger btn-block" form="block-form">Suspend</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $('.modal').on('shown.bs.modal', function() {
                $(this).find('#reason').focus();
            });
        </script>

        <script>
            let plan = [];
            let availableOptions = [];

            $.ajax({
               url : '${pageContext.request.contextPath}/contracts/super',
               type: 'get',
               data: {
                   'planName': '${contract.plan.planName}'
               },
                success: function (response) {
                    plan = response;
                    availableOptions = plan.availableOptions;
                }
            });

            $("#planChoose").change(function () {

                $("#planChoose option").attr('selected', false);
                $("#planChoose option:selected").attr('selected', true);

                $.ajax({
                    url : '${pageContext.request.contextPath}/contracts/super',
                    type: 'get',
                    data: {
                        'planName': $(this).val()
                    },
                    success: function (response) {
                        plan = response;
                        availableOptions = plan.availableOptions;

                        console.log(plan);

                        let optionsTable = document.getElementById('options');
                        optionsTable.innerHTML = '';

                        if (availableOptions.length === 0) {
                            optionsTable.insertRow().insertCell(0).innerHTML = "No available options for this plan";
                        }
                        else {
                            console.log(availableOptions);

                            let header = optionsTable.createTHead();
                            let headerRow = header.insertRow();
                            headerRow.className = 'font-weight-bold';
                            headerRow.insertCell(0);
                            headerRow.insertCell(1).innerHTML = "Name";
                            headerRow.insertCell(2).innerHTML = "Connected";
                            headerRow.insertCell(3).innerHTML = "Price";

                            let tbody = optionsTable.createTBody();

                            for (let i = 0; i < availableOptions.length; i++) {

                                let checkBoxId = 'options' + (i + 1);
                                let isMandatoryOption = availableOptions[i].connected;// ? 'checked onclick="return false;"' : '';
                                let locked = availableOptions[i].connected? '<img src="/resources/images/lock.png" width="15px" height="15px"/>' : '';

                                let cb = document.createElement('INPUT');
                                cb.setAttribute('type', 'checkbox');
                                cb.id = checkBoxId;
                                cb.name = 'options';
                                cb.value = availableOptions[i].name;
                                cb.checked = isMandatoryOption;
                                if (isMandatoryOption) cb.setAttribute('checked', 'checked');
                                cb.onclick = isMandatoryOption ? () => false : null;

                                let row = tbody.insertRow();
                                row.insertCell(0).innerHTML = locked;
                                let c2 = row.insertCell(1); c2.innerHTML = availableOptions[i].name;
                                let c3 = row.insertCell(2); c3.appendChild(cb);
                                let c4 = row.insertCell(3); c4.innerHTML = availableOptions[i].price;
                            }
                        }
                        $("#planPrice").html(plan.connectionPrice);
                        $("#monthlyPrice").html(plan.monthlyPrice);
                    }
                });
            });

            $("#options").on('change', ':checkbox', function() {
                console.log(this.value);
                console.log(availableOptions);
                let incompatibleOptions = availableOptions.find(o => o.name === this.value).incompatibleOptionsNames;

                console.log(this.checked);

                const allBoxes = $(":checkbox");

                // deselect and disable incompatible options
                allBoxes.filter(function() {
                    return incompatibleOptions.includes(this.value);
                }).prop('disabled', this.checked)
                  .prop('checked', false);

                const currentBox = allBoxes.filter(cb => allBoxes[cb].value === this.value)[0];

                // select parent if child selected
                const parentToCheck = availableOptions.filter(opt => opt.name === this.value)[0].parentOption;
                allBoxes.filter(cb => allBoxes[cb].value === parentToCheck).prop('checked', true);

                const childrenToDeselect = availableOptions.filter(opt => opt.parentOption === this.value).map(opt => opt.name);
                if (currentBox.checked === false) {
                    allBoxes.filter(cb => childrenToDeselect.includes(allBoxes[cb].value)).prop('checked', false).prop('disabled', true);
                }
                else {
                    allBoxes.filter(cb => childrenToDeselect.includes(allBoxes[cb].value)).prop('disabled', false);
                }

                // calculate total price
                const checkedBoxes = $(":checkbox:checked");
                const checkedOptionsNames = $.map(checkedBoxes, cb => cb.value);
                const totalPrice =
                    availableOptions.filter(opt => checkedOptionsNames.includes(opt.name))
                        .map(opt => opt.price)
                        .reduce((a, b) => a + b, plan.connectionPrice);

                $("#monthlyPrice").html(totalPrice);
            });
        </script>
    </c:if>
</body>
</html>


