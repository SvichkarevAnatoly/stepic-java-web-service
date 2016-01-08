package service;

import dao.UserDAO;
import datasets.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";
    private static final String hibernate_username = "test";
    private static final String hibernate_password = "test";

    private final SessionFactory sessionFactory;
    private final Map<String, Long> sessionIdToProfile;

    public AccountService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);

        sessionIdToProfile = new HashMap<>();
    }

    public long addNewUser(UserDataSet user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDAO dao = new UserDAO(session);
        long id = dao.insertUser(user);
        transaction.commit();
        session.close();
        return id;
    }

    public UserDataSet getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);
        UserDataSet ds = dao.getUserDataSetByLogin(login);
        session.close();
        return ds;
    }

    public UserDataSet getUserBySessionId(String sessionId) {
        final Long userId = sessionIdToProfile.get(sessionId);
        if(userId == null){
            return null;
        }
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);
        UserDataSet ds = dao.getUserDataSetByUserId(userId);
        session.close();
        return ds;
    }

    public void addSession(long userId, String sessionId) {
        sessionIdToProfile.put(sessionId, userId);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", hibernate_username);
        configuration.setProperty("hibernate.connection.password", hibernate_password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
