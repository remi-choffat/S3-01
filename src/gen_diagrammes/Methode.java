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
    private boolean estVisible;

    public Methode(String nom, String acces, String typeRetour, ArrayList<String> parametres) {
        this.nom = nom;
        this.acces = acces;
        this.typeRetour = typeRetour;
        this.parametres = parametres;
        this.estVisible = true;
    }

    public Methode() {
        this.nom = "Inconnu";
        this.acces = "Inconnu";
        this.typeRetour = "Inconnu";
        this.parametres = new ArrayList<>();
        this.estVisible = true;
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

    public ArrayList<String> getParametres() {
        return parametres;
    }

    public void setParametres(ArrayList<String> parametres) {
        this.parametres = parametres;
    }

    public boolean estVisible() {
        return estVisible;
    }

    public void setEstVisible(boolean estVisible) {
        this.estVisible = estVisible;
    }


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
        boolean aParametres = false;
        for (String parametre : parametres) {
            resultat += parametre + ", ";
            aParametres = true;
        }
        if (aParametres) {
            resultat = resultat.substring(0, resultat.length() - 2);
        }
        resultat = resultat + ") : " + typeRetour;
        return resultat;
    }
}
