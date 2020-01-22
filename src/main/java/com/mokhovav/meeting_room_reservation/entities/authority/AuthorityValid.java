package com.mokhovav.meeting_room_reservation.entities.authority;

import com.mokhovav.meeting_room_reservation.entities.BaseValid;
import org.springframework.stereotype.Service;

@Service
public class AuthorityValid extends BaseValid {
    public boolean notNullAndEmpty(Authority authority){
        return authority!=null && notNullAndEmpty(authority.getAuthority());
    }
}
