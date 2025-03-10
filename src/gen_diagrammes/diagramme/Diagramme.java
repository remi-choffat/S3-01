package gen_diagrammes.diagramme;

import gen_diagrammes.Main;
import gen_diagrammes.gInterface.Observateur;
import gen_diagrammes.gInterface.Sujet;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un diagramme de classes
 */
public class Diagramme implements Sujet, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Nom du package
     */
    @Setter
    @Getter
    private String nomPackage;

    /**
     * Liste des relations entre les classes
     */
    @Getter
    private List<Relation> relations = new ArrayList<>();

    /**
     * Liste des classes du diagramme
     */
    @Setter
    @Getter
    private ArrayList<Classe> listeClasses;

    /**
     * Instance unique de Diagramme
     */
    @Setter
    private static Diagramme instance;

    /**
     * Booléen qui indique si les attributs sont affichés par défaut
     */
    @Getter
    @Setter
    private boolean afficherAttributs;

    /**
     * Booléen qui indique si les méthodes sont affichées par défaut
     */
    @Getter
    @Setter
    private boolean afficherMethodes;

    /**
     * Liste des observateurs
     */
    @Getter
    private ArrayList<Observateur> listeObservateurs;

    /**
     * Nom du fichier dans lequel est enregistré le diagramme
     * (pour sauvegarde et chargement)
     */
    @Getter
    @Setter
    private String fichier;


    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private Diagramme() {
        this.nomPackage = null;
        this.listeClasses = new ArrayList<>();
        this.listeObservateurs = new ArrayList<>();
        this.afficherAttributs = true;
        this.afficherMethodes = true;
        this.fichier = null;
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
     * @return 1 si la classe a été ajoutée, 0 si la classe existe déjà, -1 si la classe est nulle
     */
    public int ajouterClasse(Classe c) {
        if (c == null) {
            return -1;
        }

        // Vérifie si une classe avec le même nom et même package existe déjà
        for (Classe classe : this.listeClasses) {
            if (c.equals(classe)) {
                return 0;
            }
        }

        this.listeClasses.add(c);
        this.updateClasses();
        return 1;
    }


    /**
     * Méthode qui permet de supprimer une classe du diagramme
     *
     * @param c classe à supprimer
     */
    public void supprimerClasse(Classe c) {
        this.listeClasses.remove(c);
        c.setVisibilite(false);
        this.updateClasses();
    }

    /**
     * Méthode qui permet de supprimer une classe du diagramme par son nom
     */
    public void supprimerClasse(String nomClasse, String nomPackage) {
        Classe classe = this.getClasse(nomClasse, nomPackage);
        if (classe != null) {
            this.listeClasses.remove(classe);
            this.updateClasses();
        }
    }


    /**
     * Supprime toutes les classes du diagramme
     */
    public void supprimerToutesClasses() {
        this.listeClasses.clear();
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
    public Classe getClasse(String simpleName, String nomPackage) {
        for (Classe c : this.listeClasses) {
            if (c.getNom().equals(simpleName)) {
                if ((nomPackage == null && c.getNomPackage() == null) || (c.getNomPackage() != null && c.getNomPackage().equals(nomPackage))) {
                    return c;
                }
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


    /**
     * Sauvegarde le diagramme dans un fichier
     *
     * @param file fichier de sauvegarde
     * @throws IOException erreur lors de la sauvegarde
     */
    public void sauvegarderDiagramme(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this.listeClasses);
        }
    }


    /**
     * Charge un diagramme depuis un fichier
     *
     * @param file fichier à charger
     * @throws IOException            erreur lors du chargement
     * @throws ClassNotFoundException classe non trouvée
     */
    public static void chargerDiagramme(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            instance.listeClasses = (ArrayList<Classe>) ois.readObject();
            Classe.updateParents();
            for (Classe c : instance.listeClasses) {
                Main.ajouterRelationsPourClasse(c);
            }
        }
    }


    /**
     * Ajoute une relation entre deux classes
     *
     * @param relation relation à ajouter
     */
    public void ajouterRelation(Relation relation) {
        relations.add(relation);
        notifierObservateurs();
    }


    /**
     * Vérifie si une relation existe entre deux classes
     *
     * @param source      classe source
     * @param destination classe destination
     * @return true si une relation existe, false sinon
     */
    public boolean contientRelation(Classe source, Classe destination) {
        for (Relation relation : relations) {
            // Vérifier si la relation est entre ces deux classes, dans un ordre quelconque
            if ((relation.getSource().equals(source) && relation.getDestination().equals(destination)) ||
                    (relation.getSource().equals(destination) && relation.getDestination().equals(source))) {
                return true;
            }
        }
        return false;
    }

}