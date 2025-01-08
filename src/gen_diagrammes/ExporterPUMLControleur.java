package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ExporterPUMLControleur implements EventHandler<ActionEvent> {

    private Stage primaryStage;

    public ExporterPUMLControleur(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void handle(ActionEvent event) {
        Exporter exp = new Exporter(Diagramme.getInstance());
        exp.exportUML(primaryStage);
    }

}
