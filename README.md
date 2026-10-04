# Gestion de stock

Application Java qui gère le stock d'un magasin de produits informatiques :
catégories, produits, commandes et lignes de commande.

## Technologies
- Java 11, Maven
- Hibernate 5.6 (annotations JPA)
- H2 en mémoire (MySQL possible, voir `application.properties`)

## Structure

| Package | Contenu |
|---|---|
| `ma.projet.classes` | Entités : `Categorie`, `Produit`, `Commande`, `LigneCommandeProduit` |
| `ma.projet.util` | `HibernateUtil` : lit `application.properties` et crée la `SessionFactory` |
| `ma.projet.dao` | `IDao<T>` : interface générique (create, update, delete, findById, findAll) |
| `ma.projet.service` | `AbstractService<T>` (code commun) + `ProduitService`, `CategorieService`, `CommandeService`, `LigneCommandeService` |
| `ma.projet.test` | `Test` : programme de test |

## Modèle
- `Categorie` 1 — * `Produit` (`@ManyToOne` dans `Produit`)
- `Produit` * — * `Commande` via la classe d'association `LigneCommandeProduit` (attribut `quantite`), avec deux `@ManyToOne`

## Méthodes de `ProduitService`
- `findByCategorie(Categorie)` : produits d'une catégorie
- `findCommandesEntreDates(Date, Date)` : produits commandés entre deux dates
- `afficherProduitsParCommande(Commande)` : produits d'une commande avec prix et quantité
- `findPrixSuperieur100()` : produits dont le prix > 100 DH (requête nommée `Produit.prixSuperieur100`)

## Lancer le projet
1. Ouvrir le dossier dans IntelliJ (File > Open) puis recharger Maven.
2. Exécuter la classe `ma.projet.test.Test`.

## Exécution
<img width="426" height="400" alt="Capture d&#39;écran 2026-10-04 023341" src="https://github.com/user-attachments/assets/db62d88b-ba8b-4a56-ba92-2a90740ea8bb" />



