package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.StackPane;

public class AfficherToutesMethodesControleur implements EventHandler<ActionEvent> {

    private StackPane stackPane;

    public AfficherToutesMethodesControleur(StackPane s) {
        stackPane = s;
    }

    public void handle(ActionEvent event) {
        CheckMenuItem c = (CheckMenuItem) event.getSource();
        Main.afficherMethodes = c.isSelected();
        if (Main.afficherMethodes) {
            Diagramme.getInstance().afficherToutesMethodes();
        } else {
            Diagramme.getInstance().masquerToutesMethodes();
        }
        //Diagramme.getInstance().afficher(stackPane);
        Diagramme.getInstance().notifierObservateurs();
    }

}
