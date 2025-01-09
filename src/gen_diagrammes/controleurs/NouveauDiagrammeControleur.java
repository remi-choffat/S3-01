package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class NouveauDiagrammeControleur implements EventHandler<ActionEvent> {

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
                } else {
                    alert.close();
                }
            });
        }
    }

}
