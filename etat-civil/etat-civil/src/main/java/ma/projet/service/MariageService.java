package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class MariageService extends AbstractService<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }

    /** Tous les mariages d'un homme, tries par date de debut. */
    public List<Mariage> findByHomme(int hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                    "SELECT m FROM Mariage m WHERE m.homme.id = :id ORDER BY m.dateDebut", Mariage.class)
                    .setParameter("id", hommeId)
                    .list();
        }
    }
}
