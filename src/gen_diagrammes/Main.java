package gen_diagrammes;
import java.util.Scanner;

//fonctionnement théorique de l'application à la fin de l'itération 1

public class Main {

    public static void main(String[] args) {
        
        //diagramme d;
        if(args.length == 0) {
            //d = new Diagramme(args[0]);
        } else {
            //d = new Diagramme();
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("enter a class path (absolute path if no selected package) : ");
        String res = sc.nextLine();
        while( ! res.equals("q")){
            if(! res.equals("*export")){
                /*
                try{
                    d.addClasse(res);
                }catch (Exception e) {
                    System.out.println("error while loading class " + res);
                    e.printStackTrace();
                }*/

                //System.out.println(d.toString());
            } else {
                System.out.println("enter a file path and name to export your diagram : ");
                res = sc.nextLine();
                //Exporter exp = new Exporter();
                //exp.export(d,res)
            }
            System.out.println("enter a class path, or type *export to export diagram : ");
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
