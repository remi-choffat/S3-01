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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        Text texteMenu = new Text("----- Liste des classes ajoutées : -----");
        menu.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: grey;");
        menu.getChildren().add(texteMenu);

        for (int i = 0; i < 20; i++) {
            Label l = new Label("");
            l.setVisible(false);
            l.setStyle("-fx-text-fill: blue;");
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
            ImageView imageView = new ImageView(new Image("https://static.vecteezy.com/system/resources/previews/023/454/938/non_2x/important-document-upload-logo-design-vector.jpg"));
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            VBox content = new VBox(10, imageView, btnCenter);
            content.setPadding(new Insets(20));
            content.setAlignment(Pos.CENTER); // Centrer le contenu

            StackPane rectangle = new StackPane(content);
            rectangle.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            rectangle.setPrefSize(300, 200);  // Taille fixe pour le rectangle
            rectangle.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);  // Limite la taille maximale à la taille préférée
            // Ajouter le gestionnaire d'événements de glisser-déposer
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
                    String filePath = db.getFiles().get(0).getAbsolutePath();
                    Classe classe = null;
                    try {
                        classe = new Classe(filePath);
                        classe.setLongueur(Math.random() * 600);
                        classe.setLargeur(Math.random() * 300);
                        System.out.println("Classe " + classe.getNom() + " ajoutée");
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    Diagramme.getInstance().ajouterClasse(classe);
                    stackPane.getChildren().clear();
                    menuAfficherDiagramme.fire();
                }
                eventDrop.setDropCompleted(success);
                eventDrop.consume();
            });
            StackPane wrapper = new StackPane(rectangle);
            wrapper.setPrefSize(800, 600);  // Taille fixe pour le conteneur
            StackPane.setAlignment(rectangle, Pos.CENTER);  // Centrer le rectangle dans le conteneur

            // TODO - Supprimer la répétition de code
            stackPane.getChildren().add(wrapper);
            // Gestionnaire d'événements pour le bouton central
            btnCenter.setOnAction(fileEvent -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner un fichier");
                // Ajouter un filtre pour les fichiers de classe Java compilés (.class)
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Classes Java compilées", "*.class"));
                Stage fileStage = (Stage) btnCenter.getScene().getWindow();
                java.io.File file = fileChooser.showOpenDialog(fileStage);
                if (file != null) {
                    Classe classe = null;
                    try {
                        classe = new Classe(file.getAbsolutePath());
                        classe.setLongueur(Math.random() * 600);
                        classe.setLargeur(Math.random() * 300);
                        System.out.println("Classe " + classe.getNom() + " ajoutée");
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    Diagramme.getInstance().ajouterClasse(classe);

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

                    stackPane.getChildren().clear();
                    menuAfficherDiagramme.fire();
                }
            });
        });


        // AFFICHER LE DIAGRAMME
        menuAfficherDiagramme.setOnAction(e -> {
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

            // Ajouter des relations
            for (int i = 0; i < diagramme.getListeClasses().size() - 1; i++) {
                VueClasse source = (VueClasse) ligneClasse.getChildren().get(i);
                VueClasse destination = (VueClasse) ligneClasse.getChildren().get(i + 1);
                VueRelation.TypeRelation typeRelation = VueRelation.TypeRelation.ASSOCIATION; // Change ce type selon tes besoins
                VueRelation vueRelation = new VueRelation(source, destination, typeRelation);
                relations.add(vueRelation);
                relationPane.getChildren().add(vueRelation);
            }

            // Mettre à jour les relations à chaque déplacement de classe
            for (Node node : ligneClasse.getChildren()) {
                if (node instanceof VueClasse vueClasse) {
                    vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                    vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                }
            }
        });


        // EXPORTER UNE IMAGE
        menuExporterImage.setOnAction(e -> {
            // Affiche le diagramme avant de faire la capture d'écran
            menuAfficherDiagramme.fire();
            Exporter exp = new Exporter(Diagramme.getInstance());
            exp.exportImage(primaryStage, stackPane);
        });

        // EXPORTER UN PLANTUML
        menuExporterUML.setOnAction(e -> {
            Exporter exp = new Exporter(Diagramme.getInstance());
            exp.exportUML(primaryStage);
        });
    }


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
        //------------------------------------------------FIN BOUTON POUR AFFICHER LE DIAGRAMME------------------------------------------------


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

    private void updateRelations() {
        for (VueRelation vueRelation : relations) {
            vueRelation.actualiser();
        }
    }

}