package com.mokhovav.meeting_room_reservation.database;

import com.mokhovav.meeting_room_reservation.entities.reservation.Reservation;
import com.mokhovav.meeting_room_reservation.entities.authority.Authority;
import com.mokhovav.meeting_room_reservation.entities.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HibernateConfig {

    private static SessionFactory sessionFactory;
    private HibernateConfig() {
    }

    @Bean
    public static SessionFactory getSessionFactory() throws Exception {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.xml");
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Authority.class);
                configuration.addAnnotatedClass(Reservation.class);
                sessionFactory = configuration.buildSessionFactory();
            }
        }catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        return sessionFactory;
    }


}
