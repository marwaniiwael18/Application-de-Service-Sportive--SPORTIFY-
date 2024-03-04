package com.example.sportify.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ClassementController {

    @FXML
    private AnchorPane interfacechange;
    private AnchorPane classementinterface;

    @FXML
    void EquipeButton(MouseEvent event) throws IOException {
        loadFXML("/com/example/sportify/rankedteam.fxml");
    }

    @FXML
    void SoloButton(MouseEvent event) throws IOException {
        loadFXML("/com/example/sportify/rankedsoloclassement.fxml");
    }

    private void loadFXML(String fxmlFilePath) throws IOException {
        // Clear the interfacechange anchor pane
        interfacechange.getChildren().clear();

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        classementinterface = fxmlLoader.load();

        // Add the loaded interface to the interfacechange anchor pane
        interfacechange.getChildren().add(classementinterface);
    }

}

