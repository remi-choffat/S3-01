package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckMenuItem;

public class ToggleAffichageMethodesControleur extends CheckMenuItem {

    public ToggleAffichageMethodesControleur(String s) {
        super(s);
    }

    public void handle(ActionEvent event) {
        Diagramme.getInstance().setAfficherMethodes(this.isSelected());
        if (this.isSelected()) {
            Diagramme.getInstance().afficherToutesMethodes();
        } else {
            Diagramme.getInstance().masquerToutesMethodes();
        }
        //Main.afficherDiagramme();
        Diagramme.getInstance().notifierObservateurs();
    }

}
