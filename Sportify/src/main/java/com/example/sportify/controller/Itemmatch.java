package com.example.sportify.controller;

import entities.Competition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import services.CompetitionServiceX;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class Itemmatch implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Button joinbutton;

    @FXML
    private Label nomcompetition;

    @FXML
    private Label terrain;

    @FXML
    private Label time;
    private ListecompetitonController listecompetitonController;

    private boolean buttonetat = true  ;

    private Competition competition ;

    private int id ;

    CompetitionServiceX competservice = new CompetitionServiceX() ;


    void SetText(int ide ,String nomm , Date datee, Time heure, String terrainnom) {
         this.id = ide ;
        nomcompetition.setText(nomm);
        date.setText(String.valueOf(datee));
        time.setText(String.valueOf(heure));
        terrain.setText(terrainnom);

    }


    int getId() {
        return this.id;
    }


    void DisableButton(boolean setbutton ) {
            buttonetat = setbutton;
            if (!buttonetat) {
                removeFromParent(joinbutton);
            }
    }
    public void setListecompetitonController(ListecompetitonController controller) {
        this.listecompetitonController = controller;
    }
    @FXML
    void joindreButton(MouseEvent event) throws IOException, SQLException {

        listecompetitonController.changeToJoueurTeamVS(id);
    }

    private void removeFromParent(Button button) {
        button.setVisible(false);
        button.setManaged(false);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!buttonetat) {
            removeFromParent(joinbutton);
        }
    }
}
