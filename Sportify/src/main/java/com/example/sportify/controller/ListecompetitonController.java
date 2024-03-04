package com.example.sportify.controller;

import entities.Competition;
import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.CompetitionServiceX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListecompetitonController implements Initializable {

    @FXML
    private VBox vboxall;

    @FXML
    private AnchorPane ListCompetitioninterface;

    CompetitionServiceX competitonService = new CompetitionServiceX() ;

    Competition competition ;
    private Utilisateur loggedUser;

    public void initData(Utilisateur User) {
        this.loggedUser= User  ;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      boolean color1=true ;
        try {
            List<Competition> competitions= competitonService.afficher() ;

        for (Competition compet:competitions) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/itemmatch.fxml"));
            HBox hboxitems = fxmlLoader.load();


            Itemmatch items = fxmlLoader.getController();
            items.SetText(compet.getID_competiton(),compet.getNom(),compet.getDate(),compet.getHeure(),compet.getTerrain().getNomTerrain());
            items.setListecompetitonController(this);


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

    public void changeToJoueurTeamVS(int matchID) throws IOException, SQLException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/JoueurTeamVS.fxml"));
        AnchorPane teamVSinterface = fxmlLoader.load();

        JoueurTeamVScontroller joueurTeamVScontroller = fxmlLoader.getController();
        joueurTeamVScontroller.setMatchDetails(matchID);// Pass the match ID to the JoueurTeamVScontroller
        joueurTeamVScontroller.initData(loggedUser);

        // Assuming ListCompetitioninterface is the parent container where you want to add JoueurTeamVS
        ListCompetitioninterface.getChildren().clear(); // Clear existing content
        ListCompetitioninterface.getChildren().add(teamVSinterface);
    }


}

