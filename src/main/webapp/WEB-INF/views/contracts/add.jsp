<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add contract</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/employee-navigation.jsp" %>

<sec:authorize access="hasRole('ROLE_EMPLOYEE')">
    <div class="container py-2">
            <form:form method="post" action="${pageContext.request.contextPath}/contracts/add/${customer.customerId}"
                       modelAttribute="contract">
                <div class="form-group">
                    <h4 class="mb-0 text-center">Customer</h4>
                    <br/>
                    <table class="table">
                        <tr>
                            <td class="font-weight-bold">Name</td>
                            <td>${customer.firstName} ${customer.lastName}</td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">E-mail</td>
                            <td>${customer.email}</td>
                        </tr>
                    </table>
                </div>
                <div class="form-group">
                    <h4 class="mb-0 text-center">Contract</h4>
                    <table class="table">
                        <tr>
                            <td class="font-weight-bold">Contract number</td>
                            <br/>
                            <td><form:input type="number" cssClass="form-control" path="contractNumber"/></td>
                            <td><form:errors cssClass="alert-danger" path="contractNumber"/></td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Activate</td>
                            <td><form:checkbox id="activate" path="active"/></td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Plan</td>
                            <td>
                                <form:select cssClass="custom-select" id="planChoose" path="plan">
                                    <form:options items="${plans}"/>
                                </form:select>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="form-group">
                    <h4 class="mb-0 text-center">Options</h4><br/>
                    <div class="col-sm-10">
                        <table id="options" class="table table-sm table-striped align-content-center">
                            <thead><th></th><th>Name</th><th>Connect</th><th>Price</th></thead>
                            <tbody>
                            <c:forEach items="${currentPlanOpts}" var="optionEntry">
                                <c:set var="isOptRequired" value="${optionEntry.value}"/>
                                <tr>
                                    <td>
                                        <c:if test="${isOptRequired}">
                                            <img src="${pageContext.request.contextPath}/resources/images/lock.png" width="15px" height="15px"/>
                                        </c:if>
                                    </td>
                                    <td>${optionEntry.key.name}</td>
                                    <td><form:checkbox path="options" value="${optionEntry.key}" onclick="${isOptRequired ? 'return false;' : ''}"/></td>
                                    <td>${optionEntry.key.price}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-10">
                        <input type="reset" class="btn btn-danger" value="Reset">
                        <input type="submit" class="btn btn-primary float-right" value="Submit">
                    </div>
                </div>
            </form:form>
    </div>

    <script>
        let plan = [];
        let availableOptions = [];

        const planChoose = $("#planChoose");

        planChoose.change(function () {

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

        planChoose.change();
    </script>
</sec:authorize>
</body>
</html>
