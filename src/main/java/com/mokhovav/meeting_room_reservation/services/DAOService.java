package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.configurations.HibernateConfiguration;
import com.mokhovav.meeting_room_reservation.datatables.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DAOService<T> implements DAO<T> {

    final SessionFactory sessionFactory;

    public DAOService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        session.close();
    }

    @Override
    public T findBy(Class c, String field, Object value) {
        Session session = sessionFactory.openSession();
        List<T> list =  session.createQuery("from "+c.getName()+" where "+field+"=:value").setParameter("value",value).list();
        session.close();
        if (list.isEmpty()) return null;
        else return  list.get(0);
    }

    @Override
    public List<T> findAllBy(Class c, String field, Object value) {
        Session session = sessionFactory.openSession();
        List<T> list = session.createQuery("from "+c.getName()+" where "+field+"=:value").setParameter("value",value).list();
        session.close();
        return list;
    }

    @Override
    public List<T> findAll(Class c) {
        Session session = sessionFactory.openSession();
        List<T> list = (List<T>) session.createQuery("From "+ c.getName()).list();
        session.close();
        return list;
    }

}
