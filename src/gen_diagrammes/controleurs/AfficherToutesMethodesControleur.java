package gen_diagrammes.controleurs;

import gen_diagrammes.Main;
import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.gInterface.Observateur;
import gen_diagrammes.vues.VueClasse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.StackPane;

/**
 * Controleur permettant d'afficher toutes les méthodes des classes du diagramme
 */
public class AfficherToutesMethodesControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public AfficherToutesMethodesControleur(StackPane s) {
        stackPane = s;
    }

    public void handle(ActionEvent event) {
        CheckMenuItem c = (CheckMenuItem) event.getSource();
        boolean isSelected = c.isSelected();
        Main.afficherMethodes = isSelected;
        if (isSelected) {
            Diagramme.getInstance().afficherToutesMethodes();
        } else {
            Diagramme.getInstance().masquerToutesMethodes();
        }
        for (Observateur observateur : Diagramme.getInstance().getListeObservateurs()) {
            if (observateur instanceof VueClasse) {
                ((VueClasse) observateur).updateShowMethodsCheckMenuItem(isSelected);
            }
        }
        Diagramme.getInstance().notifierObservateurs();
    }

}
