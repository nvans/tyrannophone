package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "block_details")
public class BlockDetails implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blocked_by_user_id")
    private User blockedByUser;

    @Column(name = "blocked_before")
    private LocalDateTime blockedBefore;

    @Column(name = "reason")
    private String reason;

    @Column(name = "block_ts", updatable = false, nullable = false)
    private LocalDateTime blockTs;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User blockedUser;

//    @MapsId
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "contract_number")
//    private Contract contract;

    /**
     * Method adds current timestamp
     * when the user persisted first time.
     */
    @PrePersist
    private void onCreate() {
        this.blockTs = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBlockedByUser() {
        return blockedByUser;
    }

    public void setBlockedByUser(User blockedByUser) {
        this.blockedByUser = blockedByUser;
    }

    public LocalDateTime getBlockedBefore() {
        return blockedBefore;
    }

    public void setBlockedBefore(LocalDateTime blockedBefore) {
        this.blockedBefore = blockedBefore;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getBlockTs() {
        return blockTs;
    }

    public void setBlockTs(LocalDateTime blockTs) {
        this.blockTs = blockTs;
    }

    public User getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(User blockedUser) {
        this.blockedUser = blockedUser;
    }

    //    @Override
//    public String toString() {
//        return "BlockDetails{" +
//                "id=" + id +
//                ", blockedByUser=" + blockedByUser +
//                ", blockedBefore=" + blockedBefore +
//                ", reason='" + reason + '\'' +
//                ", blockTs=" + blockTs +
//                '}';
//    }
}
