package ma.projet.classes;

import java.util.Date;
import javax.persistence.*;

@Entity
// Requête nommée : tâches dont le prix > 1000 DH
@NamedQuery(name = "Tache.prixSuperieur1000",
            query = "from Tache t where t.prix > 1000 order by t.id")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    // Dates PREVUES (les dates réelles sont dans EmployeTache)
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private double prix;

    // Projet 1 ---- * Tache  (composition)
    @ManyToOne
    private Projet projet;

    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }

    @Override
    public String toString() {
        return "Tache [" + id + " - " + nom + " - " + (int) prix + " DH]";
    }
}
