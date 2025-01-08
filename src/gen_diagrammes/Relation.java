package gen_diagrammes;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Représente une relation entre deux classes
 */
@Getter
public class Relation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Classe source;
    private final Classe destination;
    private final String type;

    public Relation(Classe source, Classe destination, String type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
    }

}
