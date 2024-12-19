package gen_diagrammes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private List<VueRelation> relations = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Plante UML");

        // création des boutons
        Button btnAjouter = new Button("Ajouter");
        Button btnSupprimer = new Button("Supprimer");
        Button btnExporter = new Button("Exporter");
        Button btnGenerer = new Button("Générer");
        Button btnAffichage = new Button("Affichage");

        // création d'une HBox pour les boutons de base
        HBox hbox = new HBox(10);  // 10 est l'espacement entre les boutons
        hbox.getChildren().addAll(btnAjouter, btnSupprimer, btnExporter, btnGenerer, btnAffichage);

        StackPane stackPane = new StackPane();

        // création de la mise en page principale
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hbox);
        borderPane.setCenter(stackPane);

        // création de la scène et l'ajouter à la fenêtre principale
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // gestionnaire d'événements pour le bouton "Ajouter"
        btnAjouter.setOnAction(e -> {
            Button btnPackage = new Button("Ajouter un package");
            Button btnClasse = new Button("Ajouter une classe");
            VBox vbox = new VBox(10);
            vbox.getChildren().setAll(btnPackage, btnClasse);
            stackPane.getChildren().add(vbox);

            // Gestionnaire d'événements pour le bouton "Ajouter une classe"
            btnClasse.setOnAction(event -> {
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
                        System.out.println("Fichier déposé : " + filePath);
                        Classe classe;
                        try {
                            classe = new Classe(filePath);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        Diagramme.getInstance().ajouterClasse(classe);
                    }
                    eventDrop.setDropCompleted(success);
                    eventDrop.consume();
                });
                StackPane wrapper = new StackPane(rectangle);
                wrapper.setPrefSize(800, 600);  // Taille fixe pour le conteneur
                StackPane.setAlignment(rectangle, Pos.CENTER);  // Centrer le rectangle dans le conteneur

                stackPane.getChildren().add(wrapper);
                // Gestionnaire d'événements pour le bouton central
                btnCenter.setOnAction(fileEvent -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Sélectionner un fichier");
                    Stage fileStage = (Stage) btnCenter.getScene().getWindow();
                    java.io.File file = fileChooser.showOpenDialog(fileStage);
                    if (file != null) {
                        System.out.println("Fichier sélectionné : " + file.getAbsolutePath());
                        Classe classe;
                        try {
                            classe = new Classe(file.getAbsolutePath());
                            classe.setLongueur(Math.random() * 600);
                            classe.setLargeur(Math.random() * 300);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        Diagramme.getInstance().ajouterClasse(classe);
                        stackPane.getChildren().clear();
                        btnAffichage.fire();
                    }
                });
            });
        });

        btnAffichage.setOnAction(e -> {
            Diagramme diagramme = Diagramme.getInstance();
            Pane ligneClasse = new Pane();
            Pane relationPane = new Pane();
            stackPane.getChildren().addAll(relationPane, ligneClasse);

            for (Classe c : diagramme.getListeClasses()) {
                VueClasse vueClasse = new VueClasse(c);
                makeDraggable(vueClasse);
                vueClasse.relocate(c.getLongueur(), c.getLargeur());
                ligneClasse.getChildren().add(vueClasse);
            }

            // Ajouter des relations (exemple simplifié)
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
                if (node instanceof VueClasse) {
                    VueClasse vueClasse = (VueClasse) node;
                    vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                    vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                }
            }
        });

    }

    private VueRelation.TypeRelation determineTypeRelation(Relation relation) {
        switch (relation.getType()) {
            case "heritage":
                return VueRelation.TypeRelation.HERITAGE;
            case "implementation":
                return VueRelation.TypeRelation.IMPLEMENTATION;
            case "association":
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

    private void updateRelations() {
        for (VueRelation vueRelation : relations) {
            vueRelation.actualiser();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}