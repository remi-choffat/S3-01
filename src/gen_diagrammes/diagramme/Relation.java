package gen_diagrammes.diagramme;

import gen_diagrammes.gInterface.Observateur;
import gen_diagrammes.gInterface.Sujet;
import lombok.Getter;

import java.util.ArrayList;

/**
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

}
