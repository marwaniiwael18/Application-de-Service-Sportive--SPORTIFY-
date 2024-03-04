package com.example.sportify.controller;

import entities.ClassementEquipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceClassementEquipeX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class rankedteamcontroller implements Initializable {

    @FXML
    private HBox hbox;

    @FXML
    private VBox vboxall;

    ServiceClassementEquipeX classementEqService= new ServiceClassementEquipeX() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean color1=true ;
        boolean color2= false ;
        try {
        List<ClassementEquipe> tables = classementEqService.afficher() ;
        for (ClassementEquipe table : tables) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/soloitem.fxml"));
            try {
                HBox hboxitems = fxmlLoader.load();
                soloitem items= fxmlLoader.getController() ;
                items.SetText(table.getNbre_de_match(),table.getEquipe().getNom(),table.getPoints(),table.getRank());

                if (color1) {
                    hboxitems.setStyle("-fx-background-color: #3A2F05");
                } else if (color2) {
                    hboxitems.setStyle("-fx-background-color: #292E37");
                } else {
                    hboxitems.setStyle("-fx-background-color: #271304");
                }

                color1=!color1 ;
                color2=!color2;
                vboxall.getChildren().add(hboxitems) ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
}
