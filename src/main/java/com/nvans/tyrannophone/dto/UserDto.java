package com.nvans.tyrannophone.dto;

import com.nvans.tyrannophone.model.entity.Role;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserDto implements Serializable {

    private String userName;
    private String email;
    private String password;
    private Role role;
    private boolean isActive;
    private LocalDateTime lastActivityTS;

    // Getters and Setters -->

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    // <-- Getters and Setters


    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", lastActivityTS=" + lastActivityTS +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (isActive != userDto.isActive) return false;
        if (!userName.equals(userDto.userName)) return false;
        if (!email.equals(userDto.email)) return false;
        if (!password.equals(userDto.password)) return false;
        if (!role.equals(userDto.role)) return false;
        return lastActivityTS != null ? lastActivityTS.equals(userDto.lastActivityTS) : userDto.lastActivityTS == null;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (lastActivityTS != null ? lastActivityTS.hashCode() : 0);
        return result;
    }
}
