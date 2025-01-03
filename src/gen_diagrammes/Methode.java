package gen_diagrammes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Représente une méthode d'une classe du diagramme
 */
@Setter
@Getter
public class Methode {

    private String nom;
    private String acces;
    private String typeRetour;
    private ArrayList<String> parametres;
    private boolean visible;


    /**
     * Constructeur
     *
     * @param nom        Nom de la méthode
     * @param acces      Accès de la méthode (public, private, protected, package-private)
     * @param typeRetour Type de retour de la méthode
     * @param parametres Liste des paramètres de la méthode
     */
    public Methode(String nom, String acces, String typeRetour, ArrayList<String> parametres) {
        this.nom = nom;
        this.acces = acces;
        this.typeRetour = typeRetour;
        this.parametres = parametres;
        this.visible = true;
    }


    /**
     * Constructeur par défaut
     */
    public Methode() {
        this.nom = "Inconnu";
        this.acces = "Inconnu";
        this.typeRetour = "Inconnu";
        this.parametres = new ArrayList<>();
        this.visible = true;
    }


    /**
     * Retourne une représentation textuelle de la méthode
     *
     * @return Représentation textuelle de la méthode
     */
    public String toString() {
        String texteAcces = switch (this.acces) {
            case Classe.PRIVATE -> "-";
            case Classe.PUBLIC -> "+";
            case Classe.PROTECTED -> "#";
            case Classe.PACKAGE_PRIVATE -> "~";
            default -> "";
        };
        StringBuilder resultat = new StringBuilder(texteAcces + " " + nom + "(");
        boolean aParametres = false;
        for (String parametre : parametres) {
            resultat.append(parametre).append(", ");
            aParametres = true;
        }
        if (aParametres) {
            resultat = new StringBuilder(resultat.substring(0, resultat.length() - 2));
        }
        resultat.append(") : ").append(typeRetour);
        return resultat.toString();
    }

}
