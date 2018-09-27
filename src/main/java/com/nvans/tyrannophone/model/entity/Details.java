package com.nvans.tyrannophone.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Details implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "passport", nullable = false, unique = true)
    private String passport;

    @Column(name = "address", nullable = false)
    private String address;



    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id")
    private User user;

    // Getters and Setters -->

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // <-- Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Details)) return false;
        Details details = (Details) o;
        return Objects.equals(firstName, details.firstName) &&
                Objects.equals(lastName, details.lastName) &&
                Objects.equals(passport, details.passport) &&
                Objects.equals(address, details.address) &&
                Objects.equals(user, details.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, passport, address, user);
    }
}
