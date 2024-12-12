package gen_diagrammes;

import java.util.Scanner;

// Fonctionnement théorique de l'application à la fin de l'itération 1

public class Main {

    public static void main(String[] args) {

        Diagramme d;
        if (args.length == 1) {
            d = new Diagramme(args[0], null);
        } else {
            d = new Diagramme();
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
                    e.printStackTrace();
                }

                System.out.println(d);
            } else {
                System.out.println("Enter a file path and name to export your diagram : ");
                res = sc.nextLine();
                //Exporter exp = new Exporter();
                //exp.export(d,res)
            }
            System.out.println("Enter a class path, or type *export to export diagram : ");
            res = sc.nextLine();
        }
        /*
        try { //pour tester si une classe affiche tout bien
            Classe c = new Classe("gen_diagrammes.Classe");
            System.out.println(c.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

    }

}
