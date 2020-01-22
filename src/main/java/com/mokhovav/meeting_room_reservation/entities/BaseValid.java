package com.mokhovav.meeting_room_reservation.entities;

import org.springframework.stereotype.Service;

@Service
public class BaseValid {
    public boolean notNullAndEmpty(String str){
        return str!=null && !str.isEmpty();
    }
}
