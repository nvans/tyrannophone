<div class="modal fade" id="contractModal">
    <div class="modal-dialog">
        <div class="modal-body">
            <div class="card" style="margin: 3%; width: 400px; background: azure">
                <img class="card-img" src="${pageContext.request.contextPath}/resources/images/plan-bg.jpg">
                <div class="card-header card-img-overlay">
                    <h2 class="card-title text-light text-center">Contract preview</h2>
                </div>
                <div class="card-body">
                    <h3 id="modalContractNumber" class="text-center">Contract number</h3><br/>
                    <table class="table table-sm">
                        <tr>
                            <td class="font-weight-bold">Plan</td>
                            <td id="modalPlanName"></td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Monthly price</td>
                            <td id="modalMonthlyPrice"></td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Status</td>
                            <td id="modalContractStatus"></td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Owner name</td>
                            <td id="modalOwnerName"></td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Owner e-mail</td>
                            <td id="modalOwnerEmail"></td>
                        </tr>
                    </table>
                    <div class="font-weight-bold">Connected options</div>
                    <table id="modalConnectedOptions" class="table"></table>

                    <button type="button" class="btn btn-danger float-right" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function showContract(contractNumber) {

        $.ajax({
            url : '${pageContext.request.contextPath}/api/contracts/' + contractNumber,
            type: 'get',
            success: function(contract) {

                let format = contract.contractNumber.toString();
                format = [format.slice(0, 1), ' ', format.slice(1)].join('');
                format = [format.slice(0, 5), ' ', format.slice(5)].join('');
                format = [format.slice(0, 9), ' ', format.slice(9)].join('');
                format = [format.slice(0, 12), ' ', format.slice(12)].join('');

                $("#modalContractNumber").html(format);
                $("#modalPlanName").html(contract.planName);
                $("#modalMonthlyPrice").html(contract.monthlyPrice);
                $("#modalContractStatus").html(contract.active ? 'Active' : 'Inactive');
                $("#modalOwnerName").html(contract.customerFullName);
                $("#modalOwnerEmail").html(contract.customerEmail);

                addOptionsToTable(document.getElementById("modalConnectedOptions"), contract.options);

                $("#contractModal").modal();
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

            for(let i = 0; i < options.length; i++) {
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
