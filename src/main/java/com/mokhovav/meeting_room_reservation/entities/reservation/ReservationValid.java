package com.mokhovav.meeting_room_reservation.entities.reservation;

import com.mokhovav.meeting_room_reservation.entities.BaseValid;
import com.mokhovav.meeting_room_reservation.entities.user.User;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import org.springframework.stereotype.Service;

@Service
public class ReservationValid extends BaseValid {

    public ReservationValid() {
    }

    public void dataCheck (User user, String title, String description, Integer duration, String date, String time) throws CustomException {
        if (user == null) throw new CustomException("user not selected");
        if (!notNullAndEmpty(title)) throw new CustomException("The title should not be blank");
        //if (!notNullAndEmpty(description)) throw new CustomException("The description should not be blank");
        if (!notNullAndEmpty(date)) throw new CustomException("The date should not be blank");
        if (!notNullAndEmpty(time)) throw new CustomException("The time should not be blank");
        if (duration == null || duration <= 0) throw new CustomException("Set duration in minutes");
    }
}
