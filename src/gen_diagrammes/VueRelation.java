package gen_diagrammes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Affichage d'une relation entre deux classes
 */
public class VueRelation extends Pane implements Observateur {

    public enum TypeRelation {
        HERITAGE,
        IMPLEMENTATION,
        ASSOCIATION
    }

    private final VueClasse source;
    private final VueClasse destination;
    private final TypeRelation typeRelation;

    public VueRelation(VueClasse source, VueClasse destination, TypeRelation typeRelation) {
        this.source = source;
        this.destination = destination;
        this.typeRelation = typeRelation;
        this.actualiser();
    }


    /**
     * Actualise la vue de la relation
     */
    public void actualiser() {
        this.getChildren().clear();

        // Affiche la flèche si les deux classes concernées sont affichées
        if (this.source.estVisibleClasse() && this.destination.estVisibleClasse()) {

            double startX = source.getBoundsInParent().getMinX() + source.getWidth() / 2;
            double startY = source.getBoundsInParent().getMinY() + source.getHeight() / 2;
            double endX = destination.getBoundsInParent().getMinX() + destination.getWidth() / 2;
            double endY = destination.getBoundsInParent().getMinY() + destination.getHeight() / 2;

            // Calculer l'angle de la ligne
            double angle = Math.atan2(endY - startY, endX - startX);

            // Déterminer les bordures de la classe de destination
            double deltaX = (destination.getWidth() / 2) / Math.abs(Math.cos(angle));
            double deltaY = (destination.getHeight() / 2) / Math.abs(Math.sin(angle));

            // Choisir le plus petit delta pour s'assurer que la ligne touche le bord de la classe
            double offsetX = Math.min(deltaX, deltaY) * Math.cos(angle);
            double offsetY = Math.min(deltaX, deltaY) * Math.sin(angle);

            endX -= offsetX;
            endY -= offsetY;

            // Créer et ajouter la ligne
            Line line = new Line(startX, startY, endX, endY);
            this.getChildren().add(line);

            // Créer et ajouter la tête de flèche
            double arrowLength = 15;
            double arrowWidth = 7;

            double x1 = endX - arrowLength * Math.cos(angle - Math.PI / 6);
            double y1 = endY - arrowLength * Math.sin(angle - Math.PI / 6);
            double x2 = endX - arrowLength * Math.cos(angle + Math.PI / 6);
            double y2 = endY - arrowLength * Math.sin(angle + Math.PI / 6);

            Polygon arrowHead = new Polygon(endX, endY, x1, y1, x2, y2);

            switch (typeRelation) {
                case HERITAGE:
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(2);
                    arrowHead.setFill(Color.TRANSPARENT);
                    arrowHead.setStroke(Color.BLACK);
                    break;
                case IMPLEMENTATION:
                    line.getStrokeDashArray().addAll(10.0, 10.0);
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(2);
                    arrowHead.setFill(Color.TRANSPARENT);
                    arrowHead.setStroke(Color.BLACK);
                    break;
                case ASSOCIATION:
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(2);
                    arrowHead.setFill(Color.BLACK);
                    break;
            }
            this.getChildren().add(arrowHead);
        }

    }

}
