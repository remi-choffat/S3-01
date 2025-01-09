package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Contrôleur permettant de charger un diagramme depuis un fichier
 */
public class ChargerDiagrammeControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public ChargerDiagrammeControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @Override
    public void handle(ActionEvent event) {
        // Demande si l'utilisateur souhaite enregistrer le diagramme actuel (s'il contient au moins une classe)
        if (!Diagramme.getInstance().getListeClasses().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Nouveau diagramme");
            alert.setHeaderText("Voulez-vous enregistrer le diagramme actuel avant d'en créer un nouveau ?");
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    new EnregistrerDiagrammeControleur(stackPane).handle(event);
                } else if (type != ButtonType.NO) {
                    alert.close();
                }
                Diagramme.getInstance().setFichier(null);
                Diagramme.getInstance().supprimerToutesClasses();
                stackPane.getChildren().clear();
                chargerDiagramme();
            });
        } else {
            chargerDiagramme();
        }
    }


    /**
     * Charge un diagramme depuis un fichier .plante
     */
    private void chargerDiagramme() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un diagramme");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers de diagramme PlanteUML", "*.plante"));
        Stage primaryStage = (Stage) stackPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                Diagramme.chargerDiagramme(file);
                Diagramme.getInstance().setFichier(file.getAbsolutePath());
                Diagramme.getInstance().updateClasses();
                //Diagramme.getInstance().afficher(stackPane);
                Diagramme.getInstance().notifierObservateurs();
                System.out.println("Diagramme chargé depuis " + file.getAbsolutePath());
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Erreur lors du chargement du diagramme");
            }
        }
    }

}
