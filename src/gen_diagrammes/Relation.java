package gen_diagrammes;

public class Relation {
    private final Classe source;
    private final Classe destination;
    private final String type;

    public Relation(Classe source, Classe destination, String type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
    }

    public Classe getSource() {
        return source;
    }

    public Classe getDestination() {
        return destination;
    }

    public String getType() {
        return type;
    }


}
