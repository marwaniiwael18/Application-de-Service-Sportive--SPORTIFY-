package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingMatchController implements Initializable {
    @FXML
    private ImageView star1, star2, star3, star4, star5;

    private int rating = 0;

    private Image filledStarImage;
    private Image emptyStarImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filledStarImage = new Image(getClass().getResourceAsStream("/filledStarImage.png"));
        emptyStarImage = new Image(getClass().getResourceAsStream("/emptyStarImage.jpg"));

        // Configurer chaque ImageView avec l'image d'étoile vide
        star1.setImage(emptyStarImage);
        star2.setImage(emptyStarImage);
        star3.setImage(emptyStarImage);
        star4.setImage(emptyStarImage);
        star5.setImage(emptyStarImage);
    }

    @FXML
    private void handleStar1Click() {
        setRating(1);
    }

    @FXML
    private void handleStar2Click() {
        setRating(2);
    }

    @FXML
    private void handleStar3Click() {
        setRating(3);
    }

    @FXML
    private void handleStar4Click() {
        setRating(4);
    }

    @FXML
    private void handleStar5Click() {
        setRating(5);
    }

    private void setRating(int stars) {
        rating = stars;

        // Mettre à jour l'affichage des étoiles selon la note choisie
        star1.setImage(stars >= 1 ? filledStarImage : emptyStarImage);
        star2.setImage(stars >= 2 ? filledStarImage : emptyStarImage);
        star3.setImage(stars >= 3 ? filledStarImage : emptyStarImage);
        star4.setImage(stars >= 4 ? filledStarImage : emptyStarImage);
        star5.setImage(stars >= 5 ? filledStarImage : emptyStarImage);
    }

    public int getRating() {
        return rating;
    }
    @FXML
    private Button submitButton;


    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        // Traitez la soumission de la note ici
        // Par exemple, vous pouvez fermer la fenêtre après la soumission de la note
        if (submitButton != null && submitButton.getScene() != null) {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}}
