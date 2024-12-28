package gen_diagrammes;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class ControleurVisibilite implements EventHandler<MouseEvent> {

    private final Classe classe;

    public ControleurVisibilite(Classe classe) {
        this.classe = classe;
    }

    public void handle(MouseEvent event) {
        this.classe.setVisibilite(!this.classe.isVisible());
    }

}