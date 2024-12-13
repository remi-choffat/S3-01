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

    private static Diagramme instance;

    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private Diagramme() {
        this.nomPackage = "Inconnu";
        this.listeClasses = new ArrayList<>();
    }

    /**
     * Méthode pour obtenir l'instance unique de Diagramme
     * @return instance unique de Diagramme
     */
    public static Diagramme getInstance() {
        if (instance == null) {
            instance = new Diagramme();
        }
        return instance;
    }

    /**
     * Méthode pour initialiser l'instance unique de Diagramme avec des paramètres
     * @param nomPackage nom du package
     * @param listeClasses liste des classes du package
     */
    public static void initialize(String nomPackage, ArrayList<Classe> listeClasses) {
        if (instance == null) {
            instance = new Diagramme();
            instance.nomPackage = nomPackage;
            instance.listeClasses = listeClasses;
        }
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
     * Méthode qui permet d'ajouter une classe au diagramme
     * @param c classe à ajouter au diagramme
     */
    public void ajouterClasse(Classe c) {
        this.listeClasses.add(c);
        this.updateClasses();
    }

    /**
     * Méthode qui permet de supprimer une classe du diagramme
     * @param c classe à supprimer
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


    public void updateClasses(){
        for(Classe c : this.listeClasses){
            System.out.println("a");
            c.updateAttributs();
        }
    }
}