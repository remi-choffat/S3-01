package gen_diagrammes.controleurs;

import gen_diagrammes.Main;
import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EnregistrerDiagrammeSousControleur implements EventHandler<ActionEvent> {

    private StackPane stackPane;

    public EnregistrerDiagrammeSousControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder le diagramme");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers de diagramme PlanteUML", "*.plante"));
        Stage primaryStage = (Stage) stackPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                Diagramme.getInstance().sauvegarderDiagramme(file);
                Diagramme.getInstance().setFichier(file.getAbsolutePath());
                System.out.println("Diagramme sauvegardé dans " + file.getAbsolutePath());
            } catch (IOException ex) {
                System.err.println("Erreur lors de la sauvegarde du diagramme");
            }
        }
    }

}
