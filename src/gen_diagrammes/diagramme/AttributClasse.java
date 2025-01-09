package gen_diagrammes.diagramme;

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
    @Getter
    private final Classe attribut;

    /**
     * Indique si l'attribut est une agrégation
     */
    private final boolean aggregation;

    /**
     * Indique si l'attribut est une composition
     */
    private final boolean composition;

    /**
     * Constructeur
     *
     * @param nom          Nom de l'attribut
     * @param typeAcces    Type d'accès (public, private, protected, package)
     * @param type         Type de l'attribut
     * @param cardPointeur Cardinalité côté classe à laquelle appartient l'attribut
     * @param cardPointee  Cardinalité côté classe du type de l'attribut
     * @param classe       Classe de l'attribut
     * @param aggregation  Indique si l'attribut est une agrégation
     * @param composition  Indique si l'attribut est une composition
     */
    public AttributClasse(String nom, String typeAcces, String type, String cardPointeur, String cardPointee, Classe classe, boolean aggregation, boolean composition) {
        super(nom, typeAcces, type);
        this.cardinalitePointeur = cardPointeur;
        this.cardinalitePointee = cardPointee;
        this.attribut = classe;
        this.aggregation = aggregation;
        this.composition = composition;
    }

    /**
     * Constructeur par défaut
     */
    public AttributClasse() {
        super();
        this.cardinalitePointeur = "Inconnu";
        this.cardinalitePointee = "Inconnu";
        this.attribut = null;
        this.aggregation = false;
        this.composition = false;
    }

    /**
     * Retourne une chaîne de caractères représentant l'attribut
     *
     * @return String
     */
    public String toString() {
        return super.toString() + " " + this.cardinalitePointeur + " --> " + this.cardinalitePointee;
    }

    public boolean isHeritage() {
        // Logique pour déterminer si l'attribut est une relation d'héritage
        return attribut.getType().equals(Classe.CLASS) && attribut.getNom().equals(this.getNom());
    }

    public boolean isImplementation() {
        // Logique pour déterminer si l'attribut est une relation d'implémentation
        return attribut.getType().equals(Classe.INTERFACE) && attribut.getNom().equals(this.getNom());
    }

    public boolean isAggregation() {
        // Logique pour déterminer si l'attribut est une agrégation
        return this.aggregation;
    }

    public boolean isComposition() {
        // Logique pour déterminer si l'attribut est une composition
        return this.composition;
    }
}