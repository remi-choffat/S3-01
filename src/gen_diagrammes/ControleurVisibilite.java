package gen_diagrammes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class ControleurVisibilite implements EventHandler<MouseEvent> {

    private Classe classe;

    public ControleurVisibilite(Classe classe) {
        this.classe = classe;
    }

    public void handle(MouseEvent event) {
        if (this.classe.isVisible()){
            this.classe.setVisibilite(false);
            ((Label)event.getSource()).setStyle("-fx-text-fill: #f32525;");
        } else {
            this.classe.setVisibilite(true);
            ((Label)event.getSource()).setStyle("-fx-text-fill: #3434ba;");
        }
    }
}