package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppEvent extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Charger l'interface utilisateur à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Event.fxml"));

        // Créer une scène avec l'interface utilisateur chargée
        Scene scene = new Scene(root);

        // Définir la scène sur la fenêtre principale
        stage.setTitle("Gestion des Posts et Commentaires");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
