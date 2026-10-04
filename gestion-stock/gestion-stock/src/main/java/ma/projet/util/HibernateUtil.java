package ma.projet.util;

import java.io.InputStream;
import java.util.Properties;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // 1) N9ra application.properties mn src/main/resources
            Properties props = new Properties();
            try (InputStream in = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                props.load(in);
            }

            // 2) Kandiro Configuration b had les propriétés
            Configuration cfg = new Configuration();
            cfg.setProperties(props);

            // 3) Kan3lmo Hibernate b les entités (darouri f Java SE)
            cfg.addAnnotatedClass(Categorie.class);
            cfg.addAnnotatedClass(Produit.class);
            cfg.addAnnotatedClass(Commande.class);
            cfg.addAnnotatedClass(LigneCommandeProduit.class);

            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
