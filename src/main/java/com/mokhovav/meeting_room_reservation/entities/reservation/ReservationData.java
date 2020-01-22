package com.mokhovav.meeting_room_reservation.entities.reservation;

import com.mokhovav.meeting_room_reservation.entities.BaseEntity;
import com.mokhovav.meeting_room_reservation.entities.user.User;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
public class ReservationData extends BaseEntity {
    @NotEmpty
    private String title;
    private String description;
    private long timeBegin;
    private long timeEnd;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @Transient
    private String error;

    public ReservationData() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
