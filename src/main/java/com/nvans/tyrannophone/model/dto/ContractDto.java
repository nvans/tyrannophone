package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Plan;

import java.io.Serializable;

public class ContractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long contractNumber;

    private Plan plan;

    private boolean isActive;

    private boolean isReadOnly;

    private BlockDetails blockDetails;


    public ContractDto(Contract contract, boolean readOnly) {

        if (contract == null) {
            throw new IllegalArgumentException("Contract can't be null");
        }

        this.contractNumber = contract.getContractNumber();
        this.plan = contract.getPlan();
        this.isActive = contract.isActive();
        this.isReadOnly = readOnly;
        this.blockDetails = contract.getBlockDetails();
    }

    public Long getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public BlockDetails getBlockDetails() {
        return blockDetails;
    }

    public void setBlockDetails(BlockDetails blockDetails) {
        this.blockDetails = blockDetails;
    }
}
