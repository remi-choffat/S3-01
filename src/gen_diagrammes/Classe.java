package gen_diagrammes;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Représente une classe du diagramme
 */
public class Classe {

    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String PROTECTED = "protected";

    public static final String CLASS = "class";
    public static final String INTERFACE = "interface";
    public static final String ABSTRACT = "abstract";

    /**
     * Nom de la classe
     */
    private final String nom;

    /**
     * Visibilité de la classe : public, private, protected
     */
    private final String acces;

    /**
     * Type de la classe : class, interface, abstract
     */
    private final String type;

    private ArrayList<Classe> parents;

    private ArrayList<Attribut> attributs;

    private ArrayList<Methode> methodes;



    /**
     * Constructeur par défaut
     */
    public Classe() {
        this.nom = "Classe";
        this.acces = PUBLIC;
        this.type = CLASS;
    }


    /**
     * Constructeur avec nom de la classe
     *
     * @param cheminFichier Chemin du fichier de la classe
     * @throws ClassNotFoundException Erreur si la classe n'est pas trouvée
     */
    public Classe(String cheminFichier) throws ClassNotFoundException {

        // Charge le fichier (chemin absolu sur le disque)
        ClassLoader classLoader = getClass().getClassLoader();
        classLoader.getResourceAsStream(cheminFichier);

        // Crée un objet Class à partir du ClassLoader
        Class<?> classe = classLoader.loadClass(cheminFichier);

        // Récupère le nom de la classe
        this.nom = classe.getSimpleName();

        // Récupère le type d'accès de la classe (public, private, protected)
        switch (classe.getModifiers()) {
            case Modifier.PRIVATE:
                this.acces = PRIVATE;
                break;
            case Modifier.PROTECTED:
                this.acces = PROTECTED;
                break;
            default:
                this.acces = PUBLIC;
        }

        // Récupère le type du fichier (Classe, Classe abstraîte, Interface)
        if (classe.isInterface()) {
            this.type = INTERFACE;
        } else if (Modifier.isAbstract(classe.getModifiers())) {
            this.type = ABSTRACT;
        } else {
            this.type = CLASS;
        }

    }


    /**
     * getter de l'attribut nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter de l'attribut acces
     */
    public String getAcces() {
        return acces;
    }

    /**
     * getter de l'attribut type
     */
    public String getType() {
        return type;
    }

    public ArrayList<Classe> getParents() {
        return parents;
    }

    public ArrayList<Attribut> getAttributs() {
        return attributs;
    }

    public ArrayList<Methode> getMethodes() {
        return methodes;
    }

    public void addMethode(Methode m){
        methodes.add(m);
    }

    public void addAttribut(Attribut a){
        attributs.add(a);
    }

    public void addParent(Classe c){
        //vérifie si il est encore possible d'ajouter un parent à la classe
        if(this.parents.isEmpty()){
            parents.add(c);
        } else if (this.parents.size() == 1){
            if(! this.parents.getFirst().getType().equals(c.getType())){
                parents.add(c);
            }
        }
    }

    /**
     * Affiche les propriétés de la classe
     */
    public String toString() {
        String res = this.acces + " " + this.type + " " + this.nom ;
        res += "( " ;
        for(Classe c : this.parents) {
            res += c.getNom() + " ";
        }
        res += ")\n";

        res += " - Attributs\n";
        for(Attribut at : this.attributs) {
            res += "\t"+ at.getTypeAcces() + at.getType() + at.getNom() + "\n";
        }
        res += "\nMéthodes\n";
        for(Methode m : this.methodes) {
            res += "\t"+m.getAcces() + " " + m.getTypeRetour() + " " +m.getNom() + "\n";
        }

        return res;

    }

}
