package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur pour masquer toutes les classes du diagramme
 */
public class MasquerToutesLesClassesControleur implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        Diagramme.getInstance().masquerToutesClasses();
    }

}
