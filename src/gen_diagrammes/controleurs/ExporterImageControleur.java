package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.gInterface.Exporter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Contrôleur pour exporter le diagramme en image
 */
public class ExporterImageControleur implements EventHandler<ActionEvent> {

    private final Stage primaryStage;
    private final StackPane stackPane;

    public ExporterImageControleur(Stage primaryStage, StackPane stackPane) {
        this.primaryStage = primaryStage;
        this.stackPane = stackPane;
    }

    public void handle(ActionEvent event) {
        // Affiche le diagramme avant de faire la capture d'écran
        Diagramme.getInstance().afficher(stackPane);
        Exporter exp = new Exporter(Diagramme.getInstance());
        exp.exportImage(primaryStage, stackPane);
    }

}
