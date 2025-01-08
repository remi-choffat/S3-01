package gen_diagrammes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de l'application
 */
public class Main extends Application {

    static List<VueRelation> relations = new ArrayList<>();
    private double scaleFactor = 1.0;
    static StackPane stackPane;

    static boolean afficherAttributs = true;
    static boolean afficherMethodes = true;


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

        //------------------------------------------------INTERFACE GRAPHIQUE------------------------------------------------
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
        CheckMenuItem menuAfficherTousAttributs = new CheckMenuItem("Afficher tous les attributs");
        CheckMenuItem menuAfficherToutesMethodes = new CheckMenuItem("Afficher toutes les méthodes");
        menuAjouter.getItems().addAll(menuAjouterPackage, menuAjouterClasse);
        menuSupprimer.getItems().addAll(menuSupprimerClasses, menuSupprimerToutesClasses);
        menuExporter.getItems().addAll(menuExporterImage, menuExporterUML);
        menuAffichage.getItems().addAll(menuAfficherDiagramme, new SeparatorMenuItem(), menuAfficherToutesClasses, menuMasquerToutesClasses, menuAfficherTousAttributs, menuAfficherToutesMethodes);
        menuBar.getMenus().addAll(menuFichier, menuAjouter, menuSupprimer, menuExporter, menuGenerer, menuAffichage);
        menuBar.setViewOrder(-1);

        stackPane = new VueDiagramme();
        Diagramme.getInstance().ajouterObservateur((Observateur)stackPane);

        // création de la mise en page principale
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(stackPane);

        // Création du menu affichant les classes ajoutées
        VueListeClasses vueListeClasses = new VueListeClasses();
        // ScrollPane pour afficher toute la liste
        ScrollPane scrollPane = new ScrollPane(vueListeClasses);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFocusTraversable(false);
        borderPane.setLeft(scrollPane);
        Diagramme.getInstance().ajouterObservateur(vueListeClasses);

        // création de la scène et l'ajouter à la fenêtre principale
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //------------------------------------------------FIN INTERFACE GRAPHIQUE------------------------------------------------


        // Ajout du gestionnaire d'événements de la molette pour zoomer/dezoomer
        stackPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0) {
                scaleFactor *= 1.1;
            } else {
                scaleFactor /= 1.1;
            }
            stackPane.setScaleX(scaleFactor);
            stackPane.setScaleY(scaleFactor);
            event.consume();
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
        menuCreer.setOnAction(new CreerClasseControleur(stackPane));

        // CHARGER UN DIAGRAMME
        menuFichierCharger.setOnAction(new ChargerDiagrammeControleur());

        // NOUVEAU DIAGRAMME
        menuFichierNouveau.setOnAction(new NouveauDiagrammeControleur());

        // ENREGISTRER UN DIAGRAMME DANS UN FICHIER
        menuFichierEnregistrerSous.setOnAction(new EnregistrerDiagrammeSousControleur());

        // ENREGISTRER UN DIAGRAMME EXISTANT
        menuFichierEnregistrer.setOnAction(new EnregistrerDiagrammeControleur());
        menuFichierEnregistrer.setDisable(true);

        // QUITTER L'APPLICATION
        menuFichierQuitter.setOnAction(new QuitterAppliControleur());

    }


    /**
     * Détermine le type de relation à partir de la relation
     *
     * @param relation Relation
     * @return Type de relation
     */
    private VueRelation.TypeRelation determineTypeRelation(Relation relation) {
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
    static void makeDraggable(Node node) {
        final double[] dragDelta = new double[2];

        node.setOnMousePressed(e -> {
            dragDelta[0] = node.getLayoutX() - e.getSceneX();
            dragDelta[1] = node.getLayoutY() - e.getSceneY();
            node.setCursor(javafx.scene.Cursor.MOVE);
        });

        node.setOnMouseReleased(e -> node.setCursor(javafx.scene.Cursor.HAND));

        node.setOnMouseDragged(e -> {
            double newX = e.getSceneX() + dragDelta[0];
            double newY = e.getSceneY() + dragDelta[1];

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
    static void updateRelations() {
        for (VueRelation vueRelation : relations) {
            vueRelation.actualiser();
        }
    }


    /**
     * Ajoute une classe à partir d'un fichier
     *
     * @param file      Fichier
     * @param stackPane StackPane : Conteneur du diagramme
     */
    public static void ajouterClasseDepuisFichier(File file, StackPane stackPane) {
        try {
            Classe classe = new Classe(file.getAbsolutePath());
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
                    alert.setHeaderText(typeClasse + " " + classe.getNomPackage() + "." + classe.getNom() + " est déjà présente sur le diagramme.");
                    alert.setContentText("Voulez-vous remplacer la classe existante ?");
                    alert.showAndWait().ifPresent(type -> {
                        if (type == ButtonType.OK) {
                            Diagramme.getInstance().supprimerClasse(classe.getNom(), classe.getNomPackage());
                            Diagramme.getInstance().ajouterClasse(classe);
                            System.out.println(classe.getTypeClasseString() + " " + classe.getNom() + " remplacée");
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
        Diagramme.getInstance().afficher(stackPane);
    }

}