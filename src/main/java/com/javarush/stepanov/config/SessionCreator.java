package com.javarush.stepanov.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.javarush.stepanov.entity.User;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.format.FormatMapper;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SessionCreator implements Closeable {

    private final SessionFactory sessionFactory;
    private final ThreadLocal<AtomicInteger> levelBox = new ThreadLocal<>();
    private final ThreadLocal<Session> sessionBox = new ThreadLocal<>();

    @SneakyThrows
    public SessionCreator(ApplicationProperties applicationProperties) {
        Configuration configuration = new Configuration();
        configuration.addProperties(applicationProperties);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return sessionBox.get() == null || !sessionBox.get().isOpen()
                ? sessionFactory.openSession()
                : sessionBox.get();
    }

    public void beginTransactional() {
        if (levelBox.get() == null) {
            levelBox.set(new AtomicInteger(0));
        }
        AtomicInteger level = levelBox.get();
        if (level.getAndIncrement() == 0) {
            Session session = sessionFactory.openSession();
            sessionBox.set(session);
            session.beginTransaction();
        }
        log(level.get(), "begin level: ");
    }


    public void endTransactional() {
        AtomicInteger level = levelBox.get();
        Session session = sessionBox.get();
        log(level.get(), "end level: ");
        if (level.decrementAndGet() == 0) {
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    private void log(int level, String message) {
        String simpleName = Thread.currentThread().getStackTrace()[4].toString();
        System.out.println("\t".repeat(level) + message + level + " from " + simpleName);
        System.out.flush();
    }

    public void close() {
        sessionFactory.close();
    }

    public boolean isTransactionActive() {
        try {
            return sessionFactory.getCurrentSession().getTransaction().isActive();
        } catch (Exception e) {
            return false;
        }
    }

}
