package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import entities.Role;
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
import utils.BCryptPass;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class GetsionUserAdminView {

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
    private ComboBox<Role> cborele;
    ServicUtilisateur su=new ServicUtilisateur();
    @FXML
    private ImageView imgprofile;
    @FXML
    private Label lusername;

    @FXML
    public void initialize() {
        try {
            Utilisateur u=su.getById(AuthentificationView.idLogin);
            lusername.setText(u.getNom()+" "+u.getPrenom());
            File file=new File("C:\\Users\\wael\\IdeaProjects\\Sportify\\src\\main\\resources\\com\\example\\sportify\\img\\"+u.getImage());
            Image img=new Image(file.toURI().toString());

            imgprofile.setImage(img);
            imgprofile.setFitWidth(50);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cborele.getItems().setAll(Role.values());
    }
    private int userIdToUpdate = -1;

    public void setUserForUpdate(Utilisateur user) {
        userIdToUpdate = user.getId();
        tfnom.setText(user.getNom());
        tfprenom.setText(user.getPrenom());
        tfemail.setText(user.getEmail());
        tfmpd.setText(user.getMot_de_passe());
        tfimage.setText(user.getImage());
        tfadresse.setText(user.getAdresse());
        LocalDate localDate= Instant.ofEpochMilli(user.getDate_de_naissance().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        dpdate.setValue(localDate);
        //dpdate.setValue(user.getDate_de_naissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        cborele.setValue(user.getRole());
    }

    @FXML
    void ajouterUser(ActionEvent event) {
        if(controleDeSaisie().length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(controleDeSaisie());
            alert.showAndWait();
        }
        else{
            // Create a new user with form data
            Utilisateur newUser = new Utilisateur();
            newUser.setNom(tfnom.getText());
            newUser.setPrenom(tfprenom.getText());
            newUser.setEmail(tfemail.getText());
            newUser.setMot_de_passe(tfmpd.getText()); // Ensure to handle password securely
            newUser.setImage(tfimage.getText());
            newUser.setAdresse(tfadresse.getText());

            if (dpdate.getValue() != null) {
                LocalDate localDate = dpdate.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                newUser.setDate_de_naissance(Date.from(instant));
            }

            newUser.setRole(cborele.getValue());
            try {
                su.ajouter(newUser);
                try {
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("afficher-user-admin.fxml"));
                    Parent root = loader.load();
                    Window currentWindow = tfadresse.getScene().getWindow();
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
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfimage.getScene().getWindow());
        if(file!=null){
            String filename=file.getName();
            tfimage.setText(filename);
        }


    }
    @FXML
    void updateUtilisateur(ActionEvent event) {
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
                updatedUser.setMot_de_passe(BCryptPass.hashPass(tfmpd.getText()));
                updatedUser.setImage(tfimage.getText());
                updatedUser.setAdresse(tfadresse.getText());

                if (dpdate.getValue() != null) {
                    LocalDate localDate = dpdate.getValue();
                    updatedUser.setDate_de_naissance(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }

                updatedUser.setRole(cborele.getValue());

                try {
                    su.modifier(userIdToUpdate, updatedUser);
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("afficher-user-admin.fxml"));
                        Parent root = loader.load();
                        Window currentWindow = tfadresse.getScene().getWindow();
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
                    e.printStackTrace();
                }
            }
        }

    }
    @FXML
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
        if(cborele.getValue() == null){
            erreur += "- Role non spécifiée.\n";
        }
        return erreur;

    }
    @FXML
    void gotoAfficherUser(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("afficher-user-admin.fxml"));
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
    }

}
