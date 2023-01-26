package com.dmitri.backend.util;

import com.dmitri.backend.dto.HitDTO;
import com.dmitri.backend.dto.UserDTO;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil{
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            Configuration configuration
                    = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(UserDTO.class)
                    .addAnnotatedClass(HitDTO.class);
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}