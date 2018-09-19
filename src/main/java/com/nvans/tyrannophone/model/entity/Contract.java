package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "contract_number", length = 9)
    private Long contractNumber;


    @OneToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinTable(name = "customer_contract",
            joinColumns = @JoinColumn(name = "contract_number"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Customer customer;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // TODO: Unidirectional
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "blocked_contract",
           joinColumns = @JoinColumn(name = "contract_number",
                                     referencedColumnName = "contract_number"),
           inverseJoinColumns = @JoinColumn(name = "block_details_id",
                                            referencedColumnName = "id")
               )
    private BlockDetails blockDetails;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public BlockDetails getBlockDetails() {
        return blockDetails;
    }

    public void setBlockDetails(BlockDetails blockDetails) {
        this.blockDetails = blockDetails;
    }

    // <-- Getters and Setters


}
