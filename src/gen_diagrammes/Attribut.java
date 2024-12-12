package gen_diagrammes;

/**
 * Représente un attribut d'une classe du diagramme
 */
public class Attribut {

    private String nom;
    private String typeAcces;
    private String type;

    public Attribut(String nom, String typeAcces, String type) {
        this.nom = nom;
        this.typeAcces = typeAcces;
        this.type = type;
    }

    public Attribut() {
        this.nom = "Inconnu";
        this.typeAcces = "Inconnu";
        this.type = "Inconnu";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeAcces() {
        return typeAcces;
    }

    public void setTypeAcces(String typeAcces) {
        this.typeAcces = typeAcces;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return typeAcces + " " + type + " " + nom + "\n";
    }
}
