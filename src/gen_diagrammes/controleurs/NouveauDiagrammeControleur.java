package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;

/**
 * Contrôleur pour créer un nouveau diagramme (projet)
 */
public class NouveauDiagrammeControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public NouveauDiagrammeControleur(StackPane stackPane) {
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
                System.out.println("Nouveau diagramme créé");
            });
        }
    }

}
