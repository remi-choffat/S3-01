package gen_diagrammes;

import java.util.ArrayList;

/**
 * Représente une méthode d'une classe du diagramme
 */
public class Methode {

    private String nom;
    private String acces;
    private String typeRetour;
    private ArrayList<String> parametres;

    public Methode(String nom, String acces, String typeRetour, ArrayList<String> parametres) {
        this.nom = nom;
        this.acces = acces;
        this.typeRetour = typeRetour;
        this.parametres = parametres;
    }

    public Methode() {
        this.nom = "Inconnu";
        this.acces = "Inconnu";
        this.typeRetour = "Inconnu";
        this.parametres = new ArrayList<>();
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

    public ArrayList<String> getParametres() { return parametres; }

    public void setParametres(ArrayList<String> parametres) { this.parametres = parametres; }


    public String toString() {
        String resultat = "";
        String texteAcces = "";
        switch (this.acces) {
            case Classe.PRIVATE:
                texteAcces = "-";
                break;
            case Classe.PUBLIC:
                texteAcces = "+";
                break;
            case Classe.PROTECTED:
                texteAcces = "#";
                break;
            default:
                texteAcces = "~";
                break;
        }
        resultat = texteAcces + " " + nom + "(";
        for (String parametre : parametres) {
            resultat += parametre + ", ";
        }
        resultat = resultat.substring(0, resultat.length() - 2);
        resultat = resultat + ") : " + typeRetour;
        return resultat;
    }
}
