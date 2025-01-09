package gen_diagrammes.diagramme;

import lombok.Getter;

/**
 * Représente une relation entre deux classes
 */
@Getter
public class Relation {

    private final Classe source;
    private final Classe destination;
    private final String type;

    public Relation(Classe source, Classe destination, String type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
    }



}
