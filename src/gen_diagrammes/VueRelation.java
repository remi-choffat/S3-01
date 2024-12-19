package gen_diagrammes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * Affichage d'une relation entre deux classes
 */
public class VueRelation extends Pane {

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

    public void actualiser() {
        this.getChildren().clear();

        double startX = source.getBoundsInParent().getMinX();
        double startY = source.getBoundsInParent().getMinY() + source.getHeight() / 2;
        double endX = destination.getBoundsInParent().getMinX();
        double endY = destination.getBoundsInParent().getMinY() + destination.getHeight() / 2;

        // Ajuster les coordonnées si la flèche va de droite à gauche
        if (startX > endX) {
            startX = source.getBoundsInParent().getMaxX();
            endX = destination.getBoundsInParent().getMaxX();
        }

        Line line = new Line(startX, startY, endX, endY);
        this.getChildren().add(line);

        double angle = Math.atan2(endY - startY, endX - startX);
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
