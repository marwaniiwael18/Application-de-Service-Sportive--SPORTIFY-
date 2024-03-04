package com.example.sportify.controller;

import entities.Competition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.CompetitionServiceX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PropListeCompetitionController implements Initializable {
    @FXML
    private VBox vboxall;

    CompetitionServiceX competitonService = new CompetitionServiceX() ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean color1=true ;
        try {
            List<Competition> competitions= competitonService.afficher() ;

            for (Competition compet:competitions) {
         //   for (int i=0 ; i <10 ; i++ ) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/propItemMatch.fxml"));
                HBox hboxitems = fxmlLoader.load();

                propItemMatch items = fxmlLoader.getController();
                items.SetText(compet);
                //color start
                if (color1) {
                    hboxitems.setStyle("-fx-background-color: #3A2F05");
                } else {
                    hboxitems.setStyle("-fx-background-color: #271304");
                }

                color1 = !color1;
                //color end
                vboxall.getChildren().add(hboxitems);
            }        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
