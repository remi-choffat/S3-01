package gen_diagrammes;

import java.util.ArrayList;

/**
 * Représente un diagramme de classes
 */
public class Diagramme implements Sujet {

    private String nomPackage;

    /**
     * Liste des classes du diagramme
     */
    private ArrayList<Classe> listeClasses;

    /**
     * Instance unique de Diagramme
     */
    private static Diagramme instance;

    /**
     * Liste des observateurs
     */
    private ArrayList<Observateur> listeObservateurs;

    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private Diagramme() {
        this.nomPackage = "Inconnu";
        this.listeClasses = new ArrayList<>();
    }

    /**
     * Méthode pour obtenir l'instance unique de Diagramme
     *
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
     *
     * @param nomPackage   nom du package
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
     *
     * @param c classe à ajouter au diagramme
     */
    public void ajouterClasse(Classe c) {
        this.listeClasses.add(c);
        this.updateClasses();
    }


    /**
     * Méthode qui permet de supprimer une classe du diagramme
     *
     * @param c classe à supprimer
     */
    public void supprimerClasse(Classe c) {
        this.listeClasses.remove(c);
    }


    /**
     * Affiche le diagramme de classes
     *
     * @return texte du diagramme
     */
    public String toString() {
        StringBuilder texte = new StringBuilder("Nom du package : " + nomPackage + "\n\n");
        for (Classe c : this.listeClasses) {
            texte.append(c.toString()).append("\n");
        }
        return texte.toString();
    }


    /**
     * Met à jour les attributs des classes du diagramme
     */

    public void updateClasses() {
        for (Classe c : this.listeClasses) {
            c.updateAttributs();
        }
    }

    @Override
    public void ajouterObservateur(Observateur v) {
        this.listeObservateurs.add(v);
    }

    @Override
    public void supprimerObservateur(Observateur v) {
        this.listeObservateurs.remove(v);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur v : this.listeObservateurs) {
            v.actualiser();
        }
    }


    /**
     * Méthode qui permet de récupérer une classe du diagramme par son nom
     *
     * @param simpleName nom de la classe
     * @return classe correspondante
     */
    public Classe getClasse(String simpleName) {
        for (Classe c : this.listeClasses) {
            if (c.getNom().equals(simpleName)) {
                return c;
            }
        }
        return null;
    }
}