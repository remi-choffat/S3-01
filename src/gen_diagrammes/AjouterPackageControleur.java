package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import static gen_diagrammes.Main.ajouterClasseDepuisFichier;
import static gen_diagrammes.Main.ajouterRelationsPourClasse;

public class AjouterPackageControleur implements EventHandler<ActionEvent> {

    private StackPane stackPane;

    public AjouterPackageControleur(StackPane s) {
        super();
        stackPane = s;
    }

    public void handle(ActionEvent event) {
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
            //Diagramme.getInstance().notifierObservateurs();
    }

}
