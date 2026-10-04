package ma.projet.classes;

import javax.persistence.*;

@Entity
// Requête nommée : produits dont le prix > 100 DH
@NamedQuery(name = "Produit.prixSuperieur100",
            query = "from Produit p where p.prix > 100")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;

    // * Produit  ---->  1 Categorie
    @ManyToOne
    private Categorie categorie;

    public Produit() {
    }

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit [" + reference + " - " + (int) prix + " DH]";
    }
}
