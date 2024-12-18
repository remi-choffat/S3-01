package gen_diagrammes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Classe principale de l'application
 */
public class Main extends Application {


    /**
     * Méthode principale
     * Démarre l'interface graphique et demande les chemins des fichiers .class en console
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        // Lance l'interface graphique dans un thread séparé
        new Thread(() -> Application.launch(Main.class, args)).start();

        // Demande les chemins des fichiers .class en console
        Diagramme d = Diagramme.getInstance();
        if (args.length == 1) {
            Diagramme.initialize(args[0], null);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un chemin de classe (chemin absolu si aucun package sélectionné) : ");
        String res = sc.nextLine();
        while (!res.equals("q")) {
            if (!res.equals("*export")) {
                try {
                    d.ajouterClasse(new Classe(res));
                    System.out.println(d);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                Exporter exp = new Exporter(d);
                exp.exportUML();
            }
            System.out.println("Entrez un chemin de classe, ou tapez *export pour exporter le diagramme : ");
            res = sc.nextLine();
        }
    }


    /**
     * Affiche l'interface graphique
     *
     * @param stage Stage
     */
    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group(), 800, 600);

        // Affichage manuel d'une classe (exemple)

        VBox hb = new VBox();
        hb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        hb.setAlignment(Pos.CENTER);

        Label l1 = new Label("(C) public Classe");
        hb.getChildren().add(l1);

        VBox vb = new VBox();
        vb.setPadding(new Insets(10));
        vb.setAlignment(Pos.CENTER_LEFT);
        vb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label l2 = new Label("O String nom");
        Label l3 = new Label("O int val");
        vb.getChildren().addAll(l2, l3);
        hb.getChildren().add(vb);

        VBox vb2 = new VBox();
        vb2.setPadding(new Insets(10));
        vb2.setAlignment(Pos.CENTER_LEFT);
        vb2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label l4 = new Label("O getNom() : String");
        Label l5 = new Label("O setVal(int)");
        vb2.getChildren().addAll(l4, l5);
        hb.getChildren().add(vb2);

        ((Group) scene.getRoot()).getChildren().add(hb);

        stage.setScene(scene);
        stage.setTitle("Plante UML");
        stage.show();

    }

}