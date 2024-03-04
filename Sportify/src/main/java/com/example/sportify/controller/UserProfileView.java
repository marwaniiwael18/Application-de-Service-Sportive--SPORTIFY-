package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServicUtilisateur;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UserProfileView {

    @FXML
    private ImageView imgprofile;

    @FXML
    private Label lusername;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfemail;

    @FXML
    private PasswordField tfmpd;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfadresse;

    @FXML
    private DatePicker dpdate;

    @FXML
    private Button uploadbtn;
    @FXML
    private ImageView imgprofile2;

    @FXML
    private Label lusername2;
    ServicUtilisateur servicUtilisateur=new ServicUtilisateur();
    int userIdToUpdate=-1;
    Utilisateur user;
    @FXML
    public void initialize() {

        try {
            user=servicUtilisateur.getById(AuthentificationView.idLogin);
            //lusername.setText(user.getNom()+" "+user.getPrenom());
            File file=new File("C:\\Users\\wael\\IdeaProjects\\Sportify\\src\\main\\resources\\com\\example\\sportify\\img\\"+user.getImage());
            Image img=new Image(file.toURI().toString());

            //imgprofile.setImage(img);

            //imgprofile.setFitWidth(50);
            lusername2.setText(user.getNom());
            imgprofile2.setImage(img);
            //imgprofile.setFitWidth(80);
            userIdToUpdate = user.getId();
            tfnom.setText(user.getNom());
            tfprenom.setText(user.getPrenom());
            tfemail.setText(user.getEmail());
            tfmpd.setText(user.getMot_de_passe());
            tfimage.setText(user.getImage());
            tfadresse.setText(user.getAdresse());
            LocalDate localDate= Instant.ofEpochMilli(user.getDate_de_naissance().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            dpdate.setValue(localDate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

   /* @FXML
    void logout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("authentification-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
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
    }*/
    /*@FXML
    void backToAcceuil(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-acceuil-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.
                    printStackTrace();
        }
    }*/


    @FXML
    void updateProfile(ActionEvent event) {
        if(controleDeSaisie().length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(controleDeSaisie());
            alert.showAndWait();
        }
        else{
            if (userIdToUpdate != -1) {
                Utilisateur updatedUser = new Utilisateur();
                updatedUser.setId(userIdToUpdate);
                updatedUser.setNom(tfnom.getText());
                updatedUser.setPrenom(tfprenom.getText());
                updatedUser.setEmail(tfemail.getText());
                updatedUser.setMot_de_passe(tfmpd.getText());
                updatedUser.setImage(tfimage.getText());
                updatedUser.setAdresse(tfadresse.getText());
                updatedUser.setRole(user.getRole());
                updatedUser.setNiveau_competence(user.getNiveau_competence());
                if (dpdate.getValue() != null) {
                    LocalDate localDate = dpdate.getValue();
                    updatedUser.setDate_de_naissance(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }


                try {
                    servicUtilisateur.modifier(userIdToUpdate, updatedUser);
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/sportify/PlayerInterface.fxml"));
                        Parent root = loader.load();
                        Window currentWindow = tfadresse.getScene().getWindow();
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfimage.getScene().getWindow());
        if(file!=null){
            String filename=file.getName();
            tfimage.setText(filename);
        }


    }
    public String controleDeSaisie(){
        String erreur="";
        if(tfemail.getText().trim().isEmpty() || !tfemail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            erreur+="- Email invalide ou vide.\n";
        }
        if(tfadresse.getText().trim().isEmpty()){
            erreur+="- Adresse vide.\n";
        }
        if(tfnom.getText().trim().isEmpty()){
            erreur+="- Nom vide.\n";
        }
        if(tfprenom.getText().trim().isEmpty()){
            erreur+="- Prenom vide.\n";
        }
        if(tfimage.getText().trim().isEmpty()){
            erreur+="- Image vide.\n";
        }
        if(tfmpd.getText().trim().isEmpty() || tfmpd.getText().length() < 4){
            erreur += "- Mot de passe vide ou trop court (moins de 4 caractères).\n";
        }
        if(dpdate.getValue() == null){
            erreur += "- Date de naissance non spécifiée.\n";
        }
        return erreur;

    }

}
