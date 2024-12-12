package gen_diagrammes;

import java.util.ArrayList;

/**
 * Représente un diagramme de classes
 */
public class Diagramme {

    private String nomPackage;

    /**
     * Liste des classes du diagramme
     */
    private ArrayList<Classe> listeClasses;


    /**
     * Constructeur d'un diagramme à partir d'un package existant
     * @param nomPackage nom du package
     * @param listeClasses liste des classes du package
     */
    public Diagramme(String nomPackage, ArrayList<Classe> listeClasses) {
        this.nomPackage = nomPackage;
        this.listeClasses = listeClasses;
    }


    /**
     * Constructeur d'un diagramme vide
     */
    public Diagramme() {
        this.nomPackage = "Inconnu";
        this.listeClasses = new ArrayList<>();
    }


    public String getNomPackage() {
        return nomPackage;
    }

    public void setNomPackage(String nomPackage) {
        this.nomPackage = nomPackage;
    }

    public ArrayList<Classe> getListeClasses() {
        return listeClasses;
    }

    public void setListeClasses(ArrayList<Classe> listeClasses) {
        this.listeClasses = listeClasses;
    }

    /**
     * methode qui permet d'ajouter une classe au diagramme
     * @param c, classe a ajouter au diagramme
     */
    public void ajouterClasse(Classe c) {
        this.listeClasses.add(c);
    }

    /**
     * methode qui permet de supprimer une classe du diagramme
     * @param c, classe a supprimer
     */
    public void supprimerClasse(Classe c) {
        this.listeClasses.remove(c);
    }

    public String toString() {
        String texte = "Nom du package : " + nomPackage + "\n\n";
        for (Classe c : this.listeClasses) {
            texte += c.toString() + "\n";
        }
        return texte;
    }

}
