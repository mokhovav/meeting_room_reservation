package com.mokhovav.meeting_room_reservation.database;

import java.util.List;

public interface DAO<T> {
    public boolean save(T object);
    public boolean update(T object);
    public boolean delete(T object);
    public T getById(Long id);
    public List<T> findList(String text);
    public T findObject(String text);
    public List<T> findAll(Class c);
}
