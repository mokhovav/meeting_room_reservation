package com.mokhovav.meeting_room_reservation.responses;

import com.mokhovav.meeting_room_reservation.entities.reservation.Reservation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DailySchedule {
    public String dayOfWeek;
    public String date;
    public Map<Long, Reservation> reservations;
    public long dayStart;
    public long dayEnd;
}
