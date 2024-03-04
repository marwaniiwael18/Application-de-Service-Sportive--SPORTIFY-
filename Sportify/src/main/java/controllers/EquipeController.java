package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class EquipeController {

    @FXML
    private Button btnCheck;

    @FXML
    private Button btnCreate;

    @FXML
    private MenuButton menuSports;

    @FXML
    private Text text;

    @FXML
    private VBox vbox;

    @FXML
    private AnchorPane interfaceEquipe;

    @FXML
    void Check(ActionEvent event) {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/DashboardCategorieClient.fxml"));
            AnchorPane eventt = null;
            try {
                eventt = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            interfaceEquipe.getChildren().add(eventt) ;
        }



    @FXML
    void create(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CreationEquipe.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
