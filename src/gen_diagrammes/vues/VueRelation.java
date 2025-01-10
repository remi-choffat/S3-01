package gen_diagrammes.vues;

import gen_diagrammes.diagramme.Attribut;
import gen_diagrammes.diagramme.Relation;
import gen_diagrammes.gInterface.Observateur;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
        this.layout();
        this.getChildren().clear();

        if (this.source.estVisibleClasse() && this.destination.estVisibleClasse()) {
            double destinationWidth = destination.getBoundsInParent().getWidth();
            double destinationHeight = destination.getBoundsInParent().getHeight();

            if (destinationWidth <= 0 || destinationHeight <= 0) {
                destinationWidth = 150; // Largeur par défaut
                destinationHeight = 100; // Hauteur par défaut
            }

            // Position de départ (centre de la source)
            double startX = source.getBoundsInParent().getMinX() + source.getBoundsInParent().getWidth() / 2;
            double startY = source.getBoundsInParent().getMinY() + source.getBoundsInParent().getHeight() / 2;

            // Position de fin (centre de la destination)
            double endX = destination.getBoundsInParent().getMinX() + destinationWidth / 2;
            double endY = destination.getBoundsInParent().getMinY() + destinationHeight / 2;

            // Calcul initial de l'angle
            double angle = Math.atan2(endY - startY, endX - startX);

            // Décalage spécifique aux associations pour éviter la superposition
            double offset = 20; // Décalage pour les flèches d'association
            if (typeRelation == TypeRelation.ASSOCIATION) {
                startX += offset * Math.sin(angle);
                startY -= offset * Math.cos(angle);
                endX += offset * Math.sin(angle);
                endY -= offset * Math.cos(angle);

                // Recalcul de l'angle après le décalage
                angle = Math.atan2(endY - startY, endX - startX);
            }

            // Calcul des marges pour éviter que la flèche touche les classes
            double deltaX = (destinationWidth / 2) / Math.abs(Math.cos(angle));
            double deltaY = (destinationHeight / 2) / Math.abs(Math.sin(angle));
            double offsetX = Math.min(deltaX, deltaY) * Math.cos(angle);
            double offsetY = Math.min(deltaX, deltaY) * Math.sin(angle);

            // Ajustement des positions finales pour rester en dehors des bords
            endX -= offsetX;
            endY -= offsetY;

            double margin = 10; // Marge pour éviter les collisions
            startX += margin * Math.cos(angle);
            startY += margin * Math.sin(angle);

            // Création de la ligne (corps de la flèche)
            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);

            if (typeRelation == TypeRelation.IMPLEMENTATION) {
                line.getStrokeDashArray().setAll(10.0, 5.0);
                line.setStrokeDashOffset(0);
            }

            this.getChildren().add(line);

            // Ajout de la tête de la flèche
            double arrowLength = 15; // Longueur de la tête de flèche
            double x1 = endX - arrowLength * Math.cos(angle - Math.PI / 6);
            double y1 = endY - arrowLength * Math.sin(angle - Math.PI / 6);
            double x2 = endX - arrowLength * Math.cos(angle + Math.PI / 6);
            double y2 = endY - arrowLength * Math.sin(angle + Math.PI / 6);

            switch (typeRelation) {
                case HERITAGE:
                    Polygon arrowHeadHeritage = new Polygon(endX, endY, x1, y1, x2, y2);
                    arrowHeadHeritage.setFill(Color.WHITE);
                    arrowHeadHeritage.setStroke(Color.BLACK);
                    arrowHeadHeritage.setStrokeWidth(1);
                    this.getChildren().add(arrowHeadHeritage);
                    break;
                case IMPLEMENTATION:
                    Polygon arrowHeadImplementation = new Polygon(endX, endY, x1, y1, x2, y2);
                    arrowHeadImplementation.setFill(Color.WHITE);
                    arrowHeadImplementation.setStroke(Color.BLACK);
                    arrowHeadImplementation.setStrokeWidth(0.5);
                    this.getChildren().add(arrowHeadImplementation);
                    break;
                case ASSOCIATION:
                    Line arrowSide1 = new Line(endX, endY, x1, y1);
                    Line arrowSide2 = new Line(endX, endY, x2, y2);
                    arrowSide1.setStroke(Color.BLACK);
                    arrowSide2.setStroke(Color.BLACK);
                    arrowSide1.setStrokeWidth(2);
                    arrowSide2.setStrokeWidth(2);
                    this.getChildren().addAll(arrowSide1, arrowSide2);

                    // Ajout du nom des attributs dans le cas des associations
                    for (Attribut a : source.getClasse().getAttributs()) {
                        if (a.getType().equals(destination.getClasse().getNom())) {
                            Label lNom = new Label(a.getNom());
                            this.getChildren().add(lNom);
                            lNom.setLayoutX((startX + endX) / 2);
                            lNom.setLayoutY((startY + endY) / 2);
                            lNom.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                    break;
            }
        }
    }
}