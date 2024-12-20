package gen_diagrammes;

import lombok.Setter;

public class Tache {
    @Setter
    private String nom;

    public Tache(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
