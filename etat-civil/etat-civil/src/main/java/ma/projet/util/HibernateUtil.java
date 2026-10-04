package ma.projet.util;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try (InputStream in = HibernateUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            props.load(in);

            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(Homme.class);
            cfg.addAnnotatedClass(Femme.class);
            cfg.addAnnotatedClass(Mariage.class);
            return cfg.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}
