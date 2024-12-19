package gen_diagrammes;

import lombok.Getter;
import lombok.Setter;

/**
 * Représente un attribut ayant pour type une classe du diagramme
 */
@Getter
public class AttributClasse extends Attribut {

    /**
     * Cardinalité côté classe à laquelle appartient l'attribut
     */
    @Setter
    private String cardinalitePointeur;

    /**
     * Cardinalité côté classe du type de l'attribut
     */
    @Setter
    private String cardinalitePointee;

    /**
     * Classe de l'attribut
     */
    private final Classe attribut;


    /**
     * Constructeur
     *
     * @param nom          Nom de l'attribut
     * @param typeAcces    Type d'accès (public, private, protected, package)
     * @param type         Type de l'attribut
     * @param cardPointeur Cardinalité côté classe à laquelle appartient l'attribut
     * @param cardPointee  Cardinalité côté classe du type de l'attribut
     * @param classe       Classe de l'attribut
     */
    public AttributClasse(String nom, String typeAcces, String type, String cardPointeur, String cardPointee, Classe classe) {
        super(nom, typeAcces, type);
        this.cardinalitePointeur = cardPointeur;
        this.cardinalitePointee = cardPointee;
        this.attribut = classe;
    }


    /**
     * Constructeur par défaut
     */
    public AttributClasse() {
        super();
        this.cardinalitePointeur = "Inconnu";
        this.cardinalitePointee = "Inconnu";
        this.attribut = null;
    }


    /**
     * Retourne une chaîne de caractères représentant l'attribut
     *
     * @return String
     */
    public String toString() {
        return super.toString() + " " + this.cardinalitePointeur + " --> " + this.cardinalitePointee;
    }

}
