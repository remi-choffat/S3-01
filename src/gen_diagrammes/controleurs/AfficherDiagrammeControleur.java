package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.vues.VueDiagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

/**
 * Controleur permettant d'afficher le diagramme
 */
public class AfficherDiagrammeControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public AfficherDiagrammeControleur(StackPane s) {
        super();
        stackPane = s;
    }

    public void handle(ActionEvent event) {
        ((VueDiagramme) stackPane).afficher();
        Diagramme.getInstance().notifierObservateurs();
    }

}
