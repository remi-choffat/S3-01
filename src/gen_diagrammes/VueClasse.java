package gen_diagrammes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

public class VueClasse extends VBox implements Observateur {

    private Classe classe;
    //mettre un attribut boolean visible dans les classes Classe, Methode et Attribut
    //Vue Classe ne gère pas les attributs et les méthodes, ce sera à faire dans VueAttribut et VueMethode
    //j'imagine ?

    //on gère pas encore la position, ca sera dans le contrôleur

    public VueClasse(Classe c) {
        this.classe = c;
        this.actualiser();
    }

    @Override
    public void actualiser() {
        this.getChildren().clear();
        if (this.classe.estVisible()) {
            String texteEnTete = "(C)" + this.classe.getType() + " " + this.classe.getNom();
            String texteAttributs = "";
            String texteMethodes = "";
            for (Attribut attribut : this.classe.getAttributs()) {
                if (attribut.estVisible()) {
                    texteAttributs = texteAttributs + attribut.toString() + "\n";
                }
            }
            for (Methode methode : this.classe.getMethodes()) {
                if (methode.estVisible()) {
                    texteMethodes = texteMethodes + methode.toString() + "\n";
                }
            }
            HBox h1 = new HBox();
            HBox h2 = new HBox();
            HBox h3 = new HBox();

            Text l1 = new Text(texteEnTete);
            h1.getChildren().add(l1);
            Text l2 = new Text(texteAttributs);
            h2.getChildren().add(l2);
            Text l3 = new Text(texteMethodes);
            h3.getChildren().add(l3);

            h1.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            h2.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");
            h3.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgrey;");

            this.getChildren().addAll(h1, h2, h3);
        }
    }
}
