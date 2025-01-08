package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;

public class ToggleAffichageAttributsControleur extends CheckMenuItem {

    public ToggleAffichageAttributsControleur(String s) {
        super(s);
    }

    public void handle(ActionEvent event) {
        Diagramme.getInstance().setAfficherAttributs(this.isSelected());
        if (this.isSelected()) {
            Diagramme.getInstance().afficherTousAttributs();
        } else {
            Diagramme.getInstance().masquerTousAttributs();
        }
        //afficherDiagramme();
        Diagramme.getInstance().notifierObservateurs();
    }

}
