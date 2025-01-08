package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EnregistrerDiagrammeSousControleur implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder le diagramme");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers de diagramme", "*.diag"));
        Stage primaryStage = (Stage) Main.stackPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                Diagramme.getInstance().sauvegarderDiagramme(file);
                System.out.println("Diagramme sauvegardé dans " + file.getAbsolutePath());
            } catch (IOException ex) {
                System.err.println("Erreur lors de la sauvegarde du diagramme");
            }
        }
    }

}
