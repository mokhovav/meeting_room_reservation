package com.mokhovav.meeting_room_reservation.entities.authority;

import com.mokhovav.meeting_room_reservation.entities.BaseEntity;
import com.mokhovav.meeting_room_reservation.entities.user.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@MappedSuperclass
public class AuthorityData extends BaseEntity implements GrantedAuthority { //GrantedAuthority need for getAuthorities in User
    @NotBlank(message = "Authority cannot be empty")
    private String authority;

    @ManyToMany(mappedBy = "authorities")         // Bidirectional communication
    private Set<User> users;

    @Transient
    private String error;

    public AuthorityData() {
    }

    public AuthorityData(AuthorityData authorityData) {
        setId(authorityData.getId());
        authority = authorityData.getAuthority();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
