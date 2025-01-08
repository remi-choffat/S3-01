package gen_diagrammes;

import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VueDiagramme extends StackPane implements Observateur {

    private double scaleFactor = 1.0;
    private List<VueRelation> relations = new ArrayList<>();

    public VueDiagramme(Stage primaryStage) {
        super();

        this.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0) {
                scaleFactor *= 1.1;
            } else {
                scaleFactor /= 1.1;
            }
            this.setScaleX(scaleFactor);
            this.setScaleY(scaleFactor);
            event.consume();
        });
    }

    private void updateRelations() {
        for (VueRelation vueRelation : relations) {
            vueRelation.actualiser();
        }
    }

    private void afficherDiagramme() {
        this.getChildren().clear();
        Diagramme diagramme = Diagramme.getInstance();
        Pane ligneClasse = new Pane();
        Pane relationPane = new Pane();
        this.getChildren().addAll(relationPane, ligneClasse);

        for (Classe c : diagramme.getListeClasses()) {
            VueClasse vueClasse = new VueClasse(c);
            makeDraggable(vueClasse);
            vueClasse.relocate(c.getLongueur(), c.getLargeur());
            //Diagramme.getInstance().ajouterObservateur(vueClasse);

            // Appliquer les paramètres d'affichage
            if (Diagramme.getInstance().getAfficherAttributs()) {
                c.afficherAttributs();
            } else {
                c.masquerAttributs();
            }
            if (Diagramme.getInstance().getAfficherMethodes()) {
                c.afficherMethodes();
            } else {
                c.masquerMethodes();
            }

            vueClasse.actualiser();

            ligneClasse.getChildren().add(vueClasse);
        }

        // Ajoute les relations
        for (int i = 0; i < diagramme.getListeClasses().size() - 1; i++) {
            VueClasse source = (VueClasse) ligneClasse.getChildren().get(i);
            VueClasse destination = (VueClasse) ligneClasse.getChildren().get(i + 1);
            VueRelation.TypeRelation typeRelation = VueRelation.TypeRelation.ASSOCIATION; // Change ce type selon tes besoins
            VueRelation vueRelation = new VueRelation(source, destination, typeRelation);
            relations.add(vueRelation);
            diagramme.ajouterObservateur(vueRelation);
            relationPane.getChildren().add(vueRelation);
        }

        // Met à jour les relations à chaque déplacement de classe
        for (Node node : ligneClasse.getChildren()) {
            if (node instanceof VueClasse vueClasse) {
                vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> updateRelations());
            }
        }
        updateRelations();
    }

    private void makeDraggable(Node node) {
        final double[] dragDelta = new double[2];

        node.setOnMousePressed(e -> {
            dragDelta[0] = node.getLayoutX() - e.getSceneX();
            dragDelta[1] = node.getLayoutY() - e.getSceneY();
            node.setCursor(javafx.scene.Cursor.MOVE);
        });

        node.setOnMouseReleased(e -> node.setCursor(javafx.scene.Cursor.HAND));

        node.setOnMouseDragged(e -> {
            double newX = e.getSceneX() + dragDelta[0];
            double newY = e.getSceneY() + dragDelta[1];

            node.setLayoutX(newX);
            node.setLayoutY(newY);
        });

        node.setOnMouseEntered(e -> {
            if (!e.isPrimaryButtonDown()) {
                node.setCursor(javafx.scene.Cursor.HAND);
            }
        });

        node.setOnMouseExited(e -> {
            if (!e.isPrimaryButtonDown()) {
                node.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });
    }

    public void actualiser(){
        System.out.println("kfjfkj");
        this.afficherDiagramme();
        System.out.println("finito");
    }

}
