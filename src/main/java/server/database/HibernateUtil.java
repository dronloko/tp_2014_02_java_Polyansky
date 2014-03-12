package server.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by dronloko on 02.03.14.
 */

public class HibernateUtil {
    private static Configuration configuration = new Configuration();

    private final static SessionFactory SESSION_FACTORY = createSessionFactory(configuration);

    public static SessionFactory createSessionFactory(Configuration configuration) {
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/java_Polyansky_db");
        configuration.setProperty("hibernate.connection.username", "dronloko");
        configuration.setProperty("hibernate.connection.password", "dronloko");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
}
