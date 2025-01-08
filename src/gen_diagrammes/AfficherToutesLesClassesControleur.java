package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AfficherToutesLesClassesControleur implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        Diagramme.getInstance().afficherToutesClasses();
    }

}
