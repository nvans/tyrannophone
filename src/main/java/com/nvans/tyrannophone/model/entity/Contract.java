package com.nvans.tyrannophone.model.entity;

import com.nvans.tyrannophone.utils.validation.ContractNumber;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "contract_number", length = 9)
    @ContractNumber
    private Long contractNumber;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinTable(name = "customer_contract",
            joinColumns = @JoinColumn(name = "contract_number"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Customer customer;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL) //
    @JoinTable(name = "contract_option",
            joinColumns = @JoinColumn(name = "contract_number"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "contract_block_details",
           joinColumns = @JoinColumn(name = "contract_number", referencedColumnName = "contract_number"),
           inverseJoinColumns = @JoinColumn(name = "block_details_id", referencedColumnName = "id"))
    private BlockDetails blockDetails;

    public Integer getMonthlyPrice() {
        return plan.getConnectionPrice() + options.stream().mapToInt(Option::getPrice).sum();
    }

    // Getters and Setters -->
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public BlockDetails getBlockDetails() {
        return blockDetails;
    }

    public void setBlockDetails(BlockDetails blockDetails) {
        this.blockDetails = blockDetails;
    }


    // <-- Getters and Setters


    @Override
    public String toString() {
        return contractNumber.toString();
    }
}
