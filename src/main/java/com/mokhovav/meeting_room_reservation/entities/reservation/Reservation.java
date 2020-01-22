package com.mokhovav.meeting_room_reservation.entities.reservation;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation extends ReservationData implements Comparable<Reservation>{

    public Reservation() {
    }

    @Override
    public int compareTo(Reservation r) {
        return (int)(getTimeBegin()- r.getTimeBegin());
    }
}

