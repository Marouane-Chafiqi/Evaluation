package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class FemmeService extends AbstractService<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    /** Requete native nommee : nombre d'enfants d'une femme entre deux dates. */
    public int nbEnfantsEntreDates(int femmeId, Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Object r = s.getNamedQuery("Femme.nbEnfantsEntreDates")
                    .setParameter("femmeId", femmeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .uniqueResult();
            return r == null ? 0 : ((Number) r).intValue();
        }
    }

    /** Requete nommee : femmes mariees au moins deux fois. */
    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.mariesAuMoinsDeuxFois", Femme.class).getResultList();
        }
    }

    /** Femme la plus agee (date de naissance minimale). */
    public Femme femmeLaPlusAgee() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}
