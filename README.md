# 🌱 Plante UML

_Génération automatique de diagrammes de classes_

**Plante UML** est une application Java permettant de générer automatiquement des diagrammes de classes à partir de
fichiers `.class` ou de packages contenant ces fichiers. Grâce à une interface graphique intuitive, vous pouvez
manipuler, visualiser et exporter vos diagrammes en quelques clics.

> 💻 Pour télécharger l'application, voir [cette page](https://webetu.iutnc.univ-lorraine.fr/www/choffat2u/S3/S3-01/).

---

## 🚀 Fonctionnalités principales

### 🔹 Ajout

- Importation de classes ou de packages depuis n'importe quel emplacement de stockage.

### 🔹 Exportation

- Exportation du diagramme en format UML.
- Exportation du diagramme en tant qu'image.

### 🔹 Affichage

- Affichage du diagramme complet.
- Affichage/masquage :
    - Une ou toutes les classes.
    - Les classes parentes d’une classe.
    - Toutes les relations, héritages ou implémentations.
    - Les attributs et méthodes d’une ou de plusieurs classes.

### 🔹 Gestion des fichiers

- Enregistrement d'un diagramme au format `.plante`.
- Chargement d'un diagramme au format `.plante`.
- Génération de nouveaux diagrammes.

### 🔹 Navigation et manipulation

- Création et suppression de classes directement depuis le diagramme.
- Déplacement des classes par glisser-déposer.
- Navigation dans le diagramme avec les flèches directionnelles et zoom/dézoom.

---

## 🛠️ Installation et utilisation

### 1️⃣ Cloner le dépôt GitHub

Pour commencer, clonez ce dépôt sur votre machine :

```bash
git clone git@github.com:remi-choffat/S3-01.git
```

### 2️⃣ Configurer IntelliJ IDEA

👉 Vous pouvez également utiliser un autre IDE ou un éditeur de texte pour exécuter l'application.

#### ➡️ Configurer le JDK

1. Allez dans `File > Project Structure > SDKs`.
2. Ajoutez le JDK 21 si ce n’est pas déjà fait.
3. Assurez-vous que le projet utilise Java 21 :

- Allez dans `File > Project Structure > Modules`.
- Vérifiez que le module est associé au JDK 21.

#### ➡️ Ajouter JavaFX depuis Maven

1. Allez dans `File > Project Structure > Libraries > + > From Maven`.
2. Ajoutez les bibliothèques suivantes :

- `openjfx:javafx.base:21`
- `openjfx:javafx.controls:21`
- `openjfx:javafx.swing:21`.

3. IntelliJ téléchargera automatiquement les dépendances.

#### ➡️ Configurer Lombok

1. Installez le plugin Lombok :

- Allez dans `File > Settings > Plugins > Marketplace`.
- Recherchez et installez "Lombok".

2. Activez le traitement des annotations :

- Allez dans `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`.
- Activez l'option `Enable annotation processing`.

### 3️⃣ Exécuter l’application

Créez une configuration d'exécution ou exécutez directement le fichier principal : `gen_diagrammes.Main`.

---

### 🔍 Accès aux fonctionnalités

- **Menu principal :**
    - **Fichier :** Créer, ouvrir et enregistrer vos diagrammes.
    - **Ajouter :** Ajouter des classes ou des packages.
    - **Supprimer :** Supprimer des classes du diagramme.
    - **Exporter :** Exporter les diagrammes en image ou en PlantUML.
    - **Générer :** Créer une classe manuellement.
    - **Affichage :** Contrôler la visibilité des éléments du diagramme.
- **Zoom et navigation :**
    - Utilisez la molette pour zoomer/dézoomer.
    - Utilisez les flèches directionnelles pour naviguer sur le diagramme.
    - Déplacez les éléments par glisser-déposer.

---

## 📚 Documentation

La documentation complète de l'application est disponible dans le dossier `docs` du dépôt. Vous y trouverez les rapports
de conception et d'analyse, les diagrammes UML de classes et de séquence, ainsi que nos comptes-rendus d'itérations
suivant l'avancement du projet.

## ✏️ Contributeurs

- Rémi Choffat - [remi-choffat](https://github.com/remi-choffat) (remi.choffat1@etu.univ-lorraine.fr)</br>
- Noah Laghlali - [MrPichou](https://github.com/MrPichou) (noah.laghlali5@etu.univ-lorraine.fr)</br>
- Tuline Leveque - [tuline-leveque](https://github.com/tuline-leveque) (tuline.leveque6@etu.univ-lorraine.fr) </br>
- Gabin Mathieu - [GabinM](https://github.com/GabinM) (gabin.mathieu4@etu.univ-lorraine.fr)
