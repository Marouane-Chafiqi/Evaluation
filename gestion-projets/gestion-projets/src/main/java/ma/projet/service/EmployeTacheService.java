package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.List;

import ma.projet.classes.EmployeTache;

public class EmployeTacheService extends AbstractService<EmployeTache> {

    public EmployeTacheService() {
        super(EmployeTache.class);
    }

    // Affichage commun : Num / Nom / Date Début Réelle / Date Fin Réelle
    // (utilisé par EmployeService et ProjetService)
    public static void afficherLignes(List<EmployeTache> lignes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("%-4s%-15s%-20s%s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
        for (EmployeTache et : lignes) {
            System.out.printf("%-4d%-15s%-20s%s%n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    sdf.format(et.getDateDebutReelle()),
                    sdf.format(et.getDateFinReelle()));
        }
    }
}
