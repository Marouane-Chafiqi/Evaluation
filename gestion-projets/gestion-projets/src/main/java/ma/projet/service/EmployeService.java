package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.List;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class EmployeService extends AbstractService<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    // 1) Tâches réalisées par un employé
    public void afficherTachesRealisees(Employe employe) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> lignes = s.createQuery(
                        "from EmployeTache et where et.employe.id = :idEmp order by et.id",
                        EmployeTache.class)
                    .setParameter("idEmp", employe.getId())
                    .list();

            System.out.println("Employé : " + employe.getPrenom() + " " + employe.getNom());
            EmployeTacheService.afficherLignes(lignes);
        }
    }

    // 2) Projets gérés par un employé (chef de projet)
    public void afficherProjetsGeres(Employe employe) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Projet> projets = s.createQuery(
                        "from Projet p where p.chefProjet.id = :idEmp order by p.id",
                        Projet.class)
                    .setParameter("idEmp", employe.getId())
                    .list();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Employé : " + employe.getPrenom() + " " + employe.getNom());
            System.out.printf("%-4s%-22s%-14s%s%n", "Num", "Nom", "Date Début", "Date Fin");
            for (Projet p : projets) {
                System.out.printf("%-4d%-22s%-14s%s%n",
                        p.getId(), p.getNom(),
                        sdf.format(p.getDateDebut()), sdf.format(p.getDateFin()));
            }
        }
    }
}
