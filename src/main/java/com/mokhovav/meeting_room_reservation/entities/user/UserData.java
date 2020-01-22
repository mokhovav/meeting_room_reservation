package com.mokhovav.meeting_room_reservation.entities.user;

import com.mokhovav.meeting_room_reservation.entities.reservation.Reservation;
import com.mokhovav.meeting_room_reservation.entities.authority.Authority;
import com.mokhovav.meeting_room_reservation.entities.BaseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@MappedSuperclass
public class UserData extends BaseEntity  implements UserDetails {
    @NotBlank(message = "Username cannot be empty")
    @Column(name = "user_name")
    private String username;
    private boolean active;
    @ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "users_id"))
    private Set<Authority> authorities;
    @OneToMany(mappedBy = "user")           // Bidirectional communication
    private Set<Reservation> reservations;
    @Transient
    private String error;

    public UserData() {
    }

    public UserData(UserData userData) {
        setId(userData.getId());
        username = userData.getUsername();
        active = userData.isActive();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean isHaveAuthority(Authority authority) {
        if (authority != null)
            for (Authority a : getAuthorities())
                if (a.getAuthority().equals(authority.getAuthority()))
                    return true;
        return false;
    }

    /* For thymeleaf-extras-springsecurity5*/
    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
