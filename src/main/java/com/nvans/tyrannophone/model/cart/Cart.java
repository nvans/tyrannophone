package com.nvans.tyrannophone.model.cart;

import com.nvans.tyrannophone.model.dto.PlanDto;
import com.nvans.tyrannophone.model.dto.PlanOptionDto;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class CustomerCart {

    private Contract contract;

    private PlanDto plan;

    private List<PlanOptionDto> options;

    // Methods
    public int getTotalPrice() {

        int totalPrice = plan.getConnectionPrice();

        for (PlanOptionDto option : options) {
            totalPrice += option.getPrice();
        }

        return totalPrice;
    }

    public void clearCart() {
        this.contract = null;
        this.plan = null;
        this.options = null;
    }

    public boolean isCartEmpty() {
        return contract == null && (plan == null || options == null);
    }
    // <-- Methods

    // Getters and setters -->
    public PlanDto getPlan() {
        return plan;
    }

    public void setPlan(PlanDto plan) {
        this.plan = plan;
    }

    public List<PlanOptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<PlanOptionDto> options) {
        this.options = options;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    // <-- Getters and setters


}
