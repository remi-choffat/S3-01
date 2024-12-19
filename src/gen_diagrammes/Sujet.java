package gen_diagrammes;

/**
 * Interface pour les sujets observables
 */
public interface Sujet {

    void ajouterObservateur(Observateur v);

    void supprimerObservateur(Observateur v);

    void notifierObservateurs();

}
