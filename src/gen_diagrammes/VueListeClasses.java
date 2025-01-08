package gen_diagrammes;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Affichage de la liste des classes (menu)
 */
public class VueListeClasses extends VBox implements Observateur {

    private HashMap<String, ArrayList<ContenuCellule>> listePackages;
    private TreeItem<ContenuCellule> racine;

    public VueListeClasses() {
        this.racine = new TreeItem<>(new ContenuCellule("    Liste des classes du diagramme :    "));
        this.listePackages = new HashMap<>();
    }

    public void updateListe() {
        this.getChildren().clear();
        this.listePackages = new HashMap<>();
        this.racine = new TreeItem<>(new ContenuCellule("    Liste des classes du diagramme :    "));
        this.racine.setExpanded(true);

        for (Classe c : Diagramme.getInstance().getListeClasses()) {
            //Pour chaque classe étant dans un package
            if (c.getNomPackage() != null) {
                //On vérifie si le package a déjà été ajouté à la liste
                if (this.listePackages.containsKey(c.getNomPackage())) {
                    //On récupère la liste des classes dans le package
                    ArrayList<ContenuCellule> listeClassesDansPackage = this.listePackages.get(c.getNomPackage());
                    //On y ajoute la nouvelle classe
                    listeClassesDansPackage.add(new ContenuCellule(c.getNom(),c));
                    this.listePackages.put(c.getNomPackage(),listeClassesDansPackage);
                } else {
                    ArrayList<ContenuCellule> listeClasses = new ArrayList<>();
                    listeClasses.add(new ContenuCellule(c.getNom(),c));
                    this.listePackages.put(c.getNomPackage(), listeClasses);
                }
            } else {
                TreeItem<ContenuCellule> classe = new TreeItem<ContenuCellule>(new ContenuCellule(c.getNom(),c));
                this.racine.getChildren().add(classe);
            }
        }

        for(Map.Entry<String, ArrayList<ContenuCellule>> entry : this.listePackages.entrySet()) {
            String key = entry.getKey();
            ArrayList<ContenuCellule> values = entry.getValue();
            TreeItem<ContenuCellule> racinePackage = new TreeItem<>(new ContenuCellule(key));
            //Pour chaque classe d'un package
            for (ContenuCellule tache : values) {
                TreeItem<ContenuCellule> classe = new TreeItem<>(tache);
                racinePackage.getChildren().add(classe);
            }
            racinePackage.setExpanded(true);
            this.racine.getChildren().add(racinePackage);
        }
        TreeView<ContenuCellule> treeView = new TreeView<ContenuCellule>(this.racine);

        treeView.setCellFactory(new Callback<TreeView<ContenuCellule>, TreeCell<ContenuCellule>>() {
            @Override
            public TreeCell call(TreeView treeView) {
                TreeCell<ContenuCellule> cell = new TreeCell<ContenuCellule>() {
                    @Override
                    protected void updateItem(ContenuCellule tache, boolean empty) {
                        // une TreeCell peut changer de tâche, donc changer de TreeItem
                        super.updateItem(tache, empty);

                        setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if ((tache != null)&&(tache.getClasseDeTache() != null)) {
                                    if (tache.getClasseDeTache().isVisible()){
                                        tache.getClasseDeTache().setVisibilite(false);
                                    } else {
                                        tache.getClasseDeTache().setVisibilite(true);
                                    }
                                }
                            }
                        });

                        if (tache != null) {
                            setText(tache.toString());
                            setStyle(" -fx-background-color: lightgrey ; ");
                            if (tache.getClasseDeTache() != null) {
                                if (tache.getClasseDeTache().isVisible()) {
                                    setStyle("-fx-text-fill: #1E6C93 ;");
                                } else {
                                    setStyle("-fx-text-fill: #FF5F39 ;");
                                }
                            }
                        } else {
                            setText("");
                            setStyle("");
                        }
                    }
                };
                return cell;
            }
        });
        this.getChildren().add(treeView);
    }

    @Override
    public void actualiser() {
        this.updateListe();
    }

}