package gen_diagrammes;

/**
 * Représente un attribut ayant pour type une classe du diagramme
 */
public class AttributClasse extends Attribut {

    private String cardinalitePointeur;
    private String cardinalitePointee;
    private final Classe attribut;

    public AttributClasse(String nom, String typeAcces, String type, String cardPointeur, String cardPointee, Classe classe) {
        super(nom, typeAcces, type);
        this.cardinalitePointeur = cardPointeur;
        this.cardinalitePointee = cardPointee;
        this.attribut = classe;
    }

    public AttributClasse() {
        super();
        this.cardinalitePointeur = "Inconnu";
        this.cardinalitePointee = "Inconnu";
        this.attribut = null;
    }

    public String getCardinalitePointee() {
        return this.cardinalitePointee;
    }

    public void setCardinalitePointee(String cardPointee) {
        this.cardinalitePointee = cardPointee;
    }

    public String getCardinalitePointeur() {
        return this.cardinalitePointeur;
    }

    public void setCardinalitePointeur(String cardPointeur) {
        this.cardinalitePointee = cardPointeur;
    }

    public String toString() {
        return super.toString() + "Cardinalite de la classe qui pointe : " + this.cardinalitePointeur + ", cardinalite de la classe pointee : " + this.cardinalitePointee + "\n Classe pointee : " + this.attribut;
    }
}
