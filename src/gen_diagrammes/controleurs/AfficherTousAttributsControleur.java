package gen_diagrammes.controleurs;

import gen_diagrammes.Main;
import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.StackPane;

/**
 * Controleur permettant d'afficher tous les attributs des classes du diagramme
 */
public class AfficherTousAttributsControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

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
        //Diagramme.getInstance().afficher(stackPane);
        Diagramme.getInstance().notifierObservateurs();
    }

}
