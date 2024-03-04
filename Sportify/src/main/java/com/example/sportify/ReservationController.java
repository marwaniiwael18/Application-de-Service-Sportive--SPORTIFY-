package com.example.sportify;

import com.example.sportify.AjouterReservationController;
import entities.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServiceTerrain;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Set;

public class ReservationController {

    private final ServiceTerrain SE = new ServiceTerrain();
    @FXML
    private ImageView imgprofile;

    @FXML
    private Label lusername;
    @FXML
    public HBox hBoxEvents;

    @FXML
    private AnchorPane NomId;



    @FXML
    public void initialize() {
        try {
            Set<Terrain> terrains = SE.afficher();

            for (Terrain terrain : terrains) {
                String imagePath = terrain.getImage_ter();
                Image image = loadImage(imagePath); // Use the loadImage method
                ImageView imageView = new ImageView(image);


                ImageView imageView1 = new ImageView(image);
                Label nomLabel = new Label("Nom : " + terrain.getNom());
                Label prixLabel = new Label("Prix : " + terrain.getPrix() + " DT");
                Button reserverButton = new Button("Reserver");
                reserverButton.setUserData(terrain.getID_Terrain());
                reserverButton.setOnAction(this::ajouter_part);

                Button detailsButton = new Button("Details");
                detailsButton.setOnAction(event -> showTerrainDetails(terrain));

                VBox eventBox = new VBox(imageView, nomLabel, prixLabel, reserverButton, detailsButton);
                eventBox.setSpacing(10);

                hBoxEvents.getChildren().add(eventBox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Image loadImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File imageFile = new File(imagePath);
                String imageUrl = imageFile.toURI().toURL().toString();
                return new Image(imageUrl, 150, 150, false, true);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Optionally, return a placeholder image if the URL is malformed
            }
        }
        // Return a default image or handle the error appropriately
        return new Image("/path/to/placeholder.png"); // Make sure this path is correct
    }
    private void showTerrainDetails(Terrain terrain) {
        Stage detailsStage = new Stage();
        AnchorPane detailsPane = new AnchorPane();
        detailsPane.setPadding(new Insets(10));
        URL stylesheetURL = getClass().getResource("/styles.css");
        if (stylesheetURL != null) {
            detailsPane.getStylesheets().add(stylesheetURL.toExternalForm());
            detailsPane.getStyleClass().add("terrain-details");
        } else {
            System.out.println("Stylesheet not found.");
        }
        detailsPane.getStyleClass().add("terrain-details");

        String details = String.format("Détails du Terrain:\nNom: %s\nType de surface: %s\nLocalisation: %s\nPrix: %.2f\n",
                terrain.getNom(),
                terrain.getType_surface(),
                terrain.getLocalisation(),
                terrain.getPrix());
        Label detailsLabel = new Label(details);
        detailsLabel.setFont(new Font("Arial", 14));
        detailsLabel.setWrapText(true);

        String imagePath = terrain.getImage_ter();
        Image image = null;
        if (imagePath != null) {
            try {
                File imageFile = new File(imagePath);
                image = new Image(new FileInputStream(imageFile), 200, 200, true, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Gestion du cas où le chemin de l'image est null
            }
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(event -> detailsStage.close());
        closeButton.getStyleClass().add("close-button");

        detailsPane.getChildren().addAll(imageView, detailsLabel, closeButton);
        AnchorPane.setTopAnchor(imageView, 20.0);
        AnchorPane.setTopAnchor(detailsLabel, 230.0);
        AnchorPane.setTopAnchor(closeButton, 450.0);

        Scene detailsScene = new Scene(detailsPane, 400, 500);
        detailsStage.setScene(detailsScene);
        detailsStage.setTitle("Détails du Terrain");
        detailsStage.show();
    }
    public void ajouter_part(ActionEvent actionEvent) {
        int idTerrain = (int)((Button) actionEvent.getSource()).getUserData();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReservation.fxml"));
            Parent root = loader.load();

            AjouterReservationController controller = loader.getController();
            controller.setID_Terrain(idTerrain);

            NomId.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void logout(javafx.scene.input.MouseEvent event) {
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
    void gotoprofile(javafx.scene.input.MouseEvent event) {
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
    void gotoacceuil(javafx.scene.input.MouseEvent event) {
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
    }
    @FXML
    void gotoTerrain(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Terrain.fxml"));
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