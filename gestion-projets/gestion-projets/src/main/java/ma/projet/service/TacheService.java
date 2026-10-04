package ma.projet.service;

import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;

import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class TacheService extends AbstractService<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    // 1) Tâches dont le prix > 1000 DH (requête nommée définie dans Tache)
    public List<Tache> findPrixSuperieur1000() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Tache.prixSuperieur1000", Tache.class).list();
        }
    }

    // 2) Tâches réalisées entre deux dates
    //    (dates RÉELLES : début >= d1 ET fin <= d2)
    public List<Tache> findRealiseesEntreDates(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                        "select t from Tache t where t.id in ("
                      + "  select et.tache.id from EmployeTache et "
                      + "  where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2"
                      + ") order by t.id", Tache.class)
                    .setParameter("d1", d1, TemporalType.DATE)
                    .setParameter("d2", d2, TemporalType.DATE)
                    .list();
        }
    }
}
