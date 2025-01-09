package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class QuitterAppliControleur implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        Stage primaryStage = (Stage) Main.stackPane.getScene().getWindow();
        // Demande si l'utilisateur souhaite enregistrer le diagramme actuel (s'il contient au moins une classe)
        if (!Diagramme.getInstance().getListeClasses().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Quitter");
            alert.setHeaderText("Voulez-vous enregistrer le diagramme actuel avant de quitter ?");
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    new EnregistrerDiagrammeSousControleur().handle(event);
                } else if (type == ButtonType.NO) {
                    primaryStage.close();
                } else {
                    alert.close();
                }
            });
        } else {
            primaryStage.close();
        }

        System.exit(0);
    }

}
