package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServicUtilisateur;
import utils.MailApi;

import java.io.IOException;
import java.sql.SQLException;

public class OtpVerification {

    @FXML
    private Button signUpBtn11;

    @FXML
    private TextField tfcode;
    int code=(int)Math.floor(Math.random()*(999999-100000+1)+100000);

    ServicUtilisateur servicUtilisateur=new ServicUtilisateur();
    @FXML
    public void initialize(){
        if(AuthentificationView.email!=null){
            MailApi.sendEmail(AuthentificationView.email,"Demande de verification email","Veuillez saisire ce code pour que vous pouvez verifier votre compte:"+code);

        }
    }

    @FXML
    void login(ActionEvent event) {
        if(Integer.valueOf(tfcode.getText())!=code){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText("-Veuillez verifier le code envoyer\n");
            alert.showAndWait();
        }
        else{
            Utilisateur u= null;
            try {
                u = servicUtilisateur.getByEmail(AuthentificationView.email);
                u.setVerified(true);
                servicUtilisateur.modifier(u.getId(),u);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("authentification-view.fxml"));
                Parent root = loader.load();
                Window currentWindow = tfcode.getScene().getWindow();
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
    }

}