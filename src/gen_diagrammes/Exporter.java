package gen_diagrammes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Gère l'exportation du diagramme
 *
 * @param diagramme Diagramme à exporter
 */
public record Exporter(Diagramme diagramme) {


    /**
     * Exporte le diagramme dans un fichier au format UML
     */
    public void exportUML(Stage stage) {

        StringBuilder uml = new StringBuilder();
        uml.append("@startuml\n");
        for (Classe c : diagramme.getListeClasses()) {
            uml.append(c.toPlantUML()).append("\n");
        }
        uml.append("@enduml\n");

        // Crée un fichier et l'enregistre
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le diagramme PlantUML");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PlantUML", "*.puml"));
            File file = fileChooser.showSaveDialog(stage);
            java.io.FileWriter fw = new java.io.FileWriter(file.getAbsolutePath());
            fw.write(uml.toString());
            fw.close();
            System.out.println("Fichier PlantUML enregistré : " + file.getAbsolutePath());
        } catch (java.io.IOException e) {
            System.err.println("Erreur lors de l'export du diagramme" + e.getMessage());
        }
    }


    /**
     * Exporte le diagramme dans un fichier image
     *
     * @param stage Stage
     * @param pane  Pane
     */
    public void exportImage(Stage stage, Pane pane) {
        WritableImage image = pane.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer l'image du diagramme");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PNG", "*.png"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                System.out.println("Image enregistrée : " + file.getAbsolutePath());
            } catch (IOException ex) {
                System.err.println("Erreur lors du téléchargement de l'image : " + ex.getMessage());
            }
        }
    }

}
