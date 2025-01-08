package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ExporterImageControleur implements EventHandler<ActionEvent> {

    Stage primaryStage;
    StackPane stackPane;

    public ExporterImageControleur(Stage primaryStage, StackPane stackPane) {
        this.primaryStage = primaryStage;
        this.stackPane = stackPane;
    }

    public void handle (ActionEvent event) {
        // Affiche le diagramme avant de faire la capture d'écran
        Diagramme.getInstance().afficher(stackPane);
        Exporter exp = new Exporter(Diagramme.getInstance());
        exp.exportImage(primaryStage, stackPane);
    }

}
