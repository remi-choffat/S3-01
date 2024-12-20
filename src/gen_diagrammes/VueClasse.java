package gen_diagrammes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Affichage d'une classe
 */
public class VueClasse extends VBox implements Observateur {

    /**
     * Classe à afficher
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
    }


    /**
     * Actualise la vue de la classe
     */
    @Override
    public void actualiser() {
        this.getChildren().clear();
        if (this.classe.isVisible()) {
            String texteEnTete = "(" + this.classe.getType().substring(0, 1).toUpperCase() + ") " + this.classe.getNom();
            StringBuilder texteAttributs = new StringBuilder();
            StringBuilder texteMethodes = new StringBuilder();
            for (Attribut attribut : this.classe.getAttributs()) {
                if (attribut.isVisible()) {
                    texteAttributs.append(attribut.toString()).append("\n");
                }
            }
            for (Methode methode : this.classe.getMethodes()) {
                if (methode.isVisible()) {
                    texteMethodes.append(methode.toString()).append("\n");
                }
            }
            HBox h1 = new HBox();
            HBox h2 = new HBox();
            HBox h3 = new HBox();

            Text l1 = new Text(texteEnTete);
            h1.getChildren().add(l1);
            Text l2 = new Text(texteAttributs.toString());
            h2.getChildren().add(l2);
            Text l3 = new Text(texteMethodes.toString());
            h3.getChildren().add(l3);

            h1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            h2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            h3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");

            this.getChildren().addAll(h1, h2, h3);
        }
    }
}
