package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.StackPane;

public class AfficherTousAttributsControleur implements EventHandler<ActionEvent> {

    StackPane stackPane;

    public AfficherTousAttributsControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void handle(ActionEvent event) {
        CheckMenuItem c = (CheckMenuItem) event.getSource();
        Main.afficherAttributs = c.isSelected();
        if (Main.afficherAttributs) {
            Diagramme.getInstance().afficherTousAttributs();
        } else {
            Diagramme.getInstance().masquerTousAttributs();
        }
        Diagramme.getInstance().afficher(stackPane);
    }

}
