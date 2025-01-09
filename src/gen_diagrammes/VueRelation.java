package gen_diagrammes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import lombok.Getter;

/**
 * Affichage d'une relation entre deux classes
 */
public class VueRelation extends Pane implements Observateur {

    public enum TypeRelation {
        HERITAGE,
        IMPLEMENTATION,
        ASSOCIATION,
        AGGREGATION,
        COMPOSITION
    }

    private final VueClasse source;
    private final VueClasse destination;
    @Getter
    private final TypeRelation typeRelation;

    public VueRelation(VueClasse source, VueClasse destination, TypeRelation typeRelation) {
        this.source = source;
        this.destination = destination;
        this.typeRelation = typeRelation;
        this.actualiser();
    }

    public void actualiser() {
//        System.out.println("Actualisation de VueRelation pour " + source.getClasse().getNom() + " -> " + destination.getClasse().getNom());
        this.layout(); // Forcer la mise à jour des dimensions de la Pane
        this.getChildren().clear();
        if (this.source.estVisibleClasse() && this.destination.estVisibleClasse()) {
            double destinationWidth = destination.getBoundsInParent().getWidth();
            double destinationHeight = destination.getBoundsInParent().getHeight();
            if (destinationWidth <= 0 || destinationHeight <= 0) {
                destinationWidth = 150; // Valeurs par défaut
                destinationHeight = 100;
            }
            double startX = source.getBoundsInParent().getMinX() + source.getBoundsInParent().getWidth() / 2;
            double startY = source.getBoundsInParent().getMinY() + source.getBoundsInParent().getHeight() / 2;
            double endX = destination.getBoundsInParent().getMinX() + destinationWidth / 2;
            double endY = destination.getBoundsInParent().getMinY() + destinationHeight / 2;
            double angle = Math.atan2(endY - startY, endX - startX);
            double deltaX = (destinationWidth / 2) / Math.abs(Math.cos(angle));
            double deltaY = (destinationHeight / 2) / Math.abs(Math.sin(angle));
            double offsetX = Math.min(deltaX, deltaY) * Math.cos(angle);
            double offsetY = Math.min(deltaX, deltaY) * Math.sin(angle);
            endX -= offsetX;
            endY -= offsetY;

            // Ligne principale
            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            if (typeRelation == TypeRelation.IMPLEMENTATION) {
                line.getStrokeDashArray().setAll(10.0, 5.0); // motif de tirets
                line.setStrokeDashOffset(0); // décalage des tirets
            }
            this.getChildren().add(line);
//            System.out.println("Ligne ajoutée avec coordonnées : StartX = " + startX + ", StartY = " + startY +
//                    ", EndX = " + endX + ", EndY = " + endY);

            // Ajout de la flèche
            double arrowLength = 15;
            double x1 = endX - arrowLength * Math.cos(angle - Math.PI / 6);
            double y1 = endY - arrowLength * Math.sin(angle - Math.PI / 6);
            double x2 = endX - arrowLength * Math.cos(angle + Math.PI / 6);
            double y2 = endY - arrowLength * Math.sin(angle + Math.PI / 6);
            Polygon arrowHead = new Polygon(endX, endY, x1, y1, x2, y2);

            switch (typeRelation) {
                case HERITAGE:
                    arrowHead.setFill(Color.WHITE);
                    arrowHead.setStroke(Color.BLACK);
                    arrowHead.setStrokeWidth(1);
                    this.getChildren().add(arrowHead);
//                    System.out.println("Flèche HERITAGE ajoutée avec coordonnées : EndX = " + endX + ", EndY = " + endY +
//                            ", X1 = " + x1 + ", Y1 = " + y1 + ", X2 = " + x2 + ", Y2 = " + y2);
                    break;
                case IMPLEMENTATION:
                    arrowHead.setFill(Color.WHITE);
                    arrowHead.setStroke(Color.BLACK);
                    arrowHead.setStrokeWidth(0.5);
                    this.getChildren().add(arrowHead);
//                    System.out.println("Flèche IMPLEMENTATION ajoutée avec coordonnées : EndX = " + endX + ", EndY = " + endY +
//                            ", X1 = " + x1 + ", Y1 = " + y1 + ", X2 = " + x2 + ", Y2 = " + y2);
                    break;
                case ASSOCIATION:
                    arrowHead.setFill(Color.BLACK);
                    this.getChildren().add(arrowHead);
//                    System.out.println("Flèche ASSOCIATION ajoutée avec coordonnées : EndX = " + endX + ", EndY = " + endY +
//                            ", X1 = " + x1 + ", Y1 = " + y1 + ", X2 = " + x2 + ", Y2 = " + y2);
                    break;

            }
        }
    }

}
