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
import javafx.scene.text.Text;
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

    private List<VueRelation> relations = new ArrayList<>();
    private double scaleFactor = 1.0;


    /**
     * Méthode principale
     * Démarre l'interface graphique et demande les chemins des fichiers .class en console
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
        Menu menuAjouter = new Menu("Ajouter");
        MenuItem menuAjouterPackage = new MenuItem("Package");
        MenuItem menuAjouterClasse = new MenuItem("Classe");
        Menu menuSupprimer = new Menu("Supprimer");
        Menu menuExporter = new Menu("Exporter");
        MenuItem menuExporterImage = new MenuItem("Exporter une image");
        MenuItem menuExporterUML = new MenuItem("Exporter en PlantUML");
        Menu menuGenerer = new Menu("Générer");
        Menu menuAffichage = new Menu("Affichage");
        MenuItem menuAfficherDiagramme = new MenuItem("Afficher le diagramme");
        menuAjouter.getItems().addAll(menuAjouterPackage, menuAjouterClasse);
        menuExporter.getItems().addAll(menuExporterImage, menuExporterUML);
        menuAffichage.getItems().addAll(menuAfficherDiagramme);
        menuBar.getMenus().addAll(menuAjouter, menuSupprimer, menuExporter, menuGenerer, menuAffichage);


        StackPane stackPane = new StackPane();

        // création de la mise en page principale
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(stackPane);


        //creation du menu affichant les classes ajoutees
        VBox menu = new VBox(21);
        menu.setPadding(new Insets(10));
        Text texteMenu = new Text("    Liste des classes du diagramme :    ");
        texteMenu.setStyle("-fx-text-fill: #ffffff;");
        menu.setStyle("-fx-border-color: #919090; -fx-border-width: 1; -fx-background-color: #c6c4c4;");
        menu.getChildren().add(texteMenu);

        for (int i = 0; i < 20; i++) {
            Label l = new Label("");
            l.setVisible(false);
            l.setStyle("-fx-text-fill: #3434ba;");
            l.setPadding(new Insets(20, 0, 0, 0));
            menu.getChildren().add(l);
        }

        borderPane.setLeft(menu);

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
                    ajouterClasseDepuisFichier(db.getFiles().get(0), menu, stackPane);
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
                    ajouterClasseDepuisFichier(file, menu, stackPane);
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
                                ajouterClasseDepuisFichier(file, menu, stackPane);
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
                    if (files != null) {
                        for (File file : files) {
                            ajouterClasseDepuisFichier(file, menu, stackPane);
                        }
                    } else {
                        System.err.println("Aucun fichier .class trouvé dans le dossier");
                    }
                } else {
                    System.err.println("Aucun dossier sélectionné");
                }
            });

            StackPane.setAlignment(rectangle, Pos.TOP_CENTER);
            StackPane.setMargin(rectangle, new Insets(100, 0, 0, 0));
            stackPane.getChildren().add(rectangle);
        });


        // AFFICHER LE DIAGRAMME
        menuAfficherDiagramme.setOnAction(e -> {
            afficherDiagramme(stackPane);
        });


        // EXPORTER UNE IMAGE
        menuExporterImage.setOnAction(e -> {
            // Affiche le diagramme avant de faire la capture d'écran
            afficherDiagramme(stackPane);
            ;
            Exporter exp = new Exporter(Diagramme.getInstance());
            exp.exportImage(primaryStage, stackPane);
        });

        // EXPORTER UN PLANTUML
        menuExporterUML.setOnAction(e -> {
            Exporter exp = new Exporter(Diagramme.getInstance());
            exp.exportUML(primaryStage);
        });

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
    private void makeDraggable(Node node) {
        final double[] dragDelta = new double[2];

        node.setOnMousePressed(e -> {
            dragDelta[0] = node.getLayoutX() - e.getSceneX();
            dragDelta[1] = node.getLayoutY() - e.getSceneY();
            node.setCursor(javafx.scene.Cursor.MOVE);
        });

        node.setOnMouseReleased(e -> node.setCursor(javafx.scene.Cursor.HAND));

        node.setOnMouseDragged(e -> {
            node.setLayoutX(e.getSceneX() + dragDelta[0]);
            node.setLayoutY(e.getSceneY() + dragDelta[1]);
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
        for (VueRelation vueRelation : relations) {
            vueRelation.actualiser();
        }
    }


    /**
     * Ajoute une classe à partir d'un fichier
     *
     * @param file      Fichier
     * @param menu      Menu : Liste des classes du diagramme
     * @param stackPane StackPane : Conteneur du diagramme
     */
    private void ajouterClasseDepuisFichier(File file, VBox menu, StackPane stackPane) {
        try {
            Classe classe = new Classe(file.getAbsolutePath());
            classe.setLongueur(Math.random() * 600);
            classe.setLargeur(Math.random() * 300);
            Diagramme.getInstance().ajouterClasse(classe);
            System.out.println("Classe " + classe.getNom() + " ajoutée");

            int i = 1;
            boolean estNote = false;
            ControleurVisibilite controleur = new ControleurVisibilite(classe);
            while ((i < menu.getChildren().size()) && (!estNote)) {
                if (((Label) menu.getChildren().get(i)).getText().isEmpty()) {
                    ((Label) menu.getChildren().get(i)).setText(classe.getNom());
                    menu.getChildren().get(i).setVisible(true);
                    menu.getChildren().get(i).setOnMouseClicked(controleur);
                    estNote = true;
                }
                i++;
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        stackPane.getChildren().clear();
        afficherDiagramme(stackPane);
    }


    /**
     * Affiche le diagramme
     *
     * @param stackPane StackPane
     */
    private void afficherDiagramme(StackPane stackPane) {

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
            ligneClasse.getChildren().add(vueClasse);
        }

        // Ajoute les relations
        for (int i = 0; i < diagramme.getListeClasses().size() - 1; i++) {
            VueClasse source = (VueClasse) ligneClasse.getChildren().get(i);
            VueClasse destination = (VueClasse) ligneClasse.getChildren().get(i + 1);
            VueRelation.TypeRelation typeRelation = VueRelation.TypeRelation.ASSOCIATION; // Change ce type selon tes besoins
            VueRelation vueRelation = new VueRelation(source, destination, typeRelation);
            relations.add(vueRelation);
            relationPane.getChildren().add(vueRelation);
        }

        // Met à jour les relations à chaque déplacement de classe
        for (Node node : ligneClasse.getChildren()) {
            if (node instanceof VueClasse vueClasse) {
                vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> updateRelations());
            }
        }
    }

}