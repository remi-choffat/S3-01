package gen_diagrammes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class VueDiagramme extends StackPane implements Observateur{

    public VueDiagramme(){
        super();
    }

    @Override
    public void actualiser() {
        Diagramme.getInstance().afficher(Main.stackPane);
    }


    // TODO - Faire quelque chose avec ça...
    //  La méthode actuellement utilisée est Diagramme.getInstance().afficher(StackPane)
//    private void afficherDiagramme() {
//        this.getChildren().clear();
//        Diagramme diagramme = Diagramme.getInstance();
//        Pane ligneClasse = new Pane();
//        Pane relationPane = new Pane();
//        this.getChildren().addAll(relationPane, ligneClasse);
//
//        for (Classe c : diagramme.getListeClasses()) {
//            VueClasse vueClasse;
//            if(c.getObservateurs().isEmpty()) {
//                vueClasse = new VueClasse(c);
//                Main.makeDraggable(vueClasse);
//                vueClasse.relocate(c.getLongueur(), c.getLargeur());
//                c.ajouterObservateur(vueClasse);
//            } else {
//                vueClasse = (VueClasse)c.getObservateurs().getFirst();
//            }
//
//            // Appliquer les paramètres d'affichage
//            if (Main.afficherAttributs) {
//                c.afficherAttributs();
//            } else {
//                c.masquerAttributs();
//            }
//            if (Main.afficherMethodes) {
//                c.afficherMethodes();
//            } else {
//                c.masquerMethodes();
//            }
//
//            vueClasse.actualiser();
//
//            ligneClasse.getChildren().add(vueClasse);
//        }
//
//        // Ajoute les relations
//        for (int i = 0; i < diagramme.getListeClasses().size() - 1; i++) {
//            VueClasse source = (VueClasse) ligneClasse.getChildren().get(i);
//            VueClasse destination = (VueClasse) ligneClasse.getChildren().get(i + 1);
//            VueRelation.TypeRelation typeRelation = VueRelation.TypeRelation.ASSOCIATION; // Change ce type selon tes besoins
//            VueRelation vueRelation = new VueRelation(source, destination, typeRelation);
//            Main.relations.add(vueRelation);
//            //diagramme.ajouterObservateur(vueRelation); //ptet ici, ca modifie l'arraylist de diagramme
//            relationPane.getChildren().add(vueRelation);
//        }
//
//        // Met à jour les relations à chaque déplacement de classe
//        for (Node node : ligneClasse.getChildren()) {
//            if (node instanceof VueClasse vueClasse) {
//                vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> Main.updateRelations());
//                vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> Main.updateRelations());
//            }
//        }
//        Main.updateRelations();
//    }

}
