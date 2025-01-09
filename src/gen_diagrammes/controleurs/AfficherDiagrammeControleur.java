package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

public class AfficherDiagrammeControleur implements EventHandler<ActionEvent> {

    private StackPane stackPane;

    public AfficherDiagrammeControleur(StackPane s) {
        super();
        stackPane = s;
    }

    public void handle(ActionEvent event) {
        Diagramme.getInstance().afficher(stackPane);
    }

}
