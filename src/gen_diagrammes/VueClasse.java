package gen_diagrammes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Affichage d'une classe
 */
public class VueClasse extends VBox implements Observateur {

    /**
     * Classe représentée
     */
    private final Classe classe;


    /**
     * Constructeur
     *
     * @param c Classe à afficher
     */
    public VueClasse(Classe c) {
        this.classe = c;
        this.actualiser();
        // Passe au premier plan lorsqu'on clique dessus
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> this.toFront());
    }


    /**
     * Vérifie si la classe est visible sur le diagramme
     *
     * @return true si la classe est visible, false sinon
     */
    public boolean estVisibleClasse() {
        return this.classe.isVisible();
    }


    /**
     * Actualise la vue de la classe
     */
    @Override
    public void actualiser() {
        this.getChildren().clear();
        if (this.classe.isVisible()) {
            this.setVisible(true);
            StackPane typeIndicator = createTypeIndicator(this.classe.getType());
            Text classNameText = new Text(this.classe.getNom());
            classNameText.setStyle("-fx-font-weight: bold;");

            HBox header = new HBox(typeIndicator, classNameText);
            header.setSpacing(5);
            header.setPadding(new Insets(5));
            header.setAlignment(Pos.CENTER);

            VBox vbox2 = new VBox();
            for (int i = 0; i < this.classe.getAttributs().size(); i++) {
                Attribut attribut = this.classe.getAttributs().get(i);
                // Affiche l'attribut s'il n'est pas masqué
                if (attribut.isVisible()) {
                    HBox attributBox = new HBox(createAccessIndicator(attribut.getTypeAcces()), new Text(" " + attribut));
                    attributBox.setSpacing(5);
                    vbox2.getChildren().add(attributBox);
                }
            }
            if (vbox2.getChildren().isEmpty()) {
                vbox2.setPrefHeight(10);
            }
            vbox2.setPadding(new Insets(5));

            VBox vbox3 = new VBox();
            for (int i = 0; i < this.classe.getMethodes().size(); i++) {
                Methode methode = this.classe.getMethodes().get(i);
                // N'affiche pas les méthodes lambda (générées par Java)
                // Affiche la méthode si elle n'est pas masquée
                if (!methode.getNom().contains("lambda$") && methode.isVisible()) {
                    HBox methodeBox = new HBox(createAccessIndicator(methode.getAcces()), new Text(" " + methode));
                    methodeBox.setSpacing(5);
                    vbox3.getChildren().add(methodeBox);
                }
            }
            if (vbox3.getChildren().isEmpty()) {
                vbox3.setPrefHeight(10);
            }
            vbox3.setPadding(new Insets(5));

            this.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");

            Region separator1 = new Region();
            separator1.setStyle("-fx-background-color: black;");
            separator1.setPrefHeight(2);
            Region separator2 = new Region();
            separator2.setStyle("-fx-background-color: black;");
            separator2.setPrefHeight(2);

            this.getChildren().addAll(header, separator1, vbox2, separator2, vbox3);
        } else {
            this.setVisible(false);
        }
    }


    /**
     * Crée un indicateur de type de classe
     * (cercle coloré avec la première lettre du type)
     *
     * @param type Type de la classe
     */
    private StackPane createTypeIndicator(String type) {
        Circle circle = new Circle(12);
        Text text = new Text(type.substring(0, 1).toUpperCase());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.5);
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font-weight: bold;");

        switch (type) {
            case Classe.CLASS:
                circle.setFill(Color.web("#ADD1B2"));
                break;
            case Classe.INTERFACE:
                circle.setFill(Color.web("#B4A7E5"));
                break;
            case Classe.ABSTRACT_CLASS:
                circle.setFill(Color.web("#A9DCDF"));
                break;
            default:
                circle.setFill(Color.LIGHTGRAY);
                break;
        }

        StackPane stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().addAll(circle, text);
        return stack;
    }


    /**
     * Crée un indicateur de type d'accès
     * (pour les attributs et les méthodes)
     *
     * @param access Type d'accès (public, private, protected, package)
     * @return StackPane
     */
    private StackPane createAccessIndicator(String access) {
        Circle circle = new Circle(4);
        switch (access) {
            case Classe.PUBLIC:
                circle.setFill(Color.web("#4ede50"));
                break;
            case Classe.PRIVATE:
                circle.setFill(Color.web("#f08181"));
                break;
            case Classe.PROTECTED:
                circle.setFill(Color.web("#f0e881"));
                break;
            case Classe.PACKAGE_PRIVATE:
                circle.setFill(Color.web("#81a2f0"));
                break;
            default:
                circle.setFill(Color.GRAY);
                break;
        }
        return new StackPane(circle);
    }

}