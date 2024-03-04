package com.example.sportify;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceTerrain;

public class ModifierTrController {

    private Terrain currentTerrain;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loclt;

    @FXML
    private Button modifiertr;

    @FXML
    private TextField nomt;

    @FXML
    private TextField prixt;

    @FXML
    private TextField typet;
    private Object previousController;

    public void setPreviousController(Object controller) {
        this.previousController = controller;
    }
    public void setTerrainData(Terrain terrain) {
        // Save the passed Terrain object
        this.currentTerrain = terrain;

        if (terrain != null) {
            nomt.setText(terrain.getNom());
            typet.setText(terrain.getType_surface());
            loclt.setText(terrain.getLocalisation());
            prixt.setText(String.valueOf(terrain.getPrix()));
        }
    }
    private void goBack() throws IOException {
        if (previousController instanceof AffichageTerrainController) {
            // Code to return to AffichageTerrainController
        } else if (previousController instanceof AdminTerrainController) {
            // Code to return to AdminTerrainController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminTerrain.fxml"));
            Parent root = loader.load();
            // If you have a reference to the original Stage, set the scene here
            // Otherwise, you might need to pass the Stage reference to this controller as well
            Stage stage = (Stage) nomt.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }
    @FXML
    void modifiertr(ActionEvent event) throws IOException{
        // Update the currentTerrain object with the new data from text fields
        currentTerrain.setNom(nomt.getText());
        currentTerrain.setType_surface(typet.getText());
        currentTerrain.setLocalisation(loclt.getText());
        currentTerrain.setPrix(Double.parseDouble(prixt.getText()));

        // Save the updated terrain to the database
        ServiceTerrain st = new ServiceTerrain();
        st.modifertr(currentTerrain); // Assuming 'modifertr' is the method to save the changes

        // After saving, transition back to the AffichageTerrainController view
        returnToTerrainListView();
    }

    private void returnToTerrainListView() throws IOException {
        // Code to return to the AffichageTerrainController view
        // This can be done by loading the FXML for AffichageTerrainController
        // and setting it as the root, similar to how you've navigated to this controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageTerrain.fxml"));
        Parent root = loader.load();
        nomt.getScene().setRoot(root);
    }
    @FXML
    void initialize() {
        assert loclt != null : "fx:id=\"loclt\" was not injected: check your FXML file 'ModifierTr.fxml'.";
        assert modifiertr != null : "fx:id=\"modifiertr\" was not injected: check your FXML file 'ModifierTr.fxml'.";
        assert nomt != null : "fx:id=\"nomt\" was not injected: check your FXML file 'ModifierTr.fxml'.";
        assert prixt != null : "fx:id=\"prixt\" was not injected: check your FXML file 'ModifierTr.fxml'.";
        assert typet != null : "fx:id=\"typet\" was not injected: check your FXML file 'ModifierTr.fxml'.";

    }

}
