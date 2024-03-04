package controllers;
import entities.Categorie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private Label NomSport;
    @FXML
    private Label Description;
    @FXML
    private HBox box;
    @FXML
    private ImageView imageView;

    private String [] colors = { "B9E5FF","BDB2FE","FB9AA8","FB9AA8","FF5056"};

        public void setData(Categorie categorie) {
            NomSport.setText(categorie.getNom());
            Description.setText(categorie.getDescription());

            String imagePath = categorie.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    // Charger l'image depuis le chemin spécifié
                    InputStream inputStream = getClass().getResourceAsStream(imagePath);
                    if (inputStream != null) {
                        Image image = new Image(inputStream);
                        imageView.setImage(image);
                    } else {
                        // Afficher une image par défaut si le chemin est invalide
                        System.err.println("Image file not found: " + imagePath);
                        Image defaultImage = new Image("/img/180358.jpg");
                        imageView.setImage(defaultImage);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading image: " + e.getMessage());
                }
            } else {
                // Afficher une image par défaut si le chemin est null ou vide
                System.err.println("Image path is null or empty");
                Image defaultImage = new Image("/img/180358.jpg");
                imageView.setImage(defaultImage);
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



