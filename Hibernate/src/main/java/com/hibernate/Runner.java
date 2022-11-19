package com.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.Date;

public class Runner {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConfig.createSessionFactory();
        Session session = openSession(sessionFactory);
        User user = new User("Yuri", "password", new Date(System.currentTimeMillis()));
        Post post = new Post("post1", new Date(System.currentTimeMillis()), user);
        Comment comment = new Comment("comment", post, user, new Date(System.currentTimeMillis()));
        beginTransaction(session);
        InstDAO instDAO = new InstDAO(session);
        instDAO.saveUserToDb(user);
        instDAO.savePostToDb(post);
        instDAO.saveCommentToDb(comment);
        commitTransaction(session);
        closeSession(session);
    }

    private static Session openSession(SessionFactory sessionFactory){
        return sessionFactory.openSession();
    }

    private static void beginTransaction(Session session){
        session.beginTransaction();
    }

    private static void commitTransaction(Session session){
        session.getTransaction().commit();
    }

    private static void closeSession(Session session){
        session.close();
    }

}
