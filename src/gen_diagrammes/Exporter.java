package gen_diagrammes;

/**
 * Gère l'exportation du diagramme
 */
public class Exporter {

    private final Diagramme diagramme;

    public Exporter(Diagramme diagramme) {
        this.diagramme = diagramme;
    }

    public Diagramme getDiagramme() {
        return diagramme;
    }

    /**
     * Exporte le diagramme dans un fichier au format UML
     */
    public void exportUML() {

        StringBuilder uml = new StringBuilder();
        uml.append("@startuml\n");
        for (Classe c : diagramme.getListeClasses()) {
            uml.append(c);
        }
        uml.append("@enduml\n");

        // Crée un fichier et l'enregistre
        try {
            java.io.FileWriter fw = new java.io.FileWriter("diagramme.puml");
            fw.write(uml.toString());
            fw.close();
            System.out.println("Diagramme exporté avec succès dans le fichier diagramme.puml");
        } catch (java.io.IOException e) {
            System.err.println("Error while exporting diagram");
        }

    }

}
