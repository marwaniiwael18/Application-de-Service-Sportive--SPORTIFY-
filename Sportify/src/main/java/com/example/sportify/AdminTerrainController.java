package com.example.sportify;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

import entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
import entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import services.ServiceTerrain;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AdminTerrainController {
    private final ServiceTerrain ST = new ServiceTerrain();
    @FXML
    private AnchorPane principal;
    @FXML
    private ObservableList<Terrain> TerrainsList;

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button Import_btn;

    @FXML
    private URL location;

    @FXML
    private TextField Searchfield;

    @FXML
    private Button aj;

    @FXML
    private ImageView image_id;

    @FXML
    private ListView<Terrain> listTerrain;

    @FXML
    private TextField loclt;

    @FXML
    private Button moditr;
    private Image image;

    @FXML
    private TextField nomm;

    @FXML
    private TextField nomt;

    @FXML
    private TextField prixt;

    @FXML
    private Button search;

    @FXML
    private Button supptr;
    private String imagePath;

    @FXML
    private Button tri;

    @FXML
    private Button triPrix;

    @FXML
    private TextField typet;

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


    private void loadTerrains() throws SQLException {
        ServiceTerrain serviceterrain = new ServiceTerrain();

        TerrainsList = FXCollections.observableArrayList(serviceterrain.afficher());
        listTerrain.setItems(TerrainsList);
    }

    @FXML
    void search(ActionEvent event) {
        String keyword = Searchfield.getText().toLowerCase();
        ObservableList<Terrain> filteredList = FXCollections.observableArrayList();

        for (Terrain terrain : TerrainsList) {
            if (terrain.getNom().toLowerCase().contains(keyword)) {
                filteredList.add(terrain);
            }
        }

        listTerrain.setItems(filteredList);
    }

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
    public void tri(ActionEvent event) {
        TerrainsList.sort(Comparator.comparing(Terrain::getNom));
    }


    @FXML
    void triPrix(ActionEvent event) {
        TerrainsList.sort(Comparator.comparing(Terrain::getPrix));
    }

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


    @FXML
    public void Import(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null); // Changed from principal.getScene().getWindow() to null for simplicity
        if (file != null) {
            imagePath = file.toURI().toString();
            image_id.setImage(new Image(imagePath));
        }
    }
}