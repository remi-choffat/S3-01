package gen_diagrammes;

public interface Sujet {
    public void ajouterObservateur(Observateur v);

    public void supprimerObservateur(Observateur v);

    public void notifierObservateurs();
}
