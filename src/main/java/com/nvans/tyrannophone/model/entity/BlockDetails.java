package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "block_details")
public class BlockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "blocked_by_user_id")
    private User blockedByUser;

    @Column(name = "blocked_before")
    private LocalDateTime blockedBefore;

    @Column(name = "reason")
    private String reason;

    @Column(name = "block_ts", updatable = false, nullable = false)
    private LocalDateTime blockTs;

    /**
     * Method adds current timestamp
     * when the user persisted first time.
     */
    @PrePersist
    private void onCreate() {
        this.blockTs = LocalDateTime.now();
    }

}
