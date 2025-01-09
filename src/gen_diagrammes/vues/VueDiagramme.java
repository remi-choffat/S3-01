package gen_diagrammes.vues;

import gen_diagrammes.diagramme.Classe;
import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.Main;
import gen_diagrammes.diagramme.Relation;
import gen_diagrammes.gInterface.Observateur;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import static gen_diagrammes.Main.makeDraggable;
import static gen_diagrammes.Main.updateRelations;

public class VueDiagramme extends StackPane implements Observateur {

    public VueDiagramme(){
        super();
    }

    @Override
    public void actualiser() {
        //Diagramme.getInstance().afficher(Main.stackPane);
        afficher();
    }


    public void afficher() {
        this.getChildren().clear();
        Diagramme diagramme = Diagramme.getInstance();
        Pane ligneClasse = new Pane();
        Pane relationPane = new Pane();
        this.getChildren().addAll(relationPane, ligneClasse);

        for (Classe c : diagramme.getListeClasses()) {

            VueClasse vueClasse; //

            if (c.getObservateurs().isEmpty()){ //
                vueClasse = new VueClasse(c);
                makeDraggable(vueClasse);
                vueClasse.relocate(c.getLongueur(), c.getLargeur());
                Diagramme.getInstance().ajouterObservateur(vueClasse);
                c.ajouterObservateur(vueClasse);
            } else {//
                vueClasse = (VueClasse)c.getObservateurs().getFirst();
            }

            // Appliquer les paramètres d'affichage
            if (Main.afficherAttributs) {
                c.afficherAttributs();
            } else {
                c.masquerAttributs();
            }
            if (Main.afficherMethodes) {
                c.afficherMethodes();
            } else {
                c.masquerMethodes();
            }

            vueClasse.actualiser();

            ligneClasse.getChildren().add(vueClasse);
        }

        // Ajoute les relations
        for (Relation relation : diagramme.getRelations()) {

            if( ! relation.getObservateurs().isEmpty()){
                relation.getObservateurs().clear();
            }

            VueClasse source = (VueClasse) ligneClasse.getChildren().stream()
                    .filter(node -> ((VueClasse) node).getClasse().getNom().equals(relation.getSource().getNom()))
                    .findFirst().orElse(null);

            VueClasse destination = (VueClasse) ligneClasse.getChildren().stream()
                    .filter(node -> ((VueClasse) node).getClasse().getNom().equals(relation.getDestination().getNom()))
                    .findFirst().orElse(null);


            if (source != null && destination != null) {
                VueRelation.TypeRelation typeRelation = Main.determineTypeRelation(relation);
                VueRelation vueRelation = new VueRelation(source, destination, typeRelation);
                Main.relations.add(vueRelation);
                diagramme.ajouterObservateur(vueRelation); // Ajout de l'observateur
                relationPane.getChildren().add(vueRelation);
                relation.ajouterObservateur(vueRelation);
            }

//            if (source != null && destination == null) {
//                System.out.println("Destination manquante pour la relation avec la source : " + source.getClasse().getNom());
//
//                // Test d'affichage forcé
//                Line testLine = new Line(100, 100, 300, 300);
//                testLine.setStroke(Color.RED);
//                testLine.setStrokeWidth(3);
//                relationPane.getChildren().add(testLine);
//                System.out.println("Ligne de test ajoutée pour la source : " + source.getClasse().getNom());
//            }

        }

        // Met à jour les relations à chaque déplacement de classe
        for (Node node : ligneClasse.getChildren()) {
            if (node instanceof VueClasse vueClasse) {
                vueClasse.layoutXProperty().addListener((observable, oldValue, newValue) -> updateRelations());
                vueClasse.layoutYProperty().addListener((observable, oldValue, newValue) -> updateRelations());
            }
        }

        updateRelations();

    }

}
