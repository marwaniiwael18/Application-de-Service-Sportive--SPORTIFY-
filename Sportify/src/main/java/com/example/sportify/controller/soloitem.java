package com.example.sportify.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class soloitem {
    @FXML
    private Label nbrematch;

    @FXML
    private Label nom;

    @FXML
    private Label points;

    @FXML
    private Label rank;

    void SetText(int nbre ,String nomm , int pts, int ranking) {
        nbrematch.setText(String.valueOf(nbre));
        nom.setText(nomm);
        points.setText(String.valueOf(pts));
        rank.setText(String.valueOf(ranking));
    }


}
