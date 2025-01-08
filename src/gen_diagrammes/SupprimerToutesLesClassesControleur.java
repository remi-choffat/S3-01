package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;

public class SupprimerToutesLesClassesControleur implements EventHandler<ActionEvent> {

    StackPane stackPane;

    public SupprimerToutesLesClassesControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void handle(ActionEvent event) {
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
    }

}
