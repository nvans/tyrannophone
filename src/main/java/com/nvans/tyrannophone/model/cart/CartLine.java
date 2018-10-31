package com.nvans.tyrannophone.model.cart;

import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Contract;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

//@Component
//@SessionScope
public class CartLine {

    private Long employeeId;

    private Contract contract;

    private List<PlanOptionDto> options;

    public void clearCard() {
        this.employeeId = null;
        this.contract = null;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }



    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
