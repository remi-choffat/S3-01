package gen_diagrammes.controleurs;

import gen_diagrammes.diagramme.Classe;
import gen_diagrammes.diagramme.Diagramme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class SupprimerClasseControleur implements EventHandler<ActionEvent> {

    private StackPane stackPane;

    public SupprimerClasseControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void handle(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Supprimer une classe");
        dialog.setHeaderText("Sélectionnez une classe à supprimer");

        ButtonType buttonTypeOk = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        ListView<String> listView = new ListView<>();
        ArrayList<String> nomsClasses = new ArrayList<>();
        for (Classe c : Diagramme.getInstance().getListeClasses()) {
            nomsClasses.add(c.getNomPackage() + "." + c.getNom());
        }
        listView.getItems().addAll(nomsClasses);
        dialog.getDialogPane().setContent(listView);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return listView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            Classe classe = Diagramme.getInstance().getClasse(result.split("\\.")[1], result.split("\\.")[0]);
            System.out.println(classe.getTypeClasseString() + " " + classe.getNom() + " supprimée");
            Diagramme.getInstance().supprimerClasse(classe);
            Diagramme.getInstance().afficher(stackPane);
        });
    }

}
