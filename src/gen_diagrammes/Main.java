package gen_diagrammes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
    private double dragStartX;
    private double dragStartY;
    private double offsetX;
    private double offsetY;

    private List<VueRelation> relations = new ArrayList<>();
    private double scaleFactor = 1.0;
    private StackPane stackPane;

    private boolean afficherAttributs = true;
    private boolean afficherMethodes = true;


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
        Menu menuAjouter = new Menu("Ajouter");
        MenuItem menuAjouterPackage = new MenuItem("Ajouter un package");
        MenuItem menuAjouterClasse = new MenuItem("Ajouter une classe");
        Menu menuSupprimer = new Menu("Supprimer");
        Menu menuExporter = new Menu("Exporter");
        MenuItem menuExporterImage = new MenuItem("Exporter une image");
        MenuItem menuExporterUML = new MenuItem("Exporter en PlantUML");
        Menu menuGenerer = new Menu("Générer");
        Menu menuAffichage = new Menu("Affichage");
        MenuItem menuAfficherDiagramme = new MenuItem("Afficher le diagramme");
        MenuItem menuAfficherToutesClasses = new MenuItem("Afficher toutes les classes");
        MenuItem menuMasquerToutesClasses = new MenuItem("Masquer toutes les classes");
        CheckMenuItem menuAfficherTousAttributs = new CheckMenuItem("Afficher tous les attributs");
        CheckMenuItem menuAfficherToutesMethodes = new CheckMenuItem("Afficher toutes les méthodes");
        menuAjouter.getItems().addAll(menuAjouterPackage, menuAjouterClasse);
        menuExporter.getItems().addAll(menuExporterImage, menuExporterUML);
        menuAffichage.getItems().addAll(menuAfficherDiagramme, new SeparatorMenuItem(), menuAfficherToutesClasses, menuMasquerToutesClasses, menuAfficherTousAttributs, menuAfficherToutesMethodes);
        menuBar.getMenus().addAll(menuAjouter, menuSupprimer, menuExporter, menuGenerer, menuAffichage);
        menuBar.setViewOrder(-1);

        this.stackPane = new StackPane();
        Pane classContainer = new Pane(); // Conteneur pour les classes

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

        // Ajout des styles pour le curseur
        stackPane.setOnMouseEntered(event -> {
            stackPane.setCursor(Cursor.MOVE);
        });

        stackPane.setOnMouseExited(event -> {
            stackPane.setCursor(Cursor.DEFAULT);
        });

        // Ajout des gestionnaires d'événements pour le déplacement du fond
        stackPane.setOnMousePressed(event -> {
            if (event.getTarget() == stackPane) {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
                offsetX = stackPane.getTranslateX();
                offsetY = stackPane.getTranslateY();
            }
        });

        stackPane.setOnMouseDragged(event -> {
            if (event.getTarget() == stackPane) {
                double deltaX = event.getSceneX() - dragStartX;
                double deltaY = event.getSceneY() - dragStartY;
                stackPane.setTranslateX(offsetX + deltaX);
                stackPane.setTranslateY(offsetY + deltaY);
            }
        });

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
        menuAjouterClasse.setOnAction(event -> {
            stackPane.getChildren().clear();
            Button btnCenter = new Button("Sélectionner un fichier");
            ImageView imageView = new ImageView(new Image("file:ressource/logo_importe.png"));
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            VBox content = new VBox(10, imageView, btnCenter);
            content.setPadding(new Insets(20));
            content.setAlignment(Pos.CENTER);

            StackPane rectangle = new StackPane(content);
            rectangle.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            rectangle.setPrefSize(300, 200);
            rectangle.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

            rectangle.setOnDragOver(eventDragOver -> {
                if (eventDragOver.getGestureSource() != rectangle && eventDragOver.getDragboard().hasFiles()) {
                    eventDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                eventDragOver.consume();
            });

            rectangle.setOnDragDropped(eventDrop -> {
                var db = eventDrop.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    File file = db.getFiles().get(0);
                    Classe nouvelleClasse = ajouterClasseDepuisFichier(file, stackPane);
                    ajouterRelationsPourClasse(nouvelleClasse);
                }
                eventDrop.setDropCompleted(success);
                eventDrop.consume();
            });

            btnCenter.setOnAction(fileEvent -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner un fichier");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Classes Java compilées", "*.class"));
                Stage fileStage = (Stage) btnCenter.getScene().getWindow();
                File file = fileChooser.showOpenDialog(fileStage);
                if (file != null) {
                    Classe nouvelleClasse = ajouterClasseDepuisFichier(file, stackPane);
                    ajouterRelationsPourClasse(nouvelleClasse);
                }
            });

            StackPane.setAlignment(rectangle, Pos.TOP_CENTER);
            StackPane.setMargin(rectangle, new Insets(100, 0, 0, 0));
            stackPane.getChildren().add(rectangle);
        });


        // AJOUTER UN PACKAGE
        menuAjouterPackage.setOnAction(e -> {
            stackPane.getChildren().clear();
            Button btnOpenFolder = new Button("Sélectionner un dossier");
            ImageView imageView = new ImageView(new Image("file:ressource/logo_importe.png"));
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            VBox content = new VBox(10, imageView, btnOpenFolder);
            content.setPadding(new Insets(20));
            content.setAlignment(Pos.CENTER);

            StackPane rectangle = new StackPane(content);
            rectangle.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            rectangle.setPrefSize(300, 200);
            rectangle.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

            rectangle.setOnDragOver(eventDragOver -> {
                if (eventDragOver.getGestureSource() != rectangle && eventDragOver.getDragboard().hasFiles()) {
                    eventDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                eventDragOver.consume();
            });

            rectangle.setOnDragDropped(eventDrop -> {
                var db = eventDrop.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    File selectedDirectory = db.getFiles().get(0);
                    if (selectedDirectory.isDirectory()) {
                        File[] files = selectedDirectory.listFiles((dir, name) -> name.endsWith(".class"));
                        if (files != null) {
                            for (File file : files) {
                                Classe nouvelleClasse = ajouterClasseDepuisFichier(file, stackPane);
                                ajouterRelationsPourClasse(nouvelleClasse);
                            }
                        }
                    }
                }
                eventDrop.setDropCompleted(success);
                eventDrop.consume();
            });

            btnOpenFolder.setOnAction(fileEvent -> {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Sélectionner un dossier");
                Stage fileStage = (Stage) btnOpenFolder.getScene().getWindow();
                File selectedDirectory = directoryChooser.showDialog(fileStage);
                if (selectedDirectory != null) {
                    File[] files = selectedDirectory.listFiles((dir, name) -> name.endsWith(".class"));
                    if (files != null && files.length > 0) {
                        for (File file : files) {
                            Classe nouvelleClasse = ajouterClasseDepuisFichier(file, stackPane);
                            ajouterRelationsPourClasse(nouvelleClasse);
                        }
                    } else {
                        System.err.println("Aucun fichier .class trouvé dans le répertoire " + selectedDirectory.getName());
                    }
                }
            });

            StackPane.setAlignment(rectangle, Pos.TOP_CENTER);
            StackPane.setMargin(rectangle, new Insets(100, 0, 0, 0));
            stackPane.getChildren().add(rectangle);
        });


        // AFFICHER LE DIAGRAMME
        menuAfficherDiagramme.setOnAction(e -> {
            afficherDiagramme();
        });


        // EXPORTER UNE IMAGE
        menuExporterImage.setOnAction(e -> {
            // Affiche le diagramme avant de faire la capture d'écran
            afficherDiagramme();
            ;
            Exporter exp = new Exporter(Diagramme.getInstance());
            exp.exportImage(primaryStage, stackPane);
        });

        // EXPORTER UN PLANTUML
        menuExporterUML.setOnAction(e -> {
            Exporter exp = new Exporter(Diagramme.getInstance());
            exp.exportUML(primaryStage);
        });


        // AFFICHER TOUTES LES CLASSES
        menuAfficherToutesClasses.setOnAction(e -> {
            Diagramme.getInstance().afficherToutesClasses();
        });

        // MASQUER TOUTES LES CLASSES
        menuMasquerToutesClasses.setOnAction(e -> {
            Diagramme.getInstance().masquerToutesClasses();
        });

        // AFFICHER/MASQUER TOUS LES ATTRIBUTS
        menuAfficherTousAttributs.setSelected(true);
        menuAfficherTousAttributs.setOnAction(e -> {
            afficherAttributs = menuAfficherTousAttributs.isSelected();
            if (afficherAttributs) {
                Diagramme.getInstance().afficherTousAttributs();
            } else {
                Diagramme.getInstance().masquerTousAttributs();
            }
            afficherDiagramme();
        });

        // AFFICHER/MASQUER TOUTES LES METHODES
        menuAfficherToutesMethodes.setSelected(true);
        menuAfficherToutesMethodes.setOnAction(e -> {
            afficherMethodes = menuAfficherToutesMethodes.isSelected();
            if (afficherMethodes) {
                Diagramme.getInstance().afficherToutesMethodes();
            } else {
                Diagramme.getInstance().masquerToutesMethodes();
            }
            afficherDiagramme();
        });

    }


    /**
     * Détermine le type de relation à partir de la relation
     *
     * @param relation Relation
     * @return Type de relation
     */
    private VueRelation.TypeRelation determineTypeRelation(Relation relation) {
        switch (relation.getType()) {
            case "heritage":
                return VueRelation.TypeRelation.HERITAGE; // Vérifier que "HERITAGE" est bien géré dans VueRelation
            case "implementation":
                return VueRelation.TypeRelation.IMPLEMENTATION;
            case "aggregation":
                return VueRelation.TypeRelation.AGGREGATION;
            case "composition":
                return VueRelation.TypeRelation.COMPOSITION;
            default:
                return VueRelation.TypeRelation.ASSOCIATION;
        }
    }




    /**
     * Ajoute la possibilité de déplacer un nœud par glisser-déposer
     *
     * @param node Noeud à rendre déplaçable
     */
    private void makeDraggable(Node node) {
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
    private void updateRelations() {
        System.out.println("Mise à jour des relations");
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
    private Classe ajouterClasseDepuisFichier(File file, StackPane stackPane) {
        Classe nouvelleClasse = null;
        try {
            nouvelleClasse = new Classe(file.getAbsolutePath());
            if (nouvelleClasse.getNom() != null) {
                nouvelleClasse.setLongueur(Math.random() * 600);
                nouvelleClasse.setLargeur(Math.random() * 300);
                Diagramme.getInstance().ajouterClasse(nouvelleClasse);
                ajouterRelationsPourClasse(nouvelleClasse);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        stackPane.getChildren().clear();
        afficherDiagramme();
        return nouvelleClasse;
    }


    /**
     * Affiche le diagramme
     */
    private void afficherDiagramme() {
        stackPane.getChildren().clear();
        Diagramme diagramme = Diagramme.getInstance();
        Pane ligneClasse = new Pane();
        Pane relationPane = new Pane();
        stackPane.getChildren().addAll(relationPane, ligneClasse);

        for (Classe c : diagramme.getListeClasses()) {
            VueClasse vueClasse = new VueClasse(c);
            makeDraggable(vueClasse);
            vueClasse.relocate(c.getLongueur(), c.getLargeur());
            Diagramme.getInstance().ajouterObservateur(vueClasse);

            // Appliquer les paramètres d'affichage
            if (afficherAttributs) {
                c.afficherAttributs();
            } else {
                c.masquerAttributs();
            }
            if (afficherMethodes) {
                c.afficherMethodes();
            } else {
                c.masquerMethodes();
            }

            vueClasse.actualiser();
            ligneClasse.getChildren().add(vueClasse);
        }

        // Ajoute les relations
        for (Relation relation : diagramme.getRelations()) {
            VueClasse source = (VueClasse) ligneClasse.getChildren().stream()
                    .filter(node -> ((VueClasse) node).getClasse().equals(relation.getSource()))
                    .findFirst().orElse(null);
            VueClasse destination = (VueClasse) ligneClasse.getChildren().stream()
                    .filter(node -> ((VueClasse) node).getClasse().equals(relation.getDestination()))
                    .findFirst().orElse(null);

            if (source != null && destination != null) {
                VueRelation.TypeRelation typeRelation = determineTypeRelation(relation);
                VueRelation vueRelation = new VueRelation(source, destination, typeRelation);
                relations.add(vueRelation);
                diagramme.ajouterObservateur(vueRelation);
                relationPane.getChildren().add(vueRelation);
            }
        }

        // Met à jour les relations à chaque déplacement de classe
        for (Node node : ligneClasse.getChildren()) {
            if (node instanceof VueClasse vueClasse) {
                vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> updateRelations());
            }
        }
        updateRelations();
    }

    private void ajouterRelationsPourClasse(Classe nouvelleClasse) {
        Diagramme diagramme = Diagramme.getInstance();

        // Vérifier si des relations d'héritage existent déjà avant de les ajouter
        for (Classe parent : nouvelleClasse.getParents()) {
            String typeRelation = parent.getType().equals(Classe.INTERFACE) ? "implementation" : "heritage";
            if (!diagramme.contientRelation(nouvelleClasse, parent)) { // Vérifier l'existence de la relation
                Relation relation = new Relation(nouvelleClasse, parent, typeRelation);
                diagramme.ajouterRelation(relation);
            }
        }

        // Vérifier et ajouter les relations d'association ou d'agrégation
        for (Attribut attribut : nouvelleClasse.getAttributs()) {
            if (attribut instanceof AttributClasse) {
                Classe classeAssociee = ((AttributClasse) attribut).getAttribut();
                String typeRelation = "association"; // Par défaut association
                if (((AttributClasse) attribut).isAggregation()) {
                    typeRelation = "aggregation";
                } else if (((AttributClasse) attribut).isComposition()) {
                    typeRelation = "composition";
                }
                if (!diagramme.contientRelation(nouvelleClasse, classeAssociee)) { // Vérification avant ajout
                    Relation relation = new Relation(nouvelleClasse, classeAssociee, typeRelation);
                    diagramme.ajouterRelation(relation);
                }
            }
        }
    }







}