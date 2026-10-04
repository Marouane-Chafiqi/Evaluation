package ma.projet.classes;

import java.util.Date;
import javax.persistence.*;

/**
 * Classe d'association (Employe * --- * Tache) : kat7tafed b les dates RÉELLES.
 * Kanderouha entité m3a jouj @ManyToOne.
 */
@Entity
public class EmployeTache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    @ManyToOne
    private Employe employe;

    @ManyToOne
    private Tache tache;

    public EmployeTache() {
    }

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date d) { this.dateDebutReelle = d; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date d) { this.dateFinReelle = d; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
}
