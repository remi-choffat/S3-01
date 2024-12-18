package gen_diagrammes;

/**
 * Représente un attribut d'une classe du diagramme
 */
public class Attribut {

    private String nom;
    private String typeAcces;
    private String type;
    private boolean estVisible;

    public Attribut(String nom, String typeAcces, String type) {
        this.nom = nom;
        this.typeAcces = typeAcces;
        this.type = type;
        this.estVisible = true;
    }

    public Attribut() {
        this.nom = "Inconnu";
        this.typeAcces = "Inconnu";
        this.type = "Inconnu";
        this.estVisible = true;
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

    public boolean estVisible() {
        return estVisible;
    }

    public void setEstVisible(boolean estVisible) {
        this.estVisible = estVisible;
    }

    public String toString() {
        String res = "";
        switch (typeAcces) {
            case Classe.PUBLIC -> res += "+";
            case Classe.PRIVATE -> res += "-";
            case Classe.PROTECTED -> res += "#";
            case Classe.PACKAGE_PRIVATE -> res += "~";
        }
        res += nom + " : " + type;
        return res;
    }
}