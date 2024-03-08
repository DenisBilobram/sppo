package lab3test.dao;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testcontainers.containers.PostgreSQLContainer;

import lab3test.model.RequestBean;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory(PostgreSQLContainer<?> postgreSQLContainer) {
        Configuration configuration = new Configuration();

        // Настройка свойств Hibernate
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.URL, postgreSQLContainer.getJdbcUrl());
        settings.put(Environment.USER, postgreSQLContainer.getUsername());
        settings.put(Environment.PASS, postgreSQLContainer.getPassword());
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        // Другие свойства Hibernate

        configuration.setProperties(settings);

        // Добавление аннотированных классов
        configuration.addAnnotatedClass(RequestBean.class);
        

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }


    public static Session getSession() {
        return null;
    }
}
