package ma.projet.test;

import java.sql.Date;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

public class Test {

    public static void main(String[] args) {

        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        // ========== 1. INSERTION DES DONNEES ==========
        Employe e1 = new Employe("Alami", "Yassine", "0600000001");
        Employe e2 = new Employe("Bennani", "Salma", "0600000002");
        Employe e3 = new Employe("Chraibi", "Omar", "0600000003");
        employeService.create(e1);
        employeService.create(e2);
        employeService.create(e3);

        Projet p1 = new Projet("Gestion de stock", Date.valueOf("2013-01-14"), Date.valueOf("2013-06-30"), e1);
        Projet p2 = new Projet("Site web", Date.valueOf("2013-02-01"), Date.valueOf("2013-05-31"), e1);
        Projet p3 = new Projet("Application mobile", Date.valueOf("2013-03-01"), Date.valueOf("2013-09-30"), e2);
        projetService.create(p1);
        projetService.create(p2);
        projetService.create(p3);

        // Tache(nom, dateDebutPrevue, dateFinPrevue, prix, projet)
        Tache t1 = new Tache("Analyse", Date.valueOf("2013-02-10"), Date.valueOf("2013-02-15"), 1200, p1);
        Tache t2 = new Tache("Conception", Date.valueOf("2013-03-05"), Date.valueOf("2013-03-12"), 800, p1);
        Tache t3 = new Tache("Développement", Date.valueOf("2013-04-01"), Date.valueOf("2013-04-20"), 3000, p1);
        Tache t4 = new Tache("Maquette", Date.valueOf("2013-02-15"), Date.valueOf("2013-02-28"), 600, p2);
        Tache t5 = new Tache("Intégration", Date.valueOf("2013-03-01"), Date.valueOf("2013-03-20"), 1500, p2);
        tacheService.create(t1);
        tacheService.create(t2);
        tacheService.create(t3);
        tacheService.create(t4);
        tacheService.create(t5);

        // EmployeTache(employe, tache, dateDebutReelle, dateFinReelle)
        employeTacheService.create(new EmployeTache(e1, t1, Date.valueOf("2013-02-10"), Date.valueOf("2013-02-20")));
        employeTacheService.create(new EmployeTache(e2, t2, Date.valueOf("2013-03-10"), Date.valueOf("2013-03-15")));
        employeTacheService.create(new EmployeTache(e3, t3, Date.valueOf("2013-04-10"), Date.valueOf("2013-04-25")));
        employeTacheService.create(new EmployeTache(e2, t4, Date.valueOf("2013-02-16"), Date.valueOf("2013-03-01")));
        employeTacheService.create(new EmployeTache(e3, t5, Date.valueOf("2013-03-01"), Date.valueOf("2013-03-25")));

        // ========== 2. Tâches réalisées par un employé ==========
        System.out.println("\n=== Tâches réalisées par un employé ===");
        employeService.afficherTachesRealisees(e2);

        // ========== 3. Projets gérés par un employé ==========
        System.out.println("\n=== Projets gérés par un employé ===");
        employeService.afficherProjetsGeres(e1);

        // ========== 4. Tâches planifiées d'un projet ==========
        System.out.println("\n=== Tâches planifiées d'un projet ===");
        projetService.afficherTachesPlanifiees(p1);

        // ========== 5. Tâches réalisées d'un projet (dates réelles) ==========
        System.out.println("\n=== Tâches réalisées d'un projet (dates réelles) ===");
        projetService.afficherTachesRealisees(p1);

        // ========== 6. Tâches dont le prix > 1000 DH (requête nommée) ==========
        System.out.println("\n=== Tâches dont le prix > 1000 DH ===");
        for (Tache t : tacheService.findPrixSuperieur1000()) {
            System.out.println(t);
        }

        // ========== 7. Tâches réalisées entre deux dates ==========
        System.out.println("\n=== Tâches réalisées entre 01/03/2013 et 30/04/2013 ===");
        for (Tache t : tacheService.findRealiseesEntreDates(
                Date.valueOf("2013-03-01"), Date.valueOf("2013-04-30"))) {
            System.out.println(t);
        }

        System.exit(0);
    }
}
