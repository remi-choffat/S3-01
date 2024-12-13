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

        String nomClasse = cheminFichier.substring(cheminFichier.lastIndexOf("\\") + 1, cheminFichier.length() - 6);

        Class<?> classe = null;
        int state = -1;
        boolean isValid = false;

        // Si le fichier n'est pas un .class, on renvoie une erreur
        if (!cheminFichier.endsWith(".class")) {
            throw new ClassNotFoundException("Le fichier sélectionné n'est pas une classe Java (.class)");
        }

        while (!isValid) {
            try {
                if (state > -1) {
                    cheminFichier = Classe.adjustPath(cheminFichier, state);
                }
                // Charge le fichier (chemin absolu sur le disque)
                File file = new File(cheminFichier);
                URL url = file.getParentFile().toURI().toURL();
                URL[] urls = new URL[]{url};
                URLClassLoader classLoader = new URLClassLoader(urls);

                // Charge la classe
                String className = file.getName().replace(".class", "");
                classe = classLoader.loadClass(className);
                isValid = true;
            } catch (ClassNotFoundException | MalformedURLException | NoClassDefFoundError e) {
                state++;
                if (state > 10) {
                    throw new ClassNotFoundException("Erreur lors du chargement de la classe " + nomClasse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                isValid = true;
            }
        }

        if (classe == null) {
            throw new ClassNotFoundException("Erreur lors du chargement de la classe " + nomClasse);
        }

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

            String acces;
            int mod = m.getModifiers();
            if (Modifier.isAbstract(mod)) {
                acces = ABSTRACT;
            } else if (Modifier.isPublic(mod)) {
                acces = PUBLIC;
            } else if (Modifier.isProtected(mod)) {
                acces = PROTECTED;
            } else if (Modifier.isPrivate(mod)) {
                acces = PRIVATE;
            } else {
                acces = "";
            }
            ArrayList<String> parametres = new ArrayList<>();
            for (Class<?> c : m.getParameterTypes()) {
                parametres.add(c.getSimpleName());
            }
            this.methodes.add(new Methode(m.getName(), acces, m.getReturnType().getSimpleName(), parametres));
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
     * Ajuste le chemin du fichier pour trouver la classe
     *
     * @param path  chemin du fichier
     * @param state état de l'ajustement
     * @return String
     */
    private static String adjustPath(String path, int state) {
        String[] folds = path.split("[/\\\\]");
        if (state >= folds.length) {
            throw new ArrayIndexOutOfBoundsException("état dépassé");
        } else {
            String res = "";
            for (int i = 0; i < folds.length - 1; i++) {
                res += folds[i] + "\\\\";
            }
            res += folds[folds.length - state - 2] + "." + folds[folds.length - 1];
            return res;
        }

    }


    /**
     * Affichage (PlantUML) de la classe
     *
     * @return String
     */
    public String toString() {
        StringBuilder uml = new StringBuilder();
        StringBuilder relations = new StringBuilder();
        uml.append("class ").append(this.nom).append(" {\n");
        for (Attribut a : this.attributs) {
            if (a instanceof AttributClasse) {
                relations.append(this.nom).append(" --> ").append(((AttributClasse) a).getAttribut().getNom()).append(" : ").append(a.getNom()).append("\n");
            } else {
                uml.append(a).append("\n");
            }
        }
        for (Methode m : this.methodes) {
            uml.append(m).append("\n");
        }
        uml.append("}\n");
        uml.append(relations);
        return uml.toString();
    }

    public void updateAttributs() {
        ArrayList<Attribut> res = new ArrayList<>();
        for (Attribut a : this.attributs) {
            String type = a.getType();

            boolean isClassPresent = false;
            for (Classe c : Diagramme.getInstance().getListeClasses()) {
                if (c.getNom().equals(type)) {
                    res.add(new AttributClasse(a.getNom(), acces, type, "", "", c));
                    isClassPresent = true;
                    break;
                }
            }

            if (!isClassPresent) {
                res.add(new Attribut(a.getNom(), acces, type));
            }
        }
        this.attributs = res;
    }

}
