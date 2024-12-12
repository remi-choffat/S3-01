package gen_diagrammes;

import java.lang.reflect.Modifier;

/**
 * Représente une classe du diagramme
 */
public class Classe {

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

    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String PROTECTED = "protected";

    public static final String CLASS = "class";
    public static final String INTERFACE = "interface";
    public static final String ABSTRACT = "abstract";


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


    /**
     * Affiche les propriétés de la classe
     */
    public String toString() {
        return acces + " " + type + " " + nom;
    }

}
