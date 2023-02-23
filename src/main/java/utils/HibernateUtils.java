package utils;

import Pojo.Manufacture;
import Pojo.Phone;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(Phone.class);
            configuration.addAnnotatedClass(Manufacture.class);
            StandardServiceRegistryBuilder buider = new StandardServiceRegistryBuilder();
            buider.applySettings(configuration.getProperties());
            StandardServiceRegistry serviceRegistry = buider.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return  sessionFactory;
    }
}
