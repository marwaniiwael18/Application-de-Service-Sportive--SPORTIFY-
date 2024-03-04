package com.example.sportify;

import entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServiceTerrain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TerrainController{
    private final ServiceTerrain ST = new ServiceTerrain();
    @FXML
    private ImageView imgprofile;
    @FXML
    private Label lusername;


    @FXML
    private TextField typet;
    @FXML
    private TextField prixt;

    @FXML
    private TextField nomt;
    @FXML
    private TextField loclt;

    @FXML
    private Button affter;

    @FXML
    private Button suptr;
    @FXML
    private Button modtr;



    @FXML
    private AnchorPane principal;
    @FXML
    private Button Import_btn;
    private Image image;

    @FXML
    private ImageView image_id;

    private String imagePath;



    @FXML
    private void ajt(ActionEvent event) {
        // Vérification que les champs ne sont pas vides
        if (nomt.getText().isEmpty() || typet.getText().isEmpty() || loclt.getText().isEmpty() || prixt.getText().isEmpty() || imagePath == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation des champs");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs et sélectionner une image !");
            alert.showAndWait();
            return; // Arrête la méthode si une validation échoue
        }

        // Vérification que le prix est un nombre valide
        double prix;
        try {
            prix = Double.parseDouble(prixt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation du prix");
            alert.setHeaderText(null);
            alert.setContentText("Le prix doit être un nombre valide !");
            alert.showAndWait();
            return; // Arrête la méthode si une validation échoue
        }

        // Si tout est valide, continue avec l'ajout
        Terrain c = new Terrain();
        c.setNom(nomt.getText());
        c.setType_surface(typet.getText());
        c.setLocalisation(loclt.getText());
        c.setPrix(prix);
        c.setImage_ter(imagePath);

        ServiceTerrain sm = new ServiceTerrain();
        try {
            sm.ajouter(c, imagePath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Terrain ajouté avec succès");
            alert.showAndWait();
            initialize(); // Rafraîchir les données si nécessaire
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception SQL");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du terrain: " + e.getMessage());
            alert.showAndWait();
        }
    }





    @FXML
    void initialize() {

    }

    @FXML
    void affter(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageTerrain.fxml"));
        Parent root = loader.load();
        AffichageTerrainController a = loader.getController();
        nomt.getScene().setRoot(root);
    }


    public void Import(ActionEvent Terrain) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
        File file = openFile.showOpenDialog(principal.getScene().getWindow());
        if (file != null) {
            imagePath = file.getAbsolutePath(); // Change this line
            image = new Image(file.toURI().toString(), 200, 200, false, true);
            image_id.setImage(image);
        }
    }

    /*@FXML
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
    @FXML
    void gotoprofile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-profile-view.fxml"));
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
    @FXML
    void gotoacceuil(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("UserAcceuilView.fxml"));
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

    @FXML
    void gotoreservation(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Reservation.fxml"));
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

}


