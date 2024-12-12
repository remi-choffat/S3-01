package gen_diagrammes;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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

    /**
     * Liste des classes parentes
     */
    private ArrayList<Classe> parents;

    /**
     * Liste des attributs de la classe
     */
    private ArrayList<Attribut> attributs;

    /**
     * Liste des méthodes de la classe
     */
    private ArrayList<Methode> methodes;


    /**
     * Constructeur par défaut
     */
    public Classe() {
        this.nom = "Classe";
        this.acces = PUBLIC;
        this.type = CLASS;
        this.parents = new ArrayList<Classe>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();
    }


    /**
     * Constructeur avec nom, acces et type
     * Crée directement une classe, sans passer par un fichier
     *
     * @param nom   nom de la classe
     * @param acces visibilité de la classe
     * @param type  type de la classe
     */
    public Classe(String nom, String acces, String type) {
        this.nom = nom;
        this.acces = acces;
        this.type = type;
        this.parents = new ArrayList<Classe>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();
    }


    /**
     * Constructeur avec nom de la classe
     *
     * @param cheminFichier Chemin du fichier de la classe
     * @throws ClassNotFoundException Erreur si la classe n'est pas trouvée
     */
    public Classe(String cheminFichier) throws ClassNotFoundException, MalformedURLException {

        this.parents = new ArrayList<Classe>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();

        // Charge le fichier (chemin absolu sur le disque)
        File file = new File(cheminFichier);
        URL url = file.getParentFile().toURI().toURL();
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = new URLClassLoader(urls);

        // Charge la classe
        String className = file.getName().replace(".class", "");
        Class<?> classe = classLoader.loadClass(className);


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

        // Remplit la liste des attributs
        for (Field a : classe.getDeclaredFields()) {
            String type = a.getType().getSimpleName();

            boolean isClassPresent = false;
            for (Classe c : Diagramme.getInstance().getListeClasses()) {
                if (c.getNom().equals(type)) {
                    this.attributs.add(new AttributClasse(a.getName(), acces, type, "", "", c));
                    isClassPresent = true;
                    break;
                }
            }

            if (!isClassPresent) {
                this.attributs.add(new Attribut(a.getName(), acces, type));
            }
        }

        // Remplit la liste des méthodes
        for (Method m : classe.getDeclaredMethods()) {
            String acces = switch (m.getModifiers()) {
                case Modifier.PUBLIC -> PUBLIC;
                case Modifier.PROTECTED -> PROTECTED;
                case Modifier.PRIVATE -> PRIVATE;
                case Modifier.ABSTRACT -> ABSTRACT;
                default -> "";
            };
            this.methodes.add(new Methode(m.getName(), acces, m.getReturnType().getSimpleName()));
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

    public void addMethode(Methode m) {
        methodes.add(m);
    }

    public void addAttribut(Attribut a) {
        attributs.add(a);
    }

    /**
     * Ajoute un parent à la classe
     *
     * @param c Classe parente
     */
    public void addParent(Classe c) {
        // Vérifie s'il est encore possible d'ajouter un parent à la classe
        if (this.parents.isEmpty()) {
            parents.add(c);
        } else if (this.parents.size() == 1) {
            if (!this.parents.getFirst().getType().equals(c.getType())) {
                parents.add(c);
            }
        }
    }

    /**
     * Affiche les propriétés de la classe
     */
    public String toString() {
        String res = this.acces + " " + this.type + " " + this.nom;
        res += "( ";
        for (Classe c : this.parents) {
            res += c.getNom() + " ";
        }
        res += ")\n";

        res += " - Attributs\n";
        for (Attribut at : this.attributs) {
            res += at.toString();
        }
        res += "\n - Méthodes\n";
        for (Methode m : this.methodes) {
            res += "\t" + m.getAcces() + " " + m.getTypeRetour() + " " + m.getNom() + "()\n";
        }

        return res;

    }

}
