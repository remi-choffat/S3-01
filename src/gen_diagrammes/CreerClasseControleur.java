package gen_diagrammes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CreerClasseControleur implements EventHandler<ActionEvent> {

    StackPane stackPane ;

    public CreerClasseControleur(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void handle(ActionEvent event) {
        // Masque le diagramme
        stackPane.getChildren().clear();

        VBox vb = new VBox();
        Label lb = new Label("Créer une classe");
        lb.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        lb.setPadding(new Insets(10));
        TextField tf = new TextField();
        tf.setPromptText("Nom de la classe");

        ComboBox<String> comboVisibilite = new ComboBox<>();
        comboVisibilite.getItems().addAll(Classe.PUBLIC, Classe.PRIVATE, Classe.PROTECTED);
        comboVisibilite.setValue(Classe.PUBLIC);

        ComboBox<String> comboType = new ComboBox<>();
        comboType.getItems().addAll(Classe.CLASS, Classe.ABSTRACT_CLASS, Classe.INTERFACE);
        comboType.setValue(Classe.CLASS);

        HBox ligne = new HBox(10, comboVisibilite, comboType, tf);
        ligne.setAlignment(Pos.CENTER);

        HBox boutons = new HBox(10);
        Button bCancel = new Button("Annuler");
        Button bOk = new Button("Ajouter");
        bOk.setDefaultButton(true);
        bOk.setDisable(true);
        bCancel.setCancelButton(true);
        boutons.getChildren().addAll(bOk, bCancel);
        boutons.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(lb, ligne, boutons);
        vb.setSpacing(20);
        vb.setPadding(new Insets(20));
        vb.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(vb);
        vb.setLayoutX(stackPane.getScaleX() - 0.5 * vb.getScaleX());
        vb.setLayoutY(stackPane.getScaleY() - 0.5 * vb.getScaleY());


        bOk.setOnAction(f -> {
            Classe c = new Classe(tf.getText(), comboVisibilite.getValue(), comboType.getValue());
            Diagramme.getInstance().ajouterClasse(c);
            bCancel.fire();
        });

        bCancel.setOnAction(f -> {
            stackPane.getChildren().remove(vb);
            Diagramme.getInstance().afficher(stackPane);
        });

        tf.setOnKeyTyped(f -> {
            // Désactive le bouton OK si le champ est vide
            bOk.setDisable(tf.getText().length() <= 0);
            // Met la première lettre de la classe en majuscule
            if (tf.getText().length() == 1) {
                tf.setText(tf.getText().toUpperCase());
                tf.positionCaret(tf.getText().length());
            }
        });
    }

}
