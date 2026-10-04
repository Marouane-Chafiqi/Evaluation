# Gestion de projets 

Application Java qui permet à un bureau d'études de suivre les projets, les tâches
et le temps réellement passé par les employés.

## Technologies
- Java 11, Maven
- Hibernate 5.6 (annotations JPA)
- H2 en mémoire (MySQL possible, voir `application.properties`)

## Structure

| Package | Contenu |
|---|---|
| `ma.projet.classes` | Entités : `Employe`, `Projet`, `Tache`, `EmployeTache` |
| `ma.projet.util` | `HibernateUtil` : lit `application.properties` et crée la `SessionFactory` |
| `ma.projet.dao` | `IDao<T>` : interface générique (create, update, delete, findById, findAll) |
| `ma.projet.service` | `AbstractService<T>` (code commun) + `EmployeService`, `ProjetService`, `TacheService`, `EmployeTacheService` |
| `ma.projet.test` | `Test` : programme de test |

## Modèle
- `Employe` 1 — * `Projet` : chef de projet (`@ManyToOne` dans `Projet`)
- `Projet` 1 — * `Tache` : composition (`@ManyToOne` dans `Tache`)
- `Employe` * — * `Tache` via la classe d'association `EmployeTache`
  (dates **réelles** de début et de fin, avec deux `@ManyToOne`)

Les dates de `Tache` sont les dates **prévues** ; les dates de `EmployeTache` sont les dates **réelles**.

## Méthodes demandées

| Service | Méthode | Rôle |
|---|---|---|
| `EmployeService` | `afficherTachesRealisees(Employe)` | tâches réalisées par un employé |
| `EmployeService` | `afficherProjetsGeres(Employe)` | projets gérés par un employé |
| `ProjetService` | `afficherTachesPlanifiees(Projet)` | tâches planifiées (dates prévues) |
| `ProjetService` | `afficherTachesRealisees(Projet)` | tâches réalisées avec dates réelles (format de l'énoncé) |
| `TacheService` | `findPrixSuperieur1000()` | tâches dont le prix > 1000 DH (requête nommée `Tache.prixSuperieur1000`) |
| `TacheService` | `findRealiseesEntreDates(d1, d2)` | tâches réalisées entre deux dates (début réel ≥ d1 et fin réelle ≤ d2) |

## Lancer le projet
1. Ouvrir le dossier dans IntelliJ (File > Open) puis recharger Maven.
2. Exécuter la classe `ma.projet.test.Test`.

## Exécution
<img width="376" height="516" alt="Capture d&#39;écran 2026-10-04 024032" src="https://github.com/user-attachments/assets/2eba71a0-8078-4510-9304-05030c8a9a47" />




