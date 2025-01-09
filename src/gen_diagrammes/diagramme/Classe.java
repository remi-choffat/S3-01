package gen_diagrammes.diagramme;

import gen_diagrammes.gInterface.Observateur;
import gen_diagrammes.gInterface.Sujet;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Représente une classe du diagramme
 */
@Getter
public class Classe implements Sujet, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private ArrayList<Observateur> observateurs = new ArrayList<>();

    @Override
    public void ajouterObservateur(Observateur v) {
        observateurs.add(v);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur v : observateurs) {
            v.actualiser();
        }
    }

    public void supprimerObservateur(Observateur v) {
        observateurs.remove(v);
    }

    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String PROTECTED = "protected";
    public static final String PACKAGE_PRIVATE = "package";

    public static final String CLASS = "class";
    public static final String INTERFACE = "interface";
    public static final String ABSTRACT = "{abstract}";
    public static final String ABSTRACT_CLASS = "abstract class";

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
     * Si la classe est visible ou non
     */
    private boolean visible;

    /**
     * Axe des abscisses
     */
    @Setter
    private double longueur;

    /**
     * Axe des ordonnées
     */
    @Setter
    private double largeur;


    @Getter
    private final List<Relation> relations = new ArrayList<>();

    /**
     * Nom du package dans lequel est la classe
     */
    @Getter
    private String nomPackage;


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
        this.visible = true;
        this.longueur = Math.random() * 600;
        this.largeur = Math.random() * 300;
        this.nomPackage = null;
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
        if (nom == null || nom.trim().isEmpty()) {
            nom = null;
        }
        this.nom = nom;
        this.acces = acces;
        this.type = type;
        this.parents = new ArrayList<Classe>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();
        this.visible = true;
        this.nomPackage = null;
        this.longueur = Math.random() * 600;
        this.largeur = Math.random() * 300;
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
        this.visible = true;
        this.longueur = Math.random() * 600; // Valeurs par défaut pour éviter des dimensions nulles
        this.largeur = Math.random() * 300;

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

                // Récupère le nom du package de la classe
                String nomPack = cheminFichier.substring(cheminFichier.lastIndexOf(File.separator), cheminFichier.length() - 6);
                if (nomPack.contains(".")) {
                    nomPack = nomPack.substring(1, nomPack.lastIndexOf("."));
                    this.nomPackage = nomPack;
                } else {
                    // Si la classe n'est pas dans un package, on met nomPackage à null
                    this.nomPackage = null;
                }

                isValid = true;
            } catch (ClassNotFoundException | MalformedURLException | NoClassDefFoundError |
                     ArrayIndexOutOfBoundsException e) {
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
        String nom = classe.getSimpleName();
        if (nom.trim().isEmpty()) {
            nom = null;
        }
        this.nom = nom;

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
            this.type = ABSTRACT_CLASS;
        } else {
            this.type = CLASS;
        }

        // Remplit la liste des attributs
        for (Field a : classe.getDeclaredFields()) {
            
            String type = a.getGenericType().getTypeName().replaceAll("\\b[a-zA-Z_]+\\.", "");
            int modAttribut = a.getModifiers();
            String accesAttribut;
            if (Modifier.isPublic(modAttribut)) {
                accesAttribut = PUBLIC;
            } else if (Modifier.isProtected(modAttribut)) {
                accesAttribut = PROTECTED;
            } else if (Modifier.isPrivate(modAttribut)) {
                accesAttribut = PRIVATE;
            } else {
                if (this.type.equals(INTERFACE)) {
                    accesAttribut = "";
                } else {
                    accesAttribut = PACKAGE_PRIVATE;
                }
            }

            boolean isClassPresent = false;
            for (Classe c : Diagramme.getInstance().getListeClasses()) {
                // Vérifie si le type de l'attribut est une classe du diagramme
                // ou un ensemble d'objets de cette classe (Set<Classe>, List<Classe>, Classe[], ...)
                if (type.matches(".*\\b" + c.getNom() + "\\b.*")) {
                    this.attributs.add(new AttributClasse(a.getName(), accesAttribut, type, "", "", c, false, false));
                    isClassPresent = true;
                    break;
                }
            }

            if (!isClassPresent) {
                this.attributs.add(new Attribut(a.getName(), accesAttribut, type));
            }

            if (Modifier.isStatic(modAttribut)) {
                this.attributs.getLast().setStaticAttr(true);
            }

        }
        // Trie les attributs par nom
        this.attributs.sort((a1, a2) -> a1.getNom().compareTo(a2.getNom()));

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
                if (this.type.equals(INTERFACE)) {
                    acces = "";
                } else {
                    acces = PACKAGE_PRIVATE;
                }
            }
            ArrayList<String> parametres = new ArrayList<>();
            for (Class<?> c : m.getParameterTypes()) {
                parametres.add(c.getSimpleName());
            }
            this.methodes.add(new Methode(m.getName(), acces, m.getReturnType().getSimpleName(), parametres));

            if (Modifier.isStatic(mod)) {
                this.methodes.getLast().setStaticMethode(true);
            }
            if (Modifier.isAbstract(mod)) {
                this.methodes.getLast().setAbstractMethode(true);
            }
        }
        // Trie les méthodes par nom
        this.methodes.sort((m1, m2) -> m1.getNom().compareTo(m2.getNom()));

        // Remplit la liste des classes parentes

        // Interfaces
        for (Class<?> c : classe.getInterfaces()) {
            // Vérifie si l'interface est déjà présente dans le diagramme
            boolean isPresent = false;
            for (Classe p : Diagramme.getInstance().getListeClasses()) {
                if (p.getNom().equals(c.getSimpleName())) {
                    isPresent = true;
                    break;
                }
            }

            if (!isPresent) {
                this.parents.add(new Classe(c.getSimpleName(), c.getModifiers() == Modifier.PUBLIC ? PUBLIC : "", INTERFACE));
            } else {
                // Associe la classe parente à la classe actuelle dans la liste parents
                this.parents.add(Diagramme.getInstance().getClasse(c.getSimpleName(), c.getPackageName()));
            }
        }

        // Classe parente
        Class<?> superClass = classe.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            // Vérifie si la classe parente est déjà présente dans le diagramme
            boolean isPresent = false;
            for (Classe p : Diagramme.getInstance().getListeClasses()) {
                if (p.getNom().equals(superClass.getSimpleName())) {
                    isPresent = true;
                    break;
                }
            }

            if (!isPresent) {
                this.parents.add(new Classe(superClass.getSimpleName(), superClass.getModifiers() == Modifier.PUBLIC ? PUBLIC : "", CLASS));
            } else {
                // Associe la classe parente à la classe actuelle dans la liste parents
                this.parents.add(Diagramme.getInstance().getClasse(superClass.getSimpleName(), superClass.getPackageName()));
            }
        }

    }


    /**
     * Récupère le nom de la classe
     *
     * @param etat état de la visibilité
     */
    public void setVisibilite(boolean etat) {
        this.visible = etat;
        Diagramme.getInstance().notifierObservateurs();
        this.notifierObservateurs();
    }


    /**
     * Ajoute une méthode à la classe
     *
     * @param m Méthode à ajouter
     */
    public void addMethode(Methode m) {
        methodes.add(m);
    }


    /**
     * Ajoute un attribut à la classe
     *
     * @param a Attribut à ajouter
     */
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
     * Détermine le répertoire racine pour récupérer la classe dans le bon package
     *
     * @param path  chemin du fichier
     * @param state état de l'ajustement (nombre de fois où le fichier a été ajusté)
     * @return String
     */
    private static String adjustPath(String path, int state) throws ArrayIndexOutOfBoundsException {
        String[] folds = path.split("[/\\\\]");
        if (state >= folds.length) {
            throw new ArrayIndexOutOfBoundsException("État dépassé");
        } else {
            // Remplace le dernier File.separator par un point
            return path.substring(0, path.lastIndexOf(File.separator)) + "." + path.substring(path.lastIndexOf(File.separator) + 1);
        }
    }


    /**
     * Met à jour les attributs de la classe
     */
    public void updateAttributs() {
        ArrayList<Attribut> res = new ArrayList<>();
        Iterator<Attribut> iterator = this.attributs.iterator();
        while (iterator.hasNext()) {
            Attribut a = iterator.next();
            String type = a.getType();
            String accesAttribut = a.getTypeAcces();
            String nomAttribut = a.getNom();

            boolean isClassPresent = false;
            for (Classe c : Diagramme.getInstance().getListeClasses()) {
                // Vérifie si le type de l'attribut est une classe du diagramme
                // ou un ensemble d'objets de cette classe (Set<Classe>, List<Classe>, Classe[], ...)
                if (type.matches(".*\\b" + c.getNom() + "\\b.*")) {
                    res.add(new AttributClasse(nomAttribut, accesAttribut, type, "", "", c, false, false));
                    isClassPresent = true;
                    break;
                }
            }

            if (!isClassPresent) {
                res.add(new Attribut(nomAttribut, accesAttribut, type));
            }
        }
        this.attributs = res;
    }

    /**
     * Affichage (PlantUML) de la classe
     *
     * @return String
     */
    public String toPlantUML() {

        StringBuilder uml = new StringBuilder();
        StringBuilder relations = new StringBuilder();

        // Si la classe implémente une interface ou hérite d'une classe
        // on ajoute les relations
        if (!this.parents.isEmpty()) {
            for (Classe c : this.parents) {
                if (c.getType().equals(INTERFACE)) {
                    relations.append(this.nom).append(" ..|> ").append(c.getNom()).append("\n");
                } else {
                    relations.append(this.nom).append(" --|> ").append(c.getNom()).append("\n");
                }
            }
        }

        uml.append(this.type).append(" ").append(this.nom).append(" {\n");

        // Affiche les attributs soit en texte, soit en relation (flèche)
        for (Attribut a : this.attributs) {
            if (a instanceof AttributClasse) {
                relations.append(this.nom).append(" --> ").append(((AttributClasse) a).getAttribut().getNom()).append(" : ").append(a.getNom()).append("\n");
            } else {
                uml.append(a.toPlantUML()).append("\n");
            }
        }

        // Affiche les méthodes
        for (Methode m : this.methodes) {
            uml.append(m.toPlantUML()).append("\n");
        }

        uml.append("}\n");
        uml.append(relations);
        return uml.toString();
    }


    public void ajouterRelation(Relation relation) {
        relations.add(relation);
        Diagramme.getInstance().notifierObservateurs();
    }


    /**
     * Affichage de la classe
     *
     * @return String
     */
    public String toString() {
        return this.type + " " + this.nom;
    }


    /**
     * Affiche les attributs de la classe
     */
    public void afficherAttributs() {
        for (Attribut a : this.getAttributs()) {
            a.setVisible(true);
        }
    }


    /**
     * Masque les attributs de la classe
     */
    public void masquerAttributs() {
        for (Attribut a : this.getAttributs()) {
            a.setVisible(false);
        }
    }


    /**
     * Affiche les méthodes de la classe
     */
    public void afficherMethodes() {
        for (Methode m : this.getMethodes()) {
            m.setVisible(true);
        }
    }


    /**
     * Masque les méthodes de la classe
     */
    public void masquerMethodes() {
        for (Methode m : this.getMethodes()) {
            m.setVisible(false);
        }
    }


    /**
     * Récupère le type de la classe en String
     *
     * @return String
     */
    public String getTypeClasseString() {
        return switch (type) {
            case CLASS -> "Classe";
            case INTERFACE -> "Interface";
            case ABSTRACT_CLASS -> "Classe abstraite";
            default -> "";
        };
    }


    /**
     * Affiche les classes parentes de la classe
     */
    public void afficherParents() {
        for (Classe c : this.getParents()) {
            c.setVisibilite(true);
        }
    }


    /**
     * Masque les classes parentes de la classe
     */
    public void masquerParents() {
        for (Classe c : this.getParents()) {
            c.setVisibilite(false);
        }
    }


    /**
     * Définit l'égalité entre deux classes
     * Les classes sont égales si elles ont le même nom et le même package
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Classe c) {
            // Si une seule des 2 classes a un nom null, on renvoie false
            if ((this.nom == null ^ c.getNom() == null) || (this.nomPackage == null ^ c.getNomPackage() == null)) {
                return false;
            }
            boolean packageEgal = this.nomPackage == null || Objects.equals(this.nomPackage, c.getNomPackage());
            if (this.nom == null) {
                return false;
            }
            boolean classeEgal = this.nom.equals(c.getNom());
            return packageEgal && classeEgal;
        }
        return false;
    }

}