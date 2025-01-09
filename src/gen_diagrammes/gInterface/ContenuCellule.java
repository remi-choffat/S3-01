package gen_diagrammes.gInterface;

import gen_diagrammes.diagramme.Classe;
import lombok.Getter;

/**
 * Classe représentant le contenu d'une cellule du répertoire des classes
 */
public class ContenuCellule {

    @Getter
    private String nom;

    private final Classe classe;

    public ContenuCellule(String nom, Classe c) {
        this.nom = nom;
        this.classe = c;
    }

    public ContenuCellule(String nom) {
        this.nom = nom;
        this.classe = null;
    }

    public Classe getClasseDeTache() {
        return this.classe;
    }

    public String toString() {
        return this.nom;
    }

}
