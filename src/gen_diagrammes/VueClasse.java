package gen_diagrammes;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
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
            String texteEnTete = "(" + this.classe.getType().substring(0, 1).toUpperCase() + ") " + this.classe.getNom();
            StringBuilder texteAttributs = new StringBuilder();
            StringBuilder texteMethodes = new StringBuilder();

            for (int i = 0; i < this.classe.getAttributs().size(); i++) {
                Attribut attribut = this.classe.getAttributs().get(i);
                if (attribut.isVisible()) {
                    texteAttributs.append(attribut);
                    if (i < this.classe.getAttributs().size() - 1) {
                        texteAttributs.append("\n");
                    }
                }
            }

            for (int i = 0; i < this.classe.getMethodes().size(); i++) {
                String nomMethode = this.classe.getMethodes().get(i).getNom();
                // N'affiche pas les méthodes lambda (générées par Java)
                if (!nomMethode.contains("lambda$")) {
                    Methode methode = this.classe.getMethodes().get(i);
                    if (methode.isVisible()) {
                        texteMethodes.append(methode);
                        if (i < this.classe.getMethodes().size() - 1) {
                            texteMethodes.append("\n");
                        }
                    }
                }
            }

            Text l1 = new Text(texteEnTete);
            Text l2 = new Text(texteAttributs.toString());
            Text l3 = new Text(texteMethodes.toString());

            VBox vbox1 = new VBox(l1);
            vbox1.setPadding(new Insets(5));
            VBox vbox2 = new VBox(l2);
            vbox2.setPadding(new Insets(5));
            VBox vbox3 = new VBox(l3);
            vbox3.setPadding(new Insets(5));

            this.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            this.setSpacing(5);

            Region separator1 = new Region();
            separator1.setStyle("-fx-background-color: black;");
            separator1.setPrefHeight(2);
            Region separator2 = new Region();
            separator2.setStyle("-fx-background-color: black;");
            separator2.setPrefHeight(2);

            this.getChildren().addAll(vbox1, separator1, vbox2, separator2, vbox3);
        }
    }

}