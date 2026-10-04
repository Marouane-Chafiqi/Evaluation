package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService extends AbstractService<Homme> {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    public HommeService() {
        super(Homme.class);
    }

    /** Epouses d'un homme dont le mariage a debute entre deux dates. */
    public List<Femme> epousesEntreDates(int hommeId, Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                    "SELECT m.femme FROM Mariage m WHERE m.homme.id = :id "
                  + "AND m.dateDebut BETWEEN :d1 AND :d2 ORDER BY m.dateDebut", Femme.class)
                    .setParameter("id", hommeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    /** API Criteria : hommes maries a 4 femmes (mariages debutes entre d1 et d2). */
    public List<Homme> hommesMariesAQuatreFemmes(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<Mariage> m = cq.from(Mariage.class);

            cq.select(m.get("homme").<Integer>get("id"))
              .where(cb.between(m.<Date>get("dateDebut"), d1, d2))
              .groupBy(m.get("homme").get("id"))
              .having(cb.equal(cb.countDistinct(m.get("femme")), 4L));

            List<Homme> result = new ArrayList<>();
            for (Integer id : s.createQuery(cq).getResultList()) {
                result.add(s.get(Homme.class, id));
            }
            return result;
        }
    }

    /** Affiche les mariages d'un homme (en cours puis echoues) avec les details. */
    public void afficherMariages(int hommeId) {
        Homme h = findById(hommeId);
        if (h == null) {
            System.out.println("Homme introuvable (id=" + hommeId + ")");
            return;
        }
        List<Mariage> mariages = new MariageService().findByHomme(hommeId);

        System.out.println("Nom : " + h);
        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %s   Date Debut : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme(), SDF.format(m.getDateDebut()), m.getNbrEnfant());
            }
        }

        System.out.println();
        System.out.println("Mariages echoues :");
        i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.printf("%d. Femme : %s   Date Debut : %s%n   Date Fin : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme(), SDF.format(m.getDateDebut()),
                        SDF.format(m.getDateFin()), m.getNbrEnfant());
            }
        }
    }
}
