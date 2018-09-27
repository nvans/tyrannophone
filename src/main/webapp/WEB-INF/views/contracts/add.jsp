<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add contract</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
    <%@include file="../templates/navigation.jsp"%>
    <%@include file="../templates/employee-navigation.jsp"%>

    <div class="container py-2">
        <form:form method="post" action="${pageContext.request.contextPath}/contracts/add" modelAttribute="contract">
            <div class="form-group row">
                <label for="planChoose" class="col-sm-2 col-form-label">Customer</label>
                <div class="col-sm-10">
                    <form:input path="customer" readonly="true"/>
                </div>
            </div>
            <div class="form-group row">
                <label for="planChoose" class="col-sm-2 col-form-label">Number</label>
                <div class="col-sm-10">
                    <form:input path="contractNumber"/>
                    <form:errors cssClass="alert-danger" path="contractNumber"/>
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
            <div class="form-group row">
                <label for="availableOptions" class="col-sm-2 col-form-label">Options</label>
                <div class="col-sm-10">
                    <form:select cssClass="custom-select col-sm-4" id="availableOptions" path="options" multiple="true">
                        <form:option value="">Without options</form:option>
                        <form:options items="${plans.get(0).availableOptions}"/>
                    </form:select>
                </div>
            </div>
            <div class="form-group row">
                <label for="activate" class="col-sm-2 col-form-label">Active</label>
                <div class="col-sm-10">
                    <form:checkbox id="activate" path="active"/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-10 offset-sm-2">
                    <input type="reset" class="btn btn-secondary col-sm-2"/>
                    <input type="submit" class="btn btn-success col-sm-2"/>
                </div>
            </div>
        </form:form>
    </div>

    <div id="test"></div>

    <script>
        $("#planChoose").change(function () {

            console.log($(this).val())

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
                    $('#availableOptions').html(options);
                }
            });
        });
    </script>
</body>
</html>
