package com.example.sportify.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Playercard {
    @FXML
    private ImageView img;

    @FXML
    private Text nomPlayer;

    @FXML
    public void setCard(String nom) {
        nomPlayer.setText(nom);
    }

}
