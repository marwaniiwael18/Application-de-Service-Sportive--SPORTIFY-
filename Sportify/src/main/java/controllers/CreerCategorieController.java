package controllers;

import entities.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceCategorie;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class CreerCategorieController {

    @FXML
    private Button btnafficher;

    @FXML
    private Button btncreate;

    @FXML
    private MenuButton menuButton;

    @FXML
    private TextField tfDesc;

    @FXML
    private TextField tfImage;

    @FXML
    private Text tfNom;

    @FXML
    private TextField tfNomEquipe;

    @FXML
    private VBox vbox1;
    @FXML
    private Button btnChooseImage;

    @FXML
    private void initialize() {
        MenuItem defaultItem = menuButton.getItems().get(0);
        defaultItem.fire();
    }

    @FXML
    private void handleMenuItemAction(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();


        for (MenuItem item : menuButton.getItems()) {
            item.setStyle("-fx-text-fill: black;");
        }


        selectedItem.setStyle("-fx-text-fill: white;");
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        Stage stage = (Stage) btnChooseImage.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Get the selected file path
            String imageUrl = selectedFile.toURI().toString();
            // Set the text of the tfImage TextField with the selected file path
            tfImage.setText(imageUrl);
        }
    }


    @FXML
    void afficher(ActionEvent event) {
        try{
           Parent root = FXMLLoader.load(getClass().getResource("/FXML/DashboardCategorieAdmin.fxml"));
           tfNom.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void create(ActionEvent event) {
        try {
            ServiceCategorie serviceCategorie = new ServiceCategorie();
            System.out.println("*********");
            serviceCategorie.ajouter(new Categorie(tfNom.getText(), tfDesc.getText(), tfImage.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Categorie ajoutée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

}
