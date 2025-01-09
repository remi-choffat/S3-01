package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class QuitterAppliControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public QuitterAppliControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @Override
    public void handle(ActionEvent event) {

        Stage primaryStage = (Stage) stackPane.getScene().getWindow();
        // Demande si l'utilisateur souhaite enregistrer le diagramme actuel (s'il contient au moins une classe)
        if (!Diagramme.getInstance().getListeClasses().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Quitter");
            alert.setHeaderText("Voulez-vous enregistrer le diagramme actuel avant de quitter ?");
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    new EnregistrerDiagrammeControleur(stackPane).handle(event);
                } else if (type != ButtonType.NO) {
                    alert.close();
                }
                primaryStage.close();
                System.exit(0);
            });
        } else {
            primaryStage.close();
            System.exit(0);
        }
    }

}
