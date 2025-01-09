package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MasquerToutesLesClassesControleur implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        Diagramme.getInstance().masquerToutesClasses();
    }
}
