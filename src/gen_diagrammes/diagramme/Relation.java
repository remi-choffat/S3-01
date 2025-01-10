package gen_diagrammes.diagramme;

import gen_diagrammes.gInterface.Observateur;
import gen_diagrammes.gInterface.Sujet;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Objects;

/**Classe source, Classe destination, String type
 * Représente une relation entre deux classes
 */
@Getter
public class Relation implements Sujet {

    private final Classe source;
    private final Classe destination;
    private final String type;
    @Getter
    private ArrayList<Observateur> observateurs;

    public Relation(Classe source, Classe destination, String type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.observateurs = new ArrayList<>();
//        System.out.println("NOUVEAU : " + this);
    }

    public void ajouterObservateur(Observateur observateur) {
        this.observateurs.add(observateur);
    }

    public void supprimerObservateur(Observateur observateur) {
        this.observateurs.remove(observateur);
    }

    public void notifierObservateurs() {
        for (Observateur o : this.observateurs) {
            o.actualiser();
        }
    }

    public String toString() {
        return "Relation d'" + type + " entre " + source.getNom() + " et " + destination.getNom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return type == relation.getType() && source.equals(relation.getSource()) && destination.equals(relation.getDestination());
    }



}
