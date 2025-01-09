package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Classe;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static gen_diagrammes.Main.ajouterClasseDepuisFichier;
import static gen_diagrammes.Main.ajouterRelationsPourClasse;

/**
 * Controleur permettant d'ajouter une classe au diagramme
 */
public class AjouterClasseControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public AjouterClasseControleur(StackPane sp) {
        super();
        this.stackPane = sp;
    }

    public void handle(ActionEvent event) {
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
    }

}
