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

        stackPane = new StackPane();

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
                    ajouterClasseDepuisFichier(db.getFiles().get(0), stackPane);
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
                    ajouterClasseDepuisFichier(file, stackPane);
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
                                ajouterClasseDepuisFichier(file, stackPane);
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
                            ajouterClasseDepuisFichier(file, stackPane);
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
            Diagramme.getInstance().afficher(stackPane);
        });


        // EXPORTER UNE IMAGE
        menuExporterImage.setOnAction(e -> {
            // Affiche le diagramme avant de faire la capture d'écran
            Diagramme.getInstance().afficher(stackPane);
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
            Diagramme.getInstance().afficher(stackPane);
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
            Diagramme.getInstance().afficher(stackPane);
        });

        // SUPPRIMER UNE CLASSE
        menuSupprimerClasses.setOnAction(e -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Supprimer une classe");
            dialog.setHeaderText("Sélectionnez une classe à supprimer");

            ButtonType buttonTypeOk = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

            ListView<String> listView = new ListView<>();
            ArrayList<String> nomsClasses = new ArrayList<>();
            for (Classe c : Diagramme.getInstance().getListeClasses()) {
                nomsClasses.add(c.getNomPackage() + "." + c.getNom());
            }
            listView.getItems().addAll(nomsClasses);
            dialog.getDialogPane().setContent(listView);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buttonTypeOk) {
                    return listView.getSelectionModel().getSelectedItem();
                }
                return null;
            });

            dialog.showAndWait().ifPresent(result -> {
                Classe classe = Diagramme.getInstance().getClasse(result.split("\\.")[1], result.split("\\.")[0]);
                System.out.println(classe.getTypeClasseString() + " " + classe.getNom() + " supprimée");
                Diagramme.getInstance().supprimerClasse(classe);
                Diagramme.getInstance().afficher(stackPane);
            });
        });

        // SUPPRIMER TOUTES LES CLASSES
        menuSupprimerToutesClasses.setOnAction(e -> {

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Supprimer toutes les classes");
            dialog.setHeaderText("Voulez-vous vraiment supprimer toutes les classes du diagramme actuel ?");
            ButtonType buttonTypeOk = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buttonTypeOk) {
                    return "ok";
                }
                return null;
            });

            dialog.showAndWait().ifPresent(result -> {
                if (result.equals("ok")) {
                    Diagramme.getInstance().supprimerToutesClasses();
                    System.out.println("Toutes les classes ont été supprimées");
                    stackPane.getChildren().clear();
                }
            });
        });

        // CRÉER UNE CLASSE
        menuCreer.setOnAction(e -> {

            // Masque le diagramme
            stackPane.getChildren().clear();

            VBox vb = new VBox();
            Label lb = new Label("Créer une classe");
            lb.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            lb.setPadding(new Insets(10));
            TextField tf = new TextField();
            tf.setPromptText("Nom de la classe");

            ComboBox<String> comboVisibilite = new ComboBox<>();
            comboVisibilite.getItems().addAll(Classe.PUBLIC, Classe.PRIVATE, Classe.PROTECTED);
            comboVisibilite.setValue(Classe.PUBLIC);

            ComboBox<String> comboType = new ComboBox<>();
            comboType.getItems().addAll(Classe.CLASS, Classe.ABSTRACT_CLASS, Classe.INTERFACE);
            comboType.setValue(Classe.CLASS);

            HBox ligne = new HBox(10, comboVisibilite, comboType, tf);
            ligne.setAlignment(Pos.CENTER);

            HBox boutons = new HBox(10);
            Button bCancel = new Button("Annuler");
            Button bOk = new Button("Ajouter");
            bOk.setDefaultButton(true);
            bOk.setDisable(true);
            bCancel.setCancelButton(true);
            boutons.getChildren().addAll(bOk, bCancel);
            boutons.setAlignment(Pos.CENTER);

            vb.getChildren().addAll(lb, ligne, boutons);
            vb.setSpacing(20);
            vb.setPadding(new Insets(20));
            vb.setAlignment(Pos.CENTER);

            stackPane.getChildren().add(vb);
            vb.setLayoutX(stackPane.getScaleX() - 0.5 * vb.getScaleX());
            vb.setLayoutY(stackPane.getScaleY() - 0.5 * vb.getScaleY());


            bOk.setOnAction(f -> {
                Classe c = new Classe(tf.getText(), comboVisibilite.getValue(), comboType.getValue());
                Diagramme.getInstance().ajouterClasse(c);
                bCancel.fire();
            });

            bCancel.setOnAction(f -> {
                stackPane.getChildren().remove(vb);
                Diagramme.getInstance().afficher(stackPane);
            });

            tf.setOnKeyTyped(f -> {
                // Désactive le bouton OK si le champ est vide
                bOk.setDisable(tf.getText().length() <= 0);
                // Met la première lettre de la classe en majuscule
                if (tf.getText().length() == 1) {
                    tf.setText(tf.getText().toUpperCase());
                    tf.positionCaret(tf.getText().length());
                }
            });

        });

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
    private void ajouterClasseDepuisFichier(File file, StackPane stackPane) {
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