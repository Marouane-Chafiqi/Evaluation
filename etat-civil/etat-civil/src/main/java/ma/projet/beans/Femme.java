package ma.projet.beans;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "femme")
// Requete native nommee : nombre d'enfants d'une femme entre deux dates
@NamedNativeQuery(
        name = "Femme.nbEnfantsEntreDates",
        query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m "
              + "WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :d1 AND :d2")
// Requete nommee (HQL) : femmes mariees au moins deux fois
@NamedQuery(
        name = "Femme.mariesAuMoinsDeuxFois",
        query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2")
public class Femme extends Personne {

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
}
