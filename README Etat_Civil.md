# Gestion de l'etat civil

Application Java (Maven + Hibernate + MySQL) pour gerer les citoyens (hommes / femmes) et leurs mariages.

## Lancer le projet
1. Demarrer MySQL (la base `etat_civil` est creee automatiquement).
2. Adapter `src/main/resources/application.properties` (utilisateur / mot de passe).
3. Ouvrir le projet dans IntelliJ (Maven) puis executer `ma.projet.test.Test` (JDK 17+).

## Structure
- `ma.projet.beans` : `Personne`, `Homme`, `Femme`, `Mariage`
- `ma.projet.util` : `HibernateUtil`
- `ma.projet.dao` : `IDao`
- `ma.projet.service` : `HommeService`, `FemmeService`, `MariageService`
- `ma.projet.test` : `Test` (programme de test)

## Execution
<img width="327" height="450" alt="Capture d&#39;écran 2026-10-04 025412" src="https://github.com/user-attachments/assets/be0de2fb-a2e7-44f1-8697-5124f94f6ba6" />


