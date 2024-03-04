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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServicUtilisateur;
import utils.BCryptPass;
import utils.MailApi;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPasswordView {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnmodifier;

    @FXML
    private PasswordField pfpass;

    @FXML
    private PasswordField pfpassverification;

    @FXML
    private TextField tfcode;
    @FXML
    private TextField tfemail;
    int code=(int)Math.floor(Math.random()*(999999-100000+1)+100000);
    @FXML
    public void initialize(){
        tfcode.setVisible(false);
        pfpass.setVisible(false);
        pfpassverification.setVisible(false);
        btnmodifier.setVisible(false);
    }
    ServicUtilisateur servicUtilisateur=new ServicUtilisateur();
    @FXML
    void chercherEmail(ActionEvent event) {
        try {
            if(servicUtilisateur.getByEmail(tfemail.getText())!=null){
                MailApi.sendEmail(tfemail.getText(),"Demande de changement mot de passe","Veuillez saisire ce code pour que vous pouvez modifier votre mot de passe:"+code);
                tfcode.setVisible(true);
                pfpass.setVisible(true);
                pfpassverification.setVisible(true);
                btnmodifier.setVisible(true);
            }
            else{
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("invalide");
                alert.setContentText("Email introuvable!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String controleDeSaisie(){
        String erreur="";
        if(!tfcode.getText().equals(String.valueOf(code))){
            erreur+="-Veuillez verifier le code envoyer\n";
        }
        if(!pfpass.getText().equals(pfpassverification.getText())){
            erreur+="-Veuillez verfier les mots de passes saisie\n";
        }
        return erreur;
    }
    @FXML
    void modifierMotDePasse(ActionEvent event) {
        if(controleDeSaisie().length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(controleDeSaisie());
            alert.showAndWait();
        }
        else{
            Utilisateur utilisateur= null;
            try {
                utilisateur = servicUtilisateur.getByEmail(tfemail.getText());
                utilisateur.setMot_de_passe(BCryptPass.hashPass(pfpass.getText()));
                servicUtilisateur.modifier(utilisateur.getId(),utilisateur);
                try {
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("authentification-view.fxml"));
                    Parent root = loader.load();
                    Window currentWindow = tfcode.getScene().getWindow();
                    if (currentWindow instanceof Stage) {
                        ((Stage) currentWindow).close();
                    }

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Sportify");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }

    @FXML
    void retourloginpage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("authentification-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = tfcode.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
