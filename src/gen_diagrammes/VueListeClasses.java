package gen_diagrammes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Affichage de la liste des classes (menu)
 */
public class VueListeClasses extends VBox implements Observateur {

    public VueListeClasses() {
        this.setPadding(new Insets(10));
        Text texteMenu = new Text("    Liste des classes du diagramme :    ");
        texteMenu.setStyle("-fx-text-fill: #ffffff;");
        this.setStyle("-fx-border-color: #919090; -fx-border-width: 1; -fx-background-color: #c6c4c4;");
        this.getChildren().add(texteMenu);
    }

    public void updateListe() {
        this.getChildren().clear();
        Text texteMenu = new Text("    Liste des classes du diagramme :    ");
        texteMenu.setStyle("-fx-text-fill: #ffffff;");
        this.getChildren().add(texteMenu);

        for (Classe c : Diagramme.getInstance().getListeClasses()) {
            Label l = new Label(c.getNom());
            l.setVisible(true);
            l.setPadding(new Insets(10, 0, 0, 0));
            l.setOnMouseClicked(new ControleurVisibilite(c));
            if (c.isVisible()) {
                l.setStyle("-fx-text-fill: #1E6C93;");
            } else {
                l.setStyle("-fx-text-fill: #ff5f39;");
            }
            this.getChildren().add(l);
        }
    }

    @Override
    public void actualiser() {
        this.updateListe();
    }

}