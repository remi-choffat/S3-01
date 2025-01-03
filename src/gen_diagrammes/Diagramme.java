package gen_diagrammes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Représente un diagramme de classes
 */
public class Diagramme implements Sujet {

    /**
     * Nom du package
     */
    @Setter
    @Getter
    private String nomPackage;

    /**
     * Liste des classes du diagramme
     */
    @Setter
    @Getter
    private ArrayList<Classe> listeClasses;

    /**
     * Instance unique de Diagramme
     */
    private static Diagramme instance;

    /**
     * Liste des observateurs
     */
    @Getter
    private ArrayList<Observateur> listeObservateurs;


    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private Diagramme() {
        this.nomPackage = null;
        this.listeClasses = new ArrayList<>();
        this.listeObservateurs = new ArrayList<>();
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


    /**
     * Méthode qui permet d'ajouter une classe au diagramme
     *
     * @param c classe à ajouter au diagramme
     */
    public void ajouterClasse(Classe c) {
        if (c == null) {
            return;
        }
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
        this.updateClasses();
    }


    /**
     * Affiche le diagramme de classes
     *
     * @return texte du diagramme
     */
    public String toString() {
        StringBuilder texte = new StringBuilder();
        if (this.nomPackage != null) {
            texte.append("Package : ").append(this.nomPackage).append("\n\n");
        }
        for (Classe c : this.listeClasses) {
            texte.append(c).append("\n");
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
        this.notifierObservateurs();
    }


    /**
     * Ajoute un observateur
     *
     * @param o observateur à ajouter
     */
    @Override
    public void ajouterObservateur(Observateur o) {
        this.listeObservateurs.add(o);
    }


    /**
     * Supprime un observateur
     *
     * @param o observateur à supprimer
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        this.listeObservateurs.remove(o);
    }


    /**
     * Notifie les observateurs
     */
    @Override
    public void notifierObservateurs() {
        for (Observateur o : this.listeObservateurs) {
            o.actualiser();
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


    /**
     * Affiche toutes les classes du diagramme
     */
    public void afficherToutesClasses() {
        for (Classe c : this.listeClasses) {
            c.setVisibilite(true);
        }
        this.notifierObservateurs();
    }


    /**
     * Masque toutes les classes du diagramme
     */
    public void masquerToutesClasses() {
        for (Classe c : this.listeClasses) {
            c.setVisibilite(false);
        }
        this.notifierObservateurs();
    }


    /**
     * Affiche tous les attributs de toutes les classes du diagramme
     */
    public void afficherTousAttributs() {
        for (Classe c : this.listeClasses) {
            for (Attribut a : c.getAttributs()) {
                a.setVisible(true);
            }
        }
        this.notifierObservateurs();
    }


    /**
     * Masque tous les attributs de toutes les classes du diagramme
     */
    public void masquerTousAttributs() {
        for (Classe c : this.listeClasses) {
            for (Attribut a : c.getAttributs()) {
                a.setVisible(false);
            }
        }
        this.notifierObservateurs();
    }


    /**
     * Affiche toutes les méthodes de toutes les classes du diagramme
     */
    public void afficherToutesMethodes() {
        for (Classe c : this.listeClasses) {
            for (Methode m : c.getMethodes()) {
                m.setVisible(true);
            }
        }
        this.notifierObservateurs();
    }


    /**
     * Masque toutes les méthodes de toutes les classes du diagramme
     */
    public void masquerToutesMethodes() {
        for (Classe c : this.listeClasses) {
            for (Methode m : c.getMethodes()) {
                m.setVisible(false);
            }
        }
        this.notifierObservateurs();
    }

}