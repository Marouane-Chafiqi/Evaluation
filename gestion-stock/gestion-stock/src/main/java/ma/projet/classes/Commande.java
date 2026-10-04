package ma.projet.classes;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_commande")
    private Date date;

    public Commande() {
    }

    public Commande(Date date) {
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString() {
        return "Commande [" + id + " - " + date + "]";
    }
}
