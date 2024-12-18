package gen_diagrammes;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VueClasse extends VBox implements Vue {

    //mettre un attribut boolean visible dans les classes Classe, Methode et Attribut
    //Vue Classe ne gère pas les attributs et les méthodes, ce sera à faire dans VueAttribut et VueMethode
    //j'imagine ?

    //on gère pas encore la position, ca sera dans le contrôleur

    public VueClasse() {
    }

    @Override
    public void actualiser(Modele m) {
        this.getChildren().clear();
        Classe c = (Classe) (m);
        if (m.estVisible()) {
            String texteEnTete = "(C)" + c.getType() + " " + c.getNom();
            String texteAttributs = "";
            String texteMethodes = "";
            for (Attribut attribut : c.getAttributs()) {
                if (attribut.estVisible()) {
                    texteAttributs = texteAttributs + attribut.toString() + "\n";
                }
            }
            for (Methode methode : c.getMethodes()) {
                if (methode.estVisible()) {
                    texteMethodes = texteMethodes + methode.toString() + "\n";
                }
            }
            this.getChildren().addAll(new Label(texteEnTete), new Label(texteAttributs), new Label(texteMethodes));
        }
    }
}
