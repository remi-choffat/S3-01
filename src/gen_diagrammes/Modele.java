package gen_diagrammes;

public interface Modele {
    public void ajouterObservateur(Vue v);
    public void supprimerObservateur(Vue v);
    public void notifierObservateurs();
}
