package com.mokhovav.meeting_room_reservation.entities.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User extends UserData{      //UserDetails need for login

    @NotBlank(message = "Password cannot be empty")
    private String password;
    private boolean changePassword;
    @Transient
    private String confirm;

    public User() {
        setActive(true);
        setChangePassword(true);
    }

    public User(
            @NotBlank(message = "Username cannot be empty") String userName,
            @NotBlank(message = "Password cannot be empty") String password
    ) {
        setUsername(userName);
        this.password = password;
        setActive(true);
        setChangePassword(true);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public UserData getUserData(){
        UserData userData = new UserData();
        userData.setUsername(getUsername());
        userData.setActive(isActive());
        userData.setAuthorities(getAuthorities());
        userData.setReservations(getReservations());
        return userData;
    }
}
