package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class MasquerToutesLesClassesControleur implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {
        Diagramme.getInstance().masquerToutesClasses();
    }
}
