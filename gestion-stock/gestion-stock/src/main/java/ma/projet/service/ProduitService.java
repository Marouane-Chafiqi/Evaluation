package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.TemporalType;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class ProduitService extends AbstractService<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    // 1) Liste des produits d'une catégorie
    public List<Produit> findByCategorie(Categorie categorie) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Produit p where p.categorie.id = :idCat", Produit.class)
                    .setParameter("idCat", categorie.getId())
                    .list();
        }
    }

    // 2) Produits commandés entre deux dates
    public List<Produit> findCommandesEntreDates(Date debut, Date fin) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                        "select distinct l.produit from LigneCommandeProduit l "
                      + "where l.commande.date between :d1 and :d2", Produit.class)
                    .setParameter("d1", debut, TemporalType.DATE)
                    .setParameter("d2", fin, TemporalType.DATE)
                    .list();
        }
    }

    // 3) Afficher les produits d'une commande (format demandé dans l'énoncé)
    public void afficherProduitsParCommande(Commande commande) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<LigneCommandeProduit> lignes = s.createQuery(
                        "from LigneCommandeProduit l where l.commande.id = :idCmd",
                        LigneCommandeProduit.class)
                    .setParameter("idCmd", commande.getId())
                    .list();

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            System.out.println("Commande : " + commande.getId()
                    + "     Date : " + sdf.format(commande.getDate()));
            System.out.println("Liste des produits :");
            System.out.printf("%-12s%-8s%s%n", "Référence", "Prix", "Quantité");
            for (LigneCommandeProduit l : lignes) {
                System.out.printf("%-12s%-8s%d%n",
                        l.getProduit().getReference(),
                        (int) l.getProduit().getPrix() + " DH",
                        l.getQuantite());
            }
        }
    }

    // 4) Produits dont le prix > 100 DH (requête nommée définie dans Produit)
    public List<Produit> findPrixSuperieur100() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Produit.prixSuperieur100", Produit.class).list();
        }
    }
}
