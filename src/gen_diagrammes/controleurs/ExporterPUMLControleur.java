package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.gInterface.Exporter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Contrôleur pour exporter le diagramme en un fichier .puml
 */
public class ExporterPUMLControleur implements EventHandler<ActionEvent> {

    private final Stage primaryStage;

    public ExporterPUMLControleur(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void handle(ActionEvent event) {
        Exporter exp = new Exporter(Diagramme.getInstance());
        exp.exportUML(primaryStage);
    }

}
