package gen_diagrammes;

import lombok.Getter;
import lombok.Setter;

/**
 * Représente un attribut d'une classe du diagramme
 */
@Setter
@Getter
public class Attribut {

    private String nom;
    private String typeAcces;
    private String type;
    private boolean visible;


    /**
     * Constructeur
     *
     * @param nom       Nom de l'attribut
     * @param typeAcces Type d'accès (public, private, protected, package)
     * @param type      Type de l'attribut
     */
    public Attribut(String nom, String typeAcces, String type) {
        this.nom = nom;
        this.typeAcces = typeAcces;
        this.type = type;
        this.visible = true;
    }


    /**
     * Constructeur par défaut
     */
    public Attribut() {
        this.nom = "Inconnu";
        this.typeAcces = "Inconnu";
        this.type = "Inconnu";
        this.visible = true;
    }


    /**
     * Retourne une chaîne de caractères représentant l'attribut
     *
     * @return String
     */
    public String toString() {
        String res = "";
        switch (typeAcces) {
            case Classe.PUBLIC:
                res += "+";
                break;
            case Classe.PRIVATE:
                res += "-";
                break;
            case Classe.PROTECTED:
                res += "#";
                break;
            default:
                res += "~";
                break;
        }
        res += nom + " : " + type;
        return res;
    }

}