# 🚗 Application de Gestion de Location de Voitures

> **Projet académique — Rapport & Documentation technique**  
> Réalisé par **Siham Talbi**
> Encadré par **Pr. Mohammed Berrada** & **Pr. Mohammed Moutaib**  
> Année universitaire : **2024-2025**

---

## 📋 Table des matières

- [Présentation du projet](#-présentation-du-projet)
- [Fonctionnalités clés](#-fonctionnalités-clés)
- [Architecture & Conception](#-architecture--conception)
- [Technologies utilisées](#-technologies-utilisées)
- [Captures d'écran](#-captures-décran)
- [Installation & Exécution](#-installation--exécution)
- [Structure du projet](#-structure-du-projet)
- [Perspectives d'amélioration](#-perspectives-damélioration)
- [Remerciements](#-remerciements)

---

## 🎯 Présentation du projet

Ce projet consiste en la conception et le développement d'une **application desktop de gestion de location de voitures**, destinée aux agences de location et gestionnaires de flottes automobiles. L'application centralise la gestion des **véhicules**, des **clients**, des **contrats** et de la **facturation**, avec une authentification sécurisée et une gestion des rôles (Administrateur / Utilisateur / Client).

Développée en **Java** avec une base de données **MS Access**, l'application suit une architecture **MVC** et propose des interfaces ergonomiques pour la manipulation des données et la gestion des factures.

### 🎯 Objectifs

- **Centraliser** la gestion des véhicules, clients et contrats
- **Automatiser** la facturation (calcul des coûts, génération PDF)
- **Sécuriser** les accès via une authentification et des rôles différenciés
- **Offrir** une expérience utilisateur fluide et intuitive

---

## ✨ Fonctionnalités clés

### 🔐 Authentification & Sécurité
- Connexion sécurisée via **login / mot de passe**
- Gestion des rôles : **Administrateur**, **Utilisateur (interne)**, **Client**
- Restrictions d'accès basées sur les rôles
- Sauvegarde automatique des données

### 🚙 Gestion des véhicules
- Ajout, modification, suppression de véhicules
- Consultation des caractéristiques (marque, modèle, plaque, kilométrage, disponibilité)
- Filtrage et recherche par immatriculation, marque, disponibilité

### 👥 Gestion des clients
- Enregistrement et mise à jour des informations clients
- Consultation de l'historique des locations
- Recherche par nom ou email

### 📄 Gestion des contrats
- Création de contrats après validation des réservations
- Gestion des dates, durées et conditions
- Consultation de l'historique des contrats

### 🧾 Facturation
- Calcul automatique des coûts (durée, kilomètres)
- Génération et impression des factures
- Export au format **PDF**

### 🖥️ Espaces dédiés
- **Espace Administrateur** : contrôle total sur le système
- **Espace Client** : réservation, consultation de l'historique et des factures

---

## 🏗️ Architecture & Conception

### Diagramme de cas d'utilisation

![Diagramme de cas d'utilisation](docs/uml/usecase.png)

**Acteurs :**
- **Administrateur** : accès complet (CRUD sur toutes les entités)
- **Utilisateur interne** : gestion des véhicules et utilisateurs (sans suppression ni contrats/factures)
- **Client** : réservation et consultation de son historique

### Diagramme de classes

![Diagramme de classes](docs/uml/class.png)

**Entités principales :**
- `Client` — informations personnelles, réservations
- `Véhicule` — immatriculation, marque, catégorie, disponibilité
- `Contrat` — client, véhicule, dates, montant
- `Facture` — client, véhicule, période, montant
- `Utilisateur` — login, mot de passe, rôle

### Diagrammes de séquence

| Acteur | Description |
|--------|-------------|
| **Utilisateur** | Connexion, consultation historique, ajout client/utilisateur |
| **Administrateur** | Authentification, opérations CRUD sur toutes les entités |
| **Client** | Réservation, consultation historique et factures |

---

## 🛠️ Technologies utilisées

| Technologie | Rôle |
|-------------|------|
| **Java** | Langage principal de développement |
| **JavaFX** | Bibliothèque d'interfaces graphiques (GUI) |
| **Eclipse** | Environnement de développement intégré (IDE) |
| **MS Access** | Système de gestion de base de données relationnelle |
| **CSS** | Mise en forme et style des interfaces |
| **JDBC** | Connectivité Java ↔ Base de données |
| **Astah UML** | Modélisation UML |

---

## 📸 Captures d'écran

### 🔑 Espace Administrateur

| Écran | Description |
|-------|-------------|
| ![Connexion](docs/screenshots/admin/login.png) | Page d'authentification |
| ![Tableau de bord](docs/screenshots/admin/dashboard.png) | Tableau de bord administrateur |
| ![Gestion véhicules](docs/screenshots/admin/vehicules.png) | Module de gestion des véhicules |
| ![Gestion clients](docs/screenshots/admin/clients.png) | Module de gestion des clients |
| ![Gestion factures](docs/screenshots/admin/factures.png) | Module de facturation |
| ![Gestion contrats](docs/screenshots/admin/contrats.png) | Module de gestion des contrats |
| ![Gestion utilisateurs](docs/screenshots/admin/utilisateurs.png) | Module de gestion des utilisateurs |

### 👤 Espace Client

| Écran | Description |
|-------|-------------|
| ![Tableau de bord client](docs/screenshots/client/dashboard.png) | Tableau de bord client |
| ![Véhicules disponibles](docs/screenshots/client/vehicules.png) | Liste des véhicules disponibles |
| ![Mes réservations](docs/screenshots/client/reservations.png) | Historique des réservations |
| ![Mes factures](docs/screenshots/client/factures.png) | Liste des factures |
| ![Nouvelle réservation](docs/screenshots/client/nouvelle-reservation.png) | Formulaire de réservation |

---

## 🚀 Installation & Exécution

### Prérequis
- **Java JDK 11+**
- **Eclipse IDE** (ou tout autre IDE Java)
- **Microsoft Access** (pour la base de données)
- **JavaFX SDK** (si non inclus dans le JDK)

### Étapes d'installation

```bash
# 1. Cloner le dépôt
git clone https://github.com/votre-utilisateur/location-voitures.git
cd location-voitures

# 2. Ouvrir le projet dans Eclipse
# File → Import → Existing Projects into Workspace

# 3. Configurer la connexion à la base de données
# Modifier le fichier de configuration (src/config/db.properties)
# avec le chemin vers le fichier .accdb

# 4. Lancer l'application
# Exécuter la classe principale : src/application/Main.java
```

### Configuration de la base de données

```properties
# src/config/db.properties
db.url=jdbc:ucanaccess://C:/chemin/vers/base.accdb
db.user=
db.password=
```

---

## 📁 Structure du projet

```
location-voitures/
│
├── src/
│   ├── application/
│   │   └── Main.java                 # Point d'entrée
│   ├── controllers/                  # Contrôleurs MVC
│   │   ├── LoginController.java
│   │   ├── AdminController.java
│   │   └── ClientController.java
│   ├── models/                       # Entités et DAO
│   │   ├── Vehicule.java
│   │   ├── Client.java
│   │   ├── Contrat.java
│   │   ├── Facture.java
│   │   └── Utilisateur.java
│   ├── views/                        # Interfaces JavaFX
│   │   ├── login.fxml
│   │   ├── admin_dashboard.fxml
│   │   └── client_dashboard.fxml
│   ├── dao/                          # Accès aux données (JDBC)
│   │   ├── VehiculeDAO.java
│   │   ├── ClientDAO.java
│   │   ├── ContratDAO.java
│   │   └── FactureDAO.java
│   ├── utils/                        # Utilitaires
│   │   ├── DBConnection.java
│   │   └── PDFGenerator.java
│   └── resources/
│       ├── css/                      # Feuilles de style
│       └── images/                   # Ressources graphiques
│
├── docs/
│   ├── uml/                          # Diagrammes UML
│   ├── screenshots/                  # Captures d'écran
│   └── rapport/                      # Rapport complet
│
├── lib/                              # Bibliothèques (JAR)
│   ├── ucanaccess-x.x.x.jar
│   └── javafx-sdk/
│
├── .gitignore
├── README.md
└── LICENSE
```

---


### 🌱 Impact sociétal
- **Dématérialisation** : réduction de l'usage du papier
- **Inclusivité** : accessibilité pour les malvoyants

---

## 🙏 Remerciements

Nous tenons à exprimer notre profonde gratitude à :

- **Pr. Mohammed Berrada** et **Pr. Mohammed Moutaib**, nos encadrants, pour leur guidance précieuse, leur expertise en gestion de projet et en développement logiciel (Java, MS Access), et leur disponibilité tout au long de ce projet.
- **Nos collègues et camarades** pour leurs idées novatrices et leur soutien moral.
- **Nos familles et proches** pour leur appui constant, leur patience et leurs encouragements.

---

## 📄 Licence

Ce projet est réalisé dans un cadre **académique**. Toute reproduction ou utilisation commerciale doit faire l'objet d'une autorisation préalable.


---

<p align="center">
  <i>Projet réalisé avec ❤️ dans le cadre du cursus universitaire 2024-2025</i>
</p>
