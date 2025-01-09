package gen_diagrammes.vues;

import gen_diagrammes.diagramme.Classe;
import gen_diagrammes.diagramme.Diagramme;
import gen_diagrammes.gInterface.ContenuCellule;
import gen_diagrammes.gInterface.Observateur;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.HashMap;

/**
 * Affichage de la liste des classes (menu)
 */
public class VueListeClasses extends VBox implements Observateur {

    private HashMap<String, TreeItem<ContenuCellule>> listePackages;
    private TreeItem<ContenuCellule> racine;

    public VueListeClasses() {
        this.racine = new TreeItem<>(new ContenuCellule("    Liste des classes du diagramme    "));
        this.listePackages = new HashMap<>();
    }

    public void actualiser() {
        this.getChildren().clear();
        this.listePackages = new HashMap<>();
        this.racine = new TreeItem<>(new ContenuCellule("Liste des classes du diagramme"));
        this.racine.setExpanded(true);

        for (Classe c : Diagramme.getInstance().getListeClasses()) {
            if (c.getNomPackage() != null) {
                String[] packageParts = c.getNomPackage().split("\\.");
                TreeItem<ContenuCellule> currentItem = racine;
                StringBuilder currentPath = new StringBuilder();

                for (String part : packageParts) {
                    if (!currentPath.isEmpty()) {
                        currentPath.append(".");
                    }
                    currentPath.append(part);

                    String path = currentPath.toString();
                    TreeItem<ContenuCellule> finalCurrentItem = currentItem;
                    currentItem = listePackages.computeIfAbsent(path, k -> {
                        TreeItem<ContenuCellule> newItem = new TreeItem<>(new ContenuCellule(part));
                        newItem.setExpanded(true);
                        finalCurrentItem.getChildren().add(newItem);
                        return newItem;
                    });
                }

                TreeItem<ContenuCellule> classe = new TreeItem<>(new ContenuCellule(c.getNom(), c));
                classe.setExpanded(true);
                currentItem.getChildren().add(classe);
            } else {
                TreeItem<ContenuCellule> classe = new TreeItem<>(new ContenuCellule(c.getNom(), c));
                classe.setExpanded(true);
                this.racine.getChildren().add(classe);
            }
        }

        TreeView<ContenuCellule> treeView = new TreeView<>(this.racine);
        // Adapte la taille de l'arbre à la taille de la fenêtre
        VBox.setVgrow(treeView, Priority.ALWAYS);

        treeView.setCellFactory(new Callback<>() {
            @Override
            public TreeCell<ContenuCellule> call(TreeView<ContenuCellule> treeView) {
                return new TreeCell<>() {
                    @Override
                    protected void updateItem(ContenuCellule tache, boolean empty) {
                        super.updateItem(tache, empty);

                        if (tache != null) {
                            setText(tache.toString());
                            if (tache.getClasseDeTache() != null) {
                                if (tache.getClasseDeTache().isVisible()) {
                                    setStyle("-fx-text-fill: #1E6C93;");
                                } else {
                                    setStyle("-fx-text-fill: #FF5F39;");
                                }
                            } else {
                                setStyle("-fx-text-fill: black;");
                            }
                        } else {
                            setText("");
                            setStyle("");
                        }

                        setOnMouseClicked(new EventHandler<>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (tache != null && tache.getClasseDeTache() != null) {
                                    tache.getClasseDeTache().setVisibilite(!tache.getClasseDeTache().isVisible());
                                    updateItem(tache, empty);
                                }
                                mouseEvent.consume();
                            }
                        });
                    }
                };
            }
        });

        treeView.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TreeItem<ContenuCellule> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    selectedItem.setExpanded(true);
                }
                mouseEvent.consume();
            }
        });

        this.getChildren().add(treeView);
    }
}