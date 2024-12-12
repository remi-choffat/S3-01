package gen_diagrammes;

public class Methode {
    private String nom;
    private String acces;
    private String typeRetour;

    public Methode(String nom, String acces, String typeRetour) {
        this.nom = nom;
        this.acces = acces;
        this.typeRetour = typeRetour;
    }

    public Methode() {
        this.nom = "Inconnu";
        this.acces = "Inconnu";
        this.typeRetour = "Inconnu";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAcces() {
        return acces;
    }

    public void setAcces(String acces) {
        this.acces = acces;
    }

    public String getTypeRetour() {
        return typeRetour;
    }

    public void setTypeRetour(String typeRetour) {
        this.typeRetour = typeRetour;
    }

    public String toString() {
        String texte = "Nom : " + nom + ", acces : " + acces + ", type de retour : " + typeRetour;
        return texte;
    }
}
