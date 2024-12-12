package gen_diagrammes;

public class AttributClasse extends Attribut {
    private String cardinalitePointeur;
    private String cardinalitePointee;
    private Classe attribut;

    public AttributClasse(String nom, String typeAcces, String type, String cardPointeur, String cardPointee, Classe classe) {
        super(nom, typeAcces, type);
        this.cardinalitePointee = cardPointeur;
        this.cardinalitePointee = cardPointee;
        this.attribut = classe;
    }

    public AttributClasse() {
        super();
        this.cardinalitePointee = "Inconnu";
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
        String texte = super.toString() + "Cardinalite de la classe qui pointe : " + this.cardinalitePointeur + ", cardinalite de la classe pointee : " + this.cardinalitePointee + "\n Classe pointee : " + this.attribut;
        return texte;
    }
}
