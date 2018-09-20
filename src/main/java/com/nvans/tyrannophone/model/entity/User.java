package com.nvans.tyrannophone.model.entity;

//import com.nvans.tyrannophone.model.dao.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true, updatable = false)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToOne
    @JoinTable(name = "user_block_details",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "block_id", referencedColumnName = "id", unique = true))
    private BlockDetails blockDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Details details;

    @Column(name = "last_activity_ts")
    private LocalDateTime lastActivityTS;

    @Column(name = "create_ts", updatable = false, nullable = false)
    private LocalDateTime createTS;

    @Version
    @Column(name = "update_ts", nullable = false)
    private LocalDateTime updateTS;


    /**
     * Method adds current timestamp
     * when the user persisted first time.
     */
    @PrePersist
    private void onCreate() {
        this.createTS = LocalDateTime.now();
    }

    // Getters and Setters -->
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public BlockDetails getBlockDetails() {
        return blockDetails;
    }

    public void setBlockDetails(BlockDetails blockDetails) {
        this.blockDetails = blockDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastActivityTS() {
        return lastActivityTS;
    }

    public void setLastActivityTS(LocalDateTime lastActivityTS) {
        this.lastActivityTS = lastActivityTS;
    }

    public LocalDateTime getCreateTS() {
        return createTS;
    }

    public void setCreateTS(LocalDateTime createTS) {
        this.createTS = createTS;
    }

    public LocalDateTime getUpdateTS() {
        return updateTS;
    }

    public void setUpdateTS(LocalDateTime updateTS) {
        this.updateTS = updateTS;
    }
    // <-- Getters and Setters


    @Override
    public String toString() {
        return "User{" +
//                "userId=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
//                ", roles=" + roles +
                ", isActive=" + isActive +
//                ", blockDetails=" + blockDetails +
                ", lastActivityTS=" + lastActivityTS +
                ", createTS=" + createTS +
                ", updateTS=" + updateTS +
                '}';
    }
}
