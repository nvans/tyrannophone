package com.nvans.tyrannophone.model.dto;

import com.nvans.tyrannophone.model.entity.BlockDetails;
import com.nvans.tyrannophone.model.entity.Contract;
import com.nvans.tyrannophone.model.entity.Option;
import com.nvans.tyrannophone.model.entity.Plan;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ContractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long contractNumber;

    private Plan plan;

    private boolean isActive;

    private boolean isReadOnly;

    private Set<Option> compatibleOptions;

    private Set<Option> incompatibleOptions;

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

        this.compatibleOptions = new HashSet<>();
        this.incompatibleOptions = new HashSet<>();

        for (Option optI : contract.getPlan().getAvailableOptions().keySet()) {
            for (Option optJ : contract.getPlan().getAvailableOptions().keySet()) {
                if (optI.equals(optJ)) {
                    continue;
                }

                if (optI.getIncompatibleOptions().contains(optJ)) {
                    incompatibleOptions.add(optJ);
                }
                else {
                    if (incompatibleOptions.contains(optJ)) {
                        continue;
                    }

                    compatibleOptions.add(optJ);
                }
            }
        }

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

    public Set<Option> getCompatibleOptions() {
        return compatibleOptions;
    }

    public void setCompatibleOptions(Set<Option> compatibleOptions) {
        this.compatibleOptions = compatibleOptions;
    }

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void setIncompatibleOptions(Set<Option> incompatibleOptions) {
        this.incompatibleOptions = incompatibleOptions;
    }

    public BlockDetails getBlockDetails() {
        return blockDetails;
    }

    public void setBlockDetails(BlockDetails blockDetails) {
        this.blockDetails = blockDetails;
    }
}
