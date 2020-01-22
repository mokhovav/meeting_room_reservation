package com.mokhovav.meeting_room_reservation.entities.authority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority extends AuthorityData {

    public Authority() {
    }

    public Authority(String authority) {
        setAuthority(authority);
    }
}
