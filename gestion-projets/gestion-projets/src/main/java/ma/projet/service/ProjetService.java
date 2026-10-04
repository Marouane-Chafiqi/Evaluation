package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class ProjetService extends AbstractService<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    // 1) Tâches PLANIFIEES d'un projet (dates prévues, stockées dans Tache)
    public void afficherTachesPlanifiees(Projet projet) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> taches = s.createQuery(
                        "from Tache t where t.projet.id = :idProjet order by t.id", Tache.class)
                    .setParameter("idProjet", projet.getId())
                    .list();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom());
            System.out.println("Liste des tâches planifiées:");
            System.out.printf("%-4s%-15s%-20s%-18s%s%n",
                    "Num", "Nom", "Date Début Prévue", "Date Fin Prévue", "Prix");
            for (Tache t : taches) {
                System.out.printf("%-4d%-15s%-20s%-18s%s%n",
                        t.getId(), t.getNom(),
                        sdf.format(t.getDateDebut()), sdf.format(t.getDateFin()),
                        (int) t.getPrix() + " DH");
            }
        }
    }

    // 2) Tâches RÉALISEES d'un projet avec les dates réelles (format de l'énoncé)
    public void afficherTachesRealisees(Projet projet) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> lignes = s.createQuery(
                        "from EmployeTache et where et.tache.projet.id = :idProjet order by et.id",
                        EmployeTache.class)
                    .setParameter("idProjet", projet.getId())
                    .list();

            SimpleDateFormat longFmt = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            System.out.println("Projet : " + projet.getId()
                    + "      Nom : " + projet.getNom()
                    + "     Date début : " + longFmt.format(projet.getDateDebut()));
            System.out.println("Liste des tâches:");
            EmployeTacheService.afficherLignes(lignes);
        }
    }
}
