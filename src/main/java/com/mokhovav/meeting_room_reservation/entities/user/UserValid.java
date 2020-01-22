package com.mokhovav.meeting_room_reservation.entities.user;

import com.mokhovav.meeting_room_reservation.entities.BaseValid;
import org.springframework.stereotype.Service;

@Service
public class UserValid extends BaseValid {
    public boolean userName(UserData userData) {
        return userData != null && notNullAndEmpty(userData.getUsername());
    }
}
