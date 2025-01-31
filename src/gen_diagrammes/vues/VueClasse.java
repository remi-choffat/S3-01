package gen_diagrammes.vues;

import gen_diagrammes.Main;
import gen_diagrammes.diagramme.Attribut;
import gen_diagrammes.diagramme.Classe;
import gen_diagrammes.diagramme.Methode;
import gen_diagrammes.gInterface.Observateur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Getter;

/**
 * Affichage d'une classe
 */
@Getter
public class VueClasse extends VBox implements Observateur {

    /**
     * Classe représentée
     */
    private final Classe classe;

    /**
     * Menu contextuel attaché à la vue
     */
    private ContextMenu contextMenu;


    /**
     * Constructeur
     *
     * @param c Classe à afficher
     */
    public VueClasse(Classe c) {
        this.classe = c;
        this.contextMenu = null;
        this.initialiserComportement();
        this.actualiser();
    }


    /**
     * Initialise le comportement de la vue
     * (clic, menu contextuel)
     */
    private void initialiserComportement() {

        // Passe au premier plan lorsqu'on clique dessus
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> this.toFront());

        // CRÉE UN MENU CONTEXTUEL
        this.contextMenu = new ContextMenu();

        // Crée un titre au menu
        MenuItem title = new MenuItem(this.classe.getTypeClasseString() + " " + this.classe.getNom());
        title.setStyle("-fx-font-weight: bold;");

        MenuItem hideClass = new MenuItem("Masquer la classe");
        CheckMenuItem showAttributes = new CheckMenuItem("Afficher les attributs");
        showAttributes.setSelected(true);
        CheckMenuItem showMethods = new CheckMenuItem("Afficher les méthodes");
        showMethods.setSelected(true);
        CheckMenuItem showParentClasses = new CheckMenuItem("Afficher les classes parent");
        showParentClasses.setSelected(true);
        // Si la classe n'a pas de parent, on désactive l'option
        if (this.classe.getParents().isEmpty()) {
            showParentClasses.setDisable(true);
        }
        // Lorsque le bouton est cliqué, inverse l'affichage des classes parent
        showParentClasses.setOnAction(event -> {
            if (showParentClasses.isSelected()) {
                this.classe.afficherParents();
            } else {
                this.classe.masquerParents();
            }
            actualiser();
        });

        contextMenu.getItems().addAll(title, new SeparatorMenuItem(), hideClass, new SeparatorMenuItem(), showAttributes, showMethods, showParentClasses);

        hideClass.setOnAction(event -> this.classe.setVisibilite(false));

        if (!Main.afficherAttributs) {
            showAttributes.setSelected(false);
            classe.masquerAttributs();
        }
        showAttributes.setOnAction(event -> {
            if (showAttributes.isSelected()) {
                this.classe.afficherAttributs();
            } else {
                this.classe.masquerAttributs();
            }
            actualiser();
        });

        if (!Main.afficherMethodes) {
            showMethods.setSelected(false);
            classe.masquerMethodes();
        }
        showMethods.setOnAction(event -> {
            if (showMethods.isSelected()) {
                this.classe.afficherMethodes();
            } else {
                this.classe.masquerMethodes();
            }
            actualiser();
        });

        this.setOnContextMenuRequested(e -> contextMenu.show(this, e.getScreenX(), e.getScreenY()));
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
            // Met le texte en gras, et aussi en italique si la classe est abstraite
            classNameText.setStyle("-fx-font-weight: bold;" + (this.classe.getType().equals(Classe.ABSTRACT_CLASS) ? "-fx-font-style: italic;" : ""));

            // Affiche le nom de la classe
            HBox header = new HBox(typeIndicator, classNameText);
            header.setSpacing(5);
            header.setPadding(new Insets(5, 5, 3, 5));
            header.setAlignment(Pos.CENTER);
            this.getChildren().add(header);

            // Affiche le nom du package si la classe en a un
            String packageString = this.classe.getNomPackage();
            if (packageString != null) {
                Text packageText = new Text(packageString);
                packageText.setStyle("-fx-font-size: 10px;");
                HBox packageBox = new HBox(packageText);
                packageBox.setSpacing(5);
                packageBox.setPadding(new Insets(0, 5, 5, 5));
                packageBox.setAlignment(Pos.CENTER);
                this.getChildren().add(packageBox);
            }

            VBox vbox2 = new VBox();
            for (Attribut attribut : this.classe.getAttributs()) {
                // Affiche l'attribut s'il n'est pas masqué
                if (attribut.isVisible()) {
                    Text texteAttribut = new Text(attribut.toString());
                    if (attribut.isStaticAttr()) {
                        texteAttribut.setStyle("-fx-underline: true;");
                    }
                    HBox attributBox = new HBox(createAccessIndicator(attribut.getTypeAcces()), texteAttribut);
                    attributBox.setSpacing(5);
                    vbox2.getChildren().add(attributBox);
                }
            }
            if (vbox2.getChildren().isEmpty()) {
                vbox2.setPrefHeight(10);
            }
            vbox2.setPadding(new Insets(5));

            VBox vbox3 = new VBox();
            for (Methode methode : this.classe.getMethodes()) {
                // N'affiche pas les méthodes lambda (générées par Java)
                // Affiche la méthode si elle n'est pas masquée
                if (!methode.getNom().contains("lambda$") && methode.isVisible()) {
                    Text texteMethode = new Text(methode.toString());
                    if (methode.isStaticMethode()) {
                        texteMethode.setStyle("-fx-underline: true;");
                    }
                    if (methode.isAbstractMethode()) {
                        texteMethode.setStyle("-fx-font-style: italic;");
                    }
                    HBox methodeBox = new HBox(createAccessIndicator(methode.getAcces()), texteMethode);
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

            this.getChildren().addAll(separator1, vbox2, separator2, vbox3);
            updateContextMenu();
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


    /**
     * Met à jour le menu contextuel
     */
    private void updateContextMenu() {
        // Si la classe n'a pas de parent, on désactive l'option
        contextMenu.getItems().get(6).setDisable(this.classe.getParents().isEmpty());
    }


    /**
     * Met à jour le menu contextuel pour afficher ou masquer les attributs
     *
     * @param isSelected true si les attributs sont affichés, false sinon
     */
    public void updateShowAttributesCheckMenuItem(boolean isSelected) {
        ((CheckMenuItem) contextMenu.getItems().get(4)).setSelected(isSelected);
    }


    /**
     * Met à jour le menu contextuel pour afficher ou masquer les méthodes
     *
     * @param isSelected true si les méthodes sont affichées, false sinon
     */
    public void updateShowMethodsCheckMenuItem(boolean isSelected) {
        ((CheckMenuItem) contextMenu.getItems().get(5)).setSelected(isSelected);
    }

}