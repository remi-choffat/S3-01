package gen_diagrammes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe principale de l'application
 */
public class Main extends Application {

    /**
     * Méthode principale
     * Démarre l'interface graphique et demande les chemins des fichiers .class en console
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        // Lance l'interface graphique dans un thread séparé
        new Thread(() -> Application.launch(Main.class, args)).start();

        // Demande les chemins des fichiers .class en console
        Diagramme d = Diagramme.getInstance();
        if (args.length == 1) {
            Diagramme.initialize(args[0], null);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un chemin de classe (chemin absolu si aucun package sélectionné) : ");
        String res = sc.nextLine();
        while (!res.equals("q")) {
            if (!res.equals("*export")) {
                try {
                    d.ajouterClasse(new Classe(res));
                    System.out.println(d);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                Exporter exp = new Exporter(d);
                exp.exportUML();
            }
            System.out.println("Entrez un chemin de classe, ou tapez *export pour exporter le diagramme : ");
            res = sc.nextLine();
        }
    }

    /**
     * Affiche l'interface graphique
     *
     * @param primaryStage Stage
     */
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

//        // création d'une VBox pour les nouveaux boutons
//        VBox vbox = new VBox(10);
//        vbox.setVisible(false);  // Initialement caché

        StackPane stackPane = new StackPane();

        // création de la mise en page principale
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hbox);
        borderPane.setCenter(stackPane);

        // création de la scène et l'ajouter à la fenêtre principale
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        final int[] etat = {0};

        // gestionnaire d'événements pour le bouton "Ajouter"
        btnAjouter.setOnAction(e -> {
            if (etat[0] == 0) {
                etat[0] = 1;
                Button btnPackage = new Button("Ajouter un package");
                Button btnClasse = new Button("Ajouter une classe");
                VBox vbox = new VBox(10);
                vbox.getChildren().setAll(btnPackage, btnClasse);
                stackPane.getChildren().add(vbox);

                // Gestionnaire d'événements pour le bouton "Ajouter une classe"
                btnClasse.setOnAction(event -> {
                    stackPane.getChildren().clear();
                    etat[0] = 0;
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
                            String filePath = db.getFiles().getFirst().getAbsolutePath();
                            System.out.println("Fichier déposé : " + filePath);
                            Classe classe;
                            try {
                                classe = new Classe(filePath);
                                classe.setLongueur(Math.random() * 600);
                                classe.setLargeur(Math.random() * 300);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            Diagramme.getInstance().ajouterClasse(classe);
                            stackPane.getChildren().clear();
                            etat[0] = 0;
                            btnAffichage.fire();
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
            } else {
                etat[0] = 0;
                stackPane.getChildren().clear();
                btnAffichage.fire();
            }
        });

        btnAffichage.setOnAction(e -> {
//            Classe classe, classe2;
//            try {
//                classe = new Classe("C:\\Users\\tulin\\Documents\\git\\S3-01\\out\\production\\S3-01\\gen_diagrammes\\Attribut.class");
//                classe2 = new Classe("C:\\Users\\tulin\\Documents\\git\\S3-01\\out\\production\\S3-01\\gen_diagrammes\\Exporter.class");
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//            Diagramme.getInstance().ajouterClasse(classe);
//            Diagramme.getInstance().ajouterClasse(classe2);
            stackPane.getChildren().clear();
            Diagramme diagramme = Diagramme.getInstance();
            Pane ligneClasse = new Pane();
            for (Classe c : diagramme.getListeClasses()) {
                VueClasse vueClasse = new VueClasse(c);
                vueClasse.relocate(c.getLongueur(), c.getLargeur());
                ligneClasse.getChildren().add(vueClasse);
            }
            stackPane.getChildren().add(ligneClasse);
        });
    }
}
