package gen_diagrammes;

import gen_diagrammes.controleurs.*;
import gen_diagrammes.diagramme.*;
import gen_diagrammes.vues.VueClasse;
import gen_diagrammes.vues.VueDiagramme;
import gen_diagrammes.vues.VueListeClasses;
import gen_diagrammes.vues.VueRelation;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de l'application
 */
public class Main extends Application {

    private double dragStartX;
    private double dragStartY;
    private double offsetX;
    private double offsetY;
    public static List<VueRelation> relations = new ArrayList<>();
    public static double scaleFactor = 1.0;
    public static VueDiagramme stackPane;
    public static boolean afficherAttributs = true;
    public static boolean afficherMethodes = true;
    public static boolean afficherRelations = true;
    public static boolean afficherAssociations = true;
    public static boolean afficherHeritages = true;
    public static boolean afficherImplementations = true;

    /**
     * Méthode principale
     * Démarre l'interface graphique
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";

        System.out.println(ANSI_GREEN + "╔══════════════════╗" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "║ " + ANSI_BLUE + "   Plante UML   " + ANSI_GREEN + " ║" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "╚══════════════════╝" + ANSI_RESET);

        System.setProperty("jps.track.ap.dependencies", "true");
        Application.launch(Main.class, args);

    }


    /**
     * Affiche l'interface graphique
     *
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Plante UML");
        primaryStage.getIcons().add(new Image("file:ressource/logo_PlanteUML.png"));

        // Création de la barre de menu
        MenuBar menuBar = new MenuBar();
        Menu menuFichier = new Menu("Fichier");
        MenuItem menuFichierNouveau = new MenuItem("Nouveau diagramme");
        MenuItem menuFichierCharger = new MenuItem("Ouvrir un diagramme");
        MenuItem menuFichierEnregistrerSous = new MenuItem("Enregistrer sous");
        MenuItem menuFichierEnregistrer = new MenuItem("Enregistrer");
        MenuItem menuFichierQuitter = new MenuItem("Quitter");
        menuFichier.getItems().addAll(menuFichierNouveau, menuFichierCharger, new SeparatorMenuItem(), menuFichierEnregistrerSous, menuFichierEnregistrer, new SeparatorMenuItem(), menuFichierQuitter);
        Menu menuAjouter = new Menu("Ajouter");
        MenuItem menuAjouterPackage = new MenuItem("Ajouter un package");
        MenuItem menuAjouterClasse = new MenuItem("Ajouter une classe");
        Menu menuSupprimer = new Menu("Supprimer");
        MenuItem menuSupprimerClasses = new MenuItem("Supprimer une classe");
        MenuItem menuSupprimerToutesClasses = new MenuItem("Supprimer toutes les classes");
        Menu menuExporter = new Menu("Exporter");
        MenuItem menuExporterImage = new MenuItem("Exporter une image");
        MenuItem menuExporterUML = new MenuItem("Exporter en PlantUML");
        Menu menuGenerer = new Menu("Générer");
        MenuItem menuCreer = new MenuItem("Créer une nouvelle classe");
        menuGenerer.getItems().add(menuCreer);
        Menu menuAffichage = new Menu("Affichage");
        MenuItem menuAfficherDiagramme = new MenuItem("Afficher le diagramme");
        MenuItem menuAfficherToutesClasses = new MenuItem("Afficher toutes les classes");
        MenuItem menuMasquerToutesClasses = new MenuItem("Masquer toutes les classes");
        MenuItem menuAfficherToutesRelations = new MenuItem("Afficher toutes les relations");
        MenuItem menuMasquerToutesRelations = new MenuItem("Masquer toutes les relations");
        CheckMenuItem menuAfficherTousAttributs = new CheckMenuItem("Afficher tous les attributs");
        CheckMenuItem menuAfficherToutesMethodes = new CheckMenuItem("Afficher toutes les méthodes");
        CheckMenuItem menuAfficherAssociations = new CheckMenuItem("Afficher les associations");
        CheckMenuItem menuAfficherHeritages = new CheckMenuItem("Afficher les héritages");
        CheckMenuItem menuAfficherImplementations = new CheckMenuItem("Afficher les implémentations");

        menuAjouter.getItems().addAll(menuAjouterPackage, menuAjouterClasse);
        menuSupprimer.getItems().addAll(menuSupprimerClasses, menuSupprimerToutesClasses);
        menuExporter.getItems().addAll(menuExporterImage, menuExporterUML);
        menuAffichage.getItems().addAll(menuAfficherDiagramme, new SeparatorMenuItem(), menuAfficherToutesClasses, menuMasquerToutesClasses, menuAfficherToutesRelations, menuMasquerToutesRelations, new SeparatorMenuItem(), menuAfficherTousAttributs, menuAfficherToutesMethodes, menuAfficherAssociations, menuAfficherHeritages, menuAfficherImplementations);
        menuBar.getMenus().addAll(menuFichier, menuAjouter, menuSupprimer, menuExporter, menuGenerer, menuAffichage);
        menuBar.setViewOrder(-1);

        Pane classContainer = new Pane(); // Conteneur pour les classes
        stackPane = new VueDiagramme();
        // Diagramme.getInstance().ajouterObservateur((Observateur) stackPane);
        // création de la mise en page principale
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(stackPane);

        // Création du menu affichant les classes ajoutées
        VueListeClasses vueListeClasses = new VueListeClasses();
        borderPane.setLeft(vueListeClasses);

        Diagramme.getInstance().ajouterObservateur(vueListeClasses);

        // création de la scène et l'ajouter à la fenêtre principale
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Ajout des styles pour le curseur
        stackPane.setOnMouseExited(event -> {
            stackPane.setCursor(Cursor.DEFAULT);
        });

//        // Ajout des gestionnaires d'événements pour le déplacement du fond
//        stackPane.setOnMousePressed(event -> {
//            if (event.getTarget() == stackPane) {
//                dragStartX = event.getSceneX();
//                dragStartY = event.getSceneY();
//                offsetX = stackPane.getTranslateX();
//                offsetY = stackPane.getTranslateY();
//            }
//        });
//
//        stackPane.setOnMouseDragged(event -> {
//            if (event.getTarget() == stackPane) {
//                double deltaX = event.getSceneX() - dragStartX;
//                double deltaY = event.getSceneY() - dragStartY;
//                stackPane.setTranslateX(offsetX + deltaX);
//                stackPane.setTranslateY(offsetY + deltaY);
//            }
//        });

        // Ajout des classes au conteneur
        stackPane.getChildren().add(classContainer);

        // Ajout des gestionnaires d'événements pour les classes
        for (Node node : classContainer.getChildren()) {
            if (node instanceof VueClasse) {
                node.setOnMousePressed(event -> {
                    event.consume(); // Empêche la propagation de l'événement
                });
            }
        }


        //------------------------------------------------FIN INTERFACE GRAPHIQUE------------------------------------------------


        // Ajout du gestionnaire d'événements de la molette pour zoomer/dezoomer
        scene.addEventFilter(ScrollEvent.SCROLL, event -> {
            double oldScale = scaleFactor;
            if (event.getDeltaY() > 0) {
                scaleFactor *= 1.1;
            } else {
                scaleFactor /= 1.1;
            }
            double f = (scaleFactor / oldScale) - 1;

            // Calculer les décalages en fonction de la position du curseur
            double dx = (event.getSceneX() - (stackPane.getBoundsInParent().getWidth() / 2 + stackPane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (stackPane.getBoundsInParent().getHeight() / 2 + stackPane.getBoundsInParent().getMinY()));

            // Appliquer le facteur de zoom
            stackPane.setScaleX(scaleFactor);
            stackPane.setScaleY(scaleFactor);

            // Ajuster les coordonnées de translation pour centrer le zoom sur le curseur
            stackPane.setTranslateX(stackPane.getTranslateX() - f * dx);
            stackPane.setTranslateY(stackPane.getTranslateY() - f * dy);

            event.consume();
        });

        // Déplacement avec les flèches du clavier
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                    stackPane.setTranslateY(stackPane.getTranslateY() - 20);
                    break;
                case UP:
                    stackPane.setTranslateY(stackPane.getTranslateY() + 20);
                    break;
                case RIGHT:
                    stackPane.setTranslateX(stackPane.getTranslateX() - 20);
                    break;
                case LEFT:
                    stackPane.setTranslateX(stackPane.getTranslateX() + 20);
                    break;
                default:
                    break;
            }
        });


        // AJOUTER UNE CLASSE
        menuAjouterClasse.setOnAction(new AjouterClasseControleur(stackPane));


        // AJOUTER UN PACKAGE
        menuAjouterPackage.setOnAction(new AjouterPackageControleur(stackPane));


        // AFFICHER LE DIAGRAMME
        menuAfficherDiagramme.setOnAction(new AfficherDiagrammeControleur(stackPane));


        // EXPORTER UNE IMAGE
        menuExporterImage.setOnAction(new ExporterImageControleur(primaryStage, stackPane));

        // EXPORTER UN PLANTUML
        menuExporterUML.setOnAction(new ExporterPUMLControleur(primaryStage));


        // AFFICHER TOUTES LES CLASSES
        menuAfficherToutesClasses.setOnAction(new AfficherToutesLesClassesControleur());

        // MASQUER TOUTES LES CLASSES
        menuMasquerToutesClasses.setOnAction(new MasquerToutesLesClassesControleur());

        // AFFICHER/MASQUER TOUS LES ATTRIBUTS
        menuAfficherTousAttributs.setSelected(true);
        menuAfficherTousAttributs.setOnAction(new AfficherTousAttributsControleur(stackPane));

        // AFFICHER/MASQUER TOUTES LES METHODES
        menuAfficherToutesMethodes.setSelected(true);
        menuAfficherToutesMethodes.setOnAction(new AfficherToutesMethodesControleur(stackPane));

        // SUPPRIMER UNE CLASSE
        menuSupprimerClasses.setOnAction(new SupprimerClasseControleur(stackPane));

        // SUPPRIMER TOUTES LES CLASSES
        menuSupprimerToutesClasses.setOnAction(new SupprimerToutesLesClassesControleur(stackPane));

        // CRÉER UNE CLASSE
        menuCreer.setOnAction(new CreerClasseControleur(stackPane, borderPane));

        // CHARGER UN DIAGRAMME
        menuFichierCharger.setOnAction(new ChargerDiagrammeControleur(stackPane));

        // NOUVEAU DIAGRAMME
        menuFichierNouveau.setOnAction(new NouveauDiagrammeControleur(stackPane));

        // ENREGISTRER UN DIAGRAMME DANS UN FICHIER
        menuFichierEnregistrerSous.setOnAction(new EnregistrerDiagrammeSousControleur(stackPane));

        // ENREGISTRER UN DIAGRAMME EXISTANT
        menuFichierEnregistrer.setOnAction(new EnregistrerDiagrammeControleur(stackPane));

        // QUITTER L'APPLICATION
        menuFichierQuitter.setOnAction(new QuitterAppliControleur(stackPane));

        // AFFICHER TOUTES LES RELATIONS
        menuAfficherToutesRelations.setOnAction(event -> {
            afficherAssociations = true;
            afficherHeritages = true;
            afficherImplementations = true;
            menuAfficherHeritages.setSelected(true);
            menuAfficherAssociations.setSelected(true);
            menuAfficherImplementations.setSelected(true);
            updateRelations();
        });

        // MASQUER TOUTES LES RELATIONS
        menuMasquerToutesRelations.setOnAction(event -> {
            afficherAssociations = false;
            afficherHeritages = false;
            afficherImplementations = false;
            menuAfficherHeritages.setSelected(false);
            menuAfficherAssociations.setSelected(false);
            menuAfficherImplementations.setSelected(false);
            updateRelations();
        });

        // AFFICHER/MASQUER LES ASSOCIATIONS
        menuAfficherAssociations.setSelected(true);
        menuAfficherAssociations.setOnAction(event -> {
            afficherAssociations = menuAfficherAssociations.isSelected();
            updateRelations();
        });

        // AFFICHER/MASQUER LES HERITAGES
        menuAfficherHeritages.setSelected(true);
        menuAfficherHeritages.setOnAction(event -> {
            afficherHeritages = menuAfficherHeritages.isSelected();
            updateRelations();
        });

        // AFFICHER/MASQUER LES IMPLEMENTATIONS
        menuAfficherImplementations.setSelected(true);
        menuAfficherImplementations.setOnAction(event -> {
            afficherImplementations = menuAfficherImplementations.isSelected();
            updateRelations();
        });

        // Affiche le diagramme
        menuAfficherDiagramme.fire();
        stackPane.requestFocus();

    }

    /**
     * Détermine le type de relation à partir de la relation
     *
     * @param relation Relation
     * @return Type de relation
     */
    public static VueRelation.TypeRelation determineTypeRelation(Relation relation) {
        return switch (relation.getType()) {
            case "heritage" -> VueRelation.TypeRelation.HERITAGE;
            case "implementation" -> VueRelation.TypeRelation.IMPLEMENTATION;
            default -> VueRelation.TypeRelation.ASSOCIATION;
        };
    }


    /**
     * Ajoute la possibilité de déplacer un nœud par glisser-déposer
     *
     * @param node Noeud à rendre déplaçable
     */
    public static void makeDraggable(Node node) {
        final double[] dragDelta = new double[2];
        node.setOnMousePressed(e -> {
            dragDelta[0] = node.getLayoutX() - e.getSceneX() / scaleFactor;
            dragDelta[1] = node.getLayoutY() - e.getSceneY() / scaleFactor;
            node.setCursor(javafx.scene.Cursor.MOVE);
        });

        node.setOnMouseReleased(e -> node.setCursor(javafx.scene.Cursor.HAND));

        node.setOnMouseDragged(e -> {
            double newX = e.getSceneX() / scaleFactor + dragDelta[0];
            double newY = e.getSceneY() / scaleFactor + dragDelta[1];
            node.setLayoutX(newX);
            node.setLayoutY(newY);
        });

        node.setOnMouseEntered(e -> {
            if (!e.isPrimaryButtonDown()) {
                node.setCursor(javafx.scene.Cursor.HAND);
            }
        });

        node.setOnMouseExited(e -> {
            if (!e.isPrimaryButtonDown()) {
                node.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });
    }

    /**
     * Met à jour les relations entre les classes
     */
    public static void updateRelations() {
        for (VueRelation vueRelation : relations) {
            if (afficherRelations) {
                vueRelation.setVisible((vueRelation.getTypeRelation() == VueRelation.TypeRelation.ASSOCIATION && afficherAssociations) ||
                        (vueRelation.getTypeRelation() == VueRelation.TypeRelation.HERITAGE && afficherHeritages) ||
                        (vueRelation.getTypeRelation() == VueRelation.TypeRelation.IMPLEMENTATION && afficherImplementations));
            } else {
                vueRelation.setVisible(false);
            }
            vueRelation.actualiser();
        }
    }

    /**
     * Ajoute une classe à partir d'un fichier
     *
     * @param file      Fichier
     * @param stackPane StackPane : Conteneur du diagramme
     * @return Classe
     */
    public static Classe ajouterClasseDepuisFichier(File file, StackPane stackPane) {
        Classe classe = null;
        try {
            classe = new Classe(file.getAbsolutePath());
            // N'affiche pas les classes anonymes (générées par Java)
            if (classe.getNom() != null) {
                classe.setLongueur(Math.random() * 600);
                classe.setLargeur(Math.random() * 300);
                int etatAjout = Diagramme.getInstance().ajouterClasse(classe);
                if (etatAjout == 0) {
                    // Affiche un message demandant s'il faut remplacer ou non la classe existante
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Classe existante");
                    String typeClasse = classe.getTypeClasseString();
                    if (typeClasse.equals("interface")) {
                        typeClasse = "L'interface";
                    } else {
                        typeClasse = "La " + typeClasse;
                    }
                    String nomClassePackage = classe.getNomPackage() != null ? classe.getNomPackage() + "." + classe.getNom() : classe.getNom();
                    alert.setHeaderText(typeClasse + " " + nomClassePackage + " est déjà présente sur le diagramme.");
                    alert.setContentText("Voulez-vous remplacer la classe existante ?");
                    Classe finalClasse = classe;
                    alert.showAndWait().ifPresent(type -> {
                        if (type == ButtonType.OK) {
                            Diagramme.getInstance().supprimerClasse(finalClasse.getNom(), finalClasse.getNomPackage());
                            Diagramme.getInstance().ajouterClasse(finalClasse);
                            System.out.println(finalClasse.getTypeClasseString() + " " + finalClasse.getNom() + " remplacée");
                        }
                    });
                } else if (etatAjout == -1) {
                    System.err.println("Erreur lors de l'ajout d'une classe nulle");
                } else {
                    System.out.println(classe.getTypeClasseString() + " " + classe.getNom() + " ajoutée");
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        stackPane.getChildren().clear();
        VueDiagramme v = (VueDiagramme) stackPane;
        v.actualiser();
        Diagramme.getInstance().notifierObservateurs();
        return classe;
    }

    public static void ajouterRelationsPourClasse(Classe nouvelleClasse) {
        Diagramme diagramme = Diagramme.getInstance();

        if (nouvelleClasse == null) {
            return;
        }

        // Vérification et ajout des relations d'héritage ou d'implémentation
        for (Classe parent : nouvelleClasse.getParents()) {
            String typeRelation = parent.getType().equals(Classe.INTERFACE) ? "implementation" : "heritage";
            // Vérifiez si la relation existe déjà
            if (!diagramme.contientRelation(nouvelleClasse, parent)) {
                // Vérifiez si les dimensions du parent sont valides
                if (parent.getLongueur() <= 0 || parent.getLargeur() <= 0) {
                    parent.setLongueur(Math.random() * 600);
                    parent.setLargeur(Math.random() * 300);
                }

                // Ajout de la relation
                Relation relation = new Relation(nouvelleClasse, parent, typeRelation);
                diagramme.ajouterRelation(relation);
            }
        }

        // Vérification et ajout des relations d'association, agrégation et composition
        for (Attribut attribut : nouvelleClasse.getAttributs()) {
            if (attribut instanceof AttributClasse) {
                Classe classeAssociee = ((AttributClasse) attribut).getAttribut();
                String typeRelation = "association"; // Par défaut association

                if (!diagramme.contientRelation(nouvelleClasse, classeAssociee)) {
                    // Vérifiez si les dimensions de la classe associée sont valides
                    if (classeAssociee.getLongueur() <= 0 || classeAssociee.getLargeur() <= 0) {
                        System.err.println("Dimensions invalides pour la classe associée : " + classeAssociee.getNom());
                        classeAssociee.setLongueur(Math.random() * 600);
                        classeAssociee.setLargeur(Math.random() * 300);
                        System.out.println("Dimensions corrigées pour la classe associée : Longueur = " + classeAssociee.getLongueur() + ", Largeur = " + classeAssociee.getLargeur());
                    }

                    // Ajout de la relation
                    Relation relation = new Relation(nouvelleClasse, classeAssociee, typeRelation);
                    diagramme.ajouterRelation(relation);
                }
            }
        }
    }
}