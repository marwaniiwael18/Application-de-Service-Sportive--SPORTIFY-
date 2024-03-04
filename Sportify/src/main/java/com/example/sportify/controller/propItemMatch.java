package com.example.sportify.controller;

import entities.Competition;
import entities.Equipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class propItemMatch  {
    @FXML
    private Button Scorebutton;

    @FXML
    private Label date;

    @FXML
    private Label nomcompetition;

    @FXML
    private Label terrain;

    @FXML
    private Label time;

    private Equipe equipe1 ;
    private Equipe equipe2 ;

    private Competition competition ;
    private int id ;

    void SetText(Competition compet) {
        competition = compet;
        nomcompetition.setText(compet.getNom());
        date.setText(String.valueOf(compet.getDate()));
        time.setText(String.valueOf(compet.getHeure()));
        terrain.setText(compet.getTerrain().getNomTerrain());

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Get the competition date and time
        LocalDate competitionDate = competition.getDate().toLocalDate();
        LocalTime competitionTime = competition.getHeure().toLocalTime();

        if (competitionDate.isAfter(currentDate) ||
                (competitionDate.isEqual(currentDate) && competitionTime.isAfter(currentTime))) {
            Scorebutton.setDisable(true); // Optionally, change the appearance of the button to indicate that it is disabled
            Scorebutton.setStyle("-fx-opacity: 0.5;");
        }
    }

    @FXML
    void Score(MouseEvent event) throws IOException {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/propScoreInterface.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            PropScoreInterfaceController score = fxmlLoader.getController();
            score.setScore(competition);
            Image icon = new Image("https://i.ibb.co/dgpN1Hj/logo-SPORTIFY.png");
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            scene.setFill(Color.TRANSPARENT); // to make rounded corners
            stage.initStyle(StageStyle.TRANSPARENT); // to make rounded corners
            stage.setResizable(false);
            stage.setTitle("SCORE");
            stage.setScene(scene);
            stage.show();
        }


}
