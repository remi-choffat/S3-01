package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChargerDiagrammeControleur implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        // Demande si l'utilisateur souhaite enregistrer le diagramme actuel (s'il contient au moins une classe)
        if (!Diagramme.getInstance().getListeClasses().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Nouveau diagramme");
            alert.setHeaderText("Voulez-vous enregistrer le diagramme actuel avant d'en créer un nouveau ?");
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    new EnregistrerDiagrammeSousControleur().handle(event);
                } else if (type == ButtonType.NO) {
                    Diagramme.getInstance().supprimerToutesClasses();
                    Main.stackPane.getChildren().clear();
                    chargerDiagramme();
                } else {
                    alert.close();
                }
            });
        } else {
            chargerDiagramme();
        }
    }


    private void chargerDiagramme() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un diagramme");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers de diagramme", "*.diag"));
        Stage primaryStage = (Stage) Main.stackPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                Diagramme.chargerDiagramme(file);
                Diagramme.getInstance().updateClasses();
                Diagramme.getInstance().afficher(Main.stackPane);
                System.out.println("Diagramme chargé depuis " + file.getAbsolutePath());
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Erreur lors du chargement du diagramme");
            }
        }
    }

}
