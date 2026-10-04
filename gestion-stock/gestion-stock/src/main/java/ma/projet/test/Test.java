package ma.projet.test;

import java.sql.Date;
import java.util.List;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

public class Test {

    public static void main(String[] args) {

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // ========== 1. INSERTION DES DONNEES ==========
        Categorie info = new Categorie("INF", "Informatique");
        Categorie acc = new Categorie("ACC", "Accessoires");
        categorieService.create(info);
        categorieService.create(acc);

        Produit p1 = new Produit("ES12", 120, info);
        Produit p2 = new Produit("ZR85", 100, info);
        Produit p3 = new Produit("EE85", 200, acc);
        Produit p4 = new Produit("AB10", 80, acc);
        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);

        Commande c1 = new Commande(Date.valueOf("2013-03-14"));
        Commande c2 = new Commande(Date.valueOf("2013-05-20"));
        commandeService.create(c1);
        commandeService.create(c2);

        ligneService.create(new LigneCommandeProduit(p1, c1, 7));
        ligneService.create(new LigneCommandeProduit(p2, c1, 14));
        ligneService.create(new LigneCommandeProduit(p3, c1, 5));
        ligneService.create(new LigneCommandeProduit(p4, c2, 3));
        ligneService.create(new LigneCommandeProduit(p1, c2, 2));

        // ========== 2. TEST : produits par catégorie ==========
        System.out.println("\n=== Produits de la catégorie Informatique ===");
        List<Produit> parCategorie = produitService.findByCategorie(info);
        for (Produit p : parCategorie) {
            System.out.println(p);
        }

        // ========== 3. TEST : produits commandés entre deux dates ==========
        System.out.println("\n=== Produits commandés entre 01/03/2013 et 31/03/2013 ===");
        List<Produit> entreDates = produitService.findCommandesEntreDates(
                Date.valueOf("2013-03-01"), Date.valueOf("2013-03-31"));
        for (Produit p : entreDates) {
            System.out.println(p);
        }

        // ========== 4. TEST : produits d'une commande ==========
        System.out.println("\n=== Produits de la commande " + c1.getId() + " ===");
        produitService.afficherProduitsParCommande(c1);

        // ========== 5. TEST : requête nommée (prix > 100 DH) ==========
        System.out.println("\n=== Produits dont le prix > 100 DH ===");
        for (Produit p : produitService.findPrixSuperieur100()) {
            System.out.println(p);
        }

        System.exit(0);
    }
}
