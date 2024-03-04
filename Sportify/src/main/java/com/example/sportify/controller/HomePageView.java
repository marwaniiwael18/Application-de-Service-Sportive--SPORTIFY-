package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class HomePageView
{
    @FXML
    private Label helloLB;

    @FXML
    private Label helloLB1;

    @FXML
    private Label helloLB11;

    @FXML
    private Label helloLB2;

    @FXML
    private ImageView imgprofile;

    @FXML
    private Button signUpBtn;

    @FXML
    void gotoAfficherUser(MouseEvent event) {

    }

    @FXML
    void gotoAuthpage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("authentification-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = imgprofile.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            Image icon = new Image("https://i.ibb.co/dgpN1Hj/logo-SPORTIFY.png") ;
            stage.getIcons().add(icon) ;
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(MouseEvent event) {

    }
}