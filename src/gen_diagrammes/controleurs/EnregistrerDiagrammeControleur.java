package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

import java.io.File;

public class EnregistrerDiagrammeControleur implements EventHandler<ActionEvent> {

    private final StackPane stackPane;

    public EnregistrerDiagrammeControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @Override
    public void handle(ActionEvent event) {
        // Vérifie si le diagramme a déjà été enregistré ou s'il est nouveau
        if (Diagramme.getInstance().getFichier() == null) {
            // Si le diagramme n'a pas encore été enregistré, on appelle le contrôleur pour enregistrer sous
            new EnregistrerDiagrammeSousControleur(stackPane).handle(event);
        } else {
            // Si le diagramme a déjà été enregistré, on sauvegarde le diagramme dans le fichier existant
            try {
                Diagramme.getInstance().sauvegarderDiagramme(new File(Diagramme.getInstance().getFichier()));
                System.out.println("Diagramme sauvegardé dans " + Diagramme.getInstance().getFichier());
            } catch (Exception ex) {
                System.err.println("Erreur lors de la sauvegarde du diagramme");
            }
        }
    }

}
