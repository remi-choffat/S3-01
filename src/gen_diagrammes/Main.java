package gen_diagrammes;

import java.util.Scanner;

// Fonctionnement théorique de l'application à la fin de l'itération 1

public class Main {

    public static void main(String[] args) {

        Diagramme d = Diagramme.getInstance();
        if (args.length == 1) {
            Diagramme.initialize(args[0], null);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a class path (absolute path if no selected package) : ");
        String res = sc.nextLine();
        while (!res.equals("q")) {
            if (!res.equals("*export")) {
                try {
                    d.ajouterClasse(new Classe(res));
                } catch (Exception e) {
                    System.err.println("Error while loading class " + res);
                }

                System.out.println(d);
            } else {
                Exporter exp = new Exporter(d);
                exp.exportUML();
            }
            System.out.println("Enter a class path, or type *export to export diagram : ");
            res = sc.nextLine();
        }

    }

}
