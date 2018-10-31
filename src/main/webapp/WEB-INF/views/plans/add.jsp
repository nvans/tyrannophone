<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<%@include file="../templates/navigation.jsp" %>
<%@include file="../templates/employee-navigation.jsp" %>

<sec:authorize access="hasRole('EMPLOYEE')">
    <form:form method="post" action="/plans/add" modelAttribute="plan">
        <div class="container py-3">
            <h2 class="mb-0 text-center">Create plan</h2><br/>
            <div class="row">

                <div class="col-md-6 mx-auto">
                    <div class="form-group">
                        <div class="label">Plan name</div>
                        <form:input path="planName" cssClass="form-control"/>
                    </div>
                    <div class="form-group">
                        <div class="label">Connection price</div>
                        <form:input id="connectionPrice" path="connectionPrice" default="0" type="number" cssClass="form-control"/>
                    </div>
                    <div class="form-group">
                        <div class="label">Monthly price</div>
                        <%--<form:input id="monthlyPrice" path="monthlyPrice" cssClass="form-control"/>--%>
                        <label id="monthlyPrice" class="form-control"></label>
                    </div>
                    <div class="form-group">
                        <div class="label">Description</div>
                        <form:textarea path="description" cssClass="form-control"/>
                    </div>
                    <div class="form-group">
                        <table>
                            <tr>
                                <td><form:checkbox id="cb" path="connectionAvailable"/></td>
                                <td>Connection available</td>
                            </tr>
                        </table>
                        <form:button class="btn btn-primary float-right" name="Modify">Create plan</form:button>
                    </div>
                    <tr>
                        <td><br/><br/></td>
                    </tr>
                </div>

            </div>
        </div>
        <div class="container col-lg-6">
            <table class="table table-sm table-striped justify-content-center">
                <thead class="table-secondary">
                <tr class="font-weight-bold text-center ">
                    <td>Option</td>
                    <td>Price</td>
                    <td>Availability</td>
                    <td>Connected</td>
                </tr>
                </thead>
                <tbody id="options">
                <c:forEach var="option" items="${options}">
                    <tr>
                        <td>${option.name}</td>
                        <td class="text-center">${option.price}</td>
                        <td class="text-center"><form:checkbox path="availableOptions" value="${option}"/></td>
                        <td class="text-center"><form:checkbox path="connectedOptions" value="${option}" title="${option.name}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>

    </form:form>

    <script type="text/javascript">

        let nameIndexDict = {};
        let indexNameDict = {};
        let indexPriceDict = {};
        let names = $("#options tr td:nth-child(1)");
        let prices = $("#options tr td:nth-child(2)");

        for (let i = 0; i < names.length; i++) {
            nameIndexDict[names[i].innerHTML] = i + 1;
            indexNameDict[i] = names[i].innerHTML;
            indexPriceDict[i] = parseInt(prices[i].innerHTML);
        }

        console.log(nameIndexDict);

        let allOptions = [];

        $.ajax({
            url : '${pageContext.request.contextPath}/api/options',
            type: 'get',
            success: function (response) {
                allOptions = response;
            }
        });

        let connectedOptionsCB = $("#options tr td:nth-child(4)").prop('checked',false);

        connectedOptionsCB.on('change', ':checkbox', function() {

            let incompatibleOptions = allOptions.find(o => o.name === this.value).incompatibleOptionsNames;
            console.log(incompatibleOptions);

            if (this.checked) {
                $("#options tr:nth-child("+nameIndexDict[this.value]+") td:nth-child(3) :first-child")
                    .prop('checked', true);

                console.log(this.value);

                let parent = allOptions.filter(o => o.name === this.value)[0].parentOption;

                if (parent !== '') {
                    $("#options tr:nth-child(" + nameIndexDict[parent] + ") td:nth-child(3) :first-child")
                        .prop('checked', true);
                }

                for (let i = 0; i < incompatibleOptions.length; i++) {
                    let idx = nameIndexDict[incompatibleOptions[i]];
                    $("#options tr:nth-child("+idx+") td:nth-child(4) :first-child")
                        .prop('disabled', true).prop('checked', false);
                    $("#options tr:nth-child("+idx+") td:nth-child(3) :first-child")
                        .prop('disabled', true).prop('checked', false);
                }
            }
            else {
                for (let i = 0; i < incompatibleOptions.length; i++) {
                    let idx = nameIndexDict[incompatibleOptions[i]];
                    $("#options tr:nth-child("+idx+") td:nth-child(4) :first-child").prop('disabled', false);
                    $("#options tr:nth-child("+idx+") td:nth-child(3) :first-child").prop('disabled', false);
                }
            }

            let monthlyPrice = parseInt(document.getElementById("connectionPrice").value);
            console.log(monthlyPrice);
            for (let i = 0; i < connectedOptionsCB.length; i++) {
                if(document.getElementById('connectedOptions' + (i + 1)).checked) {
                    monthlyPrice += indexPriceDict[i];
                }
            }

            console.log(monthlyPrice);
            $("#monthlyPrice").html(monthlyPrice);

        });

    </script>
</sec:authorize>
</body>
<head>
    <title>Add plan</title>
    <%@include file="../templates/header.jsp" %>
</head>
</html>
