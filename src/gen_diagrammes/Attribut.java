package gen_diagrammes;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Représente un attribut d'une classe du diagramme
 */
@Setter
@Getter
public class Attribut implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nom;
    private String typeAcces;
    private String type;
    private boolean visible;
    private boolean staticAttr;


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
     * Retourne l'affichage PlantUML de l'attribut
     *
     * @return String
     */
    public String toPlantUML() {
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
        return nom + " : " + type;
    }


    /**
     * Retourne une chaîne de caractères représentant l'attribut
     *
     * @return String
     */
    public String toString() {
        return nom + " : " + type;
    }



}