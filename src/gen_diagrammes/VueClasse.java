package gen_diagrammes;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VueClasse extends VBox implements Observateur {

    private Classe classe;
    //mettre un attribut boolean visible dans les classes Classe, Methode et Attribut
    //Vue Classe ne gère pas les attributs et les méthodes, ce sera à faire dans VueAttribut et VueMethode
    //j'imagine ?

    //on gère pas encore la position, ca sera dans le contrôleur

    public VueClasse(Classe c) {
        this.classe = c;
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
            this.getChildren().addAll(new Label(texteEnTete), new Label(texteAttributs), new Label(texteMethodes));
        }
    }
}
