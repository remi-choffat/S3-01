package gen_diagrammes;

import javafx.scene.layout.VBox;

public class VueClasse extends VBox implements Vue{

    //mettre un attribut boolean visible dans les classes Classe, Methode et Attribut
    //Vue Classe ne gère pas les attributs et les méthodes, ce sera à faire dans VueAttribut et VueMethode
    //j'imagine ?

    //on gère pas encore la position, ca sera dans le contrôleur

    public VueClasse() {}

    @Override
    public void update(Modele m) {
        //à chaque update, on efface tout et on re-remplit ?
        //à voir si on peut pas faire autrement histoire de pas faire péter la machine
        this.getChildren().clear();
        Classe c = (Classe)(m);
        //mettre un premier label avec le nom, le type et l'accès de la classe
        //mettre une vbox avec toutes les VueAttribut
        //mettre une vbox avec toutes les VueMethode
    }
}
