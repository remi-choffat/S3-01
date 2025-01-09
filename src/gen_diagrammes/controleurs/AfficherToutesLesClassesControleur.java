package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Controleur permettant d'afficher toutes les classes du diagramme
 */
public class AfficherToutesLesClassesControleur implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        Diagramme.getInstance().afficherToutesClasses();
    }

}
