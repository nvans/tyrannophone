<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
    <%@include file="templates/header.jsp" %>
</head>
<body>
<%@include file="templates/navigation.jsp" %>
<%@include file="templates/customer-navigation.jsp" %>
<%@include file="templates/employee-navigation.jsp" %>

<div class="container-fluid">
    <h2 class="text-center">Cart</h2>
    <div class="card">
        <div class="row justify-content-center">
            <%--<div class="col-6">--%>
            <form:form method="post" action="${pageContext.request.contextPath}/cart" modelAttribute="order">
            <div class="form-group">
                <label for="contract" class="font-weight-bold">Contract</label>
                <form:select id="contract" path="contract" items="${contracts}" class="form-control"/>
                <a id="showContract" href="#" onclick="showContract(${contracts.get(0)})">Show contract</a>
            </div>
            <div id="totalGroup" class="form-group">
                <label for="monthlyPrice" class="font-weight-bold">Total price</label>
                $<label id="monthlyPrice"></label>
            </div>
            <div class="form-group">
                <label for="plan" class="font-weight-bold">Plan</label>
                <form:input id="plan" path="plan.planName" readonly="true" cssClass="form-control"/>
            </div>
            <div class="form-group row">
                <label for="options" class="font-weight-bold">Options</label><br/>
                <table id="options" class="table table-sm align-content-center"></table>
            </div>
            <form:button id="completeOrder" class="btn btn-primary float-right">Complete order</form:button>
            <a class="btn btn-danger" href="${pageContext.request.contextPath}/cart/clear">Clear cart</a>
        </div>
        </form:form>
        <%--</div>--%>
    </div>
</div>
</div>


<script type="text/javascript">
    // Get plan with options
    $.ajax({
        url : '${pageContext.request.contextPath}/contracts/super',
        type: 'get',
        data: {
            'planName': '${order.plan.planName}'
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
        if (totalPrice > ${customer.balance}) {
            $("#totalGroup").addClass('bg-warning');
            $("#completeOrder").attr('disabled', 'disabled');
        }
        else {
            $("#totalGroup").removeClass('bg-warning');
            $("#completeOrder").removeAttr('disabled');
        }

    });
</script>

<script type="text/javascript">

    $("#contract").change(function() {

        $("#contract option").attr('selected', false);
        $("#contract option:selected").attr('selected', true);

        $("#showContract").attr('onclick', 'showContract('+this.value+')');
    });

</script>

<%@include file="templates/show-contract-modal.jsp"%>

</body>
</html>
