package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    private static Date d(String s) {
        try {
            return SDF.parse(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        FemmeService fs = new FemmeService();
        HommeService hs = new HommeService();
        MariageService ms = new MariageService();

        // ---------- 1. Creation de 10 femmes ----------
        Femme salima = fs.create(new Femme("RAMI", "SALIMA", "0600000001", "Casablanca", d("12/03/1968")));
        Femme amal   = fs.create(new Femme("ALI", "AMAL", "0600000002", "Rabat", d("25/07/1972")));
        Femme wafa   = fs.create(new Femme("ALAOUI", "WAFA", "0600000003", "Fes", d("02/11/1978")));
        Femme karima = fs.create(new Femme("ALAMI", "KARIMA", "0600000004", "Tanger", d("14/01/1965")));
        Femme nadia  = fs.create(new Femme("BENANI", "NADIA", "0600000005", "Safi", d("30/05/1980")));
        Femme houda  = fs.create(new Femme("TAZI", "HOUDA", "0600000006", "Agadir", d("08/09/1975")));
        Femme samira = fs.create(new Femme("IDRISSI", "SAMIRA", "0600000007", "Meknes", d("19/12/1970")));
        Femme laila  = fs.create(new Femme("FASSI", "LAILA", "0600000008", "Oujda", d("21/04/1985")));
        Femme zineb  = fs.create(new Femme("BERRADA", "ZINEB", "0600000009", "Tetouan", d("17/06/1990")));
        Femme meriem = fs.create(new Femme("CHRAIBI", "MERIEM", "0600000010", "Marrakech", d("05/10/1982")));

        // ---------- 2. Creation de 5 hommes ----------
        Homme safi   = hs.create(new Homme("SAFI", "SAID", "0700000001", "Casablanca", d("10/02/1960")));
        Homme omar   = hs.create(new Homme("BENNANI", "OMAR", "0700000002", "Rabat", d("11/08/1962")));
        Homme youssef= hs.create(new Homme("ALAOUI", "YOUSSEF", "0700000003", "Fes", d("23/03/1970")));
        Homme karim  = hs.create(new Homme("TAHIRI", "KARIM", "0700000004", "Tanger", d("09/09/1975")));
        Homme hassan = hs.create(new Homme("AMRANI", "HASSAN", "0700000005", "Agadir", d("01/01/1958")));

        // ---------- 3. Mariages ----------
        // Safi Said : 3 mariages en cours + 1 echoue (= 4 femmes)
        ms.create(new Mariage(safi, karima, d("03/09/1989"), d("03/09/1990"), 0));
        ms.create(new Mariage(safi, salima, d("03/09/1990"), null, 4));
        ms.create(new Mariage(safi, amal,   d("03/09/1995"), null, 2));
        ms.create(new Mariage(safi, wafa,   d("04/11/2000"), null, 3));
        // Omar
        ms.create(new Mariage(omar, nadia, d("15/06/1995"), null, 2));
        ms.create(new Mariage(omar, houda, d("10/01/2000"), d("10/01/2005"), 1));
        // Youssef
        ms.create(new Mariage(youssef, karima, d("20/05/1992"), d("20/05/2000"), 2));
        ms.create(new Mariage(youssef, samira, d("12/03/2002"), null, 1));
        // Karim
        ms.create(new Mariage(karim, houda, d("01/06/2006"), null, 1));
        ms.create(new Mariage(karim, laila, d("01/07/2010"), null, 1));
        // Hassan
        ms.create(new Mariage(hassan, meriem, d("04/04/2008"), null, 2));
        ms.create(new Mariage(hassan, zineb, d("09/09/2015"), null, 0));

        // ---------- 4. Liste des femmes ----------
        System.out.println("===== Liste des femmes =====");
        for (Femme f : fs.findAll()) {
            System.out.println(f.getId() + " - " + f + "  (nee le " + SDF.format(f.getDateNaissance()) + ")");
        }

        // ---------- 5. Femme la plus agee ----------
        Femme plusAgee = fs.femmeLaPlusAgee();
        System.out.println("\n===== Femme la plus agee =====");
        System.out.println(plusAgee + "  (nee le " + SDF.format(plusAgee.getDateNaissance()) + ")");

        Date debut = d("01/01/1985");
        Date fin = d("31/12/2005");

        // ---------- 6. Epouses d'un homme entre deux dates ----------
        System.out.println("\n===== Epouses de " + safi + " entre " + SDF.format(debut) + " et " + SDF.format(fin) + " =====");
        for (Femme f : hs.epousesEntreDates(safi.getId(), debut, fin)) {
            System.out.println("- " + f);
        }

        // ---------- 7. Nombre d'enfants d'une femme entre deux dates (requete native nommee) ----------
        System.out.println("\n===== Nombre d'enfants de " + karima + " entre " + SDF.format(debut) + " et " + SDF.format(fin) + " =====");
        System.out.println(fs.nbEnfantsEntreDates(karima.getId(), debut, fin));

        // ---------- 8. Femmes mariees au moins deux fois (requete nommee) ----------
        System.out.println("\n===== Femmes mariees deux fois ou plus =====");
        for (Femme f : fs.femmesMarieesAuMoinsDeuxFois()) {
            System.out.println("- " + f);
        }

        // ---------- 9. Hommes maries a 4 femmes entre deux dates (Criteria) ----------
        List<Homme> quatre = hs.hommesMariesAQuatreFemmes(debut, fin);
        System.out.println("\n===== Hommes maries a quatre femmes entre " + SDF.format(debut) + " et " + SDF.format(fin) + " =====");
        System.out.println("Nombre : " + quatre.size());
        for (Homme h : quatre) {
            System.out.println("- " + h);
        }

        // ---------- 10. Mariages d'un homme avec tous les details ----------
        System.out.println("\n===== Mariages d'un homme =====");
        hs.afficherMariages(safi.getId());

        HibernateUtil.shutdown();
    }
}
