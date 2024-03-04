package com.example.sportify;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

import entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServiceTerrain;
import javafx.scene.input.MouseEvent;

public class AffichageTerrainController {
    @FXML
    private ImageView imgprofile;

    @FXML
    private Label lusername;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button tri;

    @FXML
    private Button triPrix;
    @FXML
    private ListView<Terrain> listTerrain;
    @FXML
    private ObservableList<Terrain> TerrainsList;

    @FXML
    private Button supptr;
    @FXML
    private TextField Searchfield;
    @FXML
    private TextField nomm;

    @FXML
    private Button moditr;

    @FXML
    void initialize() {
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file 'AdminTerrain.fxml'.";
        assert nomm != null : "fx:id=\"nomm\" was not injected: check your FXML file 'AdminTerrain.fxml'.";

        ServiceTerrain st = new ServiceTerrain();
        try {
            ObservableList<Terrain> terrains = FXCollections.observableArrayList(st.afficher());
            listTerrain.setItems(terrains);

            listTerrain.setCellFactory(listView -> new ListCell<Terrain>() {
                @Override
                public void updateItem(Terrain terrain, boolean empty) {
                    super.updateItem(terrain, empty);
                    if (empty || terrain == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox with spacing of 10
                        Label nameLabel = new Label(terrain.getNom());
                        nameLabel.setMinWidth(100); // Set minimum width for the label
                        Label typeLabel = new Label(terrain.getType_surface());
                        typeLabel.setMinWidth(100); // Set minimum width for the label
                        Label locationLabel = new Label(terrain.getLocalisation());
                        locationLabel.setMinWidth(100); // Set minimum width for the label
                        Label priceLabel = new Label(String.format("%.2f", terrain.getPrix()));
                        priceLabel.setMinWidth(60); // Set minimum width for the label

                        hbox.getChildren().addAll(nameLabel, typeLabel, locationLabel, priceLabel);
                        setGraphic(hbox); // Set the custom layout as the graphic of the list cell
                    }
                }
            });
            loadTerrains();

        } catch (SQLException e) {
            e.printStackTrace(); // For debugging purposes, you might want to print the stack trace
            // Consider showing an alert to the user or logging the error to a file
        }

        listTerrain.setOnMouseClicked(event -> {
            Terrain selectedItem = listTerrain.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                nomm.setText(selectedItem.getNom());
                // You can now use selectedItem to perform other actions as needed
            }
        });
    }

    private void loadTerrains() throws SQLException {
        ServiceTerrain serviceterrain = new ServiceTerrain();

            TerrainsList = FXCollections.observableArrayList(serviceterrain.afficher());
            listTerrain.setItems(TerrainsList);
        }


// The rest of your controller code...


    @FXML
    void supptr(ActionEvent event) {
        Terrain selectedTerrain = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            ServiceTerrain st = new ServiceTerrain();
            try {
                // Call the method to delete the terrain, assuming there is a method like deleteTerrain
                st.supprimer(selectedTerrain.getID_Terrain()); // replace getID_Terrain() with your actual method name for getting ID

                // Remove the selected item from the ListView
                listTerrain.getItems().remove(selectedTerrain);

                // Clear the selection
                listTerrain.getSelectionModel().clearSelection();

                // Clear the TextField if needed
                nomm.clear();

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Terrain has been successfully deleted.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to delete terrain");
                alert.setContentText("An error occurred: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show warning alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a terrain to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void moditr(ActionEvent event) throws IOException {
        Terrain selectedTerrain = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierTr.fxml"));
            Parent root = loader.load();

            // Get the ModifierTrController and set the Terrain data
            ModifierTrController modifierTrController = loader.getController();
            modifierTrController.setTerrainData(selectedTerrain);

            // Now set the scene
            nomm.getScene().setRoot(root);
        } else {
            // Handle case where no terrain is selected
            // Show alert or log the error
        }
    }




    public void search(ActionEvent event) {
    String keyword = Searchfield.getText().toLowerCase();
    ObservableList<Terrain> filteredList = FXCollections.observableArrayList();

    for (Terrain terrain : TerrainsList) {
        if (terrain.getNom().toLowerCase().contains(keyword)) {
            filteredList.add(terrain);
        }
    }

    listTerrain.setItems(filteredList);
    }

    public void tri(ActionEvent event) {
        TerrainsList.sort(Comparator.comparing(Terrain::getNom));
    }
    @FXML
    void triPrix(ActionEvent event) {
        TerrainsList.sort(Comparator.comparing(Terrain::getPrix));

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
    }

}
